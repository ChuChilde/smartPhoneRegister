package com.hardy.fleamarket.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hardy.fleamarket.log.OutputExceptionLog;
import io.jsonwebtoken.*;

/**
 * JWT token 生成工具类
 * @author Caratacus
 */
public class JwtUtil{

    //标识用户名称
    public static final String UID = "uid";
    //加密密码
    private static final String SECRET = "WgtqaT1HNTZPZNMDJu3k";
    //过期时间5分钟
    private static final long EXPIRE = 60 * 1000;

    /**
     * 传入凭证标识ID生成token
     *
     * @param uid
     * @return
     */
    public static String generate(Integer uid) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + EXPIRE * 1000);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(UID, uid);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析Claims
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            e.getMessage();
        }
        return claims;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        return getClaim(token).getIssuedAt();
    }

    /**
     * 获取UID
     */
    public static Integer getUid(String token) {
        return Integer.valueOf(getClaim(token).get(UID).toString());
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        return getClaim(token).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    @OutputExceptionLog(message = "签名失效")
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}