package com.stalana.phonegen.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PhoneGenOperationsTest {
    PhoneGenOperations svc;

    @Before
    public void setUp(){
        svc = new PhoneGenOperationsImpl();
    }

    @Test
    public void generateAlphaNumericsTenDigitsTest(){
        int numOfCombos = 4 * 4 * 1 * 4 * 4 * 4 * 4 * 1 * 1 * 5;
        List<String> combos = svc.generateAlphaNumerics("2403866109");
        List<String> firstPage = svc.fetchAlphaNumericCombos(combos,0,4);
        Assert.assertEquals(numOfCombos,combos.size());
        List<String> resultList = Arrays.asList("2403866109","240386610W","240386610X","240386610Y");
        Assert.assertArrayEquals(resultList.toArray(), firstPage.toArray());
    }

    @Test
    public void generateAlphaNumericsSevenDigitsTest(){
        int numOfCombos = 4 * 4 * 4 * 4 * 1 * 1 * 5;
        List<String> combos = svc.generateAlphaNumerics("3866109");
        Assert.assertEquals(numOfCombos,combos.size());
    }

    @Test
    public void generateAlphaNumericsAllZerosAndOnesTest(){
        int numOfCombos = 1;
        List<String> combos= svc.generateAlphaNumerics("0110000");
        Assert.assertEquals(numOfCombos,combos.size());
    }
}
