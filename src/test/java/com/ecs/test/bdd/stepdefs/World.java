package com.ecs.test.bdd.stepdefs;

import com.ecs.cars.model.Car;
import lombok.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class World {

    private UUID carId;
    private Car existingCar;
    private ResponseEntity<Car> response;
    private ResponseEntity<String> responseString;
    private HttpStatus errorStatus;

}
