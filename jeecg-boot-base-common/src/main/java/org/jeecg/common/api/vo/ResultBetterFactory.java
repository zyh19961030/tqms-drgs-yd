package org.jeecg.common.api.vo;

public class ResultBetterFactory {

    private final static int ERROR = -1;

    private final static int FAIL = 1;

    private final static int SUCCESS = 0;

    private final static String FAIL_MSG = "fail";

    private final static String SUCCESS_MSG = "success";

    private final static String SUCCESS_MSG_TWO = "操作成功！";

    public static ResultBetter success() {
        return new ResultBetter<>(SUCCESS, SUCCESS_MSG_TWO);
    }

    public static ResultBetter success(Object data) {
        return new ResultBetter<>(SUCCESS, SUCCESS_MSG, data,true);
    }

    public static ResultBetter fail() {
        return new ResultBetter<>(FAIL, FAIL_MSG);
    }

    public static ResultBetter fail(String msg) {
        return new ResultBetter<>(FAIL, msg, null,false);
    }

    public static ResultBetter error(Object data) {
        return new ResultBetter<>(ERROR, null, data,false);
    }

}
