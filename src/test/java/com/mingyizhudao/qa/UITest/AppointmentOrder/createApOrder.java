package com.mingyizhudao.qa.UITest.AppointmentOrder;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.utilities.Generator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * Created by TianJing on 2017/10/12.
 */
public class createApOrder {
    @Test
    public void test_创建预约单() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://work.dev.myzd.info/appointment-manage/create?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImppbmcudGlhbkBtaW5neWl6aHVkYW8uY29tIiwibmFtZSI6Ilx1NzUzMFx1OTc1OSIsInN1YiI6MjMsImlzcyI6Imh0dHA6Ly9zZXJ2aWNlcy5kZXYubXl6ZC5pbmZvL2FwaS92MS9jYWxsYmFjayIsImlhdCI6MTUwNzcxMDQxNSwiZXhwIjoxNTA3OTI2NDE1LCJuYmYiOjE1MDc3MTA0MTUsImp0aSI6InNvd0gzUzNSZ0pLTVdFMXcifQ.mr0QE3R5k57-XzIVyTeg9kEr7jMxzkWW1zc-KLWoFAc");
        new Select(driver.findElement(By.cssSelector("select.form-control.input_add"))).selectByVisibleText("PC网站");
        driver.findElement(By.cssSelector("input.form-control")).clear();
        driver.findElement(By.cssSelector("input.form-control")).sendKeys("患者" + Generator.randomString(4));
        driver.findElement(By.xpath("//input[@type='tel']")).clear();
        driver.findElement(By.xpath("//input[@type='tel']")).sendKeys(String.valueOf((int) Generator.randomInt(100)));
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).clear();
        driver.findElement(By.cssSelector("#diseaseName > input.form-control")).sendKeys("疾病");
        //driver.findElement(By.cssSelector(".input-select > a[contains(text(),'疾病')])[3]")).click();
        //driver.findElement(By.xpath("(//a[contains(text(),'测试疾病')])[3]")).click();
        driver.findElement(By.cssSelector("textarea.form-control.textarea_add")).clear();
        driver.findElement(By.cssSelector("textarea.form-control.textarea_add")).sendKeys(Generator.randomString(100));
        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/button[2]")).click();
        driver.findElement(By.cssSelector(".btn-group-level > button.btn.btn-sm.btn-info")).click();
        //driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
    }
}
