package com.zzz.gamecenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Carousel {
    private Long gameId;
    private Long sortId;
    private String url;
}
