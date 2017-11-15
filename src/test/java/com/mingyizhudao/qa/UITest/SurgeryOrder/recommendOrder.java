package com.mingyizhudao.qa.UITest.SurgeryOrder;

import com.mingyizhudao.qa.UITest.Common.judgeElements;
import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.functiontest.crm.trading.surgery.Order_ReceiveTask;
import com.mingyizhudao.qa.functiontest.doctor.CreateOrder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by TianJing on 2017/10/12.
 */
public class recommendOrder extends BaseTest{

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);

    @Test
    public void test_01_新建订单_确认合作() throws InterruptedException {
        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        logger.info("orderNumber:" + orderNumber);
        logger.info("");

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" + crm_token);
        //窗口最大化
        driver.manage().window().maximize();

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //选择推荐专家
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")).click();
        //选择专家为“测试的医生”
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("测试的医生");
        driver.findElement(By.cssSelector("button.btn.btn-success")).click();
        //等待找到第一个单选框按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")));
        //点击选择第一个单选框
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")).click();
        //点击【确认推荐】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/button[2]")).click();
        //点击二次弹窗框中的确认按钮
        driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();

        //三方通话，选择确认合作
        //等待找到【记录三方通话】按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button")));

        //处理隐藏元素的方法
        Actions action = new Actions(driver);
        WebElement nav = driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button"));
        //判断当nav元素出现时才执行相应的操作
        if(nav.isDisplayed()){
            System.out.println("found=================");
            action.moveToElement(nav).build().perform();
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('appContentContainer').style.display='block';");

        WebElement onElement1 = driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[last()]/a"));
        action.moveToElement(onElement1).click().build().perform();

        //三方通话输入手术金额，确认合作
        driver.findElement(By.id("operationMoney")).clear();
        driver.findElement(By.id("operationMoney")).sendKeys("0");
        driver.findElement(By.id("doctorBackProportionLable")).clear();
        driver.findElement(By.id("doctorBackProportionLable")).sendKeys("0");
        driver.findElement(By.id("platServiceProportionLable")).clear();
        driver.findElement(By.id("platServiceProportionLable")).sendKeys("0");
        driver.findElement(By.id("serviceContent")).clear();
        driver.findElement(By.id("serviceContent")).sendKeys("测试");
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/button[2]")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();

        logger.info("待推荐专家的订单推荐专家后，是否存在【订单回退】按钮；期望存在");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/button")));
        String button = "订单回退(慎点)";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),true, "待推荐专家订单推荐专家后，页面中【订单回退】按钮应该存在");
        driver.quit();
        }

    @Test
    public void test_02_新建订单_确认不合作() throws InterruptedException {
        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        logger.info("orderNumber:" + orderNumber);
        logger.info("");

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" + crm_token);
        //窗口最大化
        driver.manage().window().maximize();

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //选择推荐专家
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")).click();
        //选择专家为“测试的医生”
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("测试的医生");
        driver.findElement(By.cssSelector("button.btn.btn-success")).click();
        //等待找到第一个单选框按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")));
        //点击选择第一个单选框
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")).click();
        //点击【确认推荐】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/button[2]")).click();
        //点击二次弹窗框中的确认按钮
        driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();

        //三方通话，选择确认合作
        //等待找到【记录三方通话】按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button")));

        //处理隐藏元素的方法
        Actions action = new Actions(driver);
        WebElement nav = driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button"));
        //判断当nav元素出现时才执行相应的操作
        if(nav.isDisplayed()){
            System.out.println("found=================");
            action.moveToElement(nav).build().perform();
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('appContentContainer').style.display='block';");

        WebElement onElement1 = driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[2]/a"));
        action.moveToElement(onElement1).click().build().perform();

        //三方通话确认不合作，输入理由
        driver.findElement(By.id("serviceContent")).clear();
        driver.findElement(By.id("serviceContent")).sendKeys("测试不通过");
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/button[2]")).click();

        logger.info("待推荐专家的订单推荐专家后确认不合作，是否存在【记录三方通过】按钮；期望不存在");
        String button = "记录三方通话";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "待推荐专家的订单推荐专家后确认不合作，不存在【记录三方通话】按钮");
        driver.quit();
    }

    @Test
    public void test_03_新建订单_合作待定() throws InterruptedException {
        String orderNumber = CreateOrder.s_CreateOrder(mainToken);
        Order_ReceiveTask.s_ReceiveTask(orderNumber);
        logger.info("orderNumber:" + orderNumber);
        logger.info("");

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/order-manage/info?orderNum=" + orderNumber + "?token=" + crm_token);
        //窗口最大化
        //driver.manage().window().maximize();

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //选择推荐专家
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[2]/div/button")).click();
        //选择专家为“测试的医生”
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("测试的医生");
        driver.findElement(By.cssSelector("button.btn.btn-success")).click();
        //等待找到第一个单选框按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")));
        //点击选择第一个单选框
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/table/tbody/tr[1]/td[1]/label/input")).click();
        //点击【确认推荐】按钮
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/button[2]")).click();
        //点击二次弹窗框中的确认按钮
        driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();

        //三方通话，选择确认合作
        //等待找到【记录三方通话】按钮
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button")));

        //处理隐藏元素的方法
        Actions action = new Actions(driver);
        WebElement nav = driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[1]/div[2]/div/div/button"));
        //判断当nav元素出现时才执行相应的操作
        if(nav.isDisplayed()){
            System.out.println("found=================");
            action.moveToElement(nav).build().perform();
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('appContentContainer').style.display='block';");

        WebElement onElement1 = driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[1]/a"));
        action.moveToElement(onElement1).click().build().perform();

        //三方通话确认不合作，输入理由
        driver.findElement(By.id("serviceContent")).clear();
        driver.findElement(By.id("serviceContent")).sendKeys("测试合作待定");
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/button[2]")).click();

        logger.info("待推荐专家的订单推荐专家后合作待定，是否存在【记录三方通话】按钮；期望存在");
        String button = "记录三方通话";
        Assert.assertEquals(judgeElements.isAppeared(driver, button),false, "待推荐专家的订单推荐专家后合作待定，存在【记录三方通话】按钮");
        driver.quit();
    }

}
