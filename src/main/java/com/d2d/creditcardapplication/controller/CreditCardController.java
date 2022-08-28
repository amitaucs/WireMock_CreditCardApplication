package com.d2d.creditcardapplication.controller;

import com.d2d.creditcardapplication.dto.CreditCardRequest;
import com.d2d.creditcardapplication.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("applyCreditCard")
    public ResponseEntity<String> applyCreditCard(@RequestBody CreditCardRequest creditCardRequest){

         if(null == creditCardRequest){
             return ResponseEntity.badRequest().body("Please provide applicant details");
         }else{
            var response = creditCardService.applyCreditCard(creditCardRequest);
            return ResponseEntity.ok(response);
         }

    }
}
