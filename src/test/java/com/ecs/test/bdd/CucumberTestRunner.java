package com.ecs.test.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        plugin = {"pretty", "html:target/cucumber/cars"},
        features = "classpath:features",
        extraGlue = "com.ecs.test.bdd.stepdefs"
)
public class CucumberTestRunner {

}
