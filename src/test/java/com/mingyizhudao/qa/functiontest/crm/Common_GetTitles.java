package com.mingyizhudao.qa.functiontest.crm;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.utilities.HttpRequest;
import com.mingyizhudao.qa.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by ttshmily on 25/4/2017.
 */
public class Common_GetTitles extends BaseTest {

    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static final String version = "/api/v1";
    public static String uri = version+"/doctors/titles";

    @Test
    public void test_01_获取所有职级列表() {

        String res = "";

        try {
            res = HttpRequest.s_SendGet(host_crm + uri, "", crm_token, null);
            s_CheckResponse(res);
            Assert.assertEquals(code, "1000000");
            int academicLen = Integer.parseInt(Generator.parseJson(data, "list:academic()"));
            int medicalLen = Integer.parseInt(Generator.parseJson(data, "list:medical()"));
            Assert.assertEquals(academicLen, 4);
            Assert.assertEquals(medicalLen, 12);

        } catch (IOException e) {
            logger.error(e);
        }
    }
}
