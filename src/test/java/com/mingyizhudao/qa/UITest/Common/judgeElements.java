package com.mingyizhudao.qa.UITest.Common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Created by TianJing on 2017/10/26.
 */
public class judgeElements {

    public static boolean isAppeared(WebDriver driver, String content) {
        boolean status = false;
        try {
            driver.findElement(By.xpath("//*[contains(.,'" + content + "')]"));
            status = true;
        } catch (NoSuchElementException e) {
            status = false;
        }
        return status;
    }
}
