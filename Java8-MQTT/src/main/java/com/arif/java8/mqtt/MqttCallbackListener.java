package com.arif.java8.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author Arif
 */
public class MqttCallbackListener {

    public static void main(String[] args) throws MqttException {
        if (args.length < 2) {
            System.out.println("Pass command line params: brokerUrl topicName");
            System.exit(1);
        }

        String url = args[0];
        String topicName = args[1];
        System.out.println("Connecting to MQTT Broker: " + url + " on Topic: " + topicName);
        MqttClient client = new MqttClient(url, MqttClient.generateClientId());
        client.setCallback(new DefaultMqttCallback());
        client.connect();
        client.subscribe(topicName);
        System.out.println("Connected");
    }

}
