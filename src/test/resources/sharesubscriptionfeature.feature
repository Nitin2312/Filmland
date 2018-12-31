 Feature: Client can subscribe his/her friend for the available subscriptions
  Scenario: client call to POST /shareservice to subscribe a friend for a filmland service
   	When the client calls /shareservice with servicename and userId of the friend
   	Then the client receives confirmation with status code 201 that friend is also subscribed for the service
  Scenario: client call to POST /shareservice to subscribe a friend for a filmland service which already exists for the friend
  	When the client calls /shareservice with servicename and userId of the friend with same filmland service
  	Then the client receives error as bad request with status code 400 	that friend is already subscribed for the service