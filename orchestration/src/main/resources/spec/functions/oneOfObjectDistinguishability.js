import { createRulesetFunction } from "@stoplight/spectral-core";

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
export default createRulesetFunction(
  {
    input: {
      type: "array",
      minItems: 2
    },
    options: null
  },
  function oneOfObjectDistinguishability(oneOfArray, opts, context) {
  try {
    // Check if parent schema has discriminator (automatic pass)
    if (hasDiscriminator(context.path, context.document)) {
      return [];
    }

    // Resolve and filter to object schemas only
    const objectSchemas = oneOfArray
      .map((schema, index) => ({
        ...resolveSchemaReference(schema, context.document),
        index
      }))
      .filter(isObjectSchema);

    // Skip if fewer than 2 object schemas
    if (objectSchemas.length < 2) {
      return [];
    }

    // Find non-distinguishable schemas using O(n) approach
    const propertySignatures = new Map(); // signature -> [schema objects]
    
    // Build property signature map in single pass
    for (const schema of objectSchemas) {
      const required = schema.required || [];
      const signature = required.toSorted().join(',');
      
      if (!propertySignatures.has(signature)) {
        propertySignatures.set(signature, []);
      }
      propertySignatures.get(signature).push(schema);
    }
    
    // Find conflicts - any signature with multiple schemas
    const errors = [];
    for (const [signature, schemas] of propertySignatures) {
      if (schemas.length > 1) {
        // These schemas are indistinguishable - report all conflicting schemas
        const indices = schemas.map(schema => schema.index).join(', ');
        const errorMessage = `Cannot distinguish oneOf options {${indices}}. Add discriminator or ensure unique required properties.`;
        errors.push({ message: errorMessage, path: [...context.path] });
      }
    }

    return errors;

  } catch (error) {
    // Gracefully handle any errors during validation
    console.warn(`oneOfObjectDistinguishability validation error: ${error.message}`);
    return [];
  }
  }
);

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
