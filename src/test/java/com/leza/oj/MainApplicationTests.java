package com.leza.oj;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {

    @Test
    void contextLoads() {

    }


    @Test
    void JwtTest() {
        String sig = "asd1#!Q#";
        JWTCreator.Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        builder.withClaim("userAccount", "45645654");
        builder.withClaim("serPassword", "12345678");
        String token1 = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(sig));

        System.out.println(JWT.require(Algorithm.HMAC256(sig)).build().verify(token1).getClaims().get("userAccount")) ;
    }

}
