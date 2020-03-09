package com.ecs.cars.service;

import com.ecs.cars.exception.NotFoundException;
import com.ecs.cars.model.Car;
import com.ecs.cars.repo.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> getCar(UUID id) {
        return carRepository.getCar(id);
    }

    public Optional<Car> createOrUpdateCar(Car car) {
        if (car.getId() == null) {
            return carRepository.createCar(car);
        } else if (carExists(car.getId())) {
            return carRepository.updateCar(car);
        }
        throw new NotFoundException("Invalid car key");
    }

    private boolean carExists(UUID id) {
        return carRepository.getCar(id).isPresent();
    }

    public void deleteCar(UUID id) {
        if (carExists(id)) {
            carRepository.deleteCar(id);
        }
        throw new IllegalArgumentException("Invalid car key");
    }

}
