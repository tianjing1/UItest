package com.mingyizhudao.qa.testcase.crm;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.util.HttpRequest;
import com.mingyizhudao.qa.util.Generator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ttshmily on 25/4/2017.
 */
public class RegisteredDoctor_Detail extends BaseTest {

    public static final Logger logger= Logger.getLogger(RegisteredDoctor_Detail.class);
    public static final String version = "/api/v1";
    public static String uri = version+"/doctors/{id}/profiles";
    public static String mock = false ? "/mockjs/1" : "";

    public static String Detail(String regId) {
        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id",regId);
        try {
            res = HttpRequest.sendGet(host_crm+uri,"", crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        return res;
    }

    @Test
    public void test_01_获取医生详情_有效ID() {

        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id",mainDoctorId);
        try {
            res = HttpRequest.sendGet(host_crm+mock+uri, "", crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertNotNull(Generator.parseJson(data, "medical_title_list"));
        Assert.assertNotNull(Generator.parseJson(data, "academic_title_list"));
        Assert.assertNotNull(Generator.parseJson(data, "inviter_name"));
        Assert.assertNotNull(Generator.parseJson(data, "hospital_name"));
        Assert.assertNotNull(Generator.parseJson(data, "icon"));
        Assert.assertNotNull(Generator.parseJson(data, "audit_state"));
        Assert.assertNotNull(Generator.parseJson(data, "staff_id"));
        Assert.assertNotNull(Generator.parseJson(data, "staff_name"));
    }

    @Test
    public void test_02_获取医生详情_无效ID() {

        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id","1"+mainDoctorId);
        try {
            res = HttpRequest.sendGet(host_crm+mock+uri, "", crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000");
    }
}