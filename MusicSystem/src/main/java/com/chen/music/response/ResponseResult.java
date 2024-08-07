package com.chen.music.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseResult {
    private boolean success;
    private int code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }

    public static ResponseResult GET(ResponseState state){
        return new ResponseResult(state);
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(ResponseState.SUCCESS);
    }
    public static ResponseResult SUCCESS(String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult ACCOUNT_NOT_LOGIN(){
        ResponseResult responseResult = new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        return responseResult;
    }

    public static ResponseResult ACCOUNT_NOT_LOGIN(String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
        responseResult.setMessage(message);
        return responseResult;
    }
    public static ResponseResult PERMISSION_DENIED(){
        return new ResponseResult(ResponseState.PERMISSION_DENIED);
    }

    public static ResponseResult FAILED(String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult ACCOUNT_DENIED() {
        return new ResponseResult(ResponseState.ACCOUNT_DENIED);
    }

    public static ResponseResult ERROR_404() {
        return new ResponseResult(ResponseState.ERROR_404);
    }

    public static ResponseResult ERROR_403() {
        return new ResponseResult(ResponseState.ERROR_403);
    }

    public static ResponseResult ERROR_504() {
        return new ResponseResult(ResponseState.ERROR_504);
    }

    public static ResponseResult ERROR_505() {
        return new ResponseResult(ResponseState.ERROR_505);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

//    public void setData(Object data) {
//        this.data = data;
//    }

    public ResponseResult setData(String key,Object data){
        this.data.put(key,data);
        return this;
    }

    public ResponseResult setData(Map<String,Object> data) {
        this.data.putAll(data);
        return this;
    }
}
