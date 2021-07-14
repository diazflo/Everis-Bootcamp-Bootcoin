package com.everis.bootcoin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootCoin {
    private String name;
    private BigDecimal purchaseCoin;
    private BigDecimal sellCoin;
}
