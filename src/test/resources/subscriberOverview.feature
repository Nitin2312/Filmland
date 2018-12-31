Feature: CLient can use the services of Filmland.
  Scenario: client call to GET /services to get list of services
    When the client calls /services
    Then the client receives list of filmland services 