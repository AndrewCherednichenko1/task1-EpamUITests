package org.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

public class DriverManager {

    public static WebDriver driver;
    public static String downloadDir;
    public static WebDriverWait wait;

    public static void initDriver() {
        String browser = System.getProperty("browser");
        downloadDir = Paths.get("src/main/downloads").toAbsolutePath().toString();
        if (browser != null && browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            driver = new ChromeDriver(getChromeOptions());
        } else {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver(getFirefoxOptions());
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    private static ChromeOptions getChromeOptions() {
        var prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadDir);
        var opts = new ChromeOptions();
        opts.setExperimentalOption("prefs", prefs);
        return opts;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", downloadDir);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");
        profile.setPreference("pdfjs.disabled", true);
        FirefoxOptions capabilities = new FirefoxOptions();
        capabilities.setProfile(profile);
        return capabilities;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getDownloadDir() {
        return downloadDir;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void quitDriver() {
        driver.quit();
    }
}
