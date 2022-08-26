package com.sofka.javareactivo.controller;

import com.sofka.javareactivo.models.Client;
import com.sofka.javareactivo.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequestMapping("/reactive/api/clients")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService bookService) {
        this.hotelService = bookService;
    }


    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Client> getAllClients(){
        return hotelService.getAllClients().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<Client> findById(@PathVariable String id){
        return hotelService.findById(id);
    }

    @PostMapping("/")
    public Mono<Client> postBook(@RequestBody @Valid Client client) {
        return hotelService.postClient(client).log();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>>  updateBook(@PathVariable @Valid String id , @RequestBody Client client) {
        return hotelService.updateClient(id, client);

    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBookById(@PathVariable String id){
        return hotelService.deleteClient(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
