package com.sofka.javareactivo.repository;

import com.sofka.javareactivo.models.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends ReactiveMongoRepository<Client, String> {
}