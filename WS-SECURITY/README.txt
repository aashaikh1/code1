
About this project :
This is a project to demonstrate calling SOAP web service in a fully secure manner using well known WS-SECURITY Standard. 

I HAVE BUILT AND RUN THIS PROJECT USING JAVA 8u202. IF YOU USE JAVA VERSION LESS THAN 8-UPDATE-202 THEN IT MAY NOT WORK BECAUSE OF THE OLD CRYPTO ARTEFACTS THAT COMES WITH OLDER JAVA. 


There are two top level directories for this project. 
I) Server Directory (contains Secure Web Service Server code I developed)
II). Client Directory (contains Secure Web Service Client code I developed)


Before running this project make sure ports 9000 and 9990 are not used by any other process and are free.

0. Download this project on your local machine. 

1. Open command terminal and ChangeDir to Server directory. 

2. Build the project using: mvn clean install

3. Run Server using: mvn -PsecureServer

4. Open another new command terminal and ChangeDir to Client directory. 

5. Build the project using: mvn clean install

6. Run Server using: mvn -PsecureClient

7. You will see that secure SOAP messages will be sent and received by client and server and then decrypted response will be displayed by client just for your info.

8. To stop the server press Control-C on server terminal window.




Just for your knowledge below is the explanation of WS-SECURITY and Sign, Encrypt, Decrypt, Verify Process:
WS-SECURITY is used to implement Message Level Security. 
WS-SECURITY use Sign, Encrypt, Decrypt, Verify steps to securely send messages maintaining Integrity and Confidentiality of the messages sent and received.

These 4 Steps Sign, Encrypt, Decrypt, Verify are implemented by using 4 Cryptographic Keys (Asymmetric KeyPairs) which are:
Public and Private Keys of Client 
Public and Private Keys of the Server.

Both Client and Server maintains two key stores which are:
KeyStore on Client: Contains Public and Private Keys of Client.
TrustStore on Client: Contains Public Key of the Server in a Certificate.
KeyStore on Server: Contains Public and Private Keys of Server.
TrustStore on Server: Contains Public Key of the Client in a Certificate.


I did Key Management using a Java based tool called KeyStore Explorer which is freely available to download on Internet.
Using KeyStore Explorer I created:
One JKS KeyStore and One TrustStore for Client (You can see them in "crypt" directory of client).
I also created One JKS KeyStore and One TrustStore for Server (You can see them in "crypt" directory of server).


Then inside client KeyStore created above I generated Asymmetric KeyPair (Public/Private) for Client.
Then inside server KeyStore created above I generated Asymmetric KeyPair (Public/Private) for Server.

Then using KeyStore Explorer I exported the Certificate Chain from Client KeyStore to be imported later into Server truststore.
Then using KeyStore Explorer I exported the Certificate Chain from Server KeyStore to be imported later into Client truststore.


When Client Sends a request to Server then Sign, Encrypt happens as below:

A) Client Creates a Signature (for Message Integrity) which is done by:
1. Client calculate Hash of a specified body/element of Soap Request.
2. Client encrypt that Hash using Private Key of the Client which is in Client's own Keystore. 

B) Then Client Encrypts (for Message Confidentiality) the specified body/element of Soap Request using Public Key of the Server which Client has it in Client's truststore.

C) Client Send both the Signature and the Encrypted Message to Server in the Soap Request.




When Server Receives the request from Client then Decrypt, Verify happens as below:

D) Server decrypts the Encrypted Message part from request using its own Private Key which it has in its Server's own Keystore.

E) Server Verify the Signature it received in the message which is done by:
3. Calculate the hash of the Message decrypted in step (D) above 
4. Decrypt the Signature it received inside request by using Public Key of the Client which Server has inside Server's truststore. This decryption results in the hash.
5. The server compares the two Hashes it now has from E.3 and E.4. If two hashes match it means that Signature has been successfully verified.

Above is how Message Level Security Works.

Transport level security is when protocol is also secure like if you send message over HTTPS instead of HTTP then it means your transport is secure.



https://www.youracclaim.com/users/arif-shaikh/badges

