package com.d2d.creditcardapplication.service;

import com.d2d.creditcardapplication.dto.CreditCardRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditCardServiceTest {

    static WireMockServer wireMockServer;
    @Autowired
    private  CreditCardService creditCardService;

    @DynamicPropertySource
    static void overRideBaseUrl(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("credit.rating.url",wireMockServer::baseUrl);
    }


    @BeforeAll
    public static void setUp() {

        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
    }

    @Test
    void testWireMock() {
        System.out.println(wireMockServer.baseUrl());
        assertTrue(wireMockServer.isRunning());
    }


    @Test
    public void test_credit_rating_higher_than_5() {

        //given
        wireMockServer.stubFor(WireMock.get("/getCreditRating/1234")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("8")));

        //when
        var creditCardRequest = CreditCardRequest.builder()
                .firstName("Amit")
                .lastname("Datta")
                .idNumber("1234")
                .build();

        var response = creditCardService.applyCreditCard(creditCardRequest);

        //then
        assertThat(response.equalsIgnoreCase("Approved"));

    }
    @Test
    public void test_credit_rating_less_than_5() {

        //given
        wireMockServer.stubFor(WireMock.get("/getCreditRating/1234")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("4")));

        //when
        var creditCardRequest = CreditCardRequest.builder()
                .firstName("Amit")
                .lastname("Datta")
                .idNumber("1234")
                .build();

        var response = creditCardService.applyCreditCard(creditCardRequest);

        //then
        assertThat(response.equalsIgnoreCase("Rejected"));

    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }
}
