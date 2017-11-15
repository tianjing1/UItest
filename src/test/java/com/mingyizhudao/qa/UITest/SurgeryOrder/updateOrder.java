package com.mingyizhudao.qa.UITest.SurgeryOrder;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.functiontest.crm.trading.surgery.Order_ReceiveTask;
import com.mingyizhudao.qa.functiontest.doctor.CreateOrder;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/6.
 */
public class updateOrder extends BaseTest{
    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);

    @Test
    public void test_01_修改订单(){
        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        logger.info("orderNumber:" + orderNumber);
        logger.info("");

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" +
               "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );
        //窗口最大化
//        driver.manage().window().maximize();

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button[3]")));
        //点击【修改订单】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button[3]")).click();

        //修改患者姓名、性别、年龄、手机号信息
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("修改姓名a");
        driver.findElement(By.xpath("(//input[@name='radioOptions'])[2]")).click();
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("60");
        driver.findElement(By.xpath("//input[@type='number']")).clear();
        driver.findElement(By.xpath("//input[@type='number']")).sendKeys("18317186256");
//        driver.findElement(By.xpath("//input[@type='type']")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[7]/div[2]/div/div[2]/div[2]/div/span[21]/span")).click();
//        driver.findElement(By.xpath("(//input[@type='type'])[2]")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[7]/div[4]/div/div[2]/div[2]/div/span[28]/span")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[8]/div[2]/div/input")).clear();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[8]/div[2]/div/input")).sendKeys("上海");

        //点击确认【提交修改】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/button[2]")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-info")).click();
        driver.quit();
    }
}
