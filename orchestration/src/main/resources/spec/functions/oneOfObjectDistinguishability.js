import { createRulesetFunction } from "@stoplight/spectral-core";

/**
 * Spectral rule function to validate oneOf object distinguishability for Java SDK deserialization.
 * Spectral finds each oneOf in your document and calls the function once per oneOf.
 * 
 * This function ensures that oneOf schemas containing object types can be properly distinguished
 * during deserialization by checking for unique required properties or discriminators.
 * 
 * @param {Array} oneOfs - The oneOf array from the OpenAPI specification
 * @param {Object} opts - Options passed to the function (unused)
 * @param {Object} context - Spectral context containing document and path information
 * @returns {Array} Array of conflicts, empty if validation passes
 */
export default createRulesetFunction(
  {
    input: {
      type: "array",
      minItems: 2
    },
    options: null
  },
  function oneOfObjectDistinguishability(oneOfs, opts, context) {
  try {
    if (hasDiscriminator(context.path, context.document)) {
      return [];
    }

    // Resolve $refs, track index and filter to object schemas only
    const objectSchemas = oneOfs
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
    
    // Build signature map - group schemas by their required properties
    for (const schema of objectSchemas) {
      const required = schema.required || [];
      const signature = required.toSorted().join(',');
      
      if (!propertySignatures.has(signature)) {
        propertySignatures.set(signature, []);
      }
      propertySignatures.get(signature).push(schema);
    }
    
    // Find conflicts - any signature with multiple schemas
    const conflicts = [];
    for (const [signature, schemas] of propertySignatures) {
      if (schemas.length > 1) {
        // These schemas are indistinguishable - report all conflicting schemas
        const indices = schemas.map(schema => schema.index).join(', ');
        const errorMessage = `Cannot distinguish oneOf options {${indices}}. Add discriminator or ensure unique required properties.`;
        conflicts.push({ message: errorMessage, path: [...context.path] });
      }
    }

    return conflicts;

  } catch (error) {
    // Gracefully handle any conflicts during validation
    console.warn(`oneOfObjectDistinguishability validation error: ${error.message}`);
    return [];
  }
  }
);

/**
 * Checks if the parent schema has a discriminator property
 */
function hasDiscriminator(path, document) {

  // Example path: ["components", "schemas", "Animal", "properties", "type", "oneOf"]
  if (path.length === 0) return false;
  
  // Navigate to parent schema (one level up from oneOf)
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
    const segments = schema.$ref.slice(2).split("/");
    
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
