import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Scanner;

public class Main {

    private static final String SOUND_INPUT = "//*[@id=\"content\"]/div/div/div[2]/div/div[1]/span/span/form/input";
    private static final String SOUND_INPUT_BTN = "//*[@id=\"content\"]/div/div/div[2]/div/div[1]/span/span/form/button";
    private static final String ACCEPT_COOKIES = "//*[@id=\"onetrust-accept-btn-handler\"]";
    private static final String FIRST_TRACK = "//*[@id=\"content\"]/div/div/div[3]/div/div/div/ul/li[1]/div/div/div/div[1]/a/div/span";
    private static final String INPUT_TRACK_URL = "/html/body/div[2]/div/center/form/div/input";
    private static final String INPUT_TRACK_BTN = "/html/body/div[2]/div/center/form/div/div/input";
    private static final String DOWNLOAD_BTN = "/html/body/div[2]/div[2]/div[1]/center/a[1]";
    private static boolean cookiesAccepted = false;

    public static void main(String[] args) {
        WebDriver driver = setUp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Track name: ");
            String trackUrl = findTrackUrl(driver, scanner.nextLine());
            downloadTrack(driver, trackUrl);
            System.out.println("Download");
        }
    }

    private static void acceptCookies(WebDriver driver) {
        if (!cookiesAccepted) {
            driver.findElement(By.xpath(ACCEPT_COOKIES)).click();
            cookiesAccepted = true;
        }
    }

    private static WebDriver setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        return new ChromeDriver();
    }

    private static String findTrackUrl(WebDriver driver, String track) {
        driver.get("https://soundcloud.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        acceptCookies(driver);
        driver.findElement(By.xpath(SOUND_INPUT)).sendKeys(track);
        driver.findElement(By.xpath(SOUND_INPUT_BTN)).click();
        driver.findElement(By.xpath(FIRST_TRACK)).click();
        return driver.getCurrentUrl();
    }

    private static void downloadTrack(WebDriver driver, String trackUrl) {
        driver.get("https://sclouddownloader.net/");
        driver.findElement(By.xpath(INPUT_TRACK_URL)).sendKeys(trackUrl);
        driver.findElement(By.xpath(INPUT_TRACK_BTN)).click();
        driver.findElement(By.xpath(DOWNLOAD_BTN)).click();
    }
}
