package com.wzw.b2cmall.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(description = "请求统一返回结果")
public  class  Result<T> extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自定义状态码",example = "0")
    private Integer code;
    @ApiModelProperty(value = "提示消息",example = "success")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    private  <T> Result() {
        code=0;
        msg="success";
    }
    public <T> Result(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public static <T> Result error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static <T> Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static <T> Result error(int code, String msg) {
        return new<T>  Result(code,msg);
    }

    public static <T> Result ok(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }


    public static Result ok() {
        return new Result();
    }
    public static Result ok(Map<String, Object> map) {
        return new Result<>().setData(map);
    }

  /*  public Result setMsg(String msg){
        this.msg=msg;
        return this;
    }
    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }
    public String getMsg(){
        return msg;
    }
    public Integer getCode() {
        return code;
    }*/
}
