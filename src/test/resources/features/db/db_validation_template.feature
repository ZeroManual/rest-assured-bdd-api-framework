@db @template @wip
Feature: DB validation template

  # Enable db.enabled=true and configure DB details using Jenkins credentials before using.
  Scenario: Validate API response data against database
    Given I set API endpoint "/posts/{id}"
    And I set path parameter "id" as "1"
    When I send "GET" request
    Then response status code should be 200
    And database query "select title from posts where id = ?" with response field "id" should match response field "title"
