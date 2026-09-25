# JavaDoc Style Guide

## Status

Accepted

## Context

The public API JavaDoc of the SAP AI SDK lacked a consistent style.
Phrases were worded differently across modules, punctuation was inconsistent, and block tags appeared in varying orders.
This inconsistency creates a fragmented impression for SDK users reading the generated API documentation.

To enforce the style mechanically, the following Checkstyle rules are active in `.pipeline/checkstyle.xml`:

- `SummaryJavadoc` — first sentence must end with a period
- `AtclauseOrder` — block tags must appear in the order: `@param`, `@return`, `@throws`, `@see`, `@since`, `@deprecated`
- `NonEmptyAtclauseDescription` — every block tag must have a description
- `JavadocParagraph` — `<p>` tags must be correctly placed

## Decision

All public and protected JavaDoc follows the rules below.

---

### 1. General

* Write in **English**.
* Every JavaDoc comment — whether on a class, method, or field — must end its first (summary) sentence with a **period (`.`)**.
* **Summary sentences** (the first sentence of a class, method, or field comment) are full sentences: capitalized first word, ending with a period.
* **Block-tag descriptions** (`@param`, `@return`, `@throws`) are sentence fragments continuing the tag: lowercase first word, ending with a period.
* Keep the summary sentence on its own conceptual line.
  Further paragraphs are separated by a `<p>` tag placed at the **start** of the new paragraph. JavaDoc does not use a closing `</p>` — the tag acts as a paragraph separator, and the `JavadocParagraph` check enforces this.

```java
/**
 * Resolves a deployment ID for a given AI model.
 *
 * <p>If multiple deployments match, the first one is returned.
 */
```

---

### 2. Classes and interfaces

* The summary sentence describes **what the class/interface is**, not what it does.
* Use noun phrases: `"A client for..."`, `"Utility for..."`, `"Configuration of..."`.

```java
/** A client for sending requests to the Orchestration service. */
public class OrchestrationClient { ... }

/** Utility for managing MDC context for AI Core request logging. */
@UtilityClass
public class RequestLogContext { ... }
```

---

### 3. Methods

* The summary sentence describes **what the method does**, starting with a third-person singular verb.
* Use verb phrases: `"Resolves..."`, `"Sets..."`, `"Creates..."`, `"Builds..."`.

```java
/**
 * Resolves the deployment ID for the given model.
 *
 * @param resourceGroup the resource group, usually {@code "default"}.
 * @param model the AI model to resolve.
 * @return the deployment ID.
 * @throws DeploymentResolutionException if no running deployment is found.
 */
String getDeploymentId(String resourceGroup, AiModel model);
```

---

### 4. Fields and constants

* Use a short noun phrase ending with a period.

```java
/** The default resource group. */
public static final String DEFAULT_RESOURCE_GROUP = "default";
```

---

### 5. Block tags

Block tags must appear in this order: `@param`, `@return`, `@throws`, `@see`, `@since`, `@deprecated`.

A block-tag description reads as a fragment continuing the tag (e.g. "`@return` the deployment ID."), so its first word is **lowercase** — unlike a summary sentence, which is a full, capitalized sentence (see section 1). Proper nouns, class names, and inline tags (`{@link ...}`, `{@code ...}`) keep their original casing.

#### `@param`
* Lowercase first word, ending with a period.
* For generic type parameters, document them last among `@param` tags.

```java
@param destination the destination used for AI Core service calls.
@param <T> the type of the successful response.
```

#### `@return`
* Lowercase first word, ending with a period.
* Do not write `"Returns ..."` — the tag already implies it.

```java
@return the deployment ID.
@return the current instance for chaining.
```

#### `@throws`
* Lowercase first word, ending with a period.
* Start with `"if ..."` to describe the condition.

```java
@throws DeploymentResolutionException if no running deployment is found for the model.
```

#### `@deprecated`
* For API replacements: `"Use {@link X} instead."`
* For deprecated AI models on AI Core without a known retirement date: `"This model is deprecated on AI Core."`
* For deprecated AI models with a retirement date and replacement: `"This model is deprecated on AI Core with a planned retirement on YYYY-MM-DD. Use {@link X} instead."`

```java
// API replacement
@deprecated Use {@link #chatCompletion(OpenAiChatCompletionRequest)} instead.

// Model deprecation without retirement date
@deprecated This model is deprecated on AI Core.

// Model deprecation with retirement date and replacement
@deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-01. Use {@link OpenAiModel#GPT_4O} instead.
```

#### `@since`
* Provide the version in which the API was introduced, using the format `major.minor.patch`.

```java
@since 1.4.0
```

---

### 6. Inline tags

* Use `{@link ClassName}` or `{@link ClassName#method()}` to reference navigable types and methods.
* Use `{@code value}` for literals, string values, primitive values, or code snippets that should not be a hyperlink.
* Do not use `{@linkplain}`.

```java
// Correct
Use {@link AiCoreService} to obtain a destination.
The default value is {@code "default"}.

// Incorrect
Use {@linkplain AiCoreService} to obtain a destination.
```

---

### 7. Notes and important remarks

Use `<p><b>Note:</b>` for supplementary remarks that do not belong in the summary sentence.

```java
// Correct
/**
 * Sets a custom base destination.
 *
 * <p><b>Note:</b> The destination is expected to have the {@code /v2/} base path set.
 *
 * @param destination the base destination.
 * @return a new instance using the provided destination.
 */
```

Do not use `NOTE:`, `<strong>Note:</strong>`, `<b>NOTE:</b>`, or plain `Note:` without HTML tags.

```java
// Incorrect
/**
 * Sets a custom base destination.
 *
 * <p>NOTE: The destination is expected to have the {@code /v2/} base path set.
 */
```

---

### 8. Code examples

Use `<pre>{@code ... }</pre>` for multi-line code examples.

```java
/**
 * Creates a client with a custom destination.
 *
 * <p>Example:
 *
 * <pre>{@code
 * var client = new OrchestrationClient(
 *     new AiCoreService().getInferenceDestination("my-rg").forScenario("orchestration"));
 * }</pre>
 *
 * @param destination the specific destination to use.
 */
```

---

### 9. What not to document

* Do not add JavaDoc to `@Override` methods — they inherit the parent's documentation.
* Do not add JavaDoc to generated code (OpenAPI-generated classes are excluded via `checkstyle-suppressions.xml`).
* Do not repeat the method or field name in the summary sentence.

```java
// Correct
/** The name of the model as registered in AI Core. */
String getModelName();

// Incorrect
/** Gets the model name. */
String getModelName();
```

---

### 10. Canonical phrases

Use the exact wording below wherever these situations apply, to ensure consistent phrasing across all modules.

#### Resource group parameter

When the resource group has no context-specific meaning, use:

```java
@param resourceGroup the resource group, usually {@code "default"}.
```

When the context is more specific, a tailored description is acceptable (e.g. `"the resource group of the deleted deployment"`).

#### Nullable parameters

Use `{@code null}` when referencing the null value inline. There is no JavaDoc-standard for nullability, so we follow the `{@code null}` convention for consistency with other inline code references. Write it as a second sentence within the `@param` description — no new line.

```java
// Correct
@param cause an optional cause of the exception. Can be {@code null} if not applicable.

// Incorrect
@param cause an optional cause of the exception, can be null if not applicable.
@param cause nullable cause.
```

#### Internal-use APIs

APIs that are part of the public class structure but not intended for use by SDK consumers must carry the following sentence as a separate `<p>` paragraph. Use exactly `"For internal use only."` — no variations such as `"intended for internal use"` or `"only for internal usage"`.

```java
// Correct
/**
 * Parses incoming JSON responses and handles errors.
 *
 * <p>For internal use only.
 */
public class ClientResponseHandler<T, R, E> { ... }

// Incorrect
/**
 * Parses incoming JSON responses and handles errors.
 *
 * <p>This class is intended for internal use only.
 */
public class ClientResponseHandler<T, R, E> { ... }
```
