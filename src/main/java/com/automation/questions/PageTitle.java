package com.automation.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

public class PageTitle {
    public static Question<String> displayed() {
        return actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            return driver.getTitle();
        };
    }
}

