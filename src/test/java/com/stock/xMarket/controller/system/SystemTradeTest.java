package com.stock.xMarket.controller.system;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SystemTradeTest {
    @Test
    public void name() {
        Random rand = new Random();
        System.out.println((double) rand.nextInt(4)/100+5.5);
    }
}