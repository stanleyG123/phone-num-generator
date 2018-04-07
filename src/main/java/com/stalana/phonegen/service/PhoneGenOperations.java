package com.stalana.phonegen.service;

import java.util.List;

/**
 * Created by stan on 4/4/18.
 * Interface to generate and retrieve alphanumeric combinations for phone numbers
 */
public interface PhoneGenOperations {

    /**
     * generates alphanumeric combinations for a given phone number
     * @param starterNumber phone number seed to use for generation
     * @return total generated
     */
    public List<String> generateAlphaNumerics(String starterNumber);

    /**
     * Fetches phone numbers per start and end pages
     * @param allAlphaNumerics all combinations
     * @param start page start
     * @param end page end
     * @return list containing alphanumeric combos
     */
    public List<String> fetchAlphaNumericCombos(List<String> allAlphaNumerics,
                                                int start, int end);
}