package com.mingyizhudao.qa.dataprofile;

import net.sf.json.JSONObject;

/**
 * Created by ttshmily on 8/4/2017.
 */
public class OrderProfile {

    public JSONObject body = new JSONObject();
    public OrderProfile(boolean init) {
        JSONObject order = new JSONObject();
        if (init) {
            order.accumulate("patient_name", "方超");
            order.accumulate("patient_gender", "1");
            order.accumulate("patient_age", "31");
            order.accumulate("patient_phone", "13817634203");
            order.accumulate("major_disease_id", "33");
            order.accumulate("minor_disease_id", "32");
            order.accumulate("diagnosis", "工程师");
            order.accumulate("expected_surgery_start_date", "2017-04-09");
            order.accumulate("expected_surgery_due_date", "2017-05-09");
            order.accumulate("expected_surgery_hospital_id", "43");
            order.accumulate("pics", "{}");
        } else {
            order.accumulate("patient_name", "");
            order.accumulate("patient_gender", "");
            order.accumulate("patient_age", "");
            order.accumulate("patient_phone", "");
            order.accumulate("major_disease_id", "");
            order.accumulate("minor_disease_id", "");
            order.accumulate("diagnosis", "");
            order.accumulate("expected_surgery_start_date", "");
            order.accumulate("expected_surgery_due_date", "");
            order.accumulate("expected_surgery_hospital_id", "");
            order.accumulate("pics", "");
        }
        this.body.accumulate("order",order);
    }

}
