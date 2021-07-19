package com.everis.bootcoin.repository;

import com.everis.bootcoin.entity.PurchaseBootCoin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface BootCoinRepository<T extends PurchaseBootCoin> extends ReactiveMongoRepository<T, UUID> {
    Flux<T> findAllByCustomerWalletId(UUID id);
}
