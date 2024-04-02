package com.zzz.gamecenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sort {
    private int sortId;
    private String sortName;
    private String sortSrc;
    private int num;
}
