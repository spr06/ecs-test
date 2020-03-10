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
    private final String GET_CAR_ENDPOINT = "/v1/get-car";
    private final String DELETE_CAR_ENDPOINT = "/v1/delete-car";

    @Given("^I have car details with (.+), (.+), (.+), (\\d+)$")
    public void validCar(String make, String model, String color, Integer year) {
        world.setExistingCar(new Car(null, make, model, color, year));
    }

    @When("^I use add api to add the car$")
    public void addCar() {
        createOrUpdate(world.getExistingCar());
    }

    @Then("^car is added$")
    public void carAdded() {
        final Car carAdded = world.getResponse().getBody();

        final Car inputCar = world.getExistingCar();

        assertCar(carAdded, inputCar);
    }

    private void assertCar(Car carFetchedByApi, Car inputCar) {
        assertThat(carFetchedByApi.getId(), is(notNullValue()));
        assertThat(carFetchedByApi.getColour(), equalTo((inputCar.getColour())));
        assertThat(carFetchedByApi.getMake(), equalTo((inputCar.getMake())));
        assertThat(carFetchedByApi.getYear(), equalTo((inputCar.getYear())));
        assertThat(carFetchedByApi.getModel(), equalTo((inputCar.getModel())));
    }

    @Then("^I receive response code (\\d+)$")
    public void responseCode(Integer status) {
        assertThat( world.getResponse().getStatusCode().value(), equalTo(status));
    }

    @Then("notFound status code is received")
    public void notFoundError() {
        assertThat(world.getErrorStatus(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Given("I have a valid car tesla model x colour grey 2010")
    public void iHaveValidCarDetailsOfTeslaModelXColourGrey() {
        createOrUpdate(new Car(null, "Tesla", "modelX", "grey", 2010));
    }

    @When("I use add api to update it to tesla model x colour black 2010")
    public void iUseAddApiToUpdateItToTeslaModelXColourBlack() {
        final Car existingCar = world.getResponse().getBody();
        world.setExistingCar(existingCar);
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

    @Then("car is retrieved correctly")
    public void carIsRetrievedCorrectly() {
        assertCar(world.getResponse().getBody(), world.getExistingCar());
    }

    @When("I use retrieve api to get it")
    public void iUseRetrieveApiToGetIt() {
        setExistingCar();

        get(world.getExistingCar().getId());
    }

    @When("I use retrieve api to get a car with a non existent uuid")
    public void iUseRetrieveApiToGetACarWithANonExistentUuid() {
        get(UUID.randomUUID());
    }

    @When("I use delete api to delete the car")
    public void iUseDeleteApiToDeleteTheCar() {
        setExistingCar();
        delete(world.getExistingCar().getId());
    }

    @When("I use delete api to delete non existant car")
    public void iUseDeleteApiToDeleteNonExistantCar() {
        delete(UUID.randomUUID());
    }

    private void setExistingCar() {
        final Car existingCar = world.getResponse().getBody();
        world.setExistingCar(existingCar);
    }

    private void createOrUpdate(Car validCar) {
        try {
            final ResponseEntity<Car> carResponseEntity = this.post(validCar);
            world.setResponse(carResponseEntity);
        } catch (HttpClientErrorException e){
            world.setErrorStatus(HttpStatus.valueOf(e.getRawStatusCode()));
        }
    }

    private String apiPostEndpoint() {
        return SERVER_URL + ":" + port + CREATE_CAR_ENDPOINT;
    }

    private String apiGetEndpoint(UUID uuid) {
        return SERVER_URL + ":" + port + GET_CAR_ENDPOINT + "/" + uuid.toString();
    }

    private String apiDeleteEndpoint(UUID uuid) {
        return SERVER_URL + ":" + port + DELETE_CAR_ENDPOINT + "/" + uuid.toString();
    }

    private ResponseEntity<Car> post(final Car car) {
        return restTemplate.postForEntity(apiPostEndpoint(), car, Car.class);
    }

    private ResponseEntity<Car> get(final UUID uuid) {
        try {
            final ResponseEntity<Car> forEntity = restTemplate.getForEntity(apiGetEndpoint(uuid), Car.class);
            world.setResponse(forEntity);
            return forEntity;
        } catch (HttpClientErrorException e){
            world.setErrorStatus(HttpStatus.valueOf(e.getRawStatusCode()));
        }
        return null;
    }

    private ResponseEntity<String> delete(final UUID uuid) {
        try{
            final ResponseEntity<String> responseEntity = restTemplate.exchange(apiDeleteEndpoint(uuid), HttpMethod.DELETE, new HttpEntity<>(HttpHeaders.ACCEPT), String.class);
            world.setResponseString(responseEntity);
            return responseEntity;
        }  catch (HttpClientErrorException e){
            world.setErrorStatus(HttpStatus.valueOf(e.getRawStatusCode()));
        }
        return null;
    }

}
