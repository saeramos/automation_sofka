package com.automation.runners;

import com.automation.models.ContactUsData;
import com.automation.questions.ContactUsSuccessMessage;
import com.automation.questions.PageTitle;
import com.automation.tasks.*;
import com.automation.utils.BrowserStackConfig;
import com.automation.utils.BugReporter;
import com.automation.utils.Constants;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.questions.Text;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import java.time.Duration;

@RunWith(SerenityRunner.class)
public class ContactUsTest {
    
    private Actor actor;
    private WebDriver driver;
    
    @Before
    public void setUp() {
        actor = Actor.named("Test User");
        
        if (BrowserStackConfig.isBrowserStackEnabled()) {
            try {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("browserName", "Chrome");
                caps.setCapability("browserVersion", "latest");
                caps.setCapability("os", "Windows");
                caps.setCapability("osVersion", "11");
                caps.setCapability("project", "Automation Sofka");
                caps.setCapability("build", System.getProperty("build.name", "Local Build"));
                caps.setCapability("name", System.getProperty("session.name", "Contact Us Test"));
                
                String browserStackUrl = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub",
                        BrowserStackConfig.getUsername(), BrowserStackConfig.getAccessKey());
                
                driver = new RemoteWebDriver(new URL(browserStackUrl), caps);
            } catch (Exception e) {
                System.err.println("Error connecting to BrowserStack: " + e.getMessage());
                driver = createLocalDriver();
            }
        } else {
            driver = createLocalDriver();
        }
        
        actor.can(BrowseTheWeb.with(driver));
    }
    
    private WebDriver createLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
    
    @Test
    public void testContactUsFlow() {
        try {
            ContactUsData testData = new ContactUsData(
                    "John Doe",
                    "john.doe@example.com",
                    "Test Subject",
                    "This is a test message for the Contact Us form automation.",
                    Constants.UPLOAD_FILE_PATH
            );
            
            actor.attemptsTo(
                    Open.url(Constants.BASE_URL)
            );
            
            String pageTitle = PageTitle.displayed().answeredBy(actor);
            assert pageTitle.contains(Constants.HOME_TITLE) : "Page title should contain " + Constants.HOME_TITLE;
            
            actor.attemptsTo(
                    NavigateToContactUs.fromHomePage(),
                    FillContactUsForm.withData(testData),
                    SubmitContactUsForm.andAcceptAlert()
            );
            
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(webDriver -> {
                    try {
                        webDriver.switchTo().alert().accept();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
            } catch (Exception e) {
            }
            
            try {
                String successMessage = ContactUsSuccessMessage.displayed().answeredBy(actor);
                assert successMessage.contains(Constants.SUCCESS_MESSAGE) : 
                    "Success message should contain: " + Constants.SUCCESS_MESSAGE;
                
                actor.attemptsTo(
                        NavigateToHome.fromContactUs()
                );
            } catch (AssertionError e) {
                String screenshotPath = takeScreenshot("success_message_validation_failed");
                BugReporter.reportBug(
                        "Success message not displayed correctly",
                        "The success message validation failed after submitting the Contact Us form",
                        "High",
                        "1. Navigate to Contact Us page\n2. Fill all required fields\n3. Upload a file\n4. Submit the form\n5. Accept the alert",
                        "Success message should be displayed: " + Constants.SUCCESS_MESSAGE,
                        "Success message was not found or does not match expected text",
                        screenshotPath
                );
                throw e;
            }
        } catch (Exception e) {
            String screenshotPath = takeScreenshot("test_execution_error");
            BugReporter.reportBug(
                    "Contact Us form test execution failed",
                    "An error occurred during the Contact Us form test execution: " + e.getMessage(),
                    "High",
                    "Execute the Contact Us test flow",
                    "Test should complete successfully",
                    "Test failed with exception: " + e.getMessage(),
                    screenshotPath
            );
            throw new RuntimeException(e);
        }
    }
    
    private String takeScreenshot(String name) {
        try {
            if (driver instanceof TakesScreenshot) {
                Path screenshotDir = Paths.get("reports/screenshots");
                Files.createDirectories(screenshotDir);
                Path screenshotPath = screenshotDir.resolve(name + "_" + System.currentTimeMillis() + ".png");
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Files.write(screenshotPath, screenshot);
                return screenshotPath.toString();
            }
        } catch (Exception e) {
            System.err.println("Error taking screenshot: " + e.getMessage());
        }
        return "";
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @AfterClass
    public static void generateBugReport() {
        try {
            BugReporter.generateBugReport();
        } catch (IOException e) {
            System.err.println("Error generating bug report: " + e.getMessage());
        }
    }
}

