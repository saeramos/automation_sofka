package com.automation.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BugReporter {
    private static final String BUG_REPORT_DIR = "reports/bugs";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static List<Bug> bugs = new ArrayList<>();
    
    public static void reportBug(String title, String description, String severity, String stepsToReproduce, String expectedResult, String actualResult, String screenshotPath) {
        Bug bug = new Bug();
        bug.id = "BUG-" + String.format("%04d", bugs.size() + 1);
        bug.title = title;
        bug.description = description;
        bug.severity = severity;
        bug.stepsToReproduce = stepsToReproduce;
        bug.expectedResult = expectedResult;
        bug.actualResult = actualResult;
        bug.screenshotPath = screenshotPath;
        bug.dateReported = LocalDateTime.now().format(DATE_FORMATTER);
        bug.status = "Open";
        
        bugs.add(bug);
    }
    
    public static void generateBugReport() throws IOException {
        java.io.File reportDir = new java.io.File(BUG_REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        String reportPath = BUG_REPORT_DIR + "/bug-report-" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".html";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportPath))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html><head>");
            writer.println("<title>Bug Report</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }");
            writer.println("h1 { color: #d32f2f; }");
            writer.println(".bug { background: white; padding: 20px; margin: 15px 0; border-radius: 5px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); border-left: 4px solid #d32f2f; }");
            writer.println(".bug-id { font-size: 18px; font-weight: bold; color: #d32f2f; }");
            writer.println(".severity { display: inline-block; padding: 5px 10px; border-radius: 3px; font-weight: bold; margin: 5px 0; }");
            writer.println(".severity-high { background: #ffebee; color: #c62828; }");
            writer.println(".severity-medium { background: #fff3e0; color: #e65100; }");
            writer.println(".severity-low { background: #f3e5f5; color: #6a1b9a; }");
            writer.println("table { width: 100%; border-collapse: collapse; margin: 10px 0; }");
            writer.println("td { padding: 8px; border: 1px solid #ddd; }");
            writer.println("td:first-child { font-weight: bold; width: 150px; background: #f5f5f5; }");
            writer.println("</style></head><body>");
            writer.println("<h1>Bug Report - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</h1>");
            writer.println("<p>Total Bugs Found: " + bugs.size() + "</p>");
            
            for (Bug bug : bugs) {
                writer.println("<div class='bug'>");
                writer.println("<div class='bug-id'>" + bug.id + ": " + bug.title + "</div>");
                writer.println("<span class='severity severity-" + bug.severity.toLowerCase() + "'>" + bug.severity + "</span>");
                writer.println("<table>");
                writer.println("<tr><td>Date Reported:</td><td>" + bug.dateReported + "</td></tr>");
                writer.println("<tr><td>Status:</td><td>" + bug.status + "</td></tr>");
                writer.println("<tr><td>Description:</td><td>" + bug.description + "</td></tr>");
                writer.println("<tr><td>Steps to Reproduce:</td><td>" + bug.stepsToReproduce + "</td></tr>");
                writer.println("<tr><td>Expected Result:</td><td>" + bug.expectedResult + "</td></tr>");
                writer.println("<tr><td>Actual Result:</td><td>" + bug.actualResult + "</td></tr>");
                if (bug.screenshotPath != null && !bug.screenshotPath.isEmpty()) {
                    writer.println("<tr><td>Screenshot:</td><td><img src='" + bug.screenshotPath + "' alt='Screenshot' style='max-width: 500px;'/></td></tr>");
                }
                writer.println("</table>");
                writer.println("</div>");
            }
            
            writer.println("</body></html>");
        }
        
        System.out.println("Bug report generated: " + reportPath);
    }
    
    public static List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }
    
    public static class Bug {
        String id;
        String title;
        String description;
        String severity;
        String stepsToReproduce;
        String expectedResult;
        String actualResult;
        String screenshotPath;
        String dateReported;
        String status;
    }
}

