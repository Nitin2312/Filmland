insert into Subscriber(email,password) values('nitin_2312@yahoo.co.in','C747238F43C8D25F867906C2441A3CACD948D9D2');
insert into Subscriber(email,password) values('nitin_23121@yahoo.co.in','C747238F43C8D25F867906C2441A3CACD948D9D2');


insert into FilmlandService(serviceId,name,availableContent,price)values(1,'Nederlandse Films',10,4.0);
insert into FilmlandService(serviceId,name,availableContent,price)values(2,'International Films',10,20.0);
insert into FilmlandService(serviceId,name,availableContent,price)values(3,'Nederlandse series',10,21.0);
insert into FilmlandService(serviceId,name,availableContent,price)values(4,'Hollywood Series',10,21.0);

insert into SubscriberService(email,serviceId,startDate,enddate)values('nitin_2312@yahoo.co.in',1,'2018-12-01 00:00:01','2019-01-01 00:00:01');

insert into SubscriberServicePayment(subscriberServiceIdentifier,paymentdate)values(1,'2019-01-01 00:00:01');
