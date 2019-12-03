About this project :
This is a prototype to implement file transfer in minimal form using JAX-WS SOAP and file is transfered from soapui client to server using MTOM.

0. Download this project in a directory 

1. Build the project using: mvn clean install

2. Run the project using: mvn exec:java
   Make sure no other proocess is listening on port 9721.

Above command will run the FileService web service in embedded container using jax-ws EndPoint.publish.

If needed the final archive jar can also be deployed in JEE APP Server like glasshfish.

3. Launch SoapUI (if u do not have SoapUI installed then download it and install it) and open soap-ui project fileService-soapui-project.xml.

4. After opening the soapui project expand it and go to "Request 1" under upload operation.

5. Examine that a file named heaven.png is attached inside the Attachment tab. This file will be sent to the FileService.

5. Press the green arrow button to send the request to FileService. This will send heaven.png embedded attachment file to FileService
   and the FileService will save it under the name (arif.png) given in Soap "Request 1".

7. Examing that the file arif.png has appeared and is saved in the same directory where you downloaded the project in Step 0 above.

===============================================================================================
8. If you like to send a different file from Soapui to FileService then u can add a differnt file by pressing green plus sign in Attachment tab
   and selecting the file u like to send from ur disk. Change the file name and extension appropriately for ur file in <fileName>arif.png</fileName>.

9. Before sending ur file make sure the "Part" & "ContentID" columns in Attachment tab contain same ID as in the <data>cid: in "Request 1".

Note: The the "Request 1" has two tags:
         <fileName>arif.png</fileName>      FileService after receiving the file will save it with this name.
         <data>cid:973279859171</data>      The number given after cid: should be same as in columns named "Part" & "ContentID" inside SoapUI Attachment Tab. 

10. You can see the generated wsdl from http://localhost:9721/FileService?wsdl

  
