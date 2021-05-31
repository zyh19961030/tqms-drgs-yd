package com.qu.util;

/**
 * 科室工具类
 *
 * @author j 2021-05-30
 */
public class DeptUtil {


    private static final String DEPT_CLINICAL = "69a05e10e12f4ab3a1cae9f812352458";
    private static final String DEPT_CLINICAL_NAME = "临床科室";


    /**
     * 判断传入科室类型是否是临床科室
     *
     * @param type
     * @return
     */
    public static boolean isClinical(String type) {
        return DEPT_CLINICAL.equals(type)||DEPT_CLINICAL_NAME.equals(type);
    }
}
