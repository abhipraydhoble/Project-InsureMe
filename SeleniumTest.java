import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the webpage
        driver.get("http://35.154.164.122:8088/");

        // Get and print the title of the webpage
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);

        // Close the browser
        driver.quit();
    }
}

