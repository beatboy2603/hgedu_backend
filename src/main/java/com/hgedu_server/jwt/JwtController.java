/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class JwtController {

    private static JwtController instance;

    public static JwtController getInstance() {
        if (instance == null) {
            instance = new JwtController();
        }
        return instance;
    }

    private String issuer = "webgiaoduc";
    private Algorithm algorithm;

    private JwtController() {
        // Link docs: https://github.com/auth0/java-jwt
        algorithm = Algorithm.HMAC256("motcaisecretkeygido");
    }

    public String createToken(String sub, int uid, int role) {
        // Create token with 30min timeline and change token each 15min
        try {
            Date exp = new Date(System.currentTimeMillis() + 30 * 60 * 1000); // 30 min expired
            Date renew = new Date(System.currentTimeMillis() + 15 * 60 * 1000); // 15 min renew token

            String token = JWT.create().withIssuer(issuer).withExpiresAt(exp).withClaim("sub", sub)
                    .withClaim("uid", uid).withClaim("role", role).withClaim("renew", renew).sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    public String verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).acceptExpiresAt(System.currentTimeMillis()).build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt.getClaim("renew").asDate().before(new Date(System.currentTimeMillis()))) {
                return createToken(jwt.getClaim("sub").asString(), jwt.getClaim("uid").asInt(),
                        jwt.getClaim("role").asInt());
            }
            return "";
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            return null;
        }
    }

    public long getUid(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asInt();
        } catch (JWTDecodeException exception) {
            // Invalid signature/claims
            return -1;
        }
    }

    public int getRole(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("role").asInt();
        } catch (JWTDecodeException exception) {
            // Invalid signature/claims
            return -1;
        }
    }
}
