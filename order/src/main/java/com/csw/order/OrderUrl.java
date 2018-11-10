package com.csw.order;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
public interface OrderUrl {

    String SERVICE_NAME = "order-service";

    String SERVICE_HOSTNAME = "http://order-service";

    String PLACE_ORDER = "/orders/place";

    String ORDER_INFO = "/orders/{orderId}";

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }
}
