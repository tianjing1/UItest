package com.mingyizhudao.qa.tc.login;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.util.HttpRequest;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created by ttshmily on 22/3/2017.
 */
public class SendVerifyCode extends BaseTest{

    public static String uri = "/api/login/sendVerifyCode";
    public static String mock = false ? "/mockjs/1" : "";
    public static String host = "http://login.dev.mingyizhudao.com";
    public static String mobile;
    public static String token;

    public static String send() {
        String res = "";
        MobileProfile body = new MobileProfile(true);
        CheckVerifyCode.mobile = mobile;
        logger.info("请求验证码到手机号" + mobile + " ...") ;
        try {
            res = HttpRequest.sendPost(SendVerifyCode.host+uri, body.body.toString(), "");
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("返回数据: " + unicodeString(res));
        logger.info("mobile是: " + mobile + "...请发送验证码到服务器进行验证");
        return mobile;
    }

    @Test
    public void 填写错误手机号发送验证码成功失败() {
        String res = "";
        MobileProfile body = new MobileProfile(false);
        body.body.replace("mobile", "");
        try {
            res = HttpRequest.sendPost(host+mock+uri,body.body.toString(), "");
            checkResponse(res);
            Assert.fail("res should fail"); // 如果没有exception，就是fail
        } catch (IOException e) {
            logger.info("res returns error because of malformed input");
        } catch (JSONException e) {
        }
    }

    @Test
    public void 填写正确手机号发送验证码成功() {
        String res = "";
        MobileProfile body = new MobileProfile(true);
        try {
            res = HttpRequest.sendPost(host+mock+uri,body.body.toString(), "");
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code,"1000000","消息码不正确");
    }

    @Test
    public void 填写错误少于11位的手机号发送验证码失败() {
        String res = "";
        MobileProfile body = new MobileProfile(false);
        body.body.replace("mobile", "1330000000");
        try {
            res = HttpRequest.sendPost(host+mock+uri,body.body.toString(), "");
            checkResponse(res);
            Assert.fail("res should fail"); // 如果没有exception，就是fail
        } catch (IOException e) {
            logger.info("res returns error because of malformed input: mobile number less than 11");
        } catch (JSONException e) {

        }
    }

}

class MobileProfile {

    public JSONObject body = new JSONObject();

    public MobileProfile(boolean init) {
        if (init) {
            body.accumulate("mobile", "13" + phone() + "9999");
        } else {
            body.accumulate("mobile", "");
        }
    }

    public String phone() {
        Random random = new Random();
        Integer m = random.nextInt(99999);
        SendVerifyCode.mobile = "13" + String.format("%05d",m) + "9999";
        return String.format("%05d",m);

    }
}
