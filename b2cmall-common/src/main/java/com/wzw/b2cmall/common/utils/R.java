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
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class R<T>{
    private static final long serialVersionUID = 1L;

    public static final String CODE_KEY="code";
    public static final String DATA_KEY="data";
    public static final String MSG_KEY="msg";


    @ApiModelProperty(value = "自定义状态码",example = "0")
    private Integer code;
    @ApiModelProperty(value = "提示消息",example = "success")
    private String msg;
    //@ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "额外的数据信息:扩展功能(信息)")
    private Map<String,Object> extraData;


    //默认构造函数是成功,为了防止不了解此类的人new意料之外的R,这里限制为私有
    public R() {
        code=0;
        msg="success";
    }
    public R(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }
    public R(int code,String msg,T data) {
        this.code=code;
        this.msg=msg;
        data=data;
    }
    /**
     * @Description: 保存额外的扩展信息,如果data还没有内容,还会默认往map中保存一份名为data的副本
     * @Param java.lang.String key
     * @Param java.lang.Object value
     * @return com.wzw.b2cmall.common.utils.R
     * date 2023/8/18 13:26
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    public R put(String key, Object value) {
        //为了兼容以前的版本 所作的扩展
        if (key.equals(CODE_KEY)){
            return setCode((Integer) value);
        }
        if (key.equals(MSG_KEY)){
            return setMsg(msg);
        }

        if (extraData==null){
            extraData=new HashMap<String,Object>();
        }
        extraData.put(key,value);
        if (data==null){
            extraData.put(DATA_KEY,value);
        }
        return this;
    }
    /**
     * @Description: 获取额外的信息
     * @Param java.lang.String key
     * @return java.lang.Object
     * date 2023/8/18 13:31
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    public Object get(String key) {
        //为了兼容以前的版本 所作的扩展
        if (key.equals(CODE_KEY)){
            return code;
        }
        if (key.equals(MSG_KEY)){
            return msg;
        }

        if (extraData==null){
            return extraData.get(key);
        }
        return null;
    }
    /**
     * @Description: 由于以上扩展, 所以需要修改 getData方法,不能使用lombok默认的
     * @Param
     * @return T
     * date 2023/8/18 13:36
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    public T getData(){
        if (data!=null){
            return data;
        }else if (extraData!=null){
            return (T) extraData.get("data");
        }
        return null;
    }
    public R setData(Object data){
        put(DATA_KEY,data);
        return this;
    }

    //error时没有数据 ,因此指定泛型为 void
    public static <G> R<G>  error(int code, String msg) {
        return new R<G>(code,msg);
    }
    public static <G> R<G>   error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }
    public static <G> R<G>  error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static <G> R<G> ok(int code, String msg,G data){
        return new R<G>(code,msg,data);
    }
    public static <G> R<G> ok(String msg) {
        return new R().setMsg(msg);
    }
    public static <G> R<G> ok(G data){
        return new R<G>().setData(data);
    }
    public static <G> R<G> ok(String msg,G data){
        return new R<G>().setData(data).setMsg(msg);
    }
    public static <G> R<G> ok() {
        return new R<G>();
    }
    public static <G> R<G>  ok(Map<String, Object> map) {
        R<G> r = new R();
        map.forEach((key,value)->{
            r.put(key,value);
        });
        return r;
    }




}
