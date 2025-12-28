package com.automation.tasks;

import com.automation.userinterfaces.ContactUsPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class NavigateToHome {
    public static Performable fromContactUs() {
        return Task.where("{0} navigates to Home page",
                Click.on(ContactUsPage.HOME_BUTTON)
        );
    }
}

