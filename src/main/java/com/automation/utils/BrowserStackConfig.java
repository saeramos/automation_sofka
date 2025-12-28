package com.automation.utils;

public class BrowserStackConfig {
    public static String getUsername() {
        return System.getProperty("browserstack.username", System.getenv("BROWSERSTACK_USERNAME"));
    }
    
    public static String getAccessKey() {
        return System.getProperty("browserstack.accesskey", System.getenv("BROWSERSTACK_ACCESS_KEY"));
    }
    
    public static boolean isBrowserStackEnabled() {
        String username = getUsername();
        String accessKey = getAccessKey();
        return username != null && !username.isEmpty() && 
               accessKey != null && !accessKey.isEmpty();
    }
}

