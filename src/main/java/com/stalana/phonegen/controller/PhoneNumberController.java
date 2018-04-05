package com.stalana.phonegen.controller;

import com.stalana.phonegen.model.PhoneNumberRequest;
import com.stalana.phonegen.service.PhoneGenOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
     * @param phoneGenRequest phoneNumber and pagination results
     * @return
     */
    @RequestMapping( value="/generate",method=RequestMethod.POST)
    public List<String> generateAlphaNumerics (@RequestBody PhoneNumberRequest phoneGenRequest) {
        svc.generateAlphaNumerics(phoneGenRequest.getPhoneNumber());
        List<String> result = svc.fetchAlphaNumericCombos(0,phoneGenRequest.getNumberPerPage());
        return result;
    }
}
