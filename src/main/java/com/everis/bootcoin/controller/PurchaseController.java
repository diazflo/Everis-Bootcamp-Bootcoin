package com.everis.bootcoin.controller;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import com.everis.bootcoin.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/bootCoin")
public class PurchaseController {

    private final PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping("/purchase")
    public Mono<PurchaseBootCoin> purchaseBootCoin(@RequestBody PurchaseRequest request){
        return service.purchaseBootCoin(request);
    }

    @GetMapping("/retrievePurchaseByCustomer/{dniOrPhoneNumber}")
    public Flux<PurchaseBootCoin> getAllPurchaseByCustomer(@PathVariable("dniOrPhoneNumber") String dniOrPhoneNumber){
        return service.getAllPurchaseByCustomer(dniOrPhoneNumber);
    }

    @GetMapping("/purchase")
    public Flux<PurchaseBootCoin> getAllPurchaseBootCoin(){
        return service.getAllPurchaseBootCoin();
    }

    @GetMapping("/purchase/{id}")
    public Mono<PurchaseBootCoin> getPurchaseBootCoinById(@PathVariable("id") UUID id){
        return service.getPurchaseBootCoinById(id);
    }

}
