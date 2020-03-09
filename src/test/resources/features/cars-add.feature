Feature: As a consumer of a RESTFul API, I would like to be able to add cars

  Scenario Outline: I am able to add cars with valid details
    Given I have valid car details with <make>, <model>, <colour>, <year>
    When I use add api to add the car
    Then car is added
    And I receive response code 200
    Examples:
      | make   | model | colour | year |
      | VW     | micra | black  | 2000 |
      | NISSAN | micra | Grey   | 2009 |



