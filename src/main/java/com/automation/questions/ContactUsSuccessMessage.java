package com.automation.questions;

import com.automation.userinterfaces.ContactUsPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ContactUsSuccessMessage {
    public static Question<String> displayed() {
        return actor -> Text.of(ContactUsPage.SUCCESS_MESSAGE).answeredBy(actor);
    }
}

