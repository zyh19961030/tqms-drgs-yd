package org.jeecg.common.api.vo;

public class ResultFactory {

    private final static int ERROR = -1;

    private final static int FAIL = 1;

    private final static int SUCCESS = 0;

    private final static String FAIL_MSG = "fail";

    private final static String SUCCESS_MSG = "success";

    private final static String SUCCESS_MSG_TWO = "操作成功！";

    public static Result success() {
        return new Result(SUCCESS, SUCCESS_MSG_TWO);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS, SUCCESS_MSG, data,true);
    }

    public static Result fail() {
        return new Result(FAIL, FAIL_MSG);
    }

    public static Result fail(String msg) {
        return new Result(FAIL, msg, null,false);
    }

    public static Result error(Object data) {
        return new Result(ERROR, null, data,false);
    }

}
