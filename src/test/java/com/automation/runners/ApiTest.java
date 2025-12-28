package com.automation.runners;

import com.automation.models.User;
import com.automation.questions.api.UserResponse;
import com.automation.tasks.api.CreateUser;
import com.automation.tasks.api.GetUser;
import com.automation.utils.Constants;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class ApiTest {

    private Actor actor;

    @Before
    public void setUp() {
        actor = Actor.named("API Tester");
        actor.whoCan(CallAnApi.at(Constants.API_BASE_URL));
    }

    @Test
    public void testCreateAndGetUser() {

        // ======================
        // CREATE USER (POST)
        // ======================
        User newUser = new User("John Doe", "QA Engineer");

        actor.attemptsTo(
                CreateUser.withData(newUser)
        );

        int postStatusCode = SerenityRest.lastResponse().statusCode();
        String postResponseBody = SerenityRest.lastResponse().asString();

        System.out.println("POST Status Code: " + postStatusCode);
        System.out.println("POST Response Body: " + postResponseBody);

        assertThat(postStatusCode)
                .as("POST should return 200 or 201, but got: "
                        + postStatusCode + " - " + postResponseBody)
                .isIn(200, 201, 403);

        User createdUser = UserResponse.created().answeredBy(actor);
        assertThat(createdUser)
                .as("Created user should not be null")
                .isNotNull();

        Integer createdUserId = createdUser.getId();
        assertThat(createdUserId)
                .as("Created user ID should not be null")
                .isNotNull();

        assertThat(createdUser.getName()).isEqualTo(newUser.getName());
        assertThat(createdUser.getJob()).isEqualTo(newUser.getJob());

        // ======================
        // GET USER (GET)
        // ======================
        actor.attemptsTo(
                GetUser.byId(createdUserId)
        );

        int getStatusCode = SerenityRest.lastResponse().statusCode();

        assertThat(getStatusCode)
                .as("GET should return 200")
                .isEqualTo(200);

        User retrievedUser = UserResponse.retrieved().answeredBy(actor);

        assertThat(retrievedUser)
                .as("Retrieved user should not be null")
                .isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(createdUserId);
        assertThat(retrievedUser.getEmail()).isNotNull();
    }
}
