@users @api
Feature: Posts API validation

  @smoke @regression @contract
  Scenario: Verify GET post by id with status, fields, response time and schema
    Given I set API endpoint "/posts/{id}"
    And I set path parameter "id" as "1"
    When I send "GET" request
    Then response status code should be 200
    And response content type should contain "application/json"
    And response time should be below 3000 ms
    And response field "id" should equal number 1
    And response field "userId" should not be null
    And response schema should match "post-schema.json"

  @regression
  Scenario: Verify GET posts list with array assertions
    Given I set API endpoint "/posts"
    And I set query parameter "userId" as "1"
    When I send "GET" request
    Then response status code should be 200
    And response array size should be greater than 5
    And each item should contain field "id"
    And each item should contain field "title"

  @smoke @regression
  Scenario: Verify POST create post using POJO serialization and deserialization
    Given I set API endpoint "/posts"
    And I prepare "createPost" request payload
    When I send "POST" request
    Then response status code should be 201
    And response field "title" should equal "Senior automation API framework"
    And I deserialize response as "PostResponse" and validate required fields

  @sit @regression
  Scenario: Verify PUT update post end-to-end
    Given I set API endpoint "/posts/{id}"
    And I set path parameter "id" as "1"
    And I prepare "updatePost" request payload
    When I send "PUT" request
    Then response status code should be 200
    And response field "id" should equal number 1
    And response field "title" should equal "Updated title from API automation"

  @regression
  Scenario: Verify DELETE post
    Given I set API endpoint "/posts/{id}"
    And I set path parameter "id" as "1"
    When I send "DELETE" request
    Then response status code should be 200
