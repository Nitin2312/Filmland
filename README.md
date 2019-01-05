# Filmland
Application for Filmland Services

This application is for the FilmLand where there are number online series movies are present and this application keeps 
track of following things:-

1. Login credentials are validated .

2. Listing the Fimland services which are subscribed by the customer and the services which are yet available to the customer.

3. Subscribers can subscribe for the new services available in Filmland.

4. Subscribers can also share the services with some other friend/subscriber.


PreRequisites for using this application.

1. You need to install Apache maven 3.5.0 for this application. 

2. Your System should have JAVA8+ installed in their system.

Installing the Application


1. For unit test cases .
mvn clean install

2. For running the integration Test case .
mvn clean install -Pintegration-test

3. For installing the application use :-
mvn clean install && java -jar target/FilmApp-0.0.1-SNAPSHOT.jar


