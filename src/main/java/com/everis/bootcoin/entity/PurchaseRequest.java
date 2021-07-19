package com.everis.bootcoin.entity;

import com.everis.bootcoin.entity.wallet.Person;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
@Builder
public class PurchaseRequest {
    private String paymentMethod;
    private BigDecimal amountRequest;
    private Person customerToPay;
    private String phoneNumberApplicant;
}
