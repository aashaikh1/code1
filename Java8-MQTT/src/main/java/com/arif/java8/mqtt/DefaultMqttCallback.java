package com.arif.java8.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Arif
 */
public class DefaultMqttCallback implements MqttCallback {

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection is lost with Mqtt Broker " + throwable.getMessage());
    }

    public void messageArrived(String topicName, MqttMessage mqttMessage) throws Exception {
        System.out.println("TopicName: " + topicName + " Message:" + new String(mqttMessage.getPayload()));
    }
}
