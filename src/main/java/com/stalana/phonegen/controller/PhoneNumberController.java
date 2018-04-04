package com.stalana.phonegen.controller;

import com.stalana.phonegen.service.PhoneGenOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by stan on 4/4/18.
 */
@RestController
public class PhoneNumberController {


    @Autowired
    PhoneGenOperations svc;

    /**
     * Generate alphanumeric combinations based on the input phone number
     * @param phoneNum
     * @return
     */
    @RequestMapping("/generate")
    public List<String> generateAlphaNumerics (@RequestParam (name="phoneNumber") String phoneNum , @RequestParam(name="itemsPerPage") Integer numbersPerPage) {
         svc.generateAlphaNumerics(phoneNum);
         return svc.fetchAlphaNumericCombos(0,numbersPerPage );
    }
}
