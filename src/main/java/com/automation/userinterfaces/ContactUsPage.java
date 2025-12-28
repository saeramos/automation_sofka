package com.automation.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ContactUsPage {
    public static final Target CONTACT_US_TITLE = Target.the("Contact Us title")
            .located(By.xpath("//h2[contains(text(), 'Get In Touch')]"));
    
    public static final Target NAME_INPUT = Target.the("Name input field")
            .located(By.name("name"));
    
    public static final Target EMAIL_INPUT = Target.the("Email input field")
            .located(By.name("email"));
    
    public static final Target SUBJECT_INPUT = Target.the("Subject input field")
            .located(By.name("subject"));
    
    public static final Target MESSAGE_TEXTAREA = Target.the("Message textarea")
            .located(By.name("message"));
    
    public static final Target UPLOAD_FILE_INPUT = Target.the("Upload file input")
            .located(By.name("upload_file"));
    
    public static final Target SUBMIT_BUTTON = Target.the("Submit button")
            .located(By.xpath("//input[@type='submit' and @value='Submit']"));
    
    public static final Target SUCCESS_MESSAGE = Target.the("Success message")
            .located(By.xpath("//div[contains(@class, 'status') and contains(text(), 'Success!')] | //div[contains(text(), 'Success! Your details have been submitted successfully.')]"));
    
    public static final Target HOME_BUTTON = Target.the("Home button")
            .located(By.xpath("//a[contains(@href, '/') and (contains(text(), 'Home') or contains(@class, 'home'))] | //a[@href='/'] | //i[@class='fa fa-home']/parent::a"));
}

