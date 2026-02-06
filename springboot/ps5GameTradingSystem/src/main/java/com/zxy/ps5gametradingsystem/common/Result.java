package com.zxy.ps5gametradingsystem.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

// 返回结果 统一类
public class Result implements Serializable {
    private  static  final  long serialVersionUID =1L;
    // 状态码
    private Integer code;
    // 提示信息 message
    private String message;
    // 返回的属性类型
    private Object data;

    /**
     * 直接返回成功结果
     * @param data
     * @return
     */
    public static Result success(Object data) {
//        return new Result(200, "success", data);
        return success(200, "success", data);
    }

    /**
     * 自定义返回成功结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result success(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }
    /**
     *不带结果直接返回成功
     ★ @return
     */
    public static Result success() {
        Result r = new Result();
        r.setCode(200);
        r.setMessage("操作成功");
        return r;
    }

    /**
     *直接返回失败信息
     @return
     */
    public static Result error() {
        return new Result(400,"操作失败",null);
    }


    /**
     * 自定义返回失败结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result error(int code,String msg,Object data){

        Result r = new Result();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }


}







