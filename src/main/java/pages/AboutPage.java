package pages;

import org.helpers.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;


public class AboutPage {

    @FindBy(xpath = "//span[text()='Contact Us']")
    private WebElement contactUsButton;

    @FindBy(name = "user_first_name")
    private WebElement firstNameField;

    @FindBy(name = "user_last_name")
    private WebElement lastNameField;

    @FindBy(name = "user_email")
    private WebElement emailField;

    @FindBy(name = "user_phone")
    private WebElement phoneField;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'how_hear_about')]")
    private WebElement howHearAboutField;

    @FindBy(name = "gdprConsent")
    private WebElement gdprCheckbox;

    @FindBy(xpath = "//button[text()='Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(@aria-required,'true')]")
    List<WebElement> allRequiredElements;

    @FindBy(className = "desktop-logo")
    private WebElement headerLogo;

    @FindBy(xpath = "//span[contains(text(), 'DOWNLOAD')]")
    private WebElement downloadReportButton;


    public AboutPage() {
        PageFactory.initElements(new AjaxElementLocatorFactory(DriverManager.getDriver(), 15), this);
    }

    public void openWebSite() {
        DriverManager.getDriver().get("https://www.epam.com/about");
    }

    public void clickContactUsButton() {
        contactUsButton.click();
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public boolean isInvalid(WebElement element) {
        return element.getAttribute("aria-invalid").contains("true");
    }

    public boolean isEachFieldHighlightedInRed() {
        HomePage homePage = new HomePage();
        Boolean[] invalidList = new Boolean[]{
                isInvalid(firstNameField),
                isInvalid(lastNameField),
                isInvalid(emailField),
                isInvalid(phoneField),
                isInvalid(howHearAboutField),
                isInvalid(gdprCheckbox)};
        return homePage.checkAllIsTrue(invalidList);
    }

    public void clickHeaderLogo() {
        headerLogo.click();
    }

    public void clickDownloadReportButton() {
        downloadReportButton.click();
    }

    public boolean isFileDownloaded(String fileName) {
        var downloadFilePath = Paths.get(DriverManager.getDownloadDir(), fileName);
        DriverManager.getWait().until(t -> downloadFilePath.toFile().exists());
        File dir = new File(DriverManager.getDownloadDir());
        File[] dirContents = dir.listFiles();
        assert dirContents != null;
        for (File dirContent : dirContents) {
            if (dirContent.getName().equals(fileName)) {
                dirContent.delete();
                return true;
            }
        }
        return false;
    }
}
