package com.elder.consumer.impl;

import com.elder.item.Product;
import com.elder.machine.VendingMachine;
import com.elder.machine.impl.VendingMachineImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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


        List<Double> change = consumer.buyProductPerSlotReturningChange(2, Arrays.asList(0.2, 0.5));

        assertNotNull(change);
    }

    @Test
    public void buyProductTest() throws Exception {

        VendingMachine vendingMachine = new VendingMachineImpl(coins, slots);

        vendingMachine.setAmountOfExchangePerCoinType(0.5, 1);
        vendingMachine.setAmountOfExchangePerCoinType(1.0, 2);
        vendingMachine.setAmountOfExchangePerCoinType(0.2, 5);

        ConsumerImpl consumer = new ConsumerImpl(vendingMachine);

        List<Double> change = consumer.buyProductPerSlotReturningChange(1, Arrays.asList(0.2, 0.5));

        assertNotNull(change);
    }


}