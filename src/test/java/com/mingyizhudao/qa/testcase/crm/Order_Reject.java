package com.mingyizhudao.qa.testcase.crm;

import com.mingyizhudao.qa.common.BaseTest;
import com.mingyizhudao.qa.testcase.doctor.CreateOrder;
import com.mingyizhudao.qa.util.HttpRequest;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ttshmily on 25/4/2017.
 */
public class Order_RejectOrder extends BaseTest {

    public static final Logger logger= Logger.getLogger(Order_RejectOrder.class);
    public static final String version = "/api/v1";
    public static String uri = version+"/orders/{orderNumber}/rejectOrder";
    public static String mock = false ? "/mockjs/1" : "";

    public static String rejectOrder(String orderId) {

        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();
        pathValue.put("orderNumber", orderId);

        try {
            res = HttpRequest.sendPost(host_crm + uri, "", crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        res = Order_Detail.Detail(orderId);
        return parseJson(JSONObject.fromObject(res), "data:status"); // 期望9000
    }


    @Test
    public void test_01_客服拒绝订单_推荐之前() {

        String res = "";
        HashMap<String, String> pathValue = new HashMap<>();

        String order_number = CreateOrder.CreateOrder(mainToken); // create an order
        if (!Order_ReceiveTask.receiveTask(order_number).equals("2000")) {
            Assert.fail("领取失败，无法进行拒绝操作");
        }
        pathValue.put("orderNumber", order_number);

        try {
            res = HttpRequest.sendPost(host_crm + uri, "", crm_token, pathValue);
        } catch (IOException e) {
            logger.error(e);
        }
        checkResponse(res);
        Assert.assertEquals(code, "1000000", "拒绝订单失败");
        res = Order_Detail.Detail(order_number);
        checkResponse(res);
        Assert.assertEquals(parseJson(data, "major_reps_id"), "chao.fang@mingyizhudao.com");
        Assert.assertEquals(parseJson(data, "status"), "9000");
        Assert.assertEquals(parseJson(data, "order_number"), order_number);
    }
}
