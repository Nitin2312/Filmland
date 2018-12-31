 Feature: Client can subscribe for the available subscriptions
  Scenario: client call to POST /subscribe to subscribe for a filmland service
   	When the client calls /subscribe with servicename
   	Then the client receives confirmation with status code 201
  Scenario: client call to POST /subscribe to subscribe for a existing filmland service
  	When the client calls /subscriber with subscribed servicename
  	Then the client receives error User already have this service with status code 400