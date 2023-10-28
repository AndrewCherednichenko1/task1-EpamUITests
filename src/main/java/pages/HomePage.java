package pages;

import org.helpers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class HomePage {

    @FindBy(xpath = "//nav[@aria-label='Main navigation']/preceding-sibling::section/div/div[@class='switch']")
    private WebElement switchModeToggle;

    @FindBy(xpath = "//body[contains(@class,'fonts-loaded')]")
    private WebElement mode;

    @FindBy(xpath = "//ul[@class='header__controls']//div[contains(@class,'location-selector-ui')]")
    private WebElement languageButton;

    @FindBy(xpath = "//a[@class='location-selector__link' and @lang='uk']")
    private WebElement languageUAButton;

    @FindBy(xpath = "//div[@class='policies-links-wrapper']")
    private WebElement policyItem;

    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    private WebElement acceptCookiesButton;

    @FindBy(id = "onetrust-banner-sdk")
    private WebElement acceptCookiesSection;

    @FindBy(xpath = "//div[contains(@class,'js-tabs-links-list')]")
    private WebElement locationButton;

    @FindBy(xpath = "//div[contains(@class,'js-tabs-item active')]")
    private WebElement regionsSection;

    @FindBy(xpath = "//span[contains(@class,'dark-iconheader')]")
    private WebElement searchIcon;

    @FindBy(id = "new_form_search")
    private WebElement searchInputField;

    @FindBy(xpath = "//h2[@class='search-results__counter']")
    private WebElement searchResultsCounter;

    private static Integer cookies = 0;

    public HomePage() {
        PageFactory.initElements(new AjaxElementLocatorFactory(DriverManager.getDriver(), 15), this);
    }

    public void openWebSite() {
        DriverManager.getDriver().get("https://www.epam.com");
    }

    public void acceptCookies() {
        if (cookies == 0) {
            cookies = 1;
            DriverManager.getWait().until(visibilityOf(acceptCookiesSection));
            DriverManager.getWait().until(elementToBeClickable(acceptCookiesButton)).click();
            DriverManager.getWait().until(invisibilityOf(acceptCookiesSection));
        }
    }

    public void clickSwitchModeToggle() {
        switchModeToggle.click();
    }

    public boolean isLightMode() {
        return mode.getAttribute("class").contains("light-mode");
    }

    public boolean isDarkMode() {
        return mode.getAttribute("class").contains("dark-mode");
    }

    public void clickLanguageButton() {
        languageButton.click();
    }

    public void clickUALanguageButton() {
        DriverManager.getWait().until(elementToBeClickable(languageUAButton)).click();
    }

    public boolean checkLanguageIsUA() {
        return DriverManager.getDriver().getCurrentUrl().equals("https://careers.epam.ua/");
    }

    public boolean checkPolicyList(String policyName) {
        return policyItem.getText().contains(policyName);
    }

    public void clickRegion(String region) {
        locationButton.findElement(By.linkText(region)).click();
    }

    public Boolean[] isRegionSelected(String region, String location) {
        return new Boolean[]{locationButton.findElement(By.linkText(region)).getAttribute("aria-selected").contains("true"),
                regionsSection.getText().contains(location)};
    }

    public void clickSearchIcon() {
        searchIcon.click();
    }

    public boolean searchByValue(String value) {
        DriverManager.getWait().until(elementToBeClickable(searchInputField)).sendKeys(value);
        searchInputField.sendKeys(Keys.ENTER);
        return searchResultsCounter.getText().contains("RESULTS FOR \"" + value + "\"");
    }

    public boolean checkAllIsTrue(Boolean[] data) {
        List<Boolean> booleans = List.of(data);
        return booleans.stream().allMatch(b -> b);
    }

    public boolean checkRegionIsSwitched(String[] regionsList, String[] firstLocationInRegionsList) {
        for (int i = 0; i < regionsList.length; i++) {
            clickRegion(regionsList[i]);
            boolean allTrue = checkAllIsTrue(isRegionSelected(regionsList[i], firstLocationInRegionsList[i]));
            if (!allTrue) {
                return false;
            }
        }
        return true;
    }
}
