package com.leza.oj.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class TokenUtils {
    //    public static class ResultWithJwt {
//        public String jwt;
//        public Object result;
//
//        public ResultWithJwt(String jwt, Object result) {
//            this.jwt = jwt;
//            this.result = result;
//        }
//    }
    public static String createToken(Map<String, Object> payload, String secret) {
        return JWT.create()
                .withPayload(payload)
                .withExpiresAt(DateUtil.offsetDay(new Date(), 2))
                .sign(Algorithm.HMAC256(secret));
    }

    public static String getUserAccount(HttpServletRequest request) {
        String jwt = getAuthorizationJwt(request);
        return getUserAccount(jwt);
    }

    public static String getUserAccount(String jwt) {
        if (StringUtils.isBlank(jwt)) {
            return null;
        }

        try {
            return JWT.decode(jwt).getClaim("userAccount").asString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAuthorizationJwt(HttpServletRequest request) {
        String schema = "Bearer ";
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isBlank(jwt) || !jwt.startsWith(schema)) {
            return null;
        }
        jwt = jwt.substring(schema.length());
        return jwt;
    }

    public static boolean verifyJwt(String jwt, Algorithm algorithm) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            jwtVerifier.verify(jwt);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static boolean verifyJwt(String jwt, String secret) {
        return verifyJwt(jwt, Algorithm.HMAC256(secret));
    }

}
