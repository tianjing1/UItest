package com.mingyizhudao.qa.functiontest.crm.kb.management;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.dataprofile.crm.DiseaseProfile_Test;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.utilities.Generator;
import com.mingyizhudao.qa.utilities.HttpRequest;
import com.mingyizhudao.qa.utilities.Helper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ttshmily on 1/6/2017.
 */
public class KBDisease_Update extends BaseTest {

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static final String version = "/api/v1";
    public static String uri = version + "/medicallibrary/diseases/{id}";

    public static HashMap<String, String> s_Update(String diseaseId, DiseaseProfile_Test dp) {
        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", diseaseId);
        res = HttpRequest.s_SendPut(host_crm + uri, dp.body.toString(), crm_token, pathValue);
        JSONObject node = JSONObject.fromObject(res);
        HashMap<String, String> result = new HashMap<>();
        if(!node.getString("code").equals("1000000")) return null;
        if(!node.has("data")) return null;
        JSONObject disease = node.getJSONObject("data");
        result.put("id", disease.getString("id"));
        result.put("name", disease.getString("name"));
        List<String> category_list = new ArrayList<>();
        JSONArray categoryList = node.getJSONObject("data").getJSONArray("category_list");
        for (int i=0; i<categoryList.size(); i++) {
            JSONObject category = categoryList.getJSONObject(i);
            category_list.add(category.getString("disease_category_id"));
        }
        result.put("category_list", category_list.toString());
        return result;
    }

    @Test
    public void test_01_更新疾病详情_基本信息() {

        String res = "";
        DiseaseProfile_Test dp = new DiseaseProfile_Test(true);
        HashMap<String, String> info = KBDisease_Create.s_Create(dp);
        if (info == null) Assert.fail("创建疾病失败，退出用例执行");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", info.get("id"));
        DiseaseProfile_Test dpModified = new DiseaseProfile_Test(false);
        dpModified.body.put("name", "修改疾病名称" + Generator.randomString(2));
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertEquals(Helper.s_ParseJson(data, "name"), dpModified.body.getString("name"));

        dpModified.body.remove("name");
        dpModified.body.put("description", "修改疾病描述" + Generator.randomString(30));
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertEquals(Helper.s_ParseJson(data, "description"), dpModified.body.getString("description"));

        dpModified.body.remove("description");
        dpModified.body.put("user_visible", 0);
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertEquals(Helper.s_ParseJson(data, "user_visible"), "false");

        dpModified.body.remove("user_visible");
        dpModified.body.put("is_common", 0);
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");
        Assert.assertEquals(Helper.s_ParseJson(data, "is_common"), "0");

    }

    @Test
    public void test_02_更新疾病详情_关联专业() {

        String res = "";
        DiseaseProfile_Test dp = new DiseaseProfile_Test(true);
        HashMap<String, String> info = KBDisease_Create.s_Create(dp);
        String diseaseId = info.get("id");
        if (info == null) Assert.fail("创建疾病失败，退出用例执行");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", diseaseId);
        DiseaseProfile_Test dpModified = new DiseaseProfile_Test(false);

        List<String> input_ids = new ArrayList<>();
        List<String> output_ids = new ArrayList<>();
        JSONObject categoryId = new JSONObject();
        String id = Generator.randomMajorId();
        categoryId.put("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");

        res = KBDisease_Detail.s_Detail(diseaseId);
        s_CheckResponse(res);
        JSONArray category_list = data.getJSONArray("category_list");
        for (int i=0; i<category_list.size(); i++) {
            JSONObject category = category_list.getJSONObject(i);
            output_ids.add(category.getString("disease_category_id"));
        }
        logger.info(input_ids.toString());
        logger.info(output_ids.toString());
        for (int i=0; i<output_ids.size(); i++) {
            Assert.assertTrue(input_ids.contains(output_ids.get(i)));
        }
        for (int i=0; i<input_ids.size(); i++) {
            Assert.assertTrue(output_ids.contains(input_ids.get(i)));
        }
    }

    @Test
    public void test_03_更新疾病详情_关联专业_去重() {

        String res = "";
        DiseaseProfile_Test dp = new DiseaseProfile_Test(true);
        HashMap<String, String> info = KBDisease_Create.s_Create(dp);
        String diseaseId = info.get("id");
        if (info == null) Assert.fail("创建疾病失败，退出用例执行");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", diseaseId);
        DiseaseProfile_Test dpModified = new DiseaseProfile_Test(false);

        List<String> input_ids = new ArrayList<>();
        List<String> output_ids = new ArrayList<>();
        JSONObject categoryId = new JSONObject();
        String id = Generator.randomMajorId();
        categoryId.put("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        //以下为重复
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
//        input_ids.add(id);
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
//        input_ids.add(id);
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");

        res = KBDisease_Detail.s_Detail(diseaseId);
        s_CheckResponse(res);
        JSONArray category_list = data.getJSONArray("category_list");
        for (int i=0; i<category_list.size(); i++) {
            JSONObject category = category_list.getJSONObject(i);
            output_ids.add(category.getString("disease_category_id"));
        }
        logger.info(input_ids.toString());
        logger.info(output_ids.toString());
        Assert.assertTrue(input_ids.size()==output_ids.size());
        for (int i=0; i<output_ids.size(); i++) {
            Assert.assertTrue(input_ids.contains(output_ids.get(i)));
        }
        for (int i=0; i<input_ids.size(); i++) {
            Assert.assertTrue(output_ids.contains(input_ids.get(i)));
        }
    }

    @Test
    public void test_04_更新疾病详情_关联专业_自动去除错误ID() {

        String res = "";
        DiseaseProfile_Test dp = new DiseaseProfile_Test(true);
        HashMap<String, String> info = KBDisease_Create.s_Create(dp);
        String diseaseId = info.get("id");
        if (info == null) Assert.fail("创建疾病失败，退出用例执行");
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", diseaseId);
        DiseaseProfile_Test dpModified = new DiseaseProfile_Test(false);

        List<String> input_ids = new ArrayList<>();
        List<String> output_ids = new ArrayList<>();
        JSONObject categoryId = new JSONObject();
        String id = Generator.randomMajorId();
        categoryId.put("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        id = Generator.randomMajorId();
        categoryId.replace("disease_category_id", id);
        dpModified.body.accumulate("category_list", categoryId);
        input_ids.add(id);
        //以下为错误
        categoryId.replace("disease_category_id", "11"+id);
        dpModified.body.accumulate("category_list", categoryId);
//        input_ids.add(id);
        categoryId.replace("disease_category_id", "22"+id);
        dpModified.body.accumulate("category_list", categoryId);
//        input_ids.add(id);
        res = HttpRequest.s_SendPut(host_crm + uri, dpModified.body.toString(), crm_token, pathValue);
        s_CheckResponse(res);
        Assert.assertNotEquals(code, "1000000"); //抱专业错误

//        res = KBDisease_Detail.s_Detail(diseaseId);
//        s_CheckResponse(res);
//        JSONArray category_list = data.getJSONArray("category_list");
//        for (int i=0; i<category_list.size(); i++) {
//            JSONObject category = category_list.getJSONObject(i);
//            output_ids.add(category.getString("disease_category_id"));
//        }
//        logger.info(input_ids.toString());
//        logger.info(output_ids.toString());
//        Assert.assertTrue(input_ids.size()==output_ids.size());
//        for (int i=0; i<output_ids.size(); i++) {
//            Assert.assertTrue(input_ids.contains(output_ids.get(i)));
//        }
//        for (int i=0; i<input_ids.size(); i++) {
//            Assert.assertTrue(output_ids.contains(input_ids.get(i)));
//        }
    }
}