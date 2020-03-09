package com.ecs.test.bdd.stepdefs;

import com.ecs.cars.model.Car;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class CarHttpClient {

    private final String SERVER_URL = "http://localhost";
    private final String CARS_ENDPOINT = "/cars";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String thingsEndpoint() {
        return SERVER_URL + ":" + port + CARS_ENDPOINT;
    }

    public ResponseEntity<Car> post(final Car car) {
        return restTemplate.postForEntity(thingsEndpoint(), car, Car.class);
    }

}
