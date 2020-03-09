package com.ecs.cars.repo;

import com.ecs.cars.model.Car;

import java.util.Optional;
import java.util.UUID;

public interface CarRepository {

    Optional<Car> getCar(UUID id);

    Optional<Car> createCar(Car car);

    Optional<Car> updateCar(Car car);

    void deleteCar(UUID uuid);

}
