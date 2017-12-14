package com.mingyizhudao.qa.utilities;

import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.dataprofile.AppointmentTask;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Create;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by TianJing on 2017/12/12.
 */
//@Listeners({com.mingyizhudao.qa.utilities.TestUsNg.class})
public class TestUsNg {
//    private WebDriver dr;
//    public WebDriver getDriver() {
//        return dr;
//    }

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);

    @Test
    public void f() throws InterruptedException{
//        String key = "webdriver.chrome.driver";
//        String value = "D:/BaiduYunDownload/selenium/chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
//        dr = new ChromeDriver();
//        System.out.println(5/0);
//        String orderId = Create.s_CreateTid(new AppointmentTask());
//        logger.info("工单号为：" + orderId);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + 3896 + "?token=" + //crm_token
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUxMjk1NzQ2NCwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEzNTYyMjY0LCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MTI5NTc0NjQsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLmuJTNquCos--dtemgteuKk-O3teKdseKohuWmqumavOGTiOW9luy6vuWQhuGmvCJ9.RmI-AUta-DOtWyGhSDa_wnJMTnR4JAvtfdV0STB4wk4"

        );
        try {
            //设置等待的时长，最长10S
            WebDriverWait wait = new WebDriverWait(driver, 10);
//          driver.manage().window().maximize();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button")));
            driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button")).click();
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[1]/div/div/input")).clear();
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[1]/div/div/input1")).sendKeys("田静");
//            System.out.println(5 / 0);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.dropdown-menu > li > a")));
            driver.findElement(By.cssSelector("ul.dropdown-menu > li > a")).click();

            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div/textarea")).clear();
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div/textarea")).sendKeys("转交单据给田静");
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]")).click();

            logger.info("工单转交给其他自己，当前受理人是否为田静；期望是");
            Thread.sleep(6000);
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div/div/div[2]/div/div[2]/div[2]/div")).getText(),
                    "田静", "工单当前受理人应该为田静");
            driver.quit();
        }catch (Exception e){
            logger.error("======Exception Reason======= ");
            logger.error(e);
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MMdd-HHmmss");
            String dateString = formatter.format(currentTime);
            String category = "测试用例";
            takeScreenShot(driver, dateString);
            driver.quit();
            Assert.fail(category + "执行失败");
        }
    }

    public static void takeScreenShot(WebDriver driver, String name){
        File output = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File file = new File("C:/Users/TianJing/Documents/testfile/", name + ".png");
        try{
            FileUtils.copyFile(output, file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
