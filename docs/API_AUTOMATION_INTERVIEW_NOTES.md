# API Automation Interview Notes

## How this framework runs in real projects

1. Developer deploys build into QA/SIT.
2. Jenkins triggers API smoke suite.
3. If smoke passes, SIT or regression suite runs.
4. Reports are published in Jenkins.
5. Failures are triaged as app issue, data issue, environment issue or automation issue.

## Senior explanation

In this framework, Cucumber BDD is used for readable business scenarios, REST Assured is used for HTTP request execution, TestNG is used as the runner, Maven controls profiles/suites, and Jenkins executes the framework in CI/CD. Maven profiles such as `smoke-suite`, `sit-suite`, and `regression-suite` map to Cucumber tags so that the same codebase can execute different test scopes without changing code.

## Standard command examples

```bash
mvn clean test -Psmoke-suite -Denv=qa
mvn clean test -Psit-suite -Denv=sit
mvn clean test -Pregression-suite -Denv=qa -DthreadCount=6
```

## What failures mean

- 4xx can be expected for negative/security tests.
- 5xx usually indicates server-side issue.
- Schema failure indicates contract mismatch.
- Response time failure may indicate performance or environment slowness.
- DB mismatch usually indicates data persistence/integration issue.
- Connection timeout can indicate environment/network/deployment issue.
```
