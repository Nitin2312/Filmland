CREATE TABLE SUBSCRIBER
        (
        email VARCHAR2(50) NOT NULL,
        password VARCHAR(50) NOT NULL,
        PRIMARY KEY (email)
        );

 CREATE TABLE FilmlandService(
 	serviceId long,
 	name varchar2(100) NOT NULL,
 	availableContent Integer,
 	price Integer,
 	PRIMARY KEY (serviceId)
 );
 
 CREATE TABLE SubscriberService( subscriberServiceIdentifier long AUTO_INCREMENT,email VARCHAR2(50) NOT NULL,
 serviceId long,
 startdate TimeStamp,
 enddate TimeStamp,
 PRIMARY KEY(subscriberServiceIdentifier),
  FOREIGN KEY (email) REFERENCES SUBSCRIBER(email),
  FOREIGN KEY (serviceId) REFERENCES FilmlandService(serviceId)
);
 
 CREATE TABLE SubscriberServicePayment(subscriberPaymentIdentifier long AUTO_INCREMENT,
 subscriberServiceIdentifier long,
 paymentdate TimeStamp,
 PRIMARY KEY(subscriberPaymentIdentifier),
 FOREIGN KEY(subscriberServiceIdentifier) REFERENCES SubscriberService(subscriberServiceIdentifier)
);
