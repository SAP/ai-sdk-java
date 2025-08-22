/**
 * Spectral rule function to validate oneOf object distinguishability for Java SDK deserialization.
 * 
 * This function ensures that oneOf schemas containing object types can be properly distinguished
 * during deserialization by checking for unique required properties or discriminators.
 * 
 * @param {Array} oneOfArray - The oneOf array from the OpenAPI specification
 * @param {Object} opts - Options passed to the function (unused)
 * @param {Object} context - Spectral context containing document and path information
 * @returns {Array} Array of validation errors, empty if validation passes
 */
export default function oneOfObjectDistinguishability(oneOfArray, opts, context) {
  // Early validation - ensure we have a valid oneOf array
  if (!Array.isArray(oneOfArray) || oneOfArray.length < 2) {
    return [];
  }

  const { document, path } = context;

  try {
    // Check if parent schema has discriminator (automatic pass)
    if (hasDiscriminator(path, document)) {
      return [];
    }

    // Resolve and filter to object schemas only
    const objectSchemas = oneOfArray
      .map((schema, index) => ({ ...resolveSchemaReference(schema, document), index }))
      .filter(isObjectSchema);

    // Skip if fewer than 2 object schemas
    if (objectSchemas.length < 2) {
      return [];
    }

    // Find non-distinguishable schemas and return errors
    const errors = [];
    for (const [currentIndex, currentSchema] of objectSchemas.entries()) {
      const currentRequired = new Set(currentSchema.required || []);
      
      for (const otherSchema of objectSchemas.slice(currentIndex + 1)) {
        const otherRequired = new Set(otherSchema.required || []);
        
        // Check if schemas cannot be distinguished
        const cannotDistinguish = !hasUniqueRequiredProperties(currentRequired, otherRequired) && 
                                  !hasUniqueRequiredProperties(otherRequired, currentRequired);
        
        if (cannotDistinguish) {
          const errorMessage = `Cannot distinguish oneOf option ${currentSchema.index} from option ${otherSchema.index}. Add discriminator or unique required properties.`;
          errors.push({ message: errorMessage, path: [...path] });
          break; // Only report first conflict per schema
        }
      }
    }

    return errors;

  } catch (error) {
    // Gracefully handle any errors during validation
    console.warn(`oneOfObjectDistinguishability validation error: ${error.message}`);
    return [];
  }
}

/**
 * Checks if the parent schema has a discriminator property
 */
function hasDiscriminator(path, document) {
  if (path.length === 0) return false;
  
  const parentPath = path.slice(0, -1);
  const parentSchema = parentPath.reduce((obj, key) => obj?.[key], document.data || document);
  
  return parentSchema?.discriminator;
}

/**
 * Resolves a schema reference if it's a $ref, otherwise returns the schema as-is
 */
function resolveSchemaReference(schema, document) {
  if (!schema?.$ref) {
    return schema || {};
  }

  try {
    const segments = schema.$ref.slice(2).split("/")
      .map(s => s.replace(/~1/g, "/").replace(/~0/g, "~"));
    
    return segments.reduce((obj, seg) => obj?.[seg], document.data || document) || {};
  } catch {
    return {};
  }
}

/**
 * Determines if a schema represents an object type
 */
function isObjectSchema(schema) {
  // Explicit object type
  if (schema.type === 'object') {
    return true;
  }

  // Has object-like properties
  if (schema.properties || schema.required) {
    return true;
  }

  // Exclude primitive types and distinguishable schemas
  if (schema.type || schema.enum || schema.const) {
    return false;
  }

  // Default to object for untyped schemas (conservative approach)
  return true;
}

/**
 * Checks if a schema has at least one required property that others don't have
 */
function hasUniqueRequiredProperties(schemaRequired, otherRequired) {
  for (const prop of schemaRequired) {
    if (!otherRequired.has(prop)) {
      return true;
    }
  }
  return false;
}
