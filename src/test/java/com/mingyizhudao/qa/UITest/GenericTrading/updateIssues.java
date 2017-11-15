package com.mingyizhudao.qa.UITest.GenericTrading;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/6.
 */
public class updateIssues {

    @Test
    public void test_01_修改工单(){

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + "2586" + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div/div/div/div[2]/button[2]")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div/div/div[2]/button[2]")).click();
        new Select(driver.findElement(By.cssSelector("select.form-control"))).selectByVisibleText("三方通话");
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("修改姓名");
        driver.findElement(By.id("inlineRadio1")).click();
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/form/div[4]/div/input")).clear();
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/form/div[4]/div/input")).sendKeys("46");
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).clear();
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).sendKeys("测试疾病2");
        driver.findElement(By.cssSelector("textarea.form-control")).clear();
        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("修改测试测试描述测试描述测试描述");
        driver.findElement(By.id("inlineRadio3")).click();
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/button[2]")).click();
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
        driver.quit();
    }

}
