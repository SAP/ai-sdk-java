/**
 * Spectral rule function to validate oneOf object distinguishability for Java SDK deserialization.
 * 
 * This function ensures that oneOf schemas containing object types can be properly distinguished
 * during deserialization by checking for unique required properties or discriminators.
 * 
 * @param {Array} targetVal - The oneOf array from the OpenAPI specification
 * @param {Object} opts - Options passed to the function (unused)
 * @param {Object} context - Spectral context containing document and path information
 * @returns {Array} Array of validation errors, empty if validation passes
 */
export default function oneOfObjectDistinguishability(targetVal, opts, context) {
  // Early validation - ensure we have a valid oneOf array
  if (!Array.isArray(targetVal) || targetVal.length < 2) {
    return [];
  }

  const { document, path } = context;
  const errors = [];

  try {
    // Check if this oneOf is in a response context
    if (!isInResponseContext(path, document)) {
      return [];
    }

    // Check if parent schema has discriminator (automatic pass)
    if (hasDiscriminator(path, document)) {
      return [];
    }

    // Resolve and analyze schemas in the oneOf
    const resolvedSchemas = resolveOneOfSchemas(targetVal, document);
    const objectSchemas = filterObjectSchemas(resolvedSchemas);

    // Skip if fewer than 2 object schemas
    if (objectSchemas.length < 2) {
      return [];
    }

    // Check distinguishability
    const nonDistinguishableSchemas = findNonDistinguishableSchemas(objectSchemas);

    // Generate errors for non-distinguishable schemas
    nonDistinguishableSchemas.forEach(({ schema, conflictingSchemas }) => {
      errors.push({
        message: createErrorMessage(schema, conflictingSchemas),
        path: [...path, schema.index]
      });
    });

  } catch (error) {
    // Gracefully handle any errors during validation
    console.warn(`oneOfObjectDistinguishability validation error: ${error.message}`);
    return [];
  }

  return errors;
}

/**
 * Determines if a oneOf schema is used in a response context.
 * Checks both direct usage under responses and indirect usage via component references.
 */
function isInResponseContext(path, document) {
  // Direct response context: anywhere under paths.*.*.responses
  const pathStr = path.join('.');
  if (pathStr.includes('paths.') && pathStr.includes('.responses.')) {
    return true;
  }

  // Indirect response context: check if schema is referenced by responses
  if (pathStr.startsWith('components.schemas.')) {
    const schemaName = extractSchemaNameFromPath(path);
    if (schemaName) {
      return isSchemaReferencedInResponses(schemaName, document);
    }
  }

  return false;
}

/**
 * Extracts schema name from a components.schemas path
 */
function extractSchemaNameFromPath(path) {
  const schemasIndex = path.indexOf('schemas');
  if (schemasIndex !== -1 && schemasIndex + 1 < path.length) {
    return path[schemasIndex + 1];
  }
  return null;
}

/**
 * Checks if a schema is referenced anywhere in response definitions
 */
function isSchemaReferencedInResponses(schemaName, document) {
  const responses = document.data?.paths;
  if (!responses) return false;

  const schemaRef = `#/components/schemas/${schemaName}`;
  
  // Recursively search for schema references in responses
  return searchForSchemaReference(responses, schemaRef);
}

/**
 * Recursively searches for schema references in an object
 */
function searchForSchemaReference(obj, targetRef) {
  if (!obj || typeof obj !== 'object') return false;

  if (Array.isArray(obj)) {
    return obj.some(item => searchForSchemaReference(item, targetRef));
  }

  // Check if this object has a $ref that matches our target
  if (obj.$ref === targetRef) {
    return true;
  }

  // Check if we're in a response context
  if (obj.responses) {
    return searchForSchemaReference(obj.responses, targetRef);
  }

  // Recursively search all object properties
  return Object.values(obj).some(value => searchForSchemaReference(value, targetRef));
}

/**
 * Checks if the parent schema has a discriminator property
 */
function hasDiscriminator(path, document) {
  // Look for discriminator at the parent level (one level up from oneOf)
  if (path.length === 0) return false;
  
  const parentPath = path.slice(0, -1);
  const parentSchema = getValueAtPath(document.data, parentPath);
  
  return parentSchema && parentSchema.discriminator;
}

/**
 * Gets a value from an object using a path array
 */
function getValueAtPath(obj, path) {
  return path.reduce((current, key) => {
    return current && current[key];
  }, obj);
}

/**
 * Resolves all schemas in the oneOf array, handling $ref references
 */
function resolveOneOfSchemas(oneOfArray, document) {
  return oneOfArray.map((schema, index) => {
    const resolved = resolveSchemaReference(schema, document);
    return {
      ...resolved,
      index,
      originalSchema: schema
    };
  });
}

/**
 * Resolves a schema reference if it's a $ref, otherwise returns the schema as-is
 */
function resolveSchemaReference(schema, document) {
  if (!schema.$ref) {
    return schema;
  }

  // Handle #/components/schemas/SchemaName references
  if (schema.$ref.startsWith('#/components/schemas/')) {
    const schemaName = schema.$ref.replace('#/components/schemas/', '');
    const resolvedSchema = document.data?.components?.schemas?.[schemaName];
    return resolvedSchema || schema;
  }

  // For other reference types, return as-is (could be extended)
  return schema;
}

/**
 * Filters schemas to only include object-type schemas
 */
function filterObjectSchemas(schemas) {
  return schemas.filter(schema => isObjectSchema(schema));
}

/**
 * Determines if a schema represents an object type
 */
function isObjectSchema(schema) {
  // Explicit object type
  if (schema.type === 'object') {
    return true;
  }

  // No explicit type but has object-like properties
  if (!schema.type && (schema.properties || schema.required)) {
    return true;
  }

  // Exclude primitive types
  if (schema.type && ['string', 'number', 'integer', 'boolean', 'array'].includes(schema.type)) {
    return false;
  }

  // Exclude schemas with enum or const (always distinguishable)
  if (schema.enum || schema.const) {
    return false;
  }

  // If no explicit type and no object indicators, assume it could be an object
  // This is a conservative approach to catch potential object schemas
  return !schema.type;
}

/**
 * Finds schemas that cannot be distinguished from others in the oneOf
 */
function findNonDistinguishableSchemas(objectSchemas) {
  const nonDistinguishable = [];

  for (let i = 0; i < objectSchemas.length; i++) {
    const schema = objectSchemas[i];
    const schemaRequired = new Set(schema.required || []);
    const conflictingSchemas = [];

    // Compare with all other schemas
    for (let j = 0; j < objectSchemas.length; j++) {
      if (i === j) continue;

      const otherSchema = objectSchemas[j];
      const otherRequired = new Set(otherSchema.required || []);

      // Check if schemas share all required properties
      const hasUniqueRequired = hasUniqueRequiredProperties(schemaRequired, otherRequired);
      
      if (!hasUniqueRequired) {
        conflictingSchemas.push(otherSchema);
      }
    }

    // If schema conflicts with others, mark as non-distinguishable
    if (conflictingSchemas.length > 0) {
      nonDistinguishable.push({
        schema,
        conflictingSchemas
      });
    }
  }

  return nonDistinguishable;
}

/**
 * Checks if a schema has at least one required property that others don't have
 */
function hasUniqueRequiredProperties(schemaRequired, otherRequired) {
  // Schema has unique required properties if it has any required property
  // that the other schema doesn't have
  for (const prop of schemaRequired) {
    if (!otherRequired.has(prop)) {
      return true;
    }
  }
  
  return false;
}

/**
 * Creates a detailed error message for non-distinguishable schemas
 */
function createErrorMessage(schema, conflictingSchemas) {
  const schemaName = getSchemaName(schema);
  const conflictingNames = conflictingSchemas.map(s => getSchemaName(s)).join(', ');
  
  return `oneOf object schema '${schemaName}' cannot be distinguished from: ${conflictingNames}. Consider adding a discriminator property or unique required properties to each schema.`;
}

/**
 * Gets a human-readable name for a schema
 */
function getSchemaName(schema) {
  // If it's a reference, extract the schema name
  if (schema.originalSchema?.$ref) {
    const ref = schema.originalSchema.$ref;
    if (ref.startsWith('#/components/schemas/')) {
      return ref.replace('#/components/schemas/', '');
    }
    return ref;
  }

  // If it has a title, use that
  if (schema.title) {
    return schema.title;
  }

  // Fallback to index-based naming
  return `Schema[${schema.index}]`;
}
