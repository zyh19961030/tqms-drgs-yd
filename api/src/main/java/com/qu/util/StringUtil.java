package com.qu.util;

import org.apache.commons.lang3.StringUtils;

/**
 * String工具类
 *
 * @author 闫润杰 2020-12-29
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }
}
