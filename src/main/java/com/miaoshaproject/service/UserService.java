package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    /*
    telephone: User register telephone num
    password: after encrypted
     */
    UserModel validateLogin(String telephone,String encrptPassword) throws BusinessException;
}
