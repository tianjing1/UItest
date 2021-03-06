package com.mingyizhudao.qa.functiontest.IMS.trading.appointment;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.common.TestLogger;
import com.mingyizhudao.qa.dataprofile.AppointmentTask;
import com.mingyizhudao.qa.utilities.Generator;
import com.mingyizhudao.qa.utilities.HttpRequest;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Detail extends BaseTest {
    public static String clazzName = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();
    public static TestLogger logger = new TestLogger(clazzName);
    public static final String version = "/api/v1";
    public static String uri = version + "/orders/{id}";

    public static String s_Detail(String id) {
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("id", id);
        return HttpRequest.s_SendGet(host_ims + uri, "", crm_token, pathValue);
    }

    @Test
    public void test_01_内部自建工单详情_检查必要字段() {
        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();

        AppointmentTask at = new AppointmentTask();
        String id = Create.s_CreateTid(at);
        pathValue.put("id", id);

        res = HttpRequest.s_SendGet(host_ims + uri, "", crm_token, pathValue);

        s_CheckResponse(res);
        Assert.assertEquals(code, "1000000");

        Assert.assertNotNull(data.getString("id"));
        Assert.assertEquals(data.getString("status"), "ASSIGNED");
        Assert.assertNotNull(data.getString("track_list"));
        Assert.assertEquals(data.getString("source_type"), at.getSource_type());
        Assert.assertNotNull(data.getString("created_at"));
        Assert.assertNotNull(data.getString("modified_at"));
        Assert.assertEquals(data.getString("creator_name"), mainOperatorName);

        JSONObject appointment_order = data.getJSONObject("appointment_order");
        Assert.assertEquals(appointment_order.getString("service_type"), at.getService_type());
        Assert.assertNotNull(appointment_order.getString("source_channel"));
        Assert.assertNotNull(appointment_order.getString("order_number"));
        Assert.assertNotNull(appointment_order.getString("appointment_status"));
        Assert.assertEquals(appointment_order.getString("patient_name"), at.getPatient_name());
        Assert.assertEquals(appointment_order.getString("patient_phone"), at.getPatient_phone());
        Assert.assertEquals(appointment_order.getString("patient_gender"), at.getPatient_gender());
        Assert.assertEquals(appointment_order.getString("patient_age"), String.valueOf(at.getPatient_age()));
        Assert.assertEquals(appointment_order.getString("patient_id_card"), at.getPatient_id_card());
        Assert.assertEquals(appointment_order.getString("patient_city_id"), at.getPatient_city_id());
        Assert.assertEquals(appointment_order.getString("patient_city_name"), Generator.cityName(at.getPatient_city_id()));
        Assert.assertNotNull(appointment_order.getString("patient_province_id"));
        Assert.assertNotNull(appointment_order.getString("patient_province_name"));
        Assert.assertEquals(appointment_order.getString("disease_id"), at.getDisease_id());
        Assert.assertEquals(appointment_order.getString("disease_name"), at.getDisease_name());
        Assert.assertEquals(appointment_order.getString("disease_description"), at.getDisease_description());
        Assert.assertEquals(appointment_order.getString("indications"), at.getIndications().toString());
        Assert.assertEquals(appointment_order.getString("previous_doctor_suggest"), at.getPrevious_doctor_suggest());
        Assert.assertEquals(appointment_order.getString("previous_hospital_id"), at.getPrevious_hospital_id());
        Assert.assertEquals(appointment_order.getString("previous_hospital_name"), at.getPrevious_hospital_name());
        Assert.assertNotNull(appointment_order.getString("previous_appointment_date"));
        Assert.assertEquals(appointment_order.getString("expected_appointment_hospital_id"), at.getExpected_appointment_hospital_id());
        Assert.assertEquals(appointment_order.getString("expected_appointment_hospital_name"), at.getExpected_appointment_hospital_name());
        Assert.assertEquals(appointment_order.getString("expected_appointment_hospital_alternative"), at.getExpected_appointment_hospital_alternative().toString());
        Assert.assertEquals(appointment_order.getString("expected_city_id"), at.getExpected_city_id());
        Assert.assertEquals(appointment_order.getString("expected_city_name"), Generator.cityName(at.getExpected_city_id()));
        Assert.assertNotNull(appointment_order.getString("expected_province_id"));
        Assert.assertNotNull(appointment_order.getString("expected_province_name"));
        Assert.assertEquals(appointment_order.getString("expected_doctor_id"), at.getExpected_doctor_id());
        Assert.assertEquals(appointment_order.getString("expected_doctor_name"), at.getExpected_doctor_name());
        Assert.assertEquals(appointment_order.getString("expected_doctor_alternative"), at.getExpected_doctor_alternative().toString());
//        Assert.assertTrue(Generator.sameDate(appointment_order.getString("expected_appointment_start_date"), at.getExpected_appointment_start_date(), "s"));
//        Assert.assertTrue(Generator.sameDate(appointment_order.getString("expected_appointment_due_date"), at.getExpected_appointment_due_date(), "s"));
//        Assert.assertTrue(Generator.sameDate(appointment_order.getString("previous_appointment_date"), at.getPrevious_appointment_date(), "d"));

    }

    //TODO
     public void test_02_外部患者提交订单的详情() {

     }
}
