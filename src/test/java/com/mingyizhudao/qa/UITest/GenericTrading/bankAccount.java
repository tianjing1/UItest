package com.mingyizhudao.qa.UITest.GenericTrading;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/7.
 */
public class bankAccount {

    @Test
    public void test_01_录入银行收款账号(){
        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + "2952" + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.manage().window().maximize();

        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[4]/div/div/h4/button")).click();
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("测试账号");
        driver.findElement(By.xpath("(//input[@value=''])[2]")).clear();
        driver.findElement(By.xpath("(//input[@value=''])[2]")).sendKeys("123123@qq.com");
        driver.findElement(By.xpath("(//input[@value=''])[3]")).clear();
        driver.findElement(By.xpath("(//input[@value=''])[3]")).sendKeys("银行账号");
        driver.findElement(By.xpath("(//input[@value=''])[4]")).clear();
        driver.findElement(By.xpath("(//input[@value=''])[4]")).sendKeys("320404198007012791");
        driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-success")).click();
    }

}
