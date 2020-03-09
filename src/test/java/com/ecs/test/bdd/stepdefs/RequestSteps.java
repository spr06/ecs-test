package com.ecs.test.bdd.stepdefs;

import com.ecs.cars.model.Car;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RequestSteps {

    private World world ;

    @Before
    public void init() {
        world = new World();
    }


    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String SERVER_URL = "http://localhost";
    private final String CREATE_CAR_ENDPOINT = "/v1/create-car";

    @Given("^I have car details with (.+), (.+), (.+), (\\d+)$")
    public void validCar(String make, String model, String color, Integer year) {
        world.setInputCar(new Car(null, make, model, color, year));
    }

    @When("^I use add api to add the car$")
    public void addCar() {
        createOrUpdate(world.getInputCar());
    }

    @Then("^car is added$")
    public void carAdded() {
        final Car carAdded = world.getResponse().getBody();

        final Car inputCar = world.getInputCar();

        assertThat(carAdded.getId(), is(notNullValue()));
        assertThat(carAdded.getColour(), equalTo((inputCar.getColour())));
        assertThat(carAdded.getMake(), equalTo((inputCar.getMake())));
        assertThat(carAdded.getYear(), equalTo((inputCar.getYear())));
        assertThat(carAdded.getModel(), equalTo((inputCar.getModel())));
    }

    @Then("^I receive response code (\\d+)$")
    public void responseCode(Integer status) {
        assertThat( world.getResponse().getStatusCode().value(), equalTo(status));
    }

    @Then("notFound status code is received")
    public void notFoundError() {
        assertThat(world.getErrorStatus(), equalTo(HttpStatus.NOT_FOUND));
    }

    private void createOrUpdate(Car validCar) {
        try {
            final ResponseEntity<Car> carResponseEntity = this.post(validCar);
            world.setResponse(carResponseEntity);
        } catch (HttpClientErrorException e){
            world.setErrorStatus(HttpStatus.valueOf(e.getRawStatusCode()));
        }
    }

    private String apiEndpoint() {
        return SERVER_URL + ":" + port + CREATE_CAR_ENDPOINT;
    }

    private ResponseEntity<Car> post(final Car car) {
        return restTemplate.postForEntity(apiEndpoint(), car, Car.class);
    }

    @Given("I have a valid car tesla model x colour grey 2010")
    public void iHaveValidCarDetailsOfTeslaModelXColourGrey() {
        createOrUpdate(new Car(null, "Tesla", "modelX", "grey", 2010));
    }

    @When("I use add api to update it to tesla model x colour black 2010")
    public void iUseAddApiToUpdateItToTeslaModelXColourBlack() {
        final Car existingCar = world.getResponse().getBody();
        world.setInputCar(existingCar);
        createOrUpdate(new Car(existingCar.getId(), existingCar.getMake(), existingCar.getModel(), "Black", existingCar.getYear()));
    }

    @Then("car is updated correctly")
    public void carIsUpdatedCorrectly() {
        assertThat(world.getResponse().getBody().getColour(), equalTo("Black"));
    }

    @When("I use add api to update a random non existent car")
    public void iUseAddApiToUpdateARandomNonExistentCar() {
        createOrUpdate(new Car(UUID.randomUUID(), "tesla", "Y", "red", 2000));
    }
}
