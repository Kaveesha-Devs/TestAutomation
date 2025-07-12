import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PostUploader {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromewebdrivers\\chrome-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.facebook.com");

        driver.findElement(By.id("email")).sendKeys("kaduwelawasantha2001@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("kaveeshaTEST");
        driver.findElement(By.name("login")).click();

        Thread.sleep(5000);

        WebElement postBox = driver.findElement(By.xpath("//span[contains(text(),\"on your mind\")]"));
        postBox.click();

        Thread.sleep(3000);

        WebElement textArea = driver.findElement(By.xpath("//div[@aria-label='Create a post']//div[@role='textbox']"));
        textArea.sendKeys("🌼 Selenium automation from IntelliJ + GitHub! 🌼");

        Thread.sleep(3000);

        WebElement postButton = driver.findElement(By.xpath("//div[@aria-label='Post']"));
        postButton.click();

        Thread.sleep(5000);
        driver.quit();
    }
}
