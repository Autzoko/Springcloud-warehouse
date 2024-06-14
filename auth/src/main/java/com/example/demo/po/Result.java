package com.example.demo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//响应码
    private String msg;
    private Object data;//返回的数据

    //增删改成功查询
    public static Result success(){
        return new Result(200,"success",null);
    }

    //查询成功响应
    public static Result success(Object data){
        return new Result(200,"success",data);
    }

    public static Result error(Integer code,String msg){
        return new Result(code,msg,null);
    }

}
