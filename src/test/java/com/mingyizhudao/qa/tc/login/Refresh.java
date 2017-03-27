package com.mingyizhudao.qa.tc.login;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.tc.GetDoctorProfile;
import com.mingyizhudao.qa.util.HttpRequest;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

import static com.mingyizhudao.qa.tc.login.Refresh.token;

/**
 * Created by ttshmily on 22/3/2017.
 */
public class Refresh extends BaseTest{

    public static String uri = "/api/login/refresh";
    public static String mock = false ? "/mockjs/1" : "";
    public static String host = "http://login.dev.mingyizhudao.com";
    public static String mobile;
    public static String token;

    public static String refresh() {
        return "";
    }

    @Test
    public void 刷新token后返回新的token且老token依然可用() {
        String res = "";

        SendVerifyCode.send();
        CheckVerifyCode.check();
        // record the old token
        String oldToken = token;

        RefreshProfile body = new RefreshProfile(true);
        try {
            res = HttpRequest.sendPost(host+mock+uri,body.body.toString(), oldToken);
            checkResponse(res);
        } catch (IOException e) {

        } catch (JSONException e) {
        }
        Assert.assertEquals(code, "1000000");
        Assert.assertNotNull(parseJson(data,"token"), "token not exist");
        Assert.assertNotNull(parseJson(data, "expire"), "expire not exist");
        Assert.assertEquals(parseJson(data, "expire"), "7200");
        // update token if succeed
        token = parseJson(data, "token");
        CheckVerifyCode.token = token;

        // check old token still effective
        String r = GetDoctorProfile.getDoctorProfile(oldToken);
        checkResponse(r);
        Assert.assertEquals(code, "1000000", "old Token expired");
        String oldProfile = parseJson(data, "doctor");
        logger.debug(oldProfile);

        // check new token taking effect
        String s = GetDoctorProfile.getDoctorProfile(token);
        checkResponse(s);
        Assert.assertEquals(code, "1000000");
        String newProfile = parseJson(data, "doctor");
        logger.debug(newProfile);

        Assert.assertEquals(oldProfile, newProfile, "both token get the same profile");
    }

    @Test
    public void body中的token和http头中的token不一致() {
        String res = "";

        SendVerifyCode.send();
        CheckVerifyCode.check();

        // record the old token
        String oldToken = token;
        RefreshProfile body = new RefreshProfile(false);
        body.body.replace("token", mainToken);
        try {
            res = HttpRequest.sendPost(host+mock+uri,body.body.toString(), oldToken);
            checkResponse(res);
        } catch (IOException e) {
            Assert.fail();
        } catch (JSONException e) {
        }
        Assert.assertEquals(code, "1000000");

        // check token in body still effective
        String r = GetDoctorProfile.getDoctorProfile(mainToken);
        checkResponse(r);
        Assert.assertEquals(code, "1000000");
//        Assert.assertNotNull(parseJson(data,"token"), "token not exist");
//        Assert.assertNotNull(parseJson(data, "expire"), "expire not exist");
//        Assert.assertEquals(parseJson(data, "expire"), "600");
    }
}

class RefreshProfile {

    public JSONObject body = new JSONObject();

    public RefreshProfile(boolean init) {
        if (init) {
            body.accumulate("token", token);
        } else {
            body.accumulate("token", "");
        }
    }

    public String phone() {
        Random random = new Random();
        Integer m = random.nextInt(99999);
        SendVerifyCode.mobile = "13" + String.format("%05d",m) + "9999";
        return String.format("%05d",m);
    }
}