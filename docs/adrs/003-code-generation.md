# Code Generation Strategy

Status: Draft, currently on hold. Requires further research into [existing OpenAPI tooling](https://openapi.tools/). 

## Background

The capabilities of AI Core, including the orchestration service, and many foundation models are described using OpenAPI specifications.
With the code generators of the SAP Cloud SDK (adapted versions of the Open Source OpenAPI generators) we can generate client side Java and TS/JS code.

We can distinguish:

- APIs of foundation models
    - Here we have virtually no control over the spec
    - The specs sometimes contain objects not relevant for us
- The AI Core API
    - Here we have some control over the spec, but can only apply compatible changes and the spec is partially generated itself
    - The spec is particularly large
- The orchestration service API
    - Here we have most control over the spec, it is designed to be extensible and will receive new features where we can influence the API spec

We can further distinguish generating DTOs (i.e. POJOs or model classes) and generating service (i.e. API) classes.

----

The main tradeoff between code generation vs. manually writing code is **quality** vs. **time**.

### Alternative 1: Use generated code as-is

Pro:
- Least effort
- High certainty that code conforms to spec, only generator bugs or feature gaps remain

Con:
- Most issues that cause the generator to fail need to be addressed in the spec
- API stability can not be guaranteed
- API quality varies from acceptable to unusable
- Everything is `public` API

### Alternative 2: Rely partially on generated code

Partially generate code, e.g. by allow-listing or block-listing individual class files in a pipeline script.

Pro:
- High flexibility
- API stability can be guaranteed

Neutral:
- Partial guarantee that code conforms to spec
- Manual work, effort & maintenance scales linearly with the % of manual vs. generated code

Con:
- Higher maintenance, requires diligent manual work
- Requires investment into tooling

### Alternative 3: Manually written code

Pro:
- Best API quality
- API stability can be guaranteed
- Full flexibility

Con:
- Highest maintenance, requires diligent manual work
- No correctness guarantee

## Decision (DRAFT!)

For the **AI Core API** we choose alternative **1**, generating everything.
- All API is considered beta and not guaranteed to be stable.
- We actively align on spec changes, ensuring the code generation and compilation succeeds, and improve API quality on a best effort basis.
- We test the aspects most relevant for GenAI use cases (Configurations, Deployments).

For the **foundation models** we choose alternative **2**, generating about 80-90 percent.
- We generate all DTO classes, potentially block-listing anything not relevant to us.
- We manually write the service class(es), as they contain rather little valuable generated code.
- We improve API quality on a best effort basis.
- We test most (75% or more) of the functionality

For the **orchestration service** we choose alternative **2**
- We generate all DTO classes
- We test most (85% or more) of the functionality
