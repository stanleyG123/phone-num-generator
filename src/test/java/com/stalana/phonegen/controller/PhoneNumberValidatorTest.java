package com.stalana.phonegen.controller;

import org.junit.Test;

public class PhoneNumberValidatorTest {
    PhoneNumberController ctr = new PhoneNumberController();

    @Test
    public void testPhoneNumberValidators(){
        ctr.validatePhoneNumber("1234567");
        ctr.validatePhoneNumber("1234567890");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberValidators_short(){
        ctr.validatePhoneNumber("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberValidators_empty(){
        ctr.validatePhoneNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberValidators_notNum(){
        ctr.validatePhoneNumber("aaaaaaa");
    }
}
