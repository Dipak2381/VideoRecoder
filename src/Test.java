import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.awt.*;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import org.monte.media.math.Rational;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;


public class Test
{
    private static ScreenRecorder screenRecorder;
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        GraphicsConfiguration gconfig = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gconfig,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,
                        MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                        ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, (int) (15 * 60)),
                new Format(MediaTypeKey, MediaType.VIDEO,
                        EncodingKey,"black",
                        FrameRateKey, Rational.valueOf(30)), null);

        WebDriver driver = new FirefoxDriver();

        // Start Capturing the Video
        screenRecorder.start();

        driver.get("https://enterprise-demo.orangehrmlive.com/auth/login");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //click on username
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        //click on pass
        driver.findElement(By.id("txtPassword")).sendKeys("admin");
        //click on login
        driver.findElement(By.name("Submit")).click();
        //click on admin
        driver.findElement(By.xpath("//div/ul/li/a[@id='menu_admin_viewAdminModule']")).click();
        //click on username
        driver.findElement(By.id("searchSystemUser_userName")).sendKeys("Toga");
        //click on ess role
        driver.findElement(By.id("searchSystemUser_essRole")).click();
        //select field
        driver.findElement(By.xpath("//option[@value='2']")).click();
        //click on supervisor role
        driver.findElement(By.id("searchSystemUser_supervisorRole")).click();
        //select field
        driver.findElement(By.xpath("//option[@value='3']")).click();
        //select on admin role by select class method
        WebElement select_adminRole = driver.findElement(By.id("searchSystemUser_adminRole"));

        Select role = new Select(select_adminRole);
        // role.selectByVisibleText("HR Manager");
        role.selectByIndex(4);

        //cilck on add
        driver.findElement(By.id("btnAdd")).click();

        WebElement select_adminRole1 = driver.findElement(By.xpath("//select[@id='systemUser_adminRole']"));
        Select role1 = new Select(select_adminRole1);
        // role1.selectByVisibleText("HR Manager");
        role1.selectByIndex(4);
        //cilck on emp name
        driver.findElement(By.id("systemUser_employeeName_empName")).sendKeys("Amadi Aswad");

        Thread.sleep(3000);
        //click on username
        DateFormat dateFormat = new SimpleDateFormat("ddhhss");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        String userName = "Akber"+date1;
//        driver.findElement(By.id("systemUser_userName")).sendKeys(userName);

        driver.findElement(By.xpath("//div/div[2]/form/fieldset/ol/li[3]/input")).sendKeys(userName);
        // click on status
        driver.findElement(By.id("systemUser_status")).click();
        //click on password
        driver.findElement(By.id("systemUser_password")).sendKeys("D1234");
        //click on confirm pass
        driver.findElement(By.id("systemUser_confirmPassword")).sendKeys("D1234");
        //click on save
        driver.findElement(By.id("btnSave")).click();

        Thread.sleep(5000);

        //click on save
        driver.findElement(By.id("saveBtn")).click();
        Thread.sleep(5000);
        //click on user name
        driver.findElement(By.name("searchSystemUser[userName]")).sendKeys(userName);
        //click on emp name
        driver.findElement(By.id("searchSystemUser_employeeName_empName")).sendKeys("Amadi Aswad");
        //click on search
        driver.findElement(By.id("searchBtn")).click();
        //verify
        //Assert.assertTrue(userName,driver.findElement(By.xpath("//[start-with(@href,'saveSystemUser?userId']")).isEnabled());
        // System.out.println(userName);
        //click on admin button
        driver.findElement(By.xpath("//a[@id='welcome']")).click();
        //click on logout
        driver.findElement(By.xpath("//div/ul/li[2]/a")).click();
        //click on user name again
        driver.findElement(By.id("txtUsername")).sendKeys(userName);
        //click on password again
        driver.findElement(By.id("txtPassword")).sendKeys("D1234");
        //click on login
        driver.findElement(By.id("btnLogin")).click();
        //verify

        String expectedText = "Welcome Amadi";
        String actualText = driver.findElement(By.className("panelTrigger")).getText();
        Assert.assertEquals(expectedText,actualText);


        System.out.println("Done");

        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("D:\\screenshots\\screenshots1.jpg"));
        // Close the Browser.
        driver.close();

        // Stop the ScreenRecorder
        screenRecorder.stop();


    }
}