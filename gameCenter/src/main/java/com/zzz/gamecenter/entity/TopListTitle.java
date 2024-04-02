package com.zzz.gamecenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopListTitle {
    private Long tlId;
    private String tlName;
    private String tlSrc;
}
