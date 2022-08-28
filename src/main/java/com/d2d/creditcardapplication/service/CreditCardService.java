package com.d2d.creditcardapplication.service;

import com.d2d.creditcardapplication.dto.CreditCardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CreditCardService {

    @Value("${credit.rating.url}")
    private String creditRatingUrl;
    private final RestTemplate restTemplate;

    public CreditCardService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String applyCreditCard(CreditCardRequest creditCardRequest){

        var rating = restTemplate.getForObject(creditRatingUrl+"/getCreditRating/"+creditCardRequest.getIdNumber(), Integer.class);
        log.info("** Rating is : {}", rating );
        return rating >= 5? "Approved" : "Rejected";
    }
}
