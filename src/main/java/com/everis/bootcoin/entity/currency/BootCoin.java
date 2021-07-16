package com.everis.bootcoin.entity.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

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
