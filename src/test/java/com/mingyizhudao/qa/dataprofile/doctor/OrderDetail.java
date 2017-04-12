package com.mingyizhudao.qa.dataprofile.doctor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by ttshmily on 8/4/2017.
 */
public class OrderDetail {

    public JSONObject body = new JSONObject();
    public JSONArray pics = new JSONArray();

    public OrderDetail(boolean init) {
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
            order.accumulate("medical_record_pictures", JSONObject.fromObject("{'key':'123';'type':'1'}"));
            order.accumulate("medical_record_pictures", JSONObject.fromObject("{'key':'456';'type':'1'}"));
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
            order.accumulate("medical_record_pictures", pics.toString());
        }
        body.accumulate("order",order);
    }

}