package com.mingyizhudao.qa.dataprofile;

import static com.mingyizhudao.qa.utilities.Generator.*;
import static com.mingyizhudao.qa.utilities.Helper.simplify;

import com.mingyizhudao.qa.functiontest.patient.PatientSendVerifyCode;
import lombok.Data;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class AppointmentOrder {

    private String expected_appointment_start_date;
    private String expected_appointment_due_date;
    private String expected_appointment_hospital_id;
    private String expected_appointment_hospital_name;
    private String expected_city_id;
    private String expected_city_name;
    private String expected_doctor_id;
    private String expected_doctor_name;
    private String expected_province_id;
    private String expected_province_name;
    private String major_disease_id;
    private String major_disease_name;
    private String disease_description;
    private List<Picture> medical_record_pictures;

    private Integer patient_age;
    private Integer patient_gender;
    private String patient_name;
    private String patient_phone;
    private String patient_city_name;
    //add by tianjing 2017/09/01
    private String code;

    private String source_type;

    private String doctor_id;
    private String doctor_user_id;
    private String doctor_name;
    private String doctor_phone;
    private String doctor_medical_title;
    private String doctor_academic_title;
    private String doctor_hospital;
    private String doctor_department;
    private String appointment_fee;
    private String doctor_fee;
    private String platform_fee;
    private String appointment_date;

    public AppointmentOrder() {
        this.patient_name = "面诊病人"+randomString(4);
        this.patient_age = (int)randomInt(100);
        this.patient_gender = (int)randomInt(2);
        this.patient_phone = randomPhone();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.expected_appointment_start_date = randomDateFromNow(2,3, df);
        this.expected_appointment_due_date = randomDateFromNow(3,8, df);

        String tmp = randomDiseaseId();
        this.major_disease_id = tmp;
        this.major_disease_name = diseaseName(tmp);
        this.disease_description = randomString(300);

        tmp = randomProvinceId();
        this.expected_province_id = tmp;
        this.expected_province_name = provinceName(tmp);

        tmp = randomCityIdUnder(tmp);
        this.expected_city_id = tmp;
        this.expected_city_name = cityName(tmp);

        tmp = randomExpertId();
        this.expected_doctor_id = tmp;
        this.expected_doctor_name = expertName(tmp);

        tmp = randomHospitalId();
        this.expected_appointment_hospital_id = tmp;
        this.expected_appointment_hospital_name = hospitalName(tmp);

        String[] sources = new String[]{"BUSINESS", "HOT_LINE", "WEIBO", "BAIDU_BRIDGE", "SUSHU", "WECHAT", "PC_WEB", "MINGYIHUI", "RED_BIRD"};
        Random random = new Random();
        this.source_type = sources[random.nextInt(sources.length)];
    }

    public AppointmentOrder(String t) {
        switch (t) {
            case "confirm": {
                this.doctor_id = randomExpertId();
                this.doctor_name = expertName(doctor_id);
                break;
            }
            case "feedback": {
                break;
            }
            case "patient": {
                this.patient_name = "面诊病人"+randomString(4);
                this.patient_age = (int)randomInt(100);
                this.patient_gender = (int)randomInt(2);
                this.patient_phone = randomPhone();
                PatientSendVerifyCode.s_SendVerifyCode(patient_phone);
                this.code = "123456";

                String tmp = randomDiseaseId();
                this.major_disease_id = tmp;
                this.major_disease_name = diseaseName(tmp);
                this.disease_description = randomString(300);

                String[] sources = new String[]{"BUSINESS", "HOT_LINE", "WEIBO", "BAIDU_BRIDGE", "SUSHU", "WECHAT", "PC_WEB", "MINGYIHUI", "RED_BIRD"};
                Random random = new Random();
                this.source_type = sources[random.nextInt(sources.length)];
                break;
            }
            case "picture": {
                this.medical_record_pictures = new ArrayList<Picture>() {
                    {
                        add(new Picture("123.jpg", "7"));
                    }
                };
                break;
            }
            default: {
                this.patient_name = "面诊病人"+randomString(4);
                this.patient_age = (int)randomInt(100);
                this.patient_gender = (int)randomInt(2);
                this.patient_phone = randomPhone();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                this.expected_appointment_start_date = randomDateFromNow(2,3, df);
                this.expected_appointment_due_date = randomDateFromNow(3,8, df);

                String tmp = randomDiseaseId();
                this.major_disease_id = tmp;
                this.major_disease_name = diseaseName(tmp);
                this.disease_description = randomString(300);

                tmp = randomProvinceId();
                this.expected_province_id = tmp;
                this.expected_province_name = provinceName(tmp);

                tmp = randomCityIdUnder(tmp);
                this.expected_city_id = tmp;
                this.expected_city_name = cityName(tmp);

                tmp = randomExpertId();
                this.expected_doctor_id = tmp;
                this.expected_doctor_name = expertName(tmp);

                tmp = randomHospitalId();
                this.expected_appointment_hospital_id = tmp;
                this.expected_appointment_hospital_name = hospitalName(tmp);

                String[] sources = new String[]{"BUSINESS", "HOT_LINE", "WEIBO", "BAIDU_BRIDGE", "SUSHU", "WECHAT", "PC_WEB", "MINGYIHUI", "RED_BIRD"};
                Random random = new Random();
                this.source_type = sources[random.nextInt(sources.length)];
            }
        }
    }

    public String transform() {
        return simplify(this).toString();
    }

    public String printPictures() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i =0; i < medical_record_pictures.size(); i++) {
            sb.append(medical_record_pictures.get(i).print());
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");
        return sb.toString();
    }

    @Data
    public class Picture {
        String key;
        String type;
        public Picture(String key, String type) {
            this.key = key;
            this.type = type;
        }

        public String print() {
            return JSONObject.fromObject(this).toString();
        }
    }
}
