package com.elder.consumer;

import java.util.List;

/**
 * Created by jan on 12/07/2016.
 */
public interface Consumer {
    Double getItemPriceBySlot(Integer productSlot);

    List<Double> buyProductPerSlotReturningChange(Integer productSlot, List<Double> coins);
}
