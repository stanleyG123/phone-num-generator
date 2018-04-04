package com.stalana.phonegen.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the phone number generator
 * Created by stan on 4/4/18.
 */
@Service
public class PhoneGenOperationsImpl implements  PhoneGenOperations{

    private ThreadLocal<List<String>> myThreadLocal = new ThreadLocal<>();

    @Override
    public void generateAlphaNumerics(String starterNumber) {
        if (starterNumber != null && starterNumber.length() > 0){
            List<String> nums = new ArrayList<>();

            Random rand = new Random();
            for (int i = 0 ; i < 1000; i ++) {
                int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
                int num2 = rand.nextInt(743);
                int num3 = rand.nextInt(10000);

                DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
                DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

                String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
                nums.add(phoneNumber);
            }

            myThreadLocal.set(nums);
        }
    }

    @Override
    public List<String> fetchAlphaNumericCombos(int start, int end) {
        List<String> allAlphaNumerics = myThreadLocal.get();
        List<String> page = new ArrayList<>();
        if (allAlphaNumerics != null && allAlphaNumerics.size() > 0){
            page = allAlphaNumerics.subList(start, end);
        }else{
            throw new IllegalStateException(" Unable to retrieve alphanumeric numbers, the generated  list is empty");
        }
        return page;
    }
}
