package com.everis.bootcoin.service;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import com.everis.bootcoin.entity.PurchaseRequest;
import com.everis.bootcoin.entity.currency.BootCoin;
import com.everis.bootcoin.entity.types.StatusPurchaseBootCoin;
import com.everis.bootcoin.entity.wallet.Wallet;
import com.everis.bootcoin.repository.BootCoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Value("${lookup.service.uri.walletperson}")
    private static String WALLET_PERSON_URI;
    @Value("${lookup.service.uri.bootcoin}")
    private static String BOOTCOIN_URI;

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
        Mono<BootCoin> coinMono = getBootCoin();
        Mono<Wallet> walletMono = getPersonWallet(request.getCustomerToPay().getPhoneNumber());

        PurchaseBootCoin bootCoin = PurchaseBootCoin.builder().build();
        return Mono.zip(coinMono, walletMono).map(objects -> {
            bootCoin.setTransactionId(UUID.randomUUID());
            bootCoin.setPhoneNumberApplicant(request.getPhoneNumberApplicant());

            bootCoin.setAmountToChange(request.getAmountRequest());
            bootCoin.setPaymentMethod(request.getPaymentMethod());
            bootCoin.setCreateDate(new Date());
            bootCoin.setLastUpdateDate(new Date());
            bootCoin.setBootCoin(objects.getT1());
            bootCoin.setCustomerWallet(objects.getT2());
            bootCoin.setStatusPurchase(StatusPurchaseBootCoin.PENDENT);

            return bootCoin;
        }).flatMap(repository::save);
    }

    @Override
    public Flux<PurchaseBootCoin> getAllPurchaseByCustomer(String dniOrPhoneNumber) {
        Mono<Wallet> walletMono = getPersonWallet(dniOrPhoneNumber);
        return walletMono.flatMapMany(wallet -> repository.findAllByCustomerWalletId(wallet.getId()));
    }

    @Override
    public Flux<PurchaseBootCoin> getAllPurchaseBootCoin() {
        return repository.findAll();
    }

    @Override
    public Mono<PurchaseBootCoin> getPurchaseBootCoinById(UUID id) {
        return repository.findById(id);
    }

    public Mono<BootCoin> getBootCoin() {
        log.info("uri " + BOOTCOIN_URI + "/currency/bootcoin/" + CURRENCY_BOOT_COIN);
        return builder.build()
                .get()
                .uri(BOOTCOIN_URI + "/currency/bootcoin/" + CURRENCY_BOOT_COIN)
                .retrieve()
                .bodyToMono(BootCoin.class);
    }

    public Mono<Wallet> getPersonWallet(String valueToFind) {
        log.info("uri " + WALLET_PERSON_URI + "/person/retrieveWalletByPerson/" + valueToFind);
        return builder.build()
                .get()
                .uri(WALLET_PERSON_URI + "/person/retrieveWalletByPerson/" + valueToFind)
                .retrieve()
                .bodyToMono(Wallet.class);
    }

    @Override
    public Mono<PurchaseBootCoin> patchPurchaseBootCoinById(String id, PurchaseBootCoin requestBody) {
        return repository.findById(UUID.fromString(id)).flatMap(purchaseBootCoin -> {
            purchaseBootCoin.setPaymentMethod((requestBody.getPaymentMethod() != null) ? requestBody.getPaymentMethod() : purchaseBootCoin.getPaymentMethod());
            purchaseBootCoin.setAmountToChange((requestBody.getAmountToChange() != null) ? requestBody.getAmountToChange() : purchaseBootCoin.getAmountToChange());
            purchaseBootCoin.setStatusPurchase((requestBody.getStatusPurchase() != null) ? requestBody.getStatusPurchase() : purchaseBootCoin.getStatusPurchase());
            purchaseBootCoin.setLastUpdateDate(new Date());
            return repository.save(purchaseBootCoin);
        }).switchIfEmpty(Mono.error(new Exception()));
    }
}
