Feature: As a consumer of a RESTFul API, I would like to be able to remove cars

  Scenario: Able to add cars with valid details
    Given  I have valid car details
    When I use add api
    Then car is added
    And I receive response code 200

  Scenario: Not able to add cars with invalid details
    Given  I have invalid car details
    When I use add api
    Then illegal argument exception is received
    And I receive response code 400



