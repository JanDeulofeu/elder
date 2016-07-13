package com.elder.machine;

import java.util.List;

/**
 * Created by jan on 10/07/2016.
 */
public interface VendingMachine {


    List<Double> getCoins();

    void setProductItemQuantityPerSlot(Integer productSlot, Integer quantity);

    Integer getProductItemQuantityPerSlot(Integer productSlot);

    void setProductItemPricePerSlot(Integer productSlot, Double price);

    Double getProductItemPricePerSlot(Integer productSlot);

    void setAmountOfExchangePerCoinType(Double coinType, Integer quantity);

    Integer getAmountOfExchangePerCoinType(Double coinType);
}
