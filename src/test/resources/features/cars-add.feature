Feature: As a consumer of a RESTFul API, I would like to be able to add cars

  Scenario Outline: I am able to add cars with valid details
    Given I have car details with <make>, <model>, <colour>, <year>
    When I use add api to add the car
    Then car is added
    And I receive response code 200
    Examples:
      | make   | model | colour | year |
      | VW     | micra | black  | 2000 |
      | NISSAN | micra | Grey   | 2009 |

  Scenario: I am able to update already added car
    Given  I have a valid car tesla model x colour grey 2010
    When I use add api to update it to tesla model x colour black 2010
    Then car is updated correctly
    And I receive response code 200

  Scenario: I get an error if I update an non existent car
    When I use add api to update a random non existent car
    Then notFound status code is received





