package com.hardy.fleamarket.controller;

import com.aliyuncs.exceptions.ClientException;
import com.hardy.fleamarket.controller.viewobject.UserVO;
import com.hardy.fleamarket.error.ResponseCommonException;
import com.hardy.fleamarket.error.EnumError;
import com.hardy.fleamarket.controller.response.CommonReturnType;
import com.hardy.fleamarket.service.UserService;
import com.hardy.fleamarket.service.model.CommentModel;
import com.hardy.fleamarket.service.model.UserModel;
import com.hardy.fleamarket.service.serviceexception.ServiceCommonException;
import com.hardy.fleamarket.utils.JwtUtil;
import com.hardy.fleamarket.utils.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession httpSession;

    /**
     * 手机验证码的获取
     *
     * @param phone
     * @return
     * @throws ResponseCommonException
     */
    @GetMapping("/smsCode")
    @ResponseBody
    public CommonReturnType getSmsCode(String phone) throws ResponseCommonException {
        if (phone == null)
            throw new ResponseCommonException(EnumError.PHONE_ERROR);
        SendSms sendSms = new SendSms();
        Map<String, Object> parameter = new HashMap<>();
        int num = (int) (Math.random() * 99999 + 10000);//六位随机验证码
        parameter.put("code", num);
        Map response;
        try {
            response = sendSms.sendSms(phone, parameter);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new ResponseCommonException(EnumError.SENDSMS_FAIL);
        }
        if (response == null)
            throw new ResponseCommonException(EnumError.SENDSMS_FAIL);
        if (response.get("Code").toString().equals("OK")) {
            //将验证码保存到session中,设置时间5分钟后失效
            httpSession.setAttribute(phone, String.valueOf(num));
            httpSession.setAttribute("time", new Date().getTime());
            return new CommonReturnType("ok", "发送验证码成功");
        }
        if (response.get("Code").toString().equals("isv.MOBILE_NUMBER_ILLEGAL"))
            throw new ResponseCommonException(EnumError.PHONE_ERROR);
        else
            throw new ResponseCommonException(EnumError.SYSTEM_ERROR);
    }

    /**
     * 接收用户注册信息和进行短信验证手机号码的有效性
     *
     * @param userInformation
     * @return
     * @throws ResponseCommonException
     */
    @PostMapping("/register")
    @ResponseBody
    public CommonReturnType register(@RequestBody Map<String, Object> userInformation) throws ResponseCommonException {
        //如果没有发送短信就提交注册则返回错误
        String time;
        try {
            time = httpSession.getAttribute("time").toString();
        } catch (NullPointerException e) {
            throw new ResponseCommonException(EnumError.ESECODE_ERROR);
        }
        //验证时间是否失效
        if ((new Date().getTime() - Long.valueOf(time)) > 300000)
            throw new ResponseCommonException(EnumError.ESECODE_ERROR);
        String smsCode;
        try {
            smsCode = httpSession.getAttribute(userInformation.get("phone").toString()).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_NOT_ALL);
        }
        if (userInformation.get("smsCode").toString().equals(smsCode)) {
            UserModel userModel;
            try {
                userModel = toUserModel(userInformation);
            } catch (NullPointerException e1) {
                e1.printStackTrace();
                throw new ResponseCommonException(EnumError.PARANETER_NOT_ALL);
            } catch (NumberFormatException e2) {
                throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
            }
            int userId;
            try {
                userId = userService.register(userModel, getMD5(userInformation.get("password").toString()));//直接在这里进行密码加密
            } catch (ServiceCommonException e) {
                e.printStackTrace();
                throw new ResponseCommonException(EnumError.REGISTER_FAIL);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new ResponseCommonException(EnumError.PARANETER_NOT_ALL);
            }
            if (userId == -1)
                throw new ResponseCommonException(EnumError.USER_HAVE_EXIST);
            //注册成功后返回登录凭证
            String returnData = JwtUtil.generate(userId);
            return new CommonReturnType("ok", returnData);
        } else {
            throw new ResponseCommonException(EnumError.ESECODE_ERROR);
        }
    }

    /**
     * 登录的同时进行位置更新
     *
     * @param account
     * @return
     * @throws ResponseCommonException
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonReturnType login(@RequestBody Map<String, Object> account) throws ResponseCommonException {
        long phone;
        String password;
        double longitude;
        double latitude;
        try {
            phone = Long.valueOf(account.get("phone").toString());
            password = getMD5(account.get("password").toString());
            longitude = Double.valueOf(account.get("longitude").toString());
            latitude = Double.valueOf(account.get("latitude").toString());
        } catch (NullPointerException e1) {
            e1.printStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_NOT_ALL);
        } catch (NumberFormatException e2) {
            e2.getMessage();
            throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
        }
        int userId = userService.login(phone, password, longitude, latitude);
        if (userId == -1)
            throw new ResponseCommonException(EnumError.USER_NOT_EXIST);
        if (userId == -2)
            throw new ResponseCommonException(EnumError.PASSWORD_ERROR);
        //登录成功返回登录凭证
        String returnData = JwtUtil.generate(userId);
        return new CommonReturnType("ok", returnData);
    }

    /**
     * 先验证用户是否登录状态
     * 通过id获取用户信息
     *
     * @return
     * @throws ResponseCommonException
     */
    @GetMapping("/{id}/information")
    @ResponseBody
    public CommonReturnType getUserInformation(@PathVariable String id,@RequestParam("token")String token) throws ResponseCommonException {
        if (JwtUtil.isExpired(token))
            throw new ResponseCommonException(EnumError.LOGIN_OVERDUE);
        int userId;
        try {
            userId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            e.getStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
        }
        UserModel userModel = userService.getInformation(userId);
        if (userModel == null)
            throw new ResponseCommonException(EnumError.USER_NOT_EXIST);
        UserVO userVO = toUserVO(userModel);
        return new CommonReturnType("ok", userVO);
    }

    /**
     * 评论他人
     * @param id
     * @param comment
     * @return
     * @throws ResponseCommonException
     */
    @PostMapping("/{id}/comment")
    @ResponseBody
    public CommonReturnType comment(@PathVariable String id ,@RequestBody Map<String, Object> comment, @RequestParam("token")String token) throws ResponseCommonException {
        if (JwtUtil.isExpired(token))
            throw new ResponseCommonException(EnumError.LOGIN_OVERDUE);
        int ownId = JwtUtil.getUid(token);
        int userId;
        try{
            userId = Integer.valueOf(id);
        }catch(NumberFormatException e){
            e.getStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
        }
        CommentModel commentModel;
        try{    commentModel = toCommentModel(comment,userId,ownId);
        }catch (NullPointerException e){
            throw new ResponseCommonException(EnumError.PARANETER_NOT_ALL);
        }
        try {
            userService.comment(commentModel);
        } catch (ServiceCommonException e) {
            e.printStackTrace();
            throw new ResponseCommonException(EnumError.REGISTER_FAIL);
        }
        return new CommonReturnType("ok","评论成功");
    }

    /**
     * 举报他人
     * @param id
     * @return
     * @throws ResponseCommonException
     */
    @PostMapping("/{id}/report")
    @ResponseBody
    public CommonReturnType report(@PathVariable String id, @RequestParam("token")String token) throws ResponseCommonException {
        if (JwtUtil.isExpired(token))
            throw new ResponseCommonException(EnumError.LOGIN_OVERDUE);
        int ownId = JwtUtil.getUid(token);
        int userId;
        try{
            userId = Integer.valueOf(id);
        }catch(NumberFormatException e){
            e.getStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
        }
        userService.report(ownId,userId);
        return new CommonReturnType("ok","举报成功");
    }

    /**
     * 点赞他人
     * @param pid
     * @return
     * @throws ResponseCommonException
     */
    @PostMapping("/{pid}/star")
    @ResponseBody
    public CommonReturnType star(@PathVariable String pid ,@RequestParam("token") String token) throws ResponseCommonException {
        if (JwtUtil.isExpired(token))
            throw new ResponseCommonException(EnumError.LOGIN_OVERDUE);
        int id;
        try{
            id = Integer.valueOf(pid);
        }catch(NumberFormatException e){
            e.getStackTrace();
            throw new ResponseCommonException(EnumError.PARANETER_FORMAT_ERROR);
        }
        userService.star(id);
        return new CommonReturnType("ok","点赞成功");
    }

    /**
     * 将前端传来的数据封装成mode结构
     * @param comment
     * @param id
     * @param ownId
     * @return
     * @throws NullPointerException
     */
    private CommentModel toCommentModel(Map comment, int id,int ownId) throws NullPointerException{
        CommentModel commentModel = new CommentModel();
        commentModel.setUserId(id);
        try {
            String content = comment.get("content").toString();
            commentModel.setContent(content);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        commentModel.setByUserId(ownId);
        return commentModel;
    }

    /**
     * 将前端传来的信息对应到对象模型里传入服务层
     * * 检查参数的合法性和完整性
     *
     * @param userInformation
     * @return
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private UserModel toUserModel(Map userInformation) throws NullPointerException, NumberFormatException {
        UserModel userModel = new UserModel();
        String phone;
        phone = userInformation.get("phone").toString();
        //名字为空则号码为名字
        String name;
        try {
            name = userInformation.get("name").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            name = phone;
        }
        //如果头像字段为空则默认一个头像
        String headPicture;
        try {
            headPicture = userInformation.get("picture").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            headPicture = "";
        }
        //如果性别字段没有则为未知性别
        int gender = 0;
        try {
            userInformation.get("gender");
        } catch (NullPointerException e) {
            e.printStackTrace();
            gender = 0;
        } catch (NumberFormatException e2) {
            e2.getMessage();
            throw e2;
        }
        if (userInformation.get("gender").toString().equals("men"))
            gender = 1;
        if (userInformation.get("gender").toString().equals("women"))
            gender = 2;
        try {
            userModel.setPhone(Long.valueOf(userInformation.get("phone").toString()));
            userModel.setName(name);
            userModel.setHeadPicture(headPicture);
            userModel.setGender(gender);
            userModel.setLongitude(Double.valueOf(userInformation.get("longitude").toString()));
            userModel.setLatitude(Double.valueOf(userInformation.get("latitude").toString()));
            userModel.setCreateDate(new Date());
        } catch (NullPointerException e1) {
            e1.printStackTrace();
            throw e1;
        } catch (NumberFormatException e2) {
            e2.getMessage();
            throw e2;
        }
        return userModel;
    }

    /**
     * 获取返回前端的用户数据
     *
     * @param userModel
     * @return
     */
    private UserVO toUserVO(UserModel userModel) {
        UserVO userVO = new UserVO();
        userVO.setName(userModel.getName());
        userVO.setHeadPicture(userModel.getHeadPicture());
        userVO.setCreditValue(userModel.getCreditValue());
        if (userModel.getGender() == 1)
            userVO.setGender("men");
        if (userModel.getGender() == 2)
            userVO.setGender("women");
        int t = (int) (new Date().getTime() - userModel.getCreateDate().getTime()) / (1000 * 3600 * 24);
        userVO.setCreateTime(t);
        try {
            double ownLongitude = Double.valueOf(httpSession.getAttribute("longitude").toString());
            double ownLatitude = Double.valueOf(httpSession.getAttribute("latitude").toString());
            userVO.setLocation(getDistance(ownLongitude, ownLatitude, userModel.getLongitude(), userModel.getLatitude()));
        } catch (Exception e) {
            userVO.setLocation(0);
        }
        userVO.setComment(userService.getCommentList(userModel.getId()));
        return userVO;
    }

    /**
     * 根据经纬度计算距离
     *
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @return
     */
    private double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double PI = 3.14159265358979323; // 圆周率
        double R = 6371229; // 地球的半径
        double x, y, distance;
        x = (longitude2 - longitude1) * PI * R * Math.cos(((latitude1 + latitude2) / 2) * PI / 180) / 180;
        y = (latitude2 - latitude1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }


    /**
     * 随意撒盐获取MD5值进行加密
     *
     * @param parameter
     * @return
     */

    private String getMD5(String parameter) {
        String base = "!@#123" + parameter;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
