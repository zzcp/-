package com.zzz.gamecenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String phone;
    @JsonIgnore
    private String pwd;
    private String imgUrl;
    private String name;
    private int gender;
    private String signature;
}
