package com.automation.tasks;

import com.automation.userinterfaces.HomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class NavigateToContactUs {
    public static Performable fromHomePage() {
        return Task.where("{0} navigates to Contact Us page",
                Click.on(HomePage.CONTACT_US_LINK)
        );
    }
}

