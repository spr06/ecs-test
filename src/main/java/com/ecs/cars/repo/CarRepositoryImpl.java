package com.ecs.cars.repo;

import com.ecs.cars.model.Car;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CarRepositoryImpl implements CarRepository {

    private static final Map<UUID, Car> carMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Car> getCar(UUID id) {
        return Optional.of(carMap.get(id));
    }

    @Override
    public Optional<Car> createCar(Car car) {
        final UUID key = UUID.randomUUID();
        final Car newCar = getCar(car, key);
        carMap.put(key, newCar);
        return Optional.of(newCar);
    }

    @Override
    public Optional<Car> updateCar(Car car) {
        final Car existingCar = getCar(car, car.getId());
        carMap.put(car.getId(), existingCar);
        return Optional.of(existingCar);
    }

    private Car getCar(Car car, UUID id) {
        return Car.builder()
                .id(id)
                .make(car.getMake())
                .model(car.getModel())
                .colour(car.getColour())
                .year(car.getYear())
                .build();
    }

    @Override
    public void deleteCar(UUID uuid) {
        carMap.remove(uuid);
    }
}
