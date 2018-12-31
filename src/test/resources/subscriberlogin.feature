Feature: Client need to login to use the services of filmLand
  Scenario: client call to POST /login to get the authorization key
  Given the credentials are known
  	When the client call the /login with the credentials
    Then the client should receive a authkey