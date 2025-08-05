import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class FacebookAutomation {
    public static void main(String[] args) {
        // Automatic ChromeDriver setup with specific version
        WebDriverManager.chromedriver().driverVersion("138.0.7204.157").setup();

        // Configure ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        WebDriver driver = new ChromeDriver(options);

        try {
            // Set timeout configurations
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

            // Navigate to Facebook
            driver.get("https://www.facebook.com");
            System.out.println("Navigated to Facebook");

            // Log in
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("kaduwelawasantha2001@gmail.com");
            driver.findElement(By.id("pass")).sendKeys("kaveeshaTEST");
            driver.findElement(By.name("login")).click();
            System.out.println("Login attempted");

            // Wait for home page to load
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@role='feed' and contains(@aria-label,'News Feed')]")),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//a[@aria-label='Home']"))
            ));
            System.out.println("Home page loaded");

            // Handle possible dialogs
            try {
                driver.findElement(By.xpath("//div[@aria-label='Close']")).click();
                System.out.println("Closed notification dialog");
            } catch (Exception ignored) {}

            // Create post
            WebElement createPost = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='button' and contains(.,'on your mind')]")
            ));
            createPost.click();
            System.out.println("Create post clicked");

            // Photo/Video button
            WebElement photoVideoButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@aria-label, 'Photo') or contains(@aria-label, 'Video')]")
            ));
            photoVideoButton.click();
            System.out.println("Photo/Video button clicked");

            // Upload file
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='file']")
            ));
            String filePath = System.getProperty("user.dir") + "/src/test/resources/test.jpg";
            fileInput.sendKeys(filePath);
            System.out.println("File uploaded: " + filePath);

            // Wait for image preview
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@aria-label='Remove']")
            ));
            System.out.println("Image preview appeared");

            // Add caption
            WebElement postText = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@aria-label=\"What's on your mind?\"]")
            ));
            postText.sendKeys("Automated test post from Selenium");
            System.out.println("Caption added");

            // Post button
            WebElement postButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@aria-label='Post' and @role='button']")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", postButton);
            System.out.println("Post button clicked");

            // Verify success
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Your post has been shared')]")),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Post shared')]"))
            ));
            System.out.println("✅ Post uploaded successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();

            // Take screenshot on error
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                // Save screenshot to file (implementation omitted for brevity)
                System.out.println("Screenshot taken for debugging");
            } catch (Exception screenshotEx) {
                System.out.println("Failed to take screenshot: " + screenshotEx.getMessage());
            }
        } finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {}
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}