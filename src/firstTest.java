import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class firstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/olegartyuhov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        WebElement skipButton = driver.findElementById("org.wikipedia:id/fragment_onboarding_skip_button");
        skipButton.click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchFieldHasText() {
        WebElement searchInput = driver.findElementById("org.wikipedia:id/search_container");
        searchInput.click();

        waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find Search Input",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "the element does not have text"
        );

    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement textElement = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find text in search field",
                5
        );

        String textInSearchInput = textElement.getAttribute("text");

        Assert.assertEquals(errorMessage, expectedText, textInSearchInput);
        return textElement;
    }
}
