package com.automation.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import com.automation.userinterfaces.ContactUsPage;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;

public class SubmitContactUsForm {
    public static Performable andAcceptAlert() {
        return Task.where("{0} submits the Contact Us form and accepts alert",
                WaitUntil.the(ContactUsPage.SUBMIT_BUTTON, WebElementStateMatchers.isVisible()),
                Click.on(ContactUsPage.SUBMIT_BUTTON)
        );
    }
}

