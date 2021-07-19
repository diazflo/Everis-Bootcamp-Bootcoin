package com.everis.bootcoin.service;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PurchaseService {

    Mono<PurchaseBootCoin> purchaseBootCoin(PurchaseRequest request);

    Flux<PurchaseBootCoin> getAllPurchaseByCustomer(String dniOrPhoneNumber);

    Flux<PurchaseBootCoin> getAllPurchaseBootCoin();

    Mono<PurchaseBootCoin> getPurchaseBootCoinById(UUID id);

    Mono<PurchaseBootCoin> patchPurchaseBootCoinById(String id, PurchaseBootCoin requestBody);
}
