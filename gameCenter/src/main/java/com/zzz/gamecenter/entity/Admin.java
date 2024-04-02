package com.zzz.gamecenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    private Long adminId;
    private String adminName;
    @JsonIgnore
    private String adminPwd;
    private String adminAcc;
}
