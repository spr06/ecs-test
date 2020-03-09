package com.ecs.cars.controller;

import com.ecs.cars.model.Car;
import com.ecs.cars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CarsController {

    private final CarService carService;

    @PostMapping("/create-car")
    public ResponseEntity<Car> create(@Valid @RequestBody Car car) {
        return ResponseEntity.of(carService.createCar(car));
    }

}
