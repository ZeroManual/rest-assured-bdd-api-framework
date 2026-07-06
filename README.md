# REST Assured BDD API Automation Framework

Lead-level Java API automation framework for sprint, SIT, regression and CI/CD execution.

## Tech Stack

- Java 17
- Maven
- REST Assured
- Cucumber BDD
- TestNG
- Hamcrest + AssertJ assertions
- JSON Schema Validation
- Jackson POJO serialization/deserialization
- Jenkins pipeline support
- Cucumber HTML/JSON/JUnit reports

## Real-Time Purpose

This framework is designed for MNC-style API automation where tests are executed from:

- local IntelliJ
- command line
- Jenkins CI pipeline
- nightly regression jobs
- SIT validation jobs
- pre-release quality gate jobs

## Important Note About Selenium

Selenium is for UI/browser automation. API testing should be done with REST Assured. In real projects, API automation is usually kept separate from Selenium UI automation for speed, stability and maintainability. You can integrate this framework with a separate Selenium UI framework later through the same Jenkins pipeline.

## Project Structure

```text
rest-assured-bdd-api-framework/
├── Jenkinsfile                         # Standard parameterized Jenkins pipeline
├── ci/
│   ├── Jenkinsfile.smoke               # Optional dedicated smoke job
│   ├── Jenkinsfile.sit                 # Optional dedicated SIT job
│   └── Jenkinsfile.regression          # Optional dedicated regression job
├── pom.xml
├── testng.xml
├── suites/
│   ├── smoke.xml
│   ├── sit.xml
│   ├── regression.xml
│   └── parallel.xml
└── src/test/
    ├── java/com/company/api/
    │   ├── assertions/                 # Reusable advanced assertion methods
    │   ├── clients/                    # REST Assured request execution layer
    │   ├── config/                     # Environment config loader
    │   ├── context/                    # Scenario shared context
    │   ├── db/                         # DB validation helper
    │   ├── hooks/                      # Cucumber hooks
    │   ├── models/                     # POJO request/response models
    │   ├── payloads/                   # Dynamic payload builders
    │   ├── runners/                    # Cucumber TestNG runner
    │   ├── steps/                      # Step definitions
    │   └── utils/                      # JSON, schema, common utilities
    └── resources/
        ├── config/                     # qa/sit/uat properties
        ├── features/                   # BDD feature files
        ├── schemas/                    # JSON schemas
        └── testdata/                   # reusable payload test data
```

## Local Commands

Run smoke tests:

```bash
mvn clean test -Psmoke-suite -Denv=qa
```

Run SIT tests:

```bash
mvn clean test -Psit-suite -Denv=sit
```

Run regression tests:

```bash
mvn clean test -Pregression-suite -Denv=qa
```

Run all tests in parallel:

```bash
mvn clean test -Pparallel-suite -Denv=qa -DthreadCount=6
```

Run a specific Cucumber tag:

```bash
mvn clean test -Denv=qa -Dcucumber.filter.tags="@smoke and @users"
```

Override base URL from command line:

```bash
mvn clean test -Psmoke-suite -Denv=qa -Dbase.uri=https://jsonplaceholder.typicode.com
```

## Jenkins Standard Practice

Recommended MNC standard: maintain one parameterized `Jenkinsfile` in Git. Create Jenkins jobs that call the same Jenkinsfile with different default parameters.

Example Jenkins jobs:

```text
api-smoke-qa          -> Jenkinsfile, SUITE=smoke-suite, ENV=qa
api-sit-suite         -> Jenkinsfile, SUITE=sit-suite, ENV=sit
api-regression-nightly-> Jenkinsfile, SUITE=regression-suite, ENV=qa, scheduled nightly
api-release-gate      -> Jenkinsfile, SUITE=full-suite, ENV=sit
```

Optional separate Jenkinsfiles are also provided inside `ci/` for companies that still prefer dedicated jobs.

## API Concepts Covered

- GET, POST, PUT, PATCH, DELETE support
- headers, query params, path params
- Bearer token and API key support
- request/response logging
- reusable request/response specifications
- POJO serialization/deserialization
- JSONPath extraction
- schema validation
- response time validation
- response header validation
- negative testing
- contract testing
- security-oriented checks
- DB validation helper
- Cucumber tags for smoke/SIT/regression/contract/security
- Jenkins CI execution

## Reports

After execution:

```text
target/cucumber-reports/cucumber.html
target/cucumber-reports/cucumber.json
target/cucumber-reports/cucumber.xml
target/surefire-reports/
```

Jenkins publishes:

- JUnit result from `target/cucumber-reports/cucumber.xml`
- archived HTML report from `target/cucumber-reports/`

## Real-Time Execution Strategy

| Suite | Tag/Profile | When to Run |
|---|---|---|
| Smoke | `@smoke` / `smoke-suite` | After every QA/SIT deployment |
| SIT | `@sit` / `sit-suite` | After integrated build deployment |
| Regression | `@regression` / `regression-suite` | Nightly or before release |
| Contract | `@contract` / `contract-suite` | When API schema/contract changes |
| Security | `@security` / `security-suite` | Before release or scheduled quality checks |
| Full | `full-suite` | Release gate |

## Senior Automation Engineer Notes

- Keep API automation independent of UI automation.
- Use tags carefully: `@smoke`, `@sit`, `@regression`, `@contract`, `@security`.
- Never hard-code environment URLs or tokens in step definitions.
- Pass environment, suite and thread count from Jenkins parameters.
- Fail the pipeline for smoke failures and release-gate regression failures.
- Publish JSON/JUnit reports for trend analysis.
- Mark unstable external dependencies clearly to avoid false failures.
