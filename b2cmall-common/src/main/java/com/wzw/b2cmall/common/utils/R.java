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
    public static final String PAGE_KEY="page";

    @ApiModelProperty(value = "自定义状态码",example = "0")
    private Integer code;
    @ApiModelProperty(value = "提示消息",example = "success")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "返回的分页数据")
    private PageUtils page;
    @ApiModelProperty(value = "额外的数据信息:扩展功能(信息)")
    private Map<String,Object> extraDatas;


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

    public T getData() {
        if (data==null&&extraDatas!=null){ //兼容旧版
            return (T) extraDatas.get(DATA_KEY);
        }
        return data;
    }

    public R setData(T data) {
        this.data = data;
        return this;
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
    @Deprecated
    public R put(String key, Object value) {
        //为了兼容以前的版本 所作的扩展
        if (key.equals(CODE_KEY)){
            return setCode((Integer) value);
        }
        if (key.equals(MSG_KEY)){
            return setMsg((String) value);
        }
        if (key.equals(PAGE_KEY)){
            return setPage((PageUtils) value);
        }
        if (key.equals(DATA_KEY)){
            return setData((T)value);
        }

        if (extraDatas ==null){
            extraDatas =new HashMap<String,Object>();
        }
        extraDatas.put(key,value);
        if (data==null){
            extraDatas.put(DATA_KEY,value);
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
    @Deprecated
    public Object get(String key) {
        //为了兼容以前的版本 所作的扩展
        if (key.equals(CODE_KEY)){
            return code;
        }
        if (key.equals(MSG_KEY)){
            return msg;
        }
        if (key.equals(PAGE_KEY)){
            return page;
        }
        if (key.equals(DATA_KEY)){
            return data;
        }
        if (extraDatas !=null&& extraDatas.containsKey(key)){
            return extraDatas.get(key);
        }
        return null;
    }

    public R putExtraData(String key,Object value){
        this.extraDatas.put(key,value);
        return this;
    }
    public Object getExtraData(String key){
        return this.extraDatas.get(key);
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

    /*
    * 因为同意返回结果会拦截异常并统一返回,因此http协议中携带的 状态码意义不大
    * 这里的code 0表示成功,是固定值,因此正常放回结果不提供设置code的构造方法
    * */
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

    /**
     * @Description: 兼容以前的api
     * @Param java.util.Map<java.lang.String map
     * @ return com.wzw.b2cmall.common.utils.R < G>
     * date 2023/9/14 22:32
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    @Deprecated
    public static <G> R<G>  ok(Map<String, Object> map) {
        R<G> r = new R();
        map.forEach((key,value)->{
            r.put(key,value);
        });
        return r;
    }


}
