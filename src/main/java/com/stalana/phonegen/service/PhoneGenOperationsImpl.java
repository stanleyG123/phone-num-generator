package com.stalana.phonegen.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Implementation of the phone number generator
 * Created by stan on 4/4/18.
 */
@Service
public class PhoneGenOperationsImpl implements  PhoneGenOperations{

    private static final Map<Integer,String> keyToLetters= new LinkedHashMap<>();

    static {
        keyToLetters.put(0, "0");
        keyToLetters.put(1, "1");
        keyToLetters.put(2, "2ABC");
        keyToLetters.put(3, "3DEF");
        keyToLetters.put(4, "4GHI");
        keyToLetters.put(5, "5JKL");
        keyToLetters.put(6, "6MNO");
        keyToLetters.put(7, "7PQRS");
        keyToLetters.put(8, "8TUV");
        keyToLetters.put(9, "9WXYZ");
    }

    /**
     * Generates AlphaNumeric combos from a given phone number
     * @param starterNumber phone number seed to use for generation
     */
    @Override
    public List<String> generateAlphaNumerics(String starterNumber) {
        if (starterNumber != null && starterNumber.length() > 0){
            Set<String> noDups = new LinkedHashSet<>();
            recur(noDups,starterNumber,0);
            List<String> combos = new ArrayList<>();
            combos.addAll(noDups);
            return combos;
        }
        return null;
    }

    // recursive compute combos
    private static void recur (Collection<String> result,final String starter,int pointer) {
        // base case we've gone through the whole phone num
        if (pointer >= starter.length()){
            return;
        }else{
            String chAt = "" + starter.charAt(pointer);
            int phoneNumDigit = Integer.parseInt(chAt);

               String alts= keyToLetters.get(phoneNumDigit);
               // loop through the combos, recomputing for each letter
               for (String ch : alts.split("")) {
                   // make phone num
                   String left = starter.substring(0, pointer);
                   String right = starter.substring(pointer + 1, starter.length());
                   String newStr = left + ch + right;
                   result.add(newStr);
                   recur(result, newStr, pointer + 1);
               }
        }
    }
    @Override
    public List<String> fetchAlphaNumericCombos(List<String> allAlphaNumerics, int start, int end) {
        List<String> page = new ArrayList<>();
        if (allAlphaNumerics != null && allAlphaNumerics.size() > 0){
            if(end > allAlphaNumerics.size()){
                end = allAlphaNumerics.size() - 1;
            }
            // because end of sublist is exlusive,  add 1 to get the last element
            page = allAlphaNumerics.subList(start, end + 1);
        }else{
            throw new IllegalStateException(" Unable to retrieve alphanumeric numbers, the generated  list is empty");
        }
        return page;
    }
}
