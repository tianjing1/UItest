package com.mingyizhudao.qa.dataprofile;

import com.mingyizhudao.qa.tc.SendVerifyCode;
import net.sf.json.JSONObject;

import java.util.Random;

/**
 * Created by ttshmily on 8/4/2017.
 */
public class MobileProfile {

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