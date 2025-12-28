package com.automation.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceReportGenerator {
    
    public static void generateReport(String jtlFilePath, String outputDir) throws IOException {
        Path jtlPath = Paths.get(jtlFilePath);
        if (!Files.exists(jtlPath)) {
            throw new FileNotFoundException("JTL file not found: " + jtlFilePath);
        }
        
        List<PerformanceMetric> metrics = parseJtlFile(jtlPath);
        String htmlReport = generateHtmlReport(metrics);
        
        Path outputPath = Paths.get(outputDir);
        Files.createDirectories(outputPath);
        
        Path reportPath = outputPath.resolve("performance-report.html");
        Files.write(reportPath, htmlReport.getBytes());
        
        System.out.println("Performance report generated: " + reportPath.toAbsolutePath());
    }
    
    private static List<PerformanceMetric> parseJtlFile(Path jtlPath) throws IOException {
        List<PerformanceMetric> metrics = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(jtlPath)) {
            String header = reader.readLine();
            if (header == null) return metrics;
            
            String[] headers = header.split(",");
            int timeIdx = -1, latencyIdx = -1, successIdx = -1, bytesIdx = -1;
            
            for (int i = 0; i < headers.length; i++) {
                String h = headers[i].trim();
                if (h.equals("timeStamp")) timeIdx = i;
                else if (h.equals("Latency")) latencyIdx = i;
                else if (h.equals("success")) successIdx = i;
                else if (h.equals("bytes")) bytesIdx = i;
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > Math.max(Math.max(timeIdx, latencyIdx), Math.max(successIdx, bytesIdx))) {
                    PerformanceMetric metric = new PerformanceMetric();
                    if (timeIdx >= 0) metric.timestamp = Long.parseLong(values[timeIdx].trim());
                    if (latencyIdx >= 0) metric.latency = Long.parseLong(values[latencyIdx].trim());
                    if (successIdx >= 0) metric.success = Boolean.parseBoolean(values[successIdx].trim());
                    if (bytesIdx >= 0) metric.bytes = Long.parseLong(values[bytesIdx].trim());
                    metrics.add(metric);
                }
            }
        }
        
        return metrics;
    }
    
    private static String generateHtmlReport(List<PerformanceMetric> metrics) {
        if (metrics.isEmpty()) {
            return "<html><body><h1>No metrics found</h1></body></html>";
        }
        
        long totalRequests = metrics.size();
        long successfulRequests = metrics.stream().filter(m -> m.success).count();
        long failedRequests = totalRequests - successfulRequests;
        double errorRate = (failedRequests * 100.0) / totalRequests;
        
        double avgLatency = metrics.stream()
                .mapToLong(m -> m.latency)
                .average()
                .orElse(0.0);
        
        long totalBytes = metrics.stream().mapToLong(m -> m.bytes).sum();
        long duration = metrics.get(metrics.size() - 1).timestamp - metrics.get(0).timestamp;
        double throughput = (totalRequests * 1000.0) / duration;
        
        return String.format(
            "<!DOCTYPE html><html><head><title>Performance Test Report</title>" +
            "<style>body{font-family:Arial,sans-serif;margin:20px;background:#f5f5f5;}" +
            "h1{color:#333;}table{border-collapse:collapse;width:100%%;background:white;margin:20px 0;}" +
            "th,td{border:1px solid #ddd;padding:12px;text-align:left;}th{background:#4CAF50;color:white;}" +
            ".metric{background:white;padding:15px;margin:10px 0;border-radius:5px;box-shadow:0 2px 4px rgba(0,0,0,0.1);}" +
            ".value{font-size:24px;font-weight:bold;color:#4CAF50;}</style></head><body>" +
            "<h1>Performance Test Report</h1>" +
            "<div class='metric'><h2>Total Requests</h2><div class='value'>%d</div></div>" +
            "<div class='metric'><h2>Successful Requests</h2><div class='value'>%d</div></div>" +
            "<div class='metric'><h2>Failed Requests</h2><div class='value'>%d</div></div>" +
            "<div class='metric'><h2>Error Rate</h2><div class='value'>%.2f%%</div></div>" +
            "<div class='metric'><h2>Average Response Time</h2><div class='value'>%.2f ms</div></div>" +
            "<div class='metric'><h2>Throughput</h2><div class='value'>%.2f req/sec</div></div>" +
            "<div class='metric'><h2>Total Bytes</h2><div class='value'>%d</div></div>" +
            "</body></html>",
            totalRequests, successfulRequests, failedRequests, errorRate, avgLatency, throughput, totalBytes
        );
    }
    
    private static class PerformanceMetric {
        long timestamp;
        long latency;
        boolean success;
        long bytes;
    }
}

