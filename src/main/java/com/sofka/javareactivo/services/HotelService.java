package com.sofka.javareactivo.services;

import com.sofka.javareactivo.models.Client;
import com.sofka.javareactivo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelServiceInterface) {
        this.hotelRepository = hotelServiceInterface;
    }

    public Flux<Client> getAllClients(){
        return hotelRepository.findAll().log().delayElements(Duration.ofSeconds(1));
    }

    public Flux<Client> getAllClientsBackPresure(int limitRequest){
        Flux<Client> clientFlux = hotelRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
        return clientFlux.limitRate(limitRequest);
    }

    public Mono<Client> findById(String id){
        return hotelRepository.findById(id);
    }

    public Mono<Client> postClient(Client client){
        return  hotelRepository.save(client).log();
    }

    public Mono<ResponseEntity<Client>> updateClient(String id, Client client){
        return hotelRepository.findById(id)
            .flatMap(dataClient -> {
                dataClient.setName(client.getName());
                dataClient.setSurName(client.getSurName());
                dataClient.setDocumentType(client.getDocumentType());
                dataClient.setDocument(client.getDocument());
                dataClient.setEmail(client.getEmail());
                dataClient.setComesFrom(client.getComesFrom());
                dataClient.setDate(client.getDate());
                dataClient.setRoom(client.getRoom());
                dataClient.setPrice(client.getPrice());
                dataClient.setState(client.getState());

                return hotelRepository.save(dataClient);
            })
                .map(updateClient -> new ResponseEntity<>(updateClient, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.OK));
    }

    public Mono<Client> deleteClient(String id){
        return hotelRepository.findById(id)
                .flatMap(deleteClient -> hotelRepository.delete(deleteClient)
                        .then(Mono.just(deleteClient)));
    }
}
