package com.everis.bootcoin.entity.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String dni;
    private String name;
    private String phoneNumber;
    private String phoneImei;
    private String email;
}
