package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin adminLogin(String adminAcc, String adminPwd);

    int findAdmin(String adminAcc);

}
