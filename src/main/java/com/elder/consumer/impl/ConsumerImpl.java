package com.elder.consumer.impl;

import com.elder.consumer.Consumer;
import com.elder.machine.VendingMachine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jan on 12/07/2016.
 */
public class ConsumerImpl implements Consumer {

    private VendingMachine vendingMachine;


    public ConsumerImpl(final VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public Double getItemPriceBySlot(final Integer productSlot) {
        return vendingMachine.getProductItemPricePerSlot(productSlot);
    }

    @Override
    public List<Double> buyProductPerSlotReturningChange(final Integer productSlot, final List<Double> coins) {

        validateInputCoinsAreValid(coins);

        Double valueToReturn = calculateExchangeToReturn(productSlot, coins);

        List<Double> coinsAvailableOrderedLowValue = vendingMachine.getCoins().stream().sorted().collect(Collectors.toList());

        List<Double> resultCoins = new ArrayList<>();

        searchCoinsToReturnTraversing(coinsAvailableOrderedLowValue, BigDecimal.valueOf(valueToReturn), resultCoins);

        validateChangeIsCorrect(valueToReturn, resultCoins);

        updateAmountOfChangePerCoinTypeInVendingMachine(resultCoins);

        decreaseNumberOfItemsAvailableInSlot(productSlot);

        return resultCoins;
    }



    private Double calculateExchangeToReturn(final Integer productSlot, final List<Double> coins) {

        Double sumCoinsValue = coins.stream().reduce(0.0, Double::sum);

        Double priceItem = vendingMachine.getProductItemPricePerSlot(productSlot);

        return Math.abs(sumCoinsValue - priceItem);
    }

    private void decreaseNumberOfItemsAvailableInSlot(final Integer productSlot) {

        Integer items = vendingMachine.getProductItemQuantityPerSlot(productSlot);
        vendingMachine.setProductItemQuantityPerSlot(productSlot, --items);
    }

    private void updateAmountOfChangePerCoinTypeInVendingMachine(final List<Double> resultCoins) {

        for (final Double coin : resultCoins) {

            Integer amount = vendingMachine.getAmountOfExchangePerCoinType(coin);
            vendingMachine.setAmountOfExchangePerCoinType(coin, --amount);
        }
    }

    private void validateChangeIsCorrect(final Double valueToReturn, final List<Double> resultCoins) {

        BigDecimal checkValue = new BigDecimal(0);

        for (final Double res : resultCoins) {

            checkValue = checkValue.add(BigDecimal.valueOf(res));
        }

        if (!checkValue.equals(BigDecimal.valueOf(valueToReturn))) {

            throw new IllegalStateException("Quantity dose not match");
        }
    }


    private void searchCoinsToReturnTraversing(final List<Double> coinsAvailableOrdered, final BigDecimal valueToReturn, List<Double> resultCoins) {

        Double coin = null;

        for (int i = 0; i < coinsAvailableOrdered.size(); i++) {

            coin = coinsAvailableOrdered.get(i);

            if (coin <= valueToReturn.doubleValue() && vendingMachine.getAmountOfExchangePerCoinType(coin) > 0) {

                BigDecimal newValue = valueToReturn.subtract(BigDecimal.valueOf(coin));

                resultCoins.add(coin);

                searchCoinsToReturnTraversing(coinsAvailableOrdered, newValue, resultCoins);

                if (newValue.longValue() == 0) {
                    break;
                }
            }
        }
    }

    private void validateInputCoinsAreValid(final List<Double> coins) {

        Set<Double> coinsSet = coins.stream().collect(Collectors.toSet());

        Long countCoinsNotValid = vendingMachine.getCoins().stream().filter(s -> coinsSet.contains(s)).count();

        if (countCoinsNotValid != coinsSet.size()) {

            throw new IllegalArgumentException("Coins inserted not valid");
        }
    }
}
