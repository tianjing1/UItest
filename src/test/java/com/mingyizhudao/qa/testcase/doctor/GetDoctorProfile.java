package com.mingyizhudao.qa.testcase.doctor;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.util.HttpRequest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
/**
 * Created by ttshmily on 20/3/2017.
 */
public class GetDoctorProfile extends BaseTest {

    public static final Logger logger= Logger.getLogger(GetDoctorProfile.class);
    public static String uri = "/api/getdoctorprofile";
    public static String mock = false ? "/mockjs/1" : "";
    public static String token= "";


    public static String getDoctorProfile(String token) {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc +mock+uri, "", token);
        } catch (IOException e) {
            logger.error(e);
        }
        return res;
    }

    @Test
    public void test_01_有token信息的请求可以获得有效信息() {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc +mock+uri,"", mainToken);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertNotNull(parseJson(data,"doctor"),"doctor字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:name"), "name字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:inviter_name"), "inviter_name字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:is_verified"),"is_verified字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:mobile"), "mobile字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:hospital_name"), "hospital_name字段缺失");

    }

    @Test
    public void test_02_没有token信息的请求不能获得个人信息并返回正确的错误提示() {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc+uri,"", "");
        } catch (IOException e) {
            logger.error(e);
        }
    //    logger.info(unicodeString(res));
        checkResponse(res);
        Assert.assertNotEquals(code, "1000000","没有登录信息，不应该返回data");

    }

    @Test
    public void test_03_错误token的请求不能获得个人信息并返回正确的错误提示() {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc +mock+uri,"", "nidawoya");
        } catch (IOException e) {
            logger.error(e);
        }
        //    logger.info(unicodeString(res));
        checkResponse(res);
        Assert.assertNotEquals(code, "1000000","错误token信息，不应该返回data");

    }

    @Test
    public void test_04_测试data字段返回了足够的医生信息() {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc +mock+uri,"", mainToken);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertNotNull(parseJson(data,"doctor"),"doctor字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:name"), "name字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:inviter_name"), "inviter_name字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:is_verified"),"is_verified字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:mobile"), "mobile字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:hospital_name"), "hospital_name字段缺失");
        Assert.assertNotNull(parseJson(data,"doctor:is_required"), "is_required字段缺失");
    }

}
