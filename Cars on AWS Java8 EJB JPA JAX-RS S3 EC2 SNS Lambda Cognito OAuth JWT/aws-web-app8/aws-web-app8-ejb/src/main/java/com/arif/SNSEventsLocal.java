package com.arif;

import javax.ejb.Local;

/**
 *
 * @author arif
 */
@Local
public interface SNSEventsLocal {
    public void send(String message);
}
