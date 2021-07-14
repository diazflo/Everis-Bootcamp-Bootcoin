package com.everis.bootcoin.entity;

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
}
