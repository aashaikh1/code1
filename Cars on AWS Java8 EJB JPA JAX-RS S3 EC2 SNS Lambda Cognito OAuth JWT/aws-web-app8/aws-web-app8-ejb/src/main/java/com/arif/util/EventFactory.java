package com.arif.util;

import com.arif.car.jpa.CarStock;

/**
 *
 * @author arif
 */
public interface EventFactory<T> {
    public T buildEvent(CarStock carStock, EventType event);
}
