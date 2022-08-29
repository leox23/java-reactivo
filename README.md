# Taller Java Reactivo

Se crea API en SpringBoot usando progracion reactiva (WebFlux) y MongoDB, , se utilizan para el projecto Mono, Flux.

Es un sistema de Hotel que maneja datos de Clientes, se aplican metodos Http para la api, conectado a un servicio de MongoDB Atlas, se hace deploy en Heroku.

[Se crea workspace en PostMan con los request para uso de la API, dar click aqui:](https://www.postman.com/winter-satellite-541952/workspace/hotel-java-webflux-taller/collection/21881181-3cdcec4d-8495-4e45-a41d-913474796e17?action=share&creator=21881181)

O si prefire, puede usar [Swagger](https://swagger.io/) para probar la API:

https://hotel-webflux.herokuapp.com/swagger-ui.html

Swager presenta error para solicitar todos los clientes de la api porque el metodo esta construido con un delay (`.delayElements(Duration.ofSeconds(1))`) lo cual **no es compatible con Swagger,** por lo cual **recomiendo consultar el metodo GET de todos los clientes desde Postman.**

​	