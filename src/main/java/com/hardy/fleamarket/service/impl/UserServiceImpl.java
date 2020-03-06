package com.hardy.fleamarket.service.impl;

import com.hardy.fleamarket.dao.CommentMapper;
import com.hardy.fleamarket.dao.PasswordMapper;
import com.hardy.fleamarket.dao.UserMapper;
import com.hardy.fleamarket.entity.Comment;
import com.hardy.fleamarket.entity.Password;
import com.hardy.fleamarket.entity.User;
import com.hardy.fleamarket.error.ResponseCommonException;
import com.hardy.fleamarket.log.OutputExceptionLog;
import com.hardy.fleamarket.service.model.CommentModel;
import com.hardy.fleamarket.service.model.UserModel;
import com.hardy.fleamarket.service.UserService;
import com.hardy.fleamarket.service.serviceexception.ExceptionIdentifyCode;
import com.hardy.fleamarket.service.serviceexception.ServiceCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    TransactionalClass transactionalClass;

    @Autowired
    CommentMapper commentMapper;


    /**
     * 进行用户注册返回用户ID
     * @param userModel
     * @param password
     * @return
     * @throws ResponseCommonException
     */
    @Override
    public int register(UserModel userModel, String password) throws ServiceCommonException {
        if (userMapper.selectByPhone(userModel.getPhone()) != null) {
            return -1;
        } else {
            return transactionalClass.insertUser(userModel, password);
        }
    }


    /**
     * 检查密码是否正确后进行位置的更新
     * @param phone
     * @param password
     * @param longitude
     * @param latitude
     * @return
     */
    @Override
    public int login(long phone, String password, double longitude , double latitude) {
        Password userPassword = passwordMapper.selectByPhone(phone);
        if(userPassword == null)
            return -1;
        if(userPassword.getPassword().equals(password)) {
            int userId = userPassword.getId();
            userMapper.updateByPrimaryKeySelective(new User(userId,null,null,null,null,longitude,latitude,null,null));
            return userId;
        }
        else
            return -2;
    }

    /**
     * 通过ID获取用户信息
     * @param id
     * @return
     */
    @Override
    public UserModel getInformation(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null)
            return null;
        return toUserModel(user);
    }

    /**
     * 举报扣两分
     * @param ownId
     * @param id
     * @return
     */
    @Override
    public int report(int ownId, int id) {
        int creditValue = userMapper.selectByPrimaryKey(id).getCreditValue();
        if(creditValue>2)
            creditValue= creditValue-2;
        User record = new User(id,null,null,null,null,null,null,null,creditValue);
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 插入评论
     * @param commentModel
     * @return
     * @throws ServiceCommonException
     */
    @OutputExceptionLog(message = "插入评论")
    @Override
    public int comment(CommentModel commentModel) throws ServiceCommonException{
        int re = commentMapper.insertSelective(toComment(commentModel));
        if( re<=0)
            throw new ServiceCommonException(ExceptionIdentifyCode.INSERT_COMMENT_EXCEPTION,commentModel.toString());
        return re;
    }

    @Override
    public List getCommentList(int id) {
        List comments = commentMapper.selectByUserId(id);
        List<CommentModel> res = new LinkedList<>();
        for(Object c:comments){
            Comment comment = (Comment)c;
            res.add(toCommentModel(comment));
        }
        return res;
    }

    @Override
    public int star(int id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        int star = comment.getStar()+1;
        Comment record = new Comment(id,null,null,null,star,null);
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * service层数据模型和DAO层的转换
     */
    private UserModel toUserModel(User user){
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setPhone(user.getPhone());
        userModel.setName(user.getName());
        userModel.setHeadPicture(user.getHeadPicture());
        userModel.setGender(user.getGender());
        userModel.setLongitude(user.getLongitude());
        userModel.setLatitude(user.getLatitude());
        userModel.setCreateDate(user.getCreateDate());
        userModel.setCreditValue(user.getCreditValue());
        return userModel;
    }
    User toUser(UserModel userModel){
        int id =userModel.getId();
        long phone=userModel.getPhone();
        String name=userModel.getName();
        String headPicture=userModel.getHeadPicture();
        int gender=userModel.getGender();
        double longitude=userModel.getLongitude();
        double latitude=userModel.getLatitude();
        Date createDate=userModel.getCreateDate();
        int creditValue=userModel.getCreditValue();
        return new User(id,phone,name,headPicture,gender,longitude,latitude,createDate,creditValue);
    }

    private CommentModel toCommentModel(Comment comment){
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setUserId(comment.getUserId());
        commentModel.setContent(comment.getContent());
        commentModel.setStar(comment.getStar());
        commentModel.setCreateTime(comment.getCreateTime());
        commentModel.setByUserId(comment.getByUserId());
        User byUser = userMapper.selectByPrimaryKey(comment.getByUserId());
        commentModel.setByUserHeadPicture(byUser.getHeadPicture());
        commentModel.setByUserName(byUser.getName());
        return commentModel;
    }

    private Comment toComment(CommentModel commentModel){
        int UserId = commentModel.getUserId();
        int byUserId = commentModel.getByUserId();
        String content = commentModel.getContent();
        return new Comment(null,UserId,byUserId,content,0,new Date());
    }
}
