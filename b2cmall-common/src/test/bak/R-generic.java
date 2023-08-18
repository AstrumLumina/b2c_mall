/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.wzw.b2cmall.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
@ApiModel
@Data
public class R<T>{
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自定义状态码",example = "0")
    private Integer code;
    @ApiModelProperty(value = "提示消息",example = "success")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "额外的数据信息:扩展功能(信息)")
    private Map<String,Object> extraData;


	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		//r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		//super.put(key, value);
		return this;
	}
    public R setData(Object data){
       return this.put("data",data);
    }

}
