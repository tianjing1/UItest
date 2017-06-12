package com.mingyizhudao.qa.testcase.crm;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.KB;
import com.mingyizhudao.qa.dataprofile.crm.ExpertProfile;
import com.mingyizhudao.qa.dataprofile.crm.HospitalProfile;
import com.mingyizhudao.qa.util.HttpRequest;
import com.mingyizhudao.qa.util.UT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ttshmily on 16/5/2017.
 */
public class KBExpert_Update extends BaseTest {

    public static final Logger logger= Logger.getLogger(KBExpert_Create.class);
    public static String uri = "/api/v1/medicallibrary/doctors/{id}";
    public static String mock = false ? "/mockjs/1" : "";
    public static String token= "";

    public static HashMap<String, String> Update(String expertId, ExpertProfile ep) {
        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, ep.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        JSONObject node = JSONObject.fromObject(res);
        HashMap<String, String> result = new HashMap<>();
        if(!node.getString("code").equals("1000000")) return null;
        if(!node.has("data")) return null;
        JSONObject expert = node.getJSONObject("data");
        result.put("id", expert.getString("id"));
        result.put("name", expert.getString("name"));
        result.put("major_id", expert.getString("major_id"));
        result.put("hospital_id", expert.getString("hospital_id"));
        return result;
    }

    @Test
    public void test_01_没有token不能操作() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String name = "改名了";
        epModified.body.put("name", name);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), "", pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertNotEquals(code, "1000000", "没有token不能操作");
    }

    @Test
    public void test_02_更新个人信息_name() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String name = "改名了";
        epModified.body.put("name", name);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "我想1000000");

        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "name"), name, "姓名未更新");
    }

    @Test
    public void test_03_更新个人信息_hospital_id() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String hospitalId = UT.randomHospitalId();
        epModified.body.put("hospital_id", hospitalId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "我想1000000");

        res = KBHospital_Detail.Detail(hospitalId);
        checkResponse(res);
        String cityId = parseJson(data, "city_id");
        String countryId = parseJson(data, "country_id");

        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "hospital_id"), hospitalId, "医生的医院ID没有更新");
        Assert.assertEquals(parseJson(data, "hospital_name"), UT.hospitalName(hospitalId), "医生的医院名称没有更新");
        Assert.assertEquals(parseJson(data, "city_id"), cityId, "医生的城市没有更新");
        Assert.assertEquals(parseJson(data, "country_id"), countryId, "医生的地区名称没有更新");

    }

    @Test
    public void test_04_更新个人信息_禁止更新city_id() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String cityId = UT.randomCityId();
        epModified.body.put("city_id", cityId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertNotEquals(code, "1000000", "禁止更新city_id");

        res = KBExpert_Detail.Detail(expertId);
    }

    @Test
    public void test_05_更新个人信息_禁止更新county_id() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String countryId = UT.randomCountyId();
        epModified.body.put("county_id", countryId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertNotEquals(code, "1000000", "禁止更新country_id");

        res = KBExpert_Detail.Detail(expertId);
    }

    @Test
    public void test_06_更新个人信息_更新major_id() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String majorId = UT.randomMajorId();
        epModified.body.put("major_id", majorId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
            checkResponse(res);
            Assert.assertEquals(code, "1000000", "更新major_id失败");
        } catch (IOException e) {
            logger.error(e);
        }
        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "major_id"), majorId, "专业ID没有更新");
        Assert.assertEquals(parseJson(data, "major"), UT.majorName(majorId), "专业名称没有更新");


        epModified.body.replace("major_id", "11"+majorId);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
            checkResponse(res);
            Assert.assertNotEquals(code, "1000000", "错误major_id");
        } catch (IOException e) {
            logger.error(e);
        }
        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "major_id"), majorId, "专业ID没有更新");
        Assert.assertEquals(parseJson(data, "major"), UT.majorName(majorId), "专业名称没有更新");
    }

    @Test
    public void test_07_更新个人信息_更新medical_title_list和academic_title_list() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医库医生失败，退出用例执行");
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String medical_title_list = UT.randomMedicalId();
        String academic_title_list = UT.randomAcademicId();
        epModified.body.put("medical_title_list", medical_title_list);
        epModified.body.put("academic_title_list", academic_title_list);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "更新medical_title_list失败");

        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "medical_title_list"), medical_title_list, "技术职称没有更新");
        Assert.assertEquals(parseJson(data, "academic_title_list"), academic_title_list, "学术职称没有更新");

        medical_title_list = UT.randomMedicalId();
        academic_title_list = UT.randomAcademicId();
        epModified.body.replace("medical_title_list", medical_title_list);
        epModified.body.replace("academic_title_list", academic_title_list);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "更新medical_title_list失败");

        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "medical_title_list"), medical_title_list, "技术职称没有更新");
        Assert.assertEquals(parseJson(data, "academic_title_list"), academic_title_list, "学术职称没有更新");
    }

    @Test
    public void test_08_更新个人信息_更新specialty和honour和description() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        String expertId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", expertId);
        ExpertProfile epModified = new ExpertProfile(false);
        String specialty = "特长"+UT.randomString(100);
        String honour = "荣誉"+UT.randomString(50);
        String description = "简介"+UT.randomString(70);
        epModified.body.put("specialty", specialty);
        epModified.body.put("honour", honour);
        epModified.body.put("description", description);
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "更新三个说明字段失败");

        res = KBExpert_Detail.Detail(expertId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "specialty"), specialty, "特长没有更新");
        Assert.assertEquals(parseJson(data, "honour"), honour, "荣誉没有更新");
        Assert.assertEquals(parseJson(data, "description"), description, "简介没有更新");
    }

    @Test(enabled = true)
    public void test_09_更新avatar_url() {
        String res = "";
        ExpertProfile ep = new ExpertProfile(true);
        HashMap<String, String> info = KBExpert_Create.Create(ep);
        if (info == null) Assert.fail("创建医院失败，退出用例执行");
        String doctorId = info.get("id");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", doctorId);
        ExpertProfile epModified = new ExpertProfile(false);
        JSONObject large = new JSONObject();
        large.put("key", "test1.jpg");
        large.put("type", "5");
        large.put("tag", "");
        JSONObject medium = new JSONObject();
        medium.put("key", "test1.jpg");
        medium.put("type", "5");
        medium.put("tag", "");
        JSONObject small = new JSONObject();
        small.put("key", "test1.jpg");
        small.put("type", "5");
        small.put("tag", "");
        epModified.body.accumulate("avatar_url", large);
        epModified.body.accumulate("avatar_url", medium);
        epModified.body.accumulate("avatar_url", small);
        logger.info(epModified.body.toString());
        try {
            res = HttpRequest.sendPut(host_crm+uri, epModified.body.toString(), crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "医院图片更新失败");

        res = KBExpert_Detail.Detail(doctorId);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "avatar_url()"), "3", "医院图片更新失败");
        Assert.assertNotNull(parseJson(data, "avatar_url():url"), "医院图片更新失败");
    }

}