package com.arif;

import javax.jms.Message;

/**
 *
 * @author arif
 */
public interface JmsMessageHandler {
    public void handleRequestSentMessage(Message message);
    public void handleRequestReceivedMessage(Message message);
    public void handleResponseSentMessage(Message message);
    public void handleResponseReceivedMessage(Message message);
}
