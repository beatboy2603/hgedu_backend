/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.jwt;

import com.hgedu_server.models.User;
import com.hgedu_server.repositories.UserRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ADMIN
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {

        final String authorizationHeader = hsr.getHeader("Authorization");

        String username = null;
        String jwt = null;
        String url = hsr.getRequestURL().toString();
//        boolean notRequired = false;
//        if (url.endsWith("/api/hello") || url.endsWith("/api/authen")|| url.contains("/api/authen/signup")|| url.contains("/news")) {
//            notRequired = true;
//            fc.doFilter(hsr, hsr1);
//        } else {
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
////                System.out.println(authorizationHeader);
//                jwt = authorizationHeader.substring(7);
//                String verifiedToken = JwtController.getInstance().verifyToken(jwt);
//                if (verifiedToken == null) {
//                    System.out.println("wrong token");
//                    return;
//                } else if (!verifiedToken.equals("")) {
//                    System.out.println("renew token");
//                    hsr1.setHeader("renewtoken", verifiedToken);
//                }
//                fc.doFilter(hsr, hsr1);
//            }
//        }

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                System.out.println(authorizationHeader);
            jwt = authorizationHeader.substring(7);
            String verifiedToken = JwtController.getInstance().verifyToken(jwt);
            System.out.println("role: "+JwtController.getInstance().getRole(jwt));
            if (verifiedToken == null) {
                System.out.println("wrong token");
                hsr1.setHeader("wrongtoken", "signout");
                fc.doFilter(hsr, hsr1);
                return;
            } else if (!verifiedToken.equals("")) {
                System.out.println("renew token");
                hsr1.setHeader("renewtoken", verifiedToken);
            }
        }
        fc.doFilter(hsr, hsr1);
    }
}
