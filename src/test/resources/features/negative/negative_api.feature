@negative @api
Feature: Negative API validations

  @regression @security
  Scenario: Verify invalid resource does not return server error
    Given I set API endpoint "/invalid-resource"
    When I send "GET" request
    Then response status code should be 404
    And response should not contain server error

  @contract @regression
  Scenario: Verify contract for comments endpoint
    Given I set API endpoint "/comments/{id}"
    And I set path parameter "id" as "1"
    When I send "GET" request
    Then response status code should be 200
    And response field "email" should match regex "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    And response schema should match "comment-schema.json"
