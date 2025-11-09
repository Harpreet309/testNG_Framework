package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/ExtentReports/ExtentReport.html");
            reporter.config().setDocumentTitle("Automation Report");
            reporter.config().setReportName("Test Execution Report");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}