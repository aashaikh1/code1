package com.arif.java8.mqtt;

import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Arif
 */
public class MqttPublisher {

    public static MqttMessage createMqttMessage(String msg) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes());
        return mqttMessage;
    }

    public static void main(String[] args) throws MqttException {
        if (args.length < 2) {
            System.out.println("Pass command line params: brokerUrl topicName");
            System.exit(1);
        }

        String url = args[0];
        String topicName = args[1];
        
        System.out.println("Connecting to MQTT Broker: " + url + " on Topic: " + topicName);

        MqttClient client = new MqttClient(url, MqttClient.generateClientId());
        client.connect();
        System.out.println("Connected!");
        System.out.println("Type your message to be sent. Type Quit or Exit to leave.");
        Scanner scanner = new Scanner(System.in);
        String message;
        do {
            message = scanner.nextLine();
            client.publish(topicName, createMqttMessage(message));
        } while (!(message.equalsIgnoreCase("quit") || message.equalsIgnoreCase("exit")));

        client.disconnect();
        System.out.println("Disconnected!");
    }

}
