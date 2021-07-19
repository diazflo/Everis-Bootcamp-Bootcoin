package com.everis.bootcoin.service;

import com.everis.bootcoin.entity.currency.BootCoin;
import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import com.everis.bootcoin.entity.wallet.Wallet;
import com.everis.bootcoin.repository.BootCoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private static String CURRENCY_BOOT_COIN = "BootCoin";
    private final BootCoinRepository<PurchaseBootCoin> repository;
    private final WebClient.Builder builder;

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

            obj.setAmountToChange(request.getAmountRequest());
            obj.setPaymentMethod(request.getPaymentMethod());
            obj.setCreateDate(new Date());
            obj.setLastUpdateDate(new Date());

            Mono<BootCoin> coinMono = getBootCoin();
            Mono<Wallet> walletMono = getPersonWallet(request.getCustomerToPay().getPhoneNumber());

            coinMono.subscribe(obj::setBootCoin);
            walletMono.subscribe(obj::setCustomerWallet);

            return obj;
        }).flatMap(purchaseBootCoin -> {
            System.out.println(purchaseBootCoin);
            return repository.save(purchaseBootCoin);
        });
    }

    @Override
    public Flux<PurchaseBootCoin> getAllPurchaseByCustomer(String dniOrPhoneNumber) {
        Flux<PurchaseBootCoin> fluxPurchase;
        if(dniOrPhoneNumber.length()==8){
            //fluxPurchase = repository.findAllPurchaseBootCoinByWalletPersonDni(dniOrPhoneNumber);
        } else {
            //fluxPurchase = repository.findAllPurchaseBootCoinByWalletPersonPhoneNumber(dniOrPhoneNumber);
        }
        return null;
    }

    public Mono<BootCoin> getBootCoin() {
        log.info("uri " + "localhost:8089/currency/bootcoin/" + CURRENCY_BOOT_COIN);
        return builder.build()
                .get()
                .uri("localhost:8089/currency/bootcoin/" + CURRENCY_BOOT_COIN)
                .retrieve()
                .bodyToMono(BootCoin.class);
    }

    public Mono<Wallet> getPersonWallet(String valueToFind) {
        log.info("uri " + "localhost:8086/person/retrieveWalletByPerson/" + valueToFind);
        return builder.build()
                .get()
                .uri("localhost:8086/person/retrieveWalletByPerson/" + valueToFind)
                .retrieve()
                .bodyToMono(Wallet.class);
    }

}
