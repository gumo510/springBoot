package com.gumo.demo.utils;

import com.gumo.demo.dto.vo.CheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author gumo
 * @date 2021/07/22
 */
public class JwtUtils {
    /**
     * 签发JWT
     *
     * @param id
     * @param secretKey   可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return String
     *
     */
    public static String createJwt(String id, String secretKey, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
//        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(secretKey) // 主题
                .setIssuer("user") // 签发者
                .setIssuedAt(now) // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJwt(String jwtStr, String appKey) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJwt(jwtStr, appKey);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrMessage(e.getMessage());
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrMessage(e.getMessage());
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrMessage(e.getMessage());
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    public static SecretKey generalKey(String appKey) {
        byte[] encodedKey = Base64.decode(appKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     *
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJwt(String jwt, String appKey) throws Exception {
        SecretKey secretKey = generalKey(appKey);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}
