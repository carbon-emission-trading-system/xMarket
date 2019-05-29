package com.stock.xMarket.response;

public class CommonReturnType {

    private Integer code;

    private Object data;

    private String message;

    //定义一个通用的创建方法
    public static CommonReturnType success() {
        CommonReturnType type = new CommonReturnType();
        type.setCode(200);
        type.setMessage("ok");
        return type;
    }


    public static CommonReturnType success(Object data) {
        CommonReturnType type = new CommonReturnType();
        type.setCode(200);
        type.setData(data);
        type.setMessage("ok");
        return type;
    }

    public static CommonReturnType error(Integer code, String message) {
        CommonReturnType type = new CommonReturnType();
        type.setCode(code);
        type.setMessage(message);
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
