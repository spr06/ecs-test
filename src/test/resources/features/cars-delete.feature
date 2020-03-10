Feature: As a consumer of a RESTFul API, I would like to be able to remove cars

  Scenario: Able to add cars with valid details
    Given I have a valid car tesla model x colour grey 2010
    When I use delete api to delete the car
    And I receive response code 200
    When I use retrieve api to get it
    Then notFound status code is received

  Scenario: Not able to add cars with invalid details
    When I use delete api to delete non existant car
    Then notFound status code is received



