package com.mingyizhudao.qa.UITest.SurgeryOrder;

import com.mingyizhudao.qa.UITest.Common.judgeElements;
import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.functiontest.crm.trading.surgery.Order_ReceiveTask;
import com.mingyizhudao.qa.functiontest.crm.trading.surgery.Order_RecommendDoctor;
import com.mingyizhudao.qa.functiontest.crm.trading.surgery.Order_ThreewayCall_V2;
import com.mingyizhudao.qa.functiontest.doctor.CreateOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.mingyizhudao.qa.common.TestLogger;

/**
 * Created by TianJing on 2017/10/25.
 */
public class rejectOrder extends BaseTest{

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static String uri = "/api/getorderdetail/{orderNumber}";

    @Test
    public void test_01_取消订单_待推荐医生的订单() throws InterruptedException {

        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        logger.info("orderNumber:" + orderNumber);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" + crm_token);
        //窗口最大化
        //driver.manage().window().maximize();
        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //选择【拒绝订单】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button[2]")).click();
        //选择拒绝的理由
        new Select(driver.findElement(By.cssSelector("select.form-control"))).selectByVisibleText("平台不接受急诊手术");
        //输入拒绝的原因
        driver.findElement(By.cssSelector("textarea")).clear();
        driver.findElement(By.cssSelector("textarea")).sendKeys("测试拒绝");
        //点击确认按钮
        driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();

        logger.info("判断待推荐订单取消后，是否还存在【推荐专家】按钮；期望不存在");
        String button = "推荐专家";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "待推荐专家的订单取消合作后，页面中【推荐专家】按钮不应该存在");
        driver.quit();
    }

    @Test
    public void test_02_取消订单_已确认合作的订单() throws InterruptedException {
        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        Order_RecommendDoctor.s_RecommendDoctor(orderNumber, "7306");//推荐的医生为测试的医生
        Order_ThreewayCall_V2.s_CallV2(orderNumber,"success");
        logger.info("orderNumber:" + orderNumber);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" + crm_token);

        //设置等待时间
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/button")));

        //点击【订单回退】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/button")).click();
        //输入订单回退原因
        driver.findElement(By.cssSelector("textarea.form-control")).clear();
        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("测试退回订单");
        //确认回退订单
        driver.findElement(By.xpath("(//button[@type='button'])[6]")).click();

        logger.info("判断已合作订单取消后，是否还存在【订单回退】按钮；期望不存在");
        String button = "订单回退(慎点)";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "已确认合作订单取消合作后，页面中【订单回退】按钮不应该存在");
        driver.quit();
    }
}

