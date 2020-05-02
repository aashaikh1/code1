package com.arif;

import javax.ejb.Local;

/**
 *
 * @author arif
 */
@Local
public interface SNSNotificationLocal {
    public void send(String message);
}
