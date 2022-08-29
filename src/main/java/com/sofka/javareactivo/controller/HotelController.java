package com.sofka.javareactivo.controller;

import com.sofka.javareactivo.models.Client;
import com.sofka.javareactivo.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PersistentPropertyAccessor;
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
@RequestMapping("clients")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService bookService) {
        this.hotelService = bookService;
    }

    //documentar mensajes de respuesta de http
    @Operation(summary = "Obtener todos los clientes guardados. Usar mejor en Postman, o directamente (Aqui en Swagger genera error porque la peticion esta contruida con (.delayElements(Duration.ofSeconds(1)) y parece no se compatible con Swagger")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Clientes Obtenidos.",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Client.class))})
    })
    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Client> getAllClients(){
        return hotelService.getAllClients().delayElements(Duration.ofSeconds(1));
    }

    @Operation(summary = "Para obtener un Cliente a travez del ID exacto. (ID requerido)")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Client Obtenido.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @GetMapping("/{id}")
    public Mono<Client> findById(@PathVariable String id){
        return hotelService.findById(id);
    }

    @Operation(summary = "Agregar nuevo cliente. (Body requerido)")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Cliente agregado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @PostMapping("/")
    public Mono<Client> postClient(@Valid @RequestBody Client client) {
        return hotelService.postClient(client).log();
    }

    @Operation(summary = "Para actualizar datos cliente. (ID cliente y Body con los datos a actualizar son requeridos)")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Datos cliente actualizados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>>  updateClient(@Valid @PathVariable String id , @RequestBody Client client) {
        return hotelService.updateClient(id, client);

    }


    @Operation(summary = "Para borrar cliente (no reversible). (ID Cliente requerido)")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Cliente borrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClientById(@PathVariable String id){
        return hotelService.deleteClient(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
