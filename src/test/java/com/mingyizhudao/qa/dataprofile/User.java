package com.mingyizhudao.qa.dataprofile;

import static com.mingyizhudao.qa.utilities.Generator.*;
import static com.mingyizhudao.qa.utilities.Helper.*;
import lombok.Data;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;

@Data
public class User {

    private UserDetail doctor;

    public User() {
        this.doctor = new UserDetail();
    }

    public String transform() {
        HashMap<String, Object> a = new HashMap<>();
        for (String o:getNotNullFieldName(this)) {
            a.put(o, simplify(getFieldValueByName(o, this)));
        }
        return JSONObject.fromObject(a).toString();
    }

    @Data
    public class UserDetail {

        private String name;
        private String department;
        private String academic_title_list;
        private String medical_title_list;
        private String hospital_id;
        private List<Picture> doctor_card_pictures;
        private List<Specialty> exp_list;

        public UserDetail() {
            this.name = "田静"+randomString(4);
            this.department = "大科室"+randomString(2);
            this.academic_title_list = randomAcademicId();
            this.medical_title_list = randomMedicalId();
            this.hospital_id = randomHospitalId();
        }

        public String transform() {
            return simplify(this).toString();
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
}



