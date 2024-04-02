package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    User findUser(String phone);

    User userLogin(String phone,String pwd);

    int addUser(User user);
    User getUserInfo(Long userId);
    int updateUser(User user);
    ArrayList<User> findUserByName(String keyword);
    int deleteUser(int userId);
    ArrayList<User> getUserList(int offset,int limit);

    int getUserNum(String keyword);
}
