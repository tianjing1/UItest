package com.mingyizhudao.qa.testcase.crm;

import com.mingyizhudao.qa.common.BaseTest;
import org.apache.log4j.Logger;

/**
 * Created by ttshmily on 25/4/2017.
 */
public class Order_RecommendDoctor extends BaseTest {

    public static final Logger logger= Logger.getLogger(Order_RecommendDoctor.class);
    public static final String version = "/api/v1";
    public static String uri = version+"/orders/{orderNumber}/recommends";
    public static String mock = false ? "/mockjs/1" : "";


}
