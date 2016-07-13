package com.elder.consumer.impl;

import com.elder.item.Product;
import com.elder.machine.VendingMachine;
import com.elder.machine.impl.VendingMachineImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by jan on 12/07/2016.
 */
public class ConsumerImplTest {


    private List<Double> coins;
    private Map<Integer, Product> slots = new HashMap<>();


    @Before
    public void init() {

        slots.put(1, new Product(0.10, 10));
        slots.put(2, new Product(0.5, 20));
        slots.put(3, new Product(1.0, 50));

        coins = Arrays.asList(0.5, 1.0, 0.2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateOnBuyProductCoinsAreValidTest() throws Exception {

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);
        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        consumer.buyProductPerSlotReturningChange(1, Arrays.asList(0.2, 0.5, 0.1));

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void buyProductCoinsChangeNotMatchesTest() throws Exception {

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);
        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        vendingMachine.setAmountOfExchangePerCoinType(0.5, 1);
        vendingMachine.setAmountOfExchangePerCoinType(1.0, 2);
        vendingMachine.setAmountOfExchangePerCoinType(0.2, 5);

        Integer numProductsBefore = vendingMachine.getProductItemQuantityPerSlot(2);

        List<Double> change = consumer.buyProductPerSlotReturningChange(2, Arrays.asList(0.2, 0.5));

        Integer numProductsAfter = vendingMachine.getProductItemQuantityPerSlot(2);

        assertEquals(numProductsAfter, numProductsBefore);

        assertNotNull(change);
    }


    @Test
    public void buyProductRollbackQuantityProductTest() throws Exception {

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);
        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        vendingMachine.setAmountOfExchangePerCoinType(0.5, 1);
        vendingMachine.setAmountOfExchangePerCoinType(1.0, 2);
        vendingMachine.setAmountOfExchangePerCoinType(0.2, 5);

        Integer numProductsBefore = vendingMachine.getProductItemQuantityPerSlot(2);

        try {

            consumer.buyProductPerSlotReturningChange(2, Arrays.asList(0.2, 0.5));
            fail();

        } catch (final IllegalStateException e) {

            Integer numProductsAfter = vendingMachine.getProductItemQuantityPerSlot(2);

            assertEquals(numProductsAfter, numProductsBefore);
        }
    }

    @Test
    public void buyProductTest() throws Exception {

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);

        vendingMachine.setAmountOfExchangePerCoinType(0.5, 1);
        vendingMachine.setAmountOfExchangePerCoinType(1.0, 2);
        vendingMachine.setAmountOfExchangePerCoinType(0.2, 5);

        Integer numProductsBefore = vendingMachine.getProductItemQuantityPerSlot(1);

        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        List<Double> change = consumer.buyProductPerSlotReturningChange(1, Arrays.asList(0.2, 0.5));

        Integer numProductsAfter = vendingMachine.getProductItemQuantityPerSlot(1);

        assertNotNull(change);


        Double changeReturned = calculateExchangeReturned(change);

        assertNotEquals(numProductsAfter, numProductsBefore);

        assertEquals(0.6, changeReturned, 0);

        assertEquals(new Integer(2), vendingMachine.getAmountOfExchangePerCoinType(0.2));

    }

    @Test
    public void buyProductTest2() throws Exception {

        coins = Arrays.asList(0.5, 1.0, 0.2, 0.1);

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);

        vendingMachine.setAmountOfExchangePerCoinType(0.5, 1);
        vendingMachine.setAmountOfExchangePerCoinType(1.0, 1);
        vendingMachine.setAmountOfExchangePerCoinType(0.2, 0);
        vendingMachine.setAmountOfExchangePerCoinType(0.1, 1);

        Integer numProductsBefore = vendingMachine.getProductItemQuantityPerSlot(1);

        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        List<Double> change = consumer.buyProductPerSlotReturningChange(1, Arrays.asList(0.2, 0.5));

        Integer numProductsAfter = vendingMachine.getProductItemQuantityPerSlot(1);

        assertNotNull(change);


        Double changeReturned = calculateExchangeReturned(change);

        assertNotEquals(numProductsAfter, numProductsBefore);

        assertEquals(0.6, changeReturned, 0);

        assertEquals(new Integer(0), vendingMachine.getAmountOfExchangePerCoinType(0.5));
        assertEquals(new Integer(0), vendingMachine.getAmountOfExchangePerCoinType(0.1));

    }



    private Double calculateExchangeReturned(final List<Double> change) {

        BigDecimal checkValue = new BigDecimal(0);

        for (final Double res : change) {

            checkValue = checkValue.add(BigDecimal.valueOf(res));
        }
        return checkValue.doubleValue();
    }


}