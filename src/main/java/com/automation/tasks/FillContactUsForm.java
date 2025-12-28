package com.automation.tasks;

import com.automation.models.ContactUsData;
import com.automation.userinterfaces.ContactUsPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Upload;

import java.nio.file.Paths;

public class FillContactUsForm {
    public static Performable withData(ContactUsData data) {
        return Task.where("{0} fills the Contact Us form with data",
                Clear.field(ContactUsPage.NAME_INPUT),
                Enter.theValue(data.getName()).into(ContactUsPage.NAME_INPUT),
                Clear.field(ContactUsPage.EMAIL_INPUT),
                Enter.theValue(data.getEmail()).into(ContactUsPage.EMAIL_INPUT),
                Clear.field(ContactUsPage.SUBJECT_INPUT),
                Enter.theValue(data.getSubject()).into(ContactUsPage.SUBJECT_INPUT),
                Clear.field(ContactUsPage.MESSAGE_TEXTAREA),
                Enter.theValue(data.getMessage()).into(ContactUsPage.MESSAGE_TEXTAREA),
                Upload.theFile(Paths.get(data.getFilePath())).to(ContactUsPage.UPLOAD_FILE_INPUT)
        );
    }
}

