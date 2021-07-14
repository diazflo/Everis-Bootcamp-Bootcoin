package com.everis.bootcoin.controller;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import com.everis.bootcoin.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
