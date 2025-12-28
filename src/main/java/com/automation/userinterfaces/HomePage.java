package com.automation.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class HomePage {
    public static final Target PAGE_TITLE = Target.the("Home page title")
            .located(By.xpath("//title[contains(text(), 'Automation Exercise')]"));
    
    public static final Target CONTACT_US_LINK = Target.the("Contact Us link")
            .located(By.xpath("//a[contains(@href, 'contact_us')] | //a[contains(text(), 'Contact us')] | //a[contains(text(), 'Contact Us')]"));
}

