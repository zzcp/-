package com.zzz.gamecenter.bean;

import com.zzz.gamecenter.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result successResult(Integer code,String msg,Object data){
        return new Result(code,msg, data);
    }

    public static Result failResult(Integer code,String msg,Object data){
        return new Result(code,msg, data);
    }

}
