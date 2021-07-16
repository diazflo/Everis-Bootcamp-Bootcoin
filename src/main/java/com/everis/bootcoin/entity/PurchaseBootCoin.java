package com.everis.bootcoin.entity;

import com.everis.bootcoin.entity.currency.BootCoin;
import com.everis.bootcoin.entity.wallet.Wallet;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Document
@Builder
public class PurchaseBootCoin {
    @Id
    private UUID transactionId;
    private BootCoin bootCoin;
    private BigDecimal amountToChange;
    private String paymentMethod;
    private Date createDate;
    private Date lastUpdateDate;
    private Wallet customerWallet;
}
