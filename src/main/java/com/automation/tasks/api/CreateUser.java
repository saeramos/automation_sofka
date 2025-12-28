package com.automation.tasks.api;

import com.automation.models.User;
import com.automation.utils.Constants;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.HashMap;
import java.util.Map;

public class CreateUser {
    public static Performable withData(User user) {
        return Task.where("{0} creates a user with data",
                Post.to("/users")
                        .with(request -> {
                            Map<String, String> body = new HashMap<>();
                            body.put("name", user.getName());
                            body.put("job", user.getJob());
                            return request
                                    .header("Content-Type", "application/json")
                                    .body(body);
                        })
        );
    }
}

