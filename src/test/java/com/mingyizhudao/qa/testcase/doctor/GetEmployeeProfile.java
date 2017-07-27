package com.mingyizhudao.qa.testcase.doctor;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.util.HttpRequest;
import com.mingyizhudao.qa.util.Generator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ttshmily on 21/3/2017.
 */
public class GetEmployeeProfile extends BaseTest {

    public static final Logger logger= Logger.getLogger(GetEmployeeProfile.class);
    public static String uri = "/api/getemployeeprofile";
    public static String mock = false ? "/mockjs/1" : "";

    public static String getEmployeeProfile() {
        return "";
    }

    @Test
    public void test_01_有token的用户请求可以获得有效信息() {
        String res = "";
        HashMap<String, String> query = new HashMap<>();
        query.put("number", Generator.randomEmployeeId());
        try {
            res = HttpRequest.sendGet(host_doc +uri,query, mainToken);
        } catch (IOException e) {
            logger.error(e);
            Assert.fail();
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "错误码应该是1000000");
        Assert.assertNotNull(Generator.parseJson(data, "employee:id"), "id must not be null");
        Assert.assertNotNull(Generator.parseJson(data, "employee:name"), "name must not be null");
        Assert.assertNotNull(Generator.parseJson(data, "employee:number"), "number must not be null");
    }

    @Test
    public void test_02_没有token的用户请求可以获得有效信息() {
        String res = "";
        HashMap<String, String> query = new HashMap<>();
        query.put("number", Generator.randomEmployeeId());
        try {
            res = HttpRequest.sendGet(host_doc +uri, query, "");
        } catch (IOException e) {
            logger.error(e);
            Assert.fail();
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertNotNull(Generator.parseJson(data, "employee:id"), "id must not be null");
        Assert.assertNotNull(Generator.parseJson(data, "employee:name"), "name must not be null");
        Assert.assertNotNull(Generator.parseJson(data, "employee:number"), "number must not be null");
    }

    @Test
    public void test_03_员工ID无效时返回空的Employee() {
        String res = "";
        try {
            res = HttpRequest.sendGet(host_doc +mock+uri,"number=SH0444", "");
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertNull(Generator.parseJson(data, "employee:id"));
    }
}
