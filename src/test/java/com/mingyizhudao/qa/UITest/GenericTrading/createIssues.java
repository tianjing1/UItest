package com.mingyizhudao.qa.UITest.GenericTrading;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/6.
 */
public class createIssues {

    @Test
    public void test_01_创建工单() throws InterruptedException{
        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/create" + "?token=" +
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUxMDMwMjM4Niwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwOTA3MTg2LCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MTAzMDIzODYsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLlm5PlqJfvoJnhiorjnoThkY3hpKbrp6buo4TvtK7tibvjtLrroYTpnLHulbfqsacifQ.IFPCnmyU2q9baT5MXF_zJ8ZZXzKE4KVJgdhSCaA_3m4"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);


        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/form/div[3]/div/select")));
        new Select(driver.findElement(By.xpath("//div[@id='appContentContainer']/div/form/div[3]/div/select"))).selectByVisibleText("商务通（PC网站）");
        driver.findElement(By.cssSelector("div.col-sm-5 > input.form-control")).clear();
        driver.findElement(By.cssSelector("div.col-sm-5 > input.form-control")).sendKeys("测试患者");
        driver.findElement(By.id("inlineRadio2")).click();

        driver.findElement(By.cssSelector("div.text > input.form-control")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/form/div[10]/div/div/div[3]/div[1]/div/span[3]")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/form/div[10]/div/div/div[3]/div[1]/div/span[3]")).click();

        driver.findElement(By.id("expertCitySelect")).findElement(By.className("content")).findElement(By.tagName("span")).click();

//        driver.findElement(By.cssSelector("#expertCitySelect > div.options.city > div > div > span")).click();
//        #expertCitySelect > div.options.city > div > div > span

//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/form/div[10]/div/div/div[3]/div/div/span")));
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/form/div[10]/div/div/div[3]/div/div/span")).click();

        //*[@id="expertCitySelect"]/div[3]/div/div/span
//        /html/body/div[1]/div/div[3]/div/form/div[10]/div/div/div[3]/div/div/span
       // /html/body/div[1]/div/div[3]/div/form/div[10]/div/div/div[3]/div[1]/div/span[3]
//        driver.findElement(By.cssSelector("div.content > span")).click();
        //html/body/div[1]/div/div[3]/div/form/div[10]/div/div/div[1]/input


//        driver.findElement(By.cssSelector("div.text > input.form-control")).click();
//        driver.findElement(By.xpath("//div[@id='expertCitySelect']/div[3]/div[3]/div/span[2]")).click();
//        driver.findElement(By.cssSelector("div.content > span")).click();


        driver.findElement(By.xpath("(//input[@type='tel'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='tel'])[2]")).sendKeys("58");
//
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).clear();
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).sendKeys("测试疾病");

        driver.findElement(By.cssSelector("textarea.form-control")).clear();
        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("测试描述测试描述测试描述");
        new Select(driver.findElement(By.cssSelector("div.col-sm-2 > select.form-control"))).selectByVisibleText("一周内");
//        driver.findElement(By.xpath("(//input[@value=''])[2]")).click();
//
//        driver.findElement(By.cssSelector("div.content > span")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/button[2]")).click();
//
//        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();


        //html/body/div[1]/div/div[3]/div/form/div[10]/div/div/div[3]/div[1]/div/span[3]

    }

}
