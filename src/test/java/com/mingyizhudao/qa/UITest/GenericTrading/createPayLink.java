package com.mingyizhudao.qa.UITest.GenericTrading;

import com.mingyizhudao.qa.UITest.Common.judgeElements;
import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.dataprofile.AppointmentTask;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.ConfirmExpert;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Create;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Detail;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Recommend;
import com.mingyizhudao.qa.utilities.Generator;
import net.sf.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianJing on 2017/11/7.
 */
public class createPayLink extends BaseTest{
    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);

    @Test
    public void test_01_未确认医生时生成支付链接(){
        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + orderId + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.manage().window().maximize();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")).click();
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys(String.valueOf(Generator.randomInt(50)));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
        driver.quit();
    }

    @Test
    public void test_02_已确认医生时生成支付链接_支付金额小于确认总金额(){

        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        int doctor_fee = (int)Generator.randomInt(10000);
        int platform_fee = (int)Generator.randomInt(10000);
        ConfirmExpert.s_ConfirmExpert(orderNumber, doctor_fee, platform_fee);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + orderId + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.manage().window().maximize();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")).click();
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys(String.valueOf((doctor_fee+platform_fee)/100-1));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("生成的支付链接时金额小于已确认金额，是否存在【确认】按钮；期望不存在");
        //html/body/div[1]/div/div[3]/div/div/div[1]/div[2]/table[2]/tbody/tr[2]/td[6]/button[1]
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/table[2]/tbody/tr[2]/td[6]/button[1]")));
        String button = "复制";
        Assert.assertEquals(judgeElements.isAppeared(driver, button), true, "生成的支付链接金额小于已确认金额，页面中【确认】按钮应该不存在");
        driver.quit();
    }

    @Test
    public void test_03_已确认医生时生成支付链接_支付金额大于确认总金额(){

        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        int doctor_fee = (int)Generator.randomInt(10000);
        int platform_fee = (int)Generator.randomInt(10000);
        ConfirmExpert.s_ConfirmExpert(orderNumber, doctor_fee, platform_fee);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + orderId + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.manage().window().maximize();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div[2]/button[1]")).click();
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys(String.valueOf((doctor_fee+platform_fee)/100+1));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("生成的支付链接时金额大于已确认金额，是否存在【确认】按钮；期望存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn.btn-primary")));
        String button = "提交";
        Assert.assertEquals(judgeElements.isAppeared(driver, button), true, "生成的支付链接金额大于已确认金额，页面中【确认】按钮应该存在");
        //driver.quit();
    }

    private String getOrderNumberByTid(String tid) {
        return JSONObject.fromObject(Detail.s_Detail(tid)).getJSONObject("data").getJSONObject("appointment_order").getString("order_number");
    }
}
