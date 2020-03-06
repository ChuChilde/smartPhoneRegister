package com.hardy.fleamarket.service.impl;

import com.hardy.fleamarket.dao.PasswordMapper;
import com.hardy.fleamarket.dao.UserMapper;
import com.hardy.fleamarket.entity.Password;
import com.hardy.fleamarket.entity.User;
import com.hardy.fleamarket.log.OutputExceptionLog;
import com.hardy.fleamarket.service.model.UserModel;
import com.hardy.fleamarket.service.serviceexception.ExceptionIdentifyCode;
import com.hardy.fleamarket.service.serviceexception.ServiceCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务管理类，执行需要事务管理的部分在该类实现
 */
@Service
public class TransactionalClass {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    UserServiceImpl userService;

    /**
     * 插入用户信息表和插入用户密码表属于同一个事物，必须都完成插入才结束，否则抛出注册失败异常回滚事务
     * @param userModel
     * @param password
     * @return
     * @throws ServiceCommonException
     */
    @OutputExceptionLog(message = "将注册用户信息同时插入用户表和密码表中")
    @Transactional(rollbackFor=Exception.class)
    public int insertUser(UserModel userModel, String password) throws ServiceCommonException {
        if (userMapper.insertSelective(userService.toUser(userModel)) <= 0)
            throw new ServiceCommonException(ExceptionIdentifyCode.INSERT_USERMESSAGE_EXCEPTION,userModel.toString());
        User user = userMapper.selectByPhone(userModel.getPhone());
        if (passwordMapper.insert(new Password(user.getId(), user.getPhone(), password)) <= 0)
            throw new ServiceCommonException(ExceptionIdentifyCode.INSERT_USERPASSWORD_EXCEPTION,password);
        return user.getId();
    }
}
