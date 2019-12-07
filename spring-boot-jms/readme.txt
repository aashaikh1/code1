About this project :
This is a prototype to implement JMS based messaging using SpringBoot and ActiveMQ. 
You can run this project using one of below two ways:

A. Embedded ActiveMQ (in this case you do not need to install ActiveMQ on your machine).
B. Installed MQ (in this case you will have to install ActiveMQ on your machine).



0. Download this project in a directory 

1. Build the project using: mvn clean install

2. Run the project using embedded MQ: mvn exec:java -Psendrecv -Dspring.profiles.active=embeddedActiveMq

3. Run the project using installed MQ: 
   Start the installed ActiveMQ on your machine. 
   The default url/port used by installed ActiveMQ for JMS connections is: tcp://localhost:61616
   The default user/pwd used by installed ActiveMQ is: admin/admin
   If Any of these values are different for your installed ActiveMQ then you need to change them respectively in resources\application-installedActiveMq.properties file in this project.
   Run the project using installed MQ: mvn exec:java -Psendrecv -Dspring.profiles.active=installedActiveMq

4. After running the project examine the output kind of similar to below:
Request Sent: Hello there MessageId: ID:DESKTOP-OGRV4LJ-58855-1575698444298-1:3:1:1:1
Request Received: Hello there MessageId: ID:DESKTOP-OGRV4LJ-58855-1575698444298-1:3:1:1:1
Response Received: Your Message has been received MessageId: ID:DESKTOP-OGRV4LJ-58855-1575698444298-1:2:1:1:1 CorrelationId: ID:DESKTOP-OGRV4LJ-58855-1575698444298-1:3:1:1:1

Note: MessageId and CorrelationId will be different on your machine but whatever they are the MessageId in Request Sent: entry will be same as the CorrelationId in the Response Received: entry. 
