package com.everis.bootcoin.service;

import com.everis.bootcoin.entity.BootCoin;
import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import com.everis.bootcoin.repository.BootCoinRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static String CURRENCY_BOOT_COIN = "BootCoin";
    private final BootCoinRepository<PurchaseBootCoin> repository;
    private final WebClient.Builder builder;
    @Getter
    @Setter
    private BootCoin objBootcoin;

    @Autowired
    public PurchaseServiceImpl(BootCoinRepository<PurchaseBootCoin> repository, WebClient.Builder builder) {
        this.repository = repository;
        this.builder = builder;
    }

    @Override
    public Mono<PurchaseBootCoin> purchaseBootCoin(PurchaseRequest request) {
        PurchaseBootCoin bootCoin = PurchaseBootCoin.builder().build();
        return Mono.just(bootCoin).map(obj -> {
            obj.setTransactionId(UUID.randomUUID());
            Mono<BootCoin> coinMono = builder.build()
                    .get()
                    .uri("localhost:8089/currency/bootcoin/" + CURRENCY_BOOT_COIN)
                    .retrieve()
                    .bodyToMono(BootCoin.class);
            log.info("uri " + "localhost:8089/currency/bootcoin/" + CURRENCY_BOOT_COIN);

            coinMono.subscribe(obj::setBootCoin);

            obj.setAmountToChange(request.getAmountRequest());
            obj.setPaymentMethod(request.getPaymentMethod());
            obj.setCreateDate(new Date());
            obj.setLastUpdateDate(new Date());
            return obj;
        }).flatMap(repository::save);
    }

    public Mono<BootCoin> retrieveBootCoin() {
        return builder.build()
                .get()
                .uri("localhost:8089/currency/bootcoin/" + CURRENCY_BOOT_COIN)
                .retrieve()
                .bodyToMono(BootCoin.class);
    }
}
