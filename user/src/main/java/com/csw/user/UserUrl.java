package com.csw.user;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
public interface UserUrl {

    String SERVICE_NAME = "user-service";

    String SERVICE_HOSTNAME = "http://user-service";

    String USER_REGISTER_URL = "/users/register";

    String USER_INFO = "/users";

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }
}
