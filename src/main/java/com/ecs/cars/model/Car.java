package com.ecs.cars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Car {

    private UUID id;

    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private String colour;
    @NotNull
    private Integer year;
}
