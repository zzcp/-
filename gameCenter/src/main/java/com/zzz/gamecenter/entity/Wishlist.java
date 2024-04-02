package com.zzz.gamecenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Wishlist {
    private Long wishlistId;
    private Long userId;
    private Long gameId;
    private boolean isPurchase;
    private Long sortId;
    private String gameName;
    private String imgUrl;
    private String score;
    private String price;
    private Object tags;

    //lombok 原因返回给前端时无前缀is_，自定义设置返回字段名
    @JsonProperty(value = "isPurchase")
    public boolean isPurchase() {
        return isPurchase;
    }
}
