package com.everis.bootcoin.entity.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    private UUID id;
    private Person person;
}
