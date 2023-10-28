import org.helpers.DriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;


public class TestRunner {

    private static HomePage homePage;

    private static AboutPage aboutPage;

    @BeforeTest
    public void setUp() {
        DriverManager.initDriver();
        homePage = new HomePage();
        aboutPage = new AboutPage();
    }

    @Test(priority = 1, description = "Check the title is correct")
    public void verifyTitle() {
        homePage.openWebSite();
        String title = DriverManager.getDriver().getTitle();
        String expectedTitle = "EPAM | Software Engineering & Product Development Services";
        Assert.assertEquals(title, expectedTitle);
    }

    @Test(priority = 2, description = "Check the ability to switch Light / Dark mode")
    public void verifyMode() {
        homePage.openWebSite();
        homePage.clickSwitchModeToggle();
        Assert.assertTrue(homePage.isLightMode());
        homePage.clickSwitchModeToggle();
        Assert.assertTrue(homePage.isDarkMode());
    }

    @Test(priority = 3, description = "Check that allow to change language to UA")
    public void verifyLanguage() {
        homePage.openWebSite();
        homePage.clickLanguageButton();
        homePage.clickUALanguageButton();
        String expectedUrl = "https://careers.epam.ua/";
        DriverManager.getWait().until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertTrue(homePage.checkLanguageIsUA());
    }

    @Test(priority = 4, description = "Check the policies list")
    public void verifyPolicyList() {
        homePage.openWebSite();
        String[] PolicyItems = {
                "INVESTORS",
                "COOKIE POLICY",
                "OPEN SOURCE",
                "APPLICANT PRIVACY NOTICE",
                "PRIVACY POLICY",
                "WEB ACCESSIBILITY"
        };
        for (String PolicyItem : PolicyItems) {
            Assert.assertTrue(homePage.checkPolicyList(PolicyItem));
        }
    }

    @Test(priority = 5, description = "Check that allow to switch location list by region")
    public void verifySwitchLocationByRegion() {
        homePage.openWebSite();
        homePage.acceptCookies();
        String[] regionsList = {"EMEA", "APAC", "AMERICAS"};
        String[] firstLocationInRegionsList = {"ARMENIA", "AUSTRALIA", "CANADA"};
        Assert.assertTrue(homePage.checkRegionIsSwitched(regionsList, firstLocationInRegionsList));
    }

    @Test(priority = 6, description = "Check the search function")
    public void verifySearch() {
        homePage.openWebSite();
        homePage.clickSearchIcon();
        Assert.assertTrue(homePage.searchByValue("AI"));
    }

    @Test(priority = 7, description = "Check form's fields validation")
    public void verifyFormFieldsValidation() {
        aboutPage.openWebSite();
        homePage.acceptCookies();
        aboutPage.clickContactUsButton();
        aboutPage.clickSubmitButton();
        Assert.assertTrue(aboutPage.isEachFieldHighlightedInRed());
    }

    @Test(priority = 8, description = "Check that the Company logo on the header leads to the main page")
    public void verifyLogoRedirectsToMainPage() {
        aboutPage.openWebSite();
        aboutPage.clickHeaderLogo();
        String expectedUrl = "https://www.epam.com/";
        DriverManager.getWait().until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), expectedUrl);
    }

    @Test(priority = 9, description = "Check that allows to download report")
    public void verifyDownloadingReport() {
        aboutPage.openWebSite();
        homePage.acceptCookies();
        aboutPage.clickDownloadReportButton();
        var reportName = "EPAM_Corporate_Overview_Q3_october.pdf";
        Assert.assertTrue(aboutPage.isFileDownloaded(reportName));
    }

    @AfterTest
    public void closeBrowser() {
        DriverManager.quitDriver();
    }
}
