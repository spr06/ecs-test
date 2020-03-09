package com.ecs.cars.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Car {

    private UUID id;

    @NotEmpty
    private String make;

    @NotEmpty
    private String model;

    @NotEmpty
    private String colour;

    @NotNull
    private Integer year;
}
