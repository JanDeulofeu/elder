package com.elder.machine.impl;

import com.elder.item.Product;
import com.elder.machine.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.fail;

/**
 * Created by jan on 12/07/2016.
 */
public class VendingMachineValidatorsTest {

    private VendingMachine vendingMachine;
    private List<Double> coins;
    private Map<Integer, Product> slots = new HashMap<>();


    @Before
    public void init() {

        slots.put(1, new Product(0.10, 10));
        slots.put(2, new Product(0.5, 20));
        slots.put(2, new Product(1.0, 50));

        coins = Arrays.asList(0.2, 0.5, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateSlotsAreEmptyOnInstantiation() throws Exception {

        vendingMachine = new VendingMachineImpl(Collections.emptyList(), Collections.emptyMap());
        vendingMachine.setProductItemQuantityPerSlot(1,1);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void validateQuantityIsNotValid() throws Exception {

        vendingMachine = new VendingMachineImpl(Collections.emptyList(), slots);
        vendingMachine.setProductItemQuantityPerSlot(1,null);

        fail();
    }

    @Test(expected = IllegalStateException.class)
    public void validatePriceIsNotValid() throws Exception {

        vendingMachine = new VendingMachineImpl(Collections.emptyList(), slots);
        vendingMachine.setProductItemPricePerSlot(1,null);

        fail();
    }

}