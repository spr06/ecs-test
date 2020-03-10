package com.ecs.cars.controller;

import com.ecs.cars.exception.NotFoundException;
import com.ecs.cars.model.Car;
import com.ecs.cars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CarsController {

    private final CarService carService;

    @GetMapping("/get-car/{id}")
    public ResponseEntity<Car> getCar(@NotNull @PathVariable("id") UUID id) {
        return ResponseEntity.of(carService.getCar(id));
    }

    @PostMapping("/create-car")
    public ResponseEntity<Car> createOrUpdate(@Valid @RequestBody Car car) {
        return ResponseEntity.of(carService.createOrUpdateCar(car));
    }

    @DeleteMapping("/delete-car/{id}")
    public void deleteCar(@NotNull @PathVariable("id") UUID id) {
        carService.deleteCar(id);
    }
}
