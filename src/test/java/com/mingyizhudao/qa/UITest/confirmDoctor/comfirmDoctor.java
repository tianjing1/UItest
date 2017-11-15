package com.mingyizhudao.qa.UITest.confirmDoctor;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.dataprofile.User;
import com.mingyizhudao.qa.functiontest.doctor.GetDoctorProfile_V1;
import com.mingyizhudao.qa.functiontest.doctor.UpdateDoctorProfile_V1;
import com.mingyizhudao.qa.functiontest.login.CheckVerifyCode;
import com.mingyizhudao.qa.functiontest.login.SendVerifyCode;
import com.mingyizhudao.qa.utilities.Generator;
import net.sf.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by TianJing on 2017/10/27.
 */
public class comfirmDoctor {

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static String uri = "/api/getorderdetail/{orderNumber}";

    protected static HashMap<String, String> s_CreateRegistered() {
        logger.info("创建注册用户...");
        String mobile = SendVerifyCode.s_Send();
        String token = CheckVerifyCode.s_Check();

        String res = GetDoctorProfile_V1.s_MyProfile(token);
        String doctorId = JSONObject.fromObject(res).getJSONObject("data").getJSONObject("doctor").getString("user_id");
        if( doctorId == null || doctorId.isEmpty()) {
            logger.error("创建注册用户失败");
            return null;
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("id", doctorId);
        result.put("mobile", mobile);
        result.put("token", token);
        logger.info("mobile为:\t"+mobile);
        logger.info("token为:\t"+token);
        logger.info("doctorId为:\t"+doctorId);
        return result;
    }

    protected static HashMap<String, String> s_CreateRegisteredDoctor(User user) {
        HashMap<String,String> info = s_CreateRegistered();
        if (info == null) return null;
        String token = info.get("token");

        logger.info("更新医生信息...");
        logger.info(UpdateDoctorProfile_V1.s_Update(token, user));
        String res = GetDoctorProfile_V1.s_MyProfile(token);
        logger.info(res);
        String doctorHospitalId = JSONObject.fromObject(res).getJSONObject("data").getJSONObject("doctor").getString("hospital_id");
        if (doctorHospitalId == null || doctorHospitalId.isEmpty()) {
            logger.error("更新失败，医生信息不完整");
            return null;
        }
        info.put("hospitalId", doctorHospitalId);
        logger.info("doctorName为:\t"+user.getDoctor().getName());
        logger.info("doctorHospitalId为:\t"+doctorHospitalId);
        logger.info("doctorHospitalName为:\t"+ Generator.hospitalName(doctorHospitalId));
        return info;
    }

    @Test
    public void test_01_认证医生失败(){

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/doctor-manage/info?id=" + "6411" +
                "?token=" + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
               // "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImppbmcudGlhbkBtaW5neWl6aHVkYW8uY29tIiwibmFtZSI6Ilx1NzUzMFx1OTc1OSIsInN1YiI6MjMsImlzcyI6Imh0dHA6Ly9zZXJ2aWNlcy5kZXYubXl6ZC5pbmZvL2FwaS92MS9jYWxsYmFjayIsImlhdCI6MTUwODk4ODc5MiwiZXhwIjoxNTA5MjA0NzkyLCJuYmYiOjE1MDg5ODg3OTIsImp0aSI6IjNBVFU2YUduanNPM3JKbFIifQ.wimXEKej2E17TRipEbBqumG8YUtJvQTYnjR_LdOYgso"
        );

        WebDriverWait wait = new WebDriverWait(driver, 10);

        //处理隐藏元素的方法
        Actions action = new Actions(driver);
        WebElement nav = driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[1]/button"));
        //判断当nav元素出现时才执行相应的操作
        if(nav.isDisplayed()){
            System.out.println("found=================");
            action.moveToElement(nav).build().perform();
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('appContentContainer').style.display='block';");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//ul[@class='dropdown-menu']/li)[last()]/a")));
        WebElement onElement1 = driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[last()]/a"));
        action.moveToElement(onElement1).click().build().perform();

        //driver.findElement(By.linkText("认证失败")).click();
        new Select(driver.findElement(By.id("failReason"))).selectByVisibleText("您的个人信息有误。");
        driver.findElement(By.id("otherReason")).clear();
        driver.findElement(By.id("otherReason")).sendKeys("测试");
        driver.findElement(By.xpath("(//button[@type='button'])[10]")).click();
    }

    @Test
    public void test_02_认证医生成功() throws InterruptedException {

        //设置启用的webdriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver2.33//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //设置登录的网址
        driver.get("http://work.dev.myzd.info/doctor-manage/info?id=" + "6129" +
                        "?token=" + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIzLCJtb2JpbGUiOiIxODMxNzE4NjI1NiIsImF2YXRhciI6Imh0dHBzOi8vcC5xbG9nby5jbi9iaXptYWlsL2VSS3J0UkwxOThGVWw4RmtmT1NEaGZkdGljYXBDTEVpY2ZWNkxVV1o1YXNoRGpYc2hzOFZicGFBLzAiLCJ1c2VyTmFtZSI6IueUsOmdmSIsInVpZCI6IlNIMDE0MyIsIm5iZiI6MTUwOTY5NTc2Miwic3RhZmZfaWQiOiJTSDAxNDMiLCJuYW1lIjoi55Sw6Z2ZIiwiZXhwIjoxNTEwMzAwNTYyLCJkZXBhcnRtZW50IjoiMTMiLCJ1c2VyIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJpYXQiOjE1MDk2OTU3NjIsImVtYWlsIjoiamluZy50aWFuQG1pbmd5aXpodWRhby5jb20iLCJqdGkiOiLgoYjupa7tn6Hjm7jlmbTjhanrtrTlj5LluYTjtZfwn4O16pi75Im75ZOp5pi3In0._P1QH3_VeL4rBiepQZLGtzpZkzE6NdqTW9tWTXI867k"
                // "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImppbmcudGlhbkBtaW5neWl6aHVkYW8uY29tIiwibmFtZSI6Ilx1NzUzMFx1OTc1OSIsInN1YiI6MjMsImlzcyI6Imh0dHA6Ly9zZXJ2aWNlcy5kZXYubXl6ZC5pbmZvL2FwaS92MS9jYWxsYmFjayIsImlhdCI6MTUwODk4ODc5MiwiZXhwIjoxNTA5MjA0NzkyLCJuYmYiOjE1MDg5ODg3OTIsImp0aSI6IjNBVFU2YUduanNPM3JKbFIifQ.wimXEKej2E17TRipEbBqumG8YUtJvQTYnjR_LdOYgso"
        );

        WebDriverWait wait = new WebDriverWait(driver, 10);

        //处理隐藏元素的方法
        Actions action = new Actions(driver);
        WebElement nav = driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[1]/button"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='appContentContainer']/div/div[1]/button")));
        //判断当nav元素出现时才执行相应的操作
        if(nav.isDisplayed()){
            System.out.println("found=================");
            action.moveToElement(nav).build().perform();
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('appContentContainer').style.display='block';");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//ul[@class='dropdown-menu']/li)[1]/a")));
        WebElement onElement1 = driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[1]/a"));
        action.moveToElement(onElement1).click().build().perform();

        //认证医生时选择医院的科室
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("department_title")));

        Thread.sleep(2000);
        new Select(driver.findElement(By.id("department_title"))).selectByIndex(1);
        new Select(driver.findElement(By.id("department_title"))).selectByValue("9");
        new Select(driver.findElement(By.id("department_title"))).selectByVisibleText("外科");
        driver.findElement(By.xpath("(//button[@type='button'])[10]")).click();

      //  driver.findElement(By.cssSelector("button.btn.btn-success.btn-sm.ml10.col-md-1']")).click();
//        driver.findElement(By.xpath("(//button[@class='btn.btn-success.btn-sm.ml10.col-md-1'])[1]")).click();
//        driver.findElement(By.xpath("//div[@id='appContentContainer']/div/div[5]/div/div[2]/div/div[2]/div/div[2]/div/div[1]/div/button")).click();
        driver.findElement(By.xpath("(//button[@type='button'])[16]")).click();

    }
}
