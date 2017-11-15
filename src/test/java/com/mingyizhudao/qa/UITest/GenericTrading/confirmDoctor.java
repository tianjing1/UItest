package com.mingyizhudao.qa.UITest.GenericTrading;

import com.mingyizhudao.qa.UITest.Common.judgeElements;
import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.dataprofile.AppointmentTask;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Create;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.CreatePayLink;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Detail;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Recommend;
import com.mingyizhudao.qa.utilities.Generator;
import net.sf.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mingyizhudao.qa.utilities.HttpRequest.s_SendPut;

/**
 * Created by TianJing on 2017/11/7.
 */
public class confirmDoctor extends BaseTest{

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static final String version = "/api/v1";
    public static String uri = version+"/orders/{orderNumber}/surgeons";

    @Test
    public void test_01_确认医生_未生成支付链接_确认金额大于0(){
        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        List<String> list = new ArrayList<>();
        int size = (int)Generator.randomInt(10);
        for (int i = 0; i < size; i++) {
            list.add(Generator.randomExpertId());
        }
        list.add("7306");
        Recommend.s_Recommend(orderNumber, list);

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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button[2]")).click();
        new Select(driver.findElement(By.name("doctor_id"))).selectByVisibleText("测试的医生 - hospital_TEST_Test");
        driver.findElement(By.name("doctor_fee")).clear();
        driver.findElement(By.name("doctor_fee")).sendKeys(String.valueOf((int)Generator.randomInt(50)));
        driver.findElement(By.name("platform_fee")).clear();
        driver.findElement(By.name("platform_fee")).sendKeys(String.valueOf((int)Generator.randomInt(50)));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("未生成支付链接，确认金额大于0时，是否存在【确认】按钮；期望不存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn.btn-primary")));
        String button = "提交";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "未生成支付链接，确认金额大于0时，页面中【确认】按钮应该不存在");
        driver.quit();
    }


    @Test
    public void test_02_确认医生_未生成支付链接__确认金额为0() {
        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        List<String> list = new ArrayList<>();
        int size = (int) Generator.randomInt(10);
        for (int i = 0; i < size; i++) {
            list.add(Generator.randomExpertId());
        }
        list.add("7306");
        Recommend.s_Recommend(orderNumber, list);

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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button[2]")).click();
        new Select(driver.findElement(By.name("doctor_id"))).selectByVisibleText("测试的医生 - hospital_TEST_Test");
        driver.findElement(By.name("doctor_fee")).clear();
        driver.findElement(By.name("doctor_fee")).sendKeys("0");
        driver.findElement(By.name("platform_fee")).clear();
        driver.findElement(By.name("platform_fee")).sendKeys("0");
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("未生成支付链接，确认金额为0时，是否存在【确认】按钮；期望不存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn.btn-primary")));
        String button = "提交";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "未生成支付链接，确认金额为0时，页面中【确认】按钮应该不存在");
        driver.quit();
    }

    @Test
    public void test_03_确认医生_确认金额小于已生成的支付链接() {
        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        List<String> list = new ArrayList<>();
        int size = (int) Generator.randomInt(10);
        for (int i = 0; i < size; i++) {
            list.add(Generator.randomExpertId());
        }
        list.add("7306");
        Recommend.s_Recommend(orderNumber, list);
        Integer payLinkFee = (int)Generator.randomInt(1000);
        CreatePayLink.s_CreatePayment(orderNumber, payLinkFee);

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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button[2]")).click();
        new Select(driver.findElement(By.name("doctor_id"))).selectByVisibleText("测试的医生 - hospital_TEST_Test");
        driver.findElement(By.name("doctor_fee")).clear();
        driver.findElement(By.name("doctor_fee")).sendKeys(String.valueOf((payLinkFee-5)/200));
        driver.findElement(By.name("platform_fee")).clear();
        driver.findElement(By.name("platform_fee")).sendKeys(String.valueOf((payLinkFee-5)/200));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("确认金额小于已生成的支付链接时，是否存在【确认】按钮；期望存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn.btn-primary")));
        String button = "提交";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),true, "确认金额小于已生成的支付链接时，页面中【确认】按钮应该存在");
        driver.quit();
    }

    @Test
    public void test_04_确认医生_确认金额大于已生成的支付链接() {
        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);
        String orderNumber = getOrderNumberByTid(orderId);

        List<String> list = new ArrayList<>();
        int size = (int) Generator.randomInt(10);
        for (int i = 0; i < size; i++) {
            list.add(Generator.randomExpertId());
        }
        list.add("7306");
        Recommend.s_Recommend(orderNumber, list);
        Integer payLinkFee = (int) Generator.randomInt(1000);
        CreatePayLink.s_CreatePayment(orderNumber, payLinkFee);

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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/div[2]/button[2]")).click();
        new Select(driver.findElement(By.name("doctor_id"))).selectByVisibleText("测试的医生 - hospital_TEST_Test");
        driver.findElement(By.name("doctor_fee")).clear();
        driver.findElement(By.name("doctor_fee")).sendKeys(String.valueOf((payLinkFee + 5) / 200));
        driver.findElement(By.name("platform_fee")).clear();
        driver.findElement(By.name("platform_fee")).sendKeys(String.valueOf((payLinkFee + 5) / 200));
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        logger.info("确认金额大于已生成的支付链接时，是否存在【确认】按钮；期望不存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.btn.btn-primary")));
        String button = "提交";
        Assert.assertEquals(judgeElements.isAppeared(driver, button), false, "确认金额大于已生成的支付链接时，页面中【确认】按钮应该不存在");
        driver.quit();
    }

    private String getOrderNumberByTid(String tid) {
        return JSONObject.fromObject(Detail.s_Detail(tid)).getJSONObject("data").getJSONObject("appointment_order").getString("order_number");
    }
}
