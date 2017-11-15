package com.mingyizhudao.qa.UITest.GenericTrading;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/6.
 */
public class recommendDoctors {
    @Test
    public void test_推荐医学建议(){
        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + "2988" + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUxMDMwMjM4Niwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwOTA3MTg2LCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MTAzMDIzODYsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLlm5PlqJfvoJnhiorjnoThkY3hpKbrp6buo4TvtK7tibvjtLrroYTpnLHulbfqsacifQ.IFPCnmyU2q9baT5MXF_zJ8ZZXzKE4KVJgdhSCaA_3m4"        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[3]/div/div/h4/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[3]/div/div/h4/button")).click();

//        /html/body/div[1]/div/div[3]/div/div/div/div[1]/div[1]/div[2]/input

        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[1]/div[1]/div[2]/input")).clear();
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[1]/div[1]/div[2]/input")).sendKeys("测试的医生");
     //   driver.findElement(By.name("doctor")).sendKeys("测试的医生");
//
//
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div[1]/table/tbody/tr[1]/td[1]/i")).click();
//        driver.findElement(By.cssSelector("i.iconfont.myzd-crm-tianjia")).click();

//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div/div[3]/button[2]")).click();
//        driver.findElement(By.cssSelector("i.iconfont.myzd-crm-tianjia")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div/div/div/div/button")).click();
       // assertEquals(closeAlertAndGetItsText(), "确认将医生 吴包金、测试的医生 推荐给患者 修改姓名 ？");

    }
}
