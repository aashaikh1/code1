About this project :
This is a prototype Chat application between peer to peer, means the application will act as a client and server at same time. When first instance of this application is started it will start listening for other instance who wants to chat on a RMI url. When second instance is started passing in the RMI URL of first instance then both can communicate with each other. 


0. After downloading the project in a directory 

1. Build the project using: mvn clean install

2. Run first instance using: 
mvn exec:java -Dexec.args="Arif 1098"

Note1: First param here "Arif" is the chat name as it appears during chat to other person Noah below.
Note2: Second param here "1098" is the port on which RMI registry will be started for first instance. Make sure this port is not in use otherwise change the port num appropriately in above and below (for second instance) both commands.


3. Run second instance using:
mvn exec:java -Dexec.args="Noah 1099 //localhost:1098/ChatPeer"

Note1: First param here "Noah" is the chat name as it appears during chat to other person Arif above.
Note2: Second param here "1099" is the port on which RMI registry will be started for second instance. Make sure this port is not in use otherwise change the port num appropriately.
Note3: Third param here "//localhost:1098/ChatPeer" is the RMI URL/PORT of first instance.

4. To exit press Control C. Exiting properly via pressing Coltrol C will remove client with other connected peers.
