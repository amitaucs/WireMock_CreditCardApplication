package com.d2d.creditcardapplication.dto;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class CreditCardRequest {

   private final String firstName;
   private final String lastname;
   private final String idNumber;
}
