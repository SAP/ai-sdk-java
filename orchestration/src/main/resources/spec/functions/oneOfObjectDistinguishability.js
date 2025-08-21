export default function oneOfObjectDistinguishability(targetVal, opts, context) {
  if (!isValidOneOf(targetVal)) {
    return [];
  }

  if (hasDiscriminator(context)) {
    return []; // Valid - discriminator handles distinguishability
  }

  // NEW: Only check oneOf schemas that are used in response contexts (deserialization)
  if (!isInResponseContext(context)) {
    return []; // Skip if only used in requests (serialization-only)
  }

  const objectSchemas = extractObjectSchemas(targetVal, context);
  
  if (objectSchemas.length <= 1) {
    return []; // Valid - need at least 2 object schemas to have conflicts
  }

return validateObjectDistinguishability(objectSchemas, context);
};

// Helper functions for better readability

function isValidOneOf(oneOfArray) {
  return Array.isArray(oneOfArray) && oneOfArray.length >= 2;
}

function hasDiscriminator(context) {
  const parent = context.path[context.path.length - 2];
  return parent && parent.discriminator;
}

function extractObjectSchemas(oneOfArray, context) {
  const PRIMITIVE_TYPES = ['string', 'number', 'integer', 'boolean', 'array'];
  const objectSchemas = [];

  for (let i = 0; i < oneOfArray.length; i++) {
    const option = oneOfArray[i];
    const resolvedSchema = resolveSchemaRef(option, context.document);
    
    if (isPrimitiveType(resolvedSchema, PRIMITIVE_TYPES)) {
      continue; // Skip primitives - they're auto-distinguishable
    }

    objectSchemas.push({
      index: i,
      schema: resolvedSchema,
      required: resolvedSchema.required || []
    });
  }

  return objectSchemas;
}

function isPrimitiveType(schema, primitiveTypes) {
  return schema.type && primitiveTypes.includes(schema.type);
}

function resolveSchemaRef(schema, document) {
  if (!schema.$ref) {
    return schema;
  }

  try {
    return resolveRef(schema.$ref, document);
  } catch (e) {
    // If ref resolution fails, treat as object for safety
    return { type: 'object', required: [] };
  }
}

function validateObjectDistinguishability(objectSchemas, context) {
  const errors = [];
  
  // Get schema names for better error messages
  const schemaNames = objectSchemas.map(schema => getSchemaName(schema, context));
  
  for (const objectSchema of objectSchemas) {
    if (!hasUniqueRequiredProperty(objectSchema, objectSchemas)) {
      errors.push(createDistinguishabilityError(objectSchema, objectSchemas, schemaNames, context));
    }
  }
  return errors;
}

function hasUniqueRequiredProperty(targetSchema, allSchemas) {
  // Check if any required property exists only in this schema
  for (const requiredProp of targetSchema.required) {
    if (isRequiredPropertyUniqueToSchema(requiredProp, targetSchema, allSchemas)) {
      return true;
    }
  }
  
  return false;
}

function isRequiredPropertyUniqueToSchema(property, targetSchema, allSchemas) {
  return allSchemas
    .filter(schema => schema !== targetSchema)
    .every(otherSchema => !otherSchema.required.includes(property));
}

function createDistinguishabilityError(objectSchema, allSchemas, schemaNames, context) {
  const currentSchemaName = schemaNames[objectSchema.index] || `option ${objectSchema.index}`;
  const conflictingSchemas = allSchemas
    .filter(schema => schema !== objectSchema)
    .map((schema, idx) => schemaNames[schema.index] || `option ${schema.index}`);
  
  let message = `Cannot distinguish oneOf option '${currentSchemaName}' from options`;
  
  if (conflictingSchemas.length > 0) {
    message += ` (${conflictingSchemas.join(', ')})`;
  }
  
  message += '. Either add a discriminator property, or ensure each schema has unique required properties';
  
  // Return in the format Spectral expects for custom function errors
  return {
    message: message
  };
}

function getSchemaName(objectSchema, context) {
  // Try to get the original oneOf array from the document
  let current = context.document;
  
  // Navigate to the oneOf location using the context path
  for (let i = 0; i < context.path.length; i++) {
    if (current && typeof current === 'object') {
      current = current[context.path[i]];
    }
  }
  
  // current should now be the oneOf array
  if (Array.isArray(current) && current[objectSchema.index]) {
    const option = current[objectSchema.index];
    if (option.$ref) {
      // Extract schema name from $ref like "#/components/schemas/SystemChatMessage"
      const refParts = option.$ref.split('/');
      return refParts[refParts.length - 1];
    }
  }
  
  return null;
}

function getSharedRequiredProperties(targetSchema, allSchemas) {
  const sharedProps = [];
  
  for (const prop of targetSchema.required) {
    const isShared = allSchemas
      .filter(schema => schema !== targetSchema)
      .some(otherSchema => otherSchema.required.includes(prop));
    
    if (isShared) {
      sharedProps.push(prop);
    }
  }
  
  return sharedProps;
}

// NEW: Function to determine if a oneOf is in a response context (needs deserialization)
function isInResponseContext(context) {
  const pathString = context.path.join('.');
  
  // Direct response schema - definitely needs deserialization
  if (pathString.includes('responses')) {
    return true;
  }
  
  // Component schema - need to trace where it's used
  if (pathString.includes('components.schemas')) {
    return isSchemaUsedInResponses(context);
  }
  
  // Default to false for other contexts (like requestBody)
  return false;
}

// Function to check if a component schema is referenced in any response
function isSchemaUsedInResponses(context) {
  const document = context.document;
  
  // Extract the schema name from the path
  const schemaName = getSchemaNameFromPath(context.path);
  if (!schemaName) {
    return true; // If we can't determine, err on the side of caution
  }
  
  // Search for references to this schema in responses
  return findSchemaUsageInResponses(document, schemaName);
}

// Extract schema name from a path like ['components', 'schemas', 'ChatMessage', 'oneOf']
function getSchemaNameFromPath(path) {
  const componentsIndex = path.indexOf('components');
  const schemasIndex = path.indexOf('schemas');
  
  if (componentsIndex !== -1 && schemasIndex === componentsIndex + 1 && path.length > schemasIndex + 1) {
    return path[schemasIndex + 1];
  }
  
  return null;
}

// Recursively search for schema references in responses
function findSchemaUsageInResponses(document, schemaName) {
  const schemaRef = `#/components/schemas/${schemaName}`;
  
  // Check all paths and their responses
  if (document.paths) {
    for (const pathKey in document.paths) {
      const pathItem = document.paths[pathKey];
      
      for (const method in pathItem) {
        const operation = pathItem[method];
        
        if (operation && operation.responses) {
          if (containsSchemaRef(operation.responses, schemaRef)) {
            return true;
          }
        }
      }
    }
  }
  
  return false;
}

// Recursively check if an object contains a reference to the schema
function containsSchemaRef(obj, schemaRef) {
  if (!obj || typeof obj !== 'object') {
    return false;
  }
  
  // Check if this object has the $ref we're looking for
  if (obj.$ref === schemaRef) {
    return true;
  }
  
  // Recursively check all properties
  for (const key in obj) {
    if (containsSchemaRef(obj[key], schemaRef)) {
      return true;
    }
  }
  
  return false;
}

// Utility function to resolve $ref paths
function resolveRef(ref, document) {
  if (!ref.startsWith('#/')) {
    throw new Error('Only internal refs supported');
  }
  
  const pathSegments = ref.substring(2).split('/');
  let current = document;
  
  for (const segment of pathSegments) {
    if (!current || typeof current !== 'object') {
      throw new Error('Invalid ref path');
    }
    current = current[segment];
  }
  
  return current;
}
