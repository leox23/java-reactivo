package com.sofka.javareactivo.models;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Document(collection = "cliente")
public class Client {
    @Id
    private String id;

    @NotBlank
    @Size(min=5, max = 10)
    private String name;

    @NotBlank
    @Size(min=3, max = 16)
    private String surName;

    @NotBlank
    @Size(min=2, max = 8)
    private String documentType;

    @NotBlank
    @Size(min=5, max = 10)
    private String document;

    @NotBlank
    @Size(min=6, max = 22)
    private String email;

    @NotBlank
    @Size(min=4, max = 16)
    private String comesFrom;

    @NotNull
    @DateTimeFormat(pattern="yyyy-mm-ddT:00:00:00.000+00:00")
    private Date date;

    @NotNull
    private Number room;

    @NotNull
    private Number price;

    @NotNull
    private Number state;

    public Client(String id, String name, String surName, String documentType, String document, String email, String comesFrom, Date date, Number room, Number price, Number state) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.documentType = documentType;
        this.document = document;
        this.email = email;
        this.comesFrom = comesFrom;
        this.date = date;
        this.room = room;
        this.price = price;
        this.state = state;
    }
}
