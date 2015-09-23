package soy.wimmer;

import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class CucumberStepdefs implements En {
    public CucumberStepdefs() {
	Given("^I have some dummy code$", () -> {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	});

	When("^I try to test it$", () -> {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	});

	Then("^it should work with cucumber-java(\\d+)$", (Integer arg1) -> {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	});
    }
}
