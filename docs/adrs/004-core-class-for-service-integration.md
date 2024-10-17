# Core class for service integration

Status: In-Progress.


## Background

The integration with AI Core is based on multiple layers of API.
Depending on the usage scenario the user wants to interact with (1) AI Core, (2) foundation models, or (3) the orchestration service.
API design and expected behavior heavily depend on the use case and functional- and non-functional requirements.
When evaluating use cases we have to consider _nice_ users following best-practices with the same priority as _naughty_ users who do not follow recommendations nor read JavaDoc.

Our product is the API.

----

## API design requirements

Let's distinguish between functional and non-functional requirements.

### Non-Functional requirements

* **Stability**
  * The API should be stable upon release.
  * Additions and extensions are allowed. 
  * Breaking changes to existing methods are not allowed.
* **Consistency**:
  * The API should be reasonable consistent across all available service integrations.
  * This includes naming conventions, method signatures, and compatible return types.
* **Usability**:
  * The API should be easy to use and understand.
  * The user is expected to mostly auto-complete the code.
    Therefore fluent API design is expected.
  * The user expects that all available methods may lead to a reasonable outcome.
  * The user expects logic to be customizable.
  * General code guidelines apply like JavaDoc, method names, and parameter count and -names.
* **Simplicity**:
  * The core API should not (immediately) expose any internal methods or classes. 
  * No ambiguity.
    No repetition and redundant data.
  * Low hierarchy of API, i.e. deep nesting of classes is not required.
  * Object immutability is preferred.
* **Transparency**:
  * The API should be transparent about its behavior.
  * Use correct method name prefix to indicate actions, e.g. `get`, `list`, `resolve`, `update`.


SAP Cloud SDK flavored requirements:
* Consider public interfaces over specific classes to be exposed to user.
  This makes implementations easier to extend and replace in the future.

### Functional requirements

* **Optimization**
  * Avoid redundant operations that cost heavy resources like time or memory, e.g. HTTP requests.
  * Avoid premature computation of data.
  * When evaluating, consider multi-threading and parallelism.
    For shared objects, consider thread-safety.
* **Documentation**
  * Document public API usage in our repository.
  * Document best-practices and recommended API usage.
  * Document pitfalls and bad-practices.
  * Document error messages and solutions.

SAP Cloud SDK flavored requirements:
* Log activity, intermediate results, fallbacks and errors.


## State of core class

| API                                                                            | Behavior                                                                                                                                     |
|--------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `new AiCoreService()`                                                          | <ul><li>&#x2610; Resolve Service Binding / Destination</li><li>&#x2610; Resolve Deployments (cached)</li><li>&#x2610; Instantiate Cache</li></ul> |
| `withDestination()`                                                            | <ul><li>&#x2610; Resolve Service Binding / Destination</li><li>&#x2610; Resolve Deployments (cached)</li><li>&#x2610; Instantiate Cache</li></ul> |
| `forDeployment()`<br> `forDeploymentByModel()`<br> `forDeploymentbyScenario()` | <ul> <li>&#x2610; Resolve Service Binding / Destination</li> <li>&#x2610; Resolve Deployments (cached)</li> <li>&#x2612; Instantiate Cache</li> </ul>            |
| `withResourceGroup()`                                                          | <ul> <li>&#x2610; Resolve Service Binding / Destination</li> <li>&#x2610; Resolve Deployments (cached)</li> <li>&#x2612; Instantiate Cache</li> </ul>            |
| `client()`                                                                     | <ul> <li>&#x2612; Resolve Service Binding / Destination</li> <li>&#x2612; Resolve Deployments (cached)</li> <li>&#x2610; Instantiate Cache</li> </ul>            |
| `destination()`                                                                | <ul> <li>&#x2612; Resolve Service Binding / Destination</li> <li>&#x2612; Resolve Deployments (cached)</li> <li>&#x2610; Instantiate Cache</li> </ul>            |

Properties:
* Lazy evaluation of deployments
* Lazy evaluation of service binding (if no destination is provided)
* Service Binding lookup itself is not cached by AI SDK itself, but by Service Binding library.
* Immutable objects.
* Custom method overloads on (sub-classes of) `AiCoreService` are propagated down to the client.

Pro:
- No redundant HTTP traffic.
- Slim API contract, not many public methods.

Con:
- Allowing for Resource Group declaration **only** on deployment level requires API separation.
  Two classes are necessary, where one class would've been better.
- High maintainability cost.
  Extending or changing the API and its inherent behavior will likely require a complete implementation rewrite.

## Decision

- postponed for now