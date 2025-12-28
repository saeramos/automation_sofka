package com.automation.tasks.api;

import com.automation.utils.Constants;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetUser {
    public static Performable byId(Integer userId) {
        return Task.where("{0} gets user by id " + userId,
                Get.resource("/users/{id}")
                        .with(request -> request.pathParam("id", userId))
        );
    }
}

