package com.automation.questions.api;

import com.automation.models.User;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class UserResponse {
    public static Question<User> created() {
        return actor -> SerenityRest.lastResponse().as(User.class);
    }
    
    public static Question<Integer> createdUserId() {
        return actor -> {
            User user = SerenityRest.lastResponse().as(User.class);
            return user.getId();
        };
    }
    
    public static Question<User> retrieved() {
        return actor -> {
            return SerenityRest.lastResponse()
                    .jsonPath()
                    .getObject("data", User.class);
        };
    }
}

