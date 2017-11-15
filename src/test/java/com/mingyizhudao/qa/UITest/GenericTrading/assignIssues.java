package com.mingyizhudao.qa.UITest.GenericTrading;

import com.mingyizhudao.qa.UITest.Common.judgeElements;
import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.dataprofile.AppointmentTask;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Create;
import com.mingyizhudao.qa.functiontest.IMS.trading.appointment.Detail;
import net.sf.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/11/6.
 */
public class assignIssues extends BaseTest{

    @Test
    public void test_01_分配受理人(){

        String orderId = Create.s_CreateTid(new AppointmentTask());
        logger.info("工单号为：" + orderId);

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/issue-manage/info/" + orderId + "?token=" + crm_token
//                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUxMDMwMjM4Niwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwOTA3MTg2LCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MTAzMDIzODYsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLlm5PlqJfvoJnhiorjnoThkY3hpKbrp6buo4TvtK7tibvjtLrroYTpnLHulbfqsacifQ.IFPCnmyU2q9baT5MXF_zJ8ZZXzKE4KVJgdhSCaA_3m4"
        );

        //设置等待的时长，最长10S
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

        //html/body/div[1]/div/div[3]/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button")));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button")).click();
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("田");
        driver.findElement(By.linkText("田小静")).click();
        driver.findElement(By.cssSelector("div.col-sm-8 > textarea.form-control")).clear();
        driver.findElement(By.cssSelector("div.col-sm-8 > textarea.form-control")).sendKeys("转交单据");
        driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-success")).click();

        logger.info("工单转交给其他受理人，是否存当前受理人为田小静；期望是");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div/div[2]/div/div/div[2]/div/div[1]/h4/button")));
        String assginee = "田小静";
        Assert.assertEquals(judgeElements.isAppeared(driver, assginee), true, "当前工单受理人应该为田小静");
        driver.quit();
    }


}
