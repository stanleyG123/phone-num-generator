package com.stalana.phonegen.controller;

import com.stalana.phonegen.model.PhoneNumberRequest;
import com.stalana.phonegen.model.PhoneNumberResponse;
import com.stalana.phonegen.service.PhoneGenOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller for fetching alpha numeric phone number combinations
 * Created by stan on 4/4/18.
 */
@RestController
public class PhoneNumberController {
    private static final String ALL_COMBOS = "all_combos";
    private static final int FIRST_PAGE_START = 0;

    @Autowired
    PhoneGenOperations svc;

    /**
     * Generate alphanumeric combinations based on the input phone number
     *
     * @param phoneGenRequest phoneNumber and pagination results
     * @return
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public PhoneNumberResponse generateAlphaNumerics(@RequestBody PhoneNumberRequest phoneGenRequest,
                                                     HttpServletRequest request) {
        validatePhoneNumber(phoneGenRequest.getPhoneNumber());
        List<String> allCombos = svc.generateAlphaNumerics(phoneGenRequest.getPhoneNumber());
        request.getSession().setAttribute(ALL_COMBOS, allCombos);
        List<String> page = svc.fetchAlphaNumericCombos(allCombos, FIRST_PAGE_START, phoneGenRequest.getNumberPerPage() - 1);
        PhoneNumberResponse response = new PhoneNumberResponse();
        response.setCombos(page);
        response.setPageStart(FIRST_PAGE_START);
        response.setPageEnd(phoneGenRequest.getNumberPerPage() - 1);
        response.setNumberOfCombos(allCombos.size());
        return response;

    }

    protected void validatePhoneNumber(String phoneNumber){
        if (phoneNumber == null
                || phoneNumber.length() == 0
                || (phoneNumber.length() != 7 && phoneNumber.length() != 10 )
                || !phoneNumber.chars().allMatch( Character::isDigit )){
            throw new IllegalArgumentException(" Bad phone number. Expecting a number either 7 or 10 digits");
        }
    }

    /**
     * Fetch page of alphanumeric numbers
     *
     * @param pageStart page start and page end
     * @return phone numbers in the page
     */
    @RequestMapping(value = "/fetchPage", method = RequestMethod.GET)
    public PhoneNumberResponse fetchPage(@RequestParam(value = "pageStart") Integer pageStart,
                                         @RequestParam(value = "pageEnd") Integer pageEnd,
                                         HttpServletRequest request) {
        List<String> combos = (List<String>) request.getSession().getAttribute(ALL_COMBOS);
        if (pageStart < 0 || pageEnd >= combos.size()){
            throw new IllegalArgumentException("Unable to fetch combos, out of bounds");
        }
        List<String> page = svc.fetchAlphaNumericCombos(combos, pageStart, pageEnd);

        PhoneNumberResponse response = new PhoneNumberResponse();
        response.setCombos(page);
        response.setPageStart(pageStart);
        response.setPageEnd(pageEnd);
        return response;
    }
}
