
About this project :
This is a prototype to implement MQTT (Message Queuing Telemetry Transport) based messaging using Mosquitto as Broker. 

To run this project you need to install Mosquitto Broker (https://mosquitto.org/download/)
I installed the broker on my windows "Program Files" folder.
In a new terminal window run the broker using: C:\Program Files\mosquitto>mosquitto
By default Mosquitton runs on port: 1883


0. Download this project in a directory 

1. Build the project using: mvn clean install

2. In a new terminal window run the Java MQTT Callback Listener using: mvn exec:java -Pcallback

3. In a new terminal window run the Java MQTT Publisher using: mvn exec:java -Ppublish

4. In Publisher window type message to be sent to broker.

5. Confirm that the message is received on the Callback Listener window.



U can publish/subscribe messages using Mosquitto built in pub/sub as well.
start the built in MQTT publisher in new terminal
C:\Program Files\mosquitto>mosquitto_sub -h localhost -t arif

start the built in MQTT subscriber in third terminal
C:\Program Files\mosquitto>mosquitto_pub -h localhost -t arif -m "hello"

Note: Here arif is just the topic name.