package com.everis.bootcoin.service;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseService {

    Mono<PurchaseBootCoin> purchaseBootCoin(PurchaseRequest request);

    Flux<PurchaseBootCoin> getAllPurchaseByCustomer(String dniOrPhoneNumber);
}
