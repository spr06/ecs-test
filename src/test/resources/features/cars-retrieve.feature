Feature: As a consumer of a RESTFul API, I would like to be able to get cars

  Scenario: I am able to retrieve existing car
    Given  I have a valid car tesla model x colour grey 2010
    When I use retrieve api to get it
    Then car is retrieved correctly
    And I receive response code 200

  Scenario: Not able to add cars with invalid details
    When I use retrieve api to get a car with a non existent uuid
    Then notFound status code is received



