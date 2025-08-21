// Spectral custom function: ensure oneOf object options are distinguishable during deserialization
export default function oneOfObjectDistinguishability(targetVal, opts, context) {
  if (!Array.isArray(targetVal) || targetVal.length < 2) return [];
  
  const parent = getAtPath(context.document, context.path.slice(0, -1));
  if (parent?.discriminator) return [];
  
  if (!isInResponseContext(context)) return [];
  
  const objectOptions = targetVal
    .map((option, index) => {
      const resolved = resolveSchema(option, context.document);
      const isObject = resolved?.type === "object" || 
                      (!resolved?.type && (resolved?.properties || resolved?.required));
      
      return isObject ? {
        index,
        required: resolved.required?.filter(p => typeof p === "string") || [],
        name: option?.$ref?.split("/").pop() || resolved.title?.trim() || `option ${index}`
      } : null;
    })
    .filter(Boolean);
  
  if (objectOptions.length <= 1) return [];
  
  // Count required property frequencies
  const counts = new Map();
  objectOptions.forEach(opt => 
    opt.required.forEach(prop => counts.set(prop, (counts.get(prop) || 0) + 1))
  );
  
  // Find options without unique required properties
  return objectOptions
    .filter(opt => !opt.required.some(prop => counts.get(prop) === 1))
    .map(opt => ({
      message: `Cannot distinguish oneOf option '${opt.name}' from [${
        objectOptions.filter(o => o !== opt).map(o => o.name).join(", ")
      }]. Add discriminator or unique required properties.${
        opt.required.filter(p => counts.get(p) > 1).length ? 
        ` Shared: ${opt.required.filter(p => counts.get(p) > 1).join(", ")}.` : ""
      }`,
      path: [...context.path, opt.index]
    }));
}

function isInResponseContext(context) {
  const path = context.path;
  if (path.includes("responses")) return true;
  
  const schemaName = extractSchemaName(path);
  return schemaName ? isSchemaUsedInResponses(context.document, schemaName) : false;
}

function extractSchemaName(path) {
  const i = path.indexOf("components");
  return i !== -1 && path[i + 1] === "schemas" ? path[i + 2] : null;
}

function resolveSchema(schemaOrRef, document) {
  if (!schemaOrRef?.$ref) return schemaOrRef || {};
  
  try {
    const segments = schemaOrRef.$ref.slice(2).split("/")
      .map(s => s.replace(/~1/g, "/").replace(/~0/g, "~"));
    return segments.reduce((obj, seg) => obj?.[seg], document);
  } catch {
    return { type: "object", required: [] };
  }
}

function getAtPath(obj, pathArray) {
  return pathArray.reduce((current, segment) => 
    current && typeof current === "object" ? current[segment] : undefined, obj);
}

// Response context detection with caching
const responseSchemaCache = new WeakMap();

function isSchemaUsedInResponses(document, schemaName) {
  if (!responseSchemaCache.has(document)) {
    const schemas = new Set();
    try {
      Object.values(document.paths || {}).forEach(pathItem => 
        Object.values(pathItem || {}).forEach(operation => {
          if (operation?.responses) collectSchemaRefs(operation.responses, schemas);
        })
      );
      responseSchemaCache.set(document, schemas);
    } catch {
      return true; // On error, assume schema is used
    }
  }
  
  return responseSchemaCache.get(document)?.has(schemaName) ?? true;
}

function collectSchemaRefs(obj, schemas) {
  if (!obj || typeof obj !== "object") return;
  
  if (obj.$ref?.startsWith("#/components/schemas/")) {
    schemas.add(obj.$ref.split("/").pop());
  }
  
  Object.values(obj).forEach(value => collectSchemaRefs(value, schemas));
}
