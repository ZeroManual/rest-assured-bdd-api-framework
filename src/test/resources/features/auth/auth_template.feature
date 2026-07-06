@auth @template @wip
Feature: Auth template examples

  # This feature is a template. Remove @wip after mapping your real auth API.
  @security
  Scenario: Verify secured endpoint with bearer token
    Given I set bearer token from config
    And I set API endpoint "/secure/profile"
    When I send "GET" request
    Then response status code should be 200

  @security
  Scenario: Verify secured endpoint rejects missing token
    Given I clear auth headers
    And I set API endpoint "/secure/profile"
    When I send "GET" request
    Then response status code should be 401
