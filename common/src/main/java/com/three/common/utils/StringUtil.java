package com.three.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 字符串工具类
 *
 * @author cssw
 * @date 2017-11-10 上午11:03:50
 */
public class StringUtil {

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.isEmpty() || str.replaceAll(" ", "").isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(String... strs) {
        for (int i = 0; i < strs.length; i++) {
            if (isBlank(strs[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean contains(String str, String key) {
        if (str != null && str.contains(key)) {
            return true;
        }
        return false;
    }

    public static boolean contains(String... strs) {
        for (int i = 0; i < strs.length - 1; i++) {
            if (contains(strs[i], strs[strs.length - 1])) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String[] strs, String key) {
        for (int i = 0; i < strs.length; i++) {
            if (contains(strs[i], key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 首字母大写
     *
     * @param in
     * @return
     */
    public static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }

    public static String getStr(String str) {
        return str == null ? "" : str;
    }

    public static Set<Long> getStrToIdSet(String ids) {
        String[] idArray = StringUtils.split(ids, ",");
        return getStringArrayToIdSet(idArray);
    }

    public static Set<Long> getStringArrayToIdSet(String[] ids) {
        Set<Long> idSet = new HashSet<>();
        if (ids != null) {
            for (String id : ids) {
                if (isNotBlank(id)) {
                    idSet.add(Long.valueOf(id));
                }
            }
        }
        return idSet;
    }

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getStrToDate(String time) {
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
