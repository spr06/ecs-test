package com.ecs.test.bdd.stepdefs;

import com.ecs.cars.model.Car;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RequestSteps {

    private World world = new World();

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String SERVER_URL = "http://localhost";
    private final String CREATE_CAR_ENDPOINT = "/v1/create-car";

    @Given("^I have valid car details with (.+), (.+), (.+), (\\d+)$")
    public void validCar(String make, String model, String color, Integer year) {
        world.setInputCar(new Car(null, make, model, color, year));
    }

    @When("^I use add api to add the car$")
    public void addCar() {
        postCar(world.getInputCar());
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

    private void postCar(Car validCar) {
        final ResponseEntity<Car> carResponseEntity = this.post(validCar);
        world.setResponse(carResponseEntity);

    }

    private String apiEndpoint() {
        return SERVER_URL + ":" + port + CREATE_CAR_ENDPOINT;
    }

    private ResponseEntity<Car> post(final Car car) {
        return restTemplate.postForEntity(apiEndpoint(), car, Car.class);
    }


}
