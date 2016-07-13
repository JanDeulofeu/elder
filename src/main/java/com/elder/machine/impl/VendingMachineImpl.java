package com.elder.machine.impl;

import com.elder.item.Product;
import com.elder.machine.VendingMachine;

import java.util.*;

/**
 * Created by jan on 10/07/2016.
 */
public class VendingMachineImpl implements VendingMachine {

    private Map<Integer, Product> productSlots = new HashMap<Integer, Product>();
    private Map<Double, Integer> exchangePerCoinTypes = new HashMap<Double, Integer>();
    private List<Double> coins = new ArrayList<>();


    public VendingMachineImpl(final List<Double> coins, final Map<Integer, Product> productSlots) {
        this.coins = coins;
        this.productSlots = productSlots;
    }


    public Map<Integer, Product> getProductSlots() {
        return productSlots;
    }

    @Override
    public List<Double> getCoins() {
        return coins;
    }


    @Override
    public void setProductItemQuantityPerSlot(final Integer productSlot, final Integer quantity) {

        if (validateProductSlotExists(productSlot) && validateQuantityIsValid(quantity)) {
            productSlots.get(productSlot).setQuantity(quantity);
        }
    }

    @Override
    public Integer getProductItemQuantityPerSlot(final Integer productSlot) {

        if (validateProductSlotExists(productSlot)) {
            return productSlots.get(productSlot).getQuantity();
        }

        return Integer.MIN_VALUE;
    }

    @Override
    public void setProductItemPricePerSlot(final Integer productSlot, final Double price) {

        if (validateProductSlotExists(productSlot) && validatePriceIsValid(price)) {
            productSlots.get(productSlot).setPrice(price);
        }
    }

    @Override
    public Double getProductItemPricePerSlot(final Integer productSlot) {

        if (validateProductSlotExists(productSlot)) {
            return productSlots.get(productSlot).getPrice();
        }

        return Double.MIN_VALUE;
    }

    @Override
    public void setAmountOfExchangePerCoinType(final Double coinType, final Integer quantity) {

        if (validateQuantityIsValid(quantity) && validateCoinTypeIsValid(coinType)) {
            exchangePerCoinTypes.put(coinType, quantity);
        }
    }

    @Override
    public Integer getAmountOfExchangePerCoinType(final Double coinType) {

        if (validateCoinTypeExistsInExchange(coinType) && validateCoinTypeIsValid(coinType)) {
            return exchangePerCoinTypes.get(coinType);
        }
        return Integer.MIN_VALUE;
    }


    private boolean validateCoinTypeIsValid(final Double coinType) throws IllegalArgumentException {
        if (coins == null || coins.isEmpty() || !coins.contains(coinType)) {
            throw new IllegalArgumentException("Coin type not valid");
        }

        return true;
    }


    private boolean validateCoinTypeExistsInExchange(final Double coinType) throws IllegalArgumentException {
        if (exchangePerCoinTypes == null || exchangePerCoinTypes.isEmpty() || !exchangePerCoinTypes.containsKey(coinType)) {
            throw new IllegalArgumentException("Coin type not existing");
        }

        return true;
    }

    private boolean validateProductSlotExists(final Integer productSlot) throws IllegalArgumentException {
        if (productSlots == null || productSlots.isEmpty() || !productSlots.containsKey(productSlot)) {
            throw new IllegalArgumentException("Product Slot not valid");
        }
        return true;
    }

    private boolean validateQuantityIsValid(final Integer quantity) throws IllegalStateException {
        if (quantity == null || quantity < 0) {
            throw new IllegalStateException("Quantity value not valid");
        }
        return true;
    }

    private boolean validatePriceIsValid(final Double price) throws IllegalStateException {
        if (price == null || price < 0) {
            throw new IllegalStateException("Price value not valid");
        }
        return true;
    }
}
