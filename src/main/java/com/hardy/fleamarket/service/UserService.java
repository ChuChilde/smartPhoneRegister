package com.hardy.fleamarket.service;

import com.hardy.fleamarket.error.ResponseCommonException;
import com.hardy.fleamarket.service.model.CommentModel;
import com.hardy.fleamarket.service.model.UserModel;
import com.hardy.fleamarket.service.serviceexception.ServiceCommonException;

import java.util.List;

public interface UserService {

    int register(UserModel userModel, String password) throws ResponseCommonException, ServiceCommonException;

    int login(long phone, String password,double longitude,double latitude);

    UserModel getInformation(int id);

    int report(int ownId,int id);

    int comment(CommentModel commentModel) throws ServiceCommonException;

    List getCommentList(int id);

    int star(int id);
}
