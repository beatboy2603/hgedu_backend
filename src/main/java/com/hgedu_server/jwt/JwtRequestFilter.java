/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.jwt;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ADMIN
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String REQUEST_AT = "request-at";
    public static final String EXECUTION_TIME = "execution-time";

    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {

        final String authorizationHeader = hsr.getHeader("Authorization");

        String username = null;
        String jwt = null;
        HttpServletResponse myResponse = null;
        MyResponseRequestWrapper responseWrapper = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("author " + jwt);
            System.out.println(JwtController.getInstance().getUid(jwt));
            String verifiedToken = JwtController.getInstance().verifyToken(jwt);
            if (verifiedToken == null) {
                System.out.println("wrong token");
            } else {
                myResponse = (HttpServletResponse) hsr1;
                responseWrapper = new MyResponseRequestWrapper(myResponse);
                responseWrapper.addHeader("Authorization", "true");
                System.out.println("tokenVerified");
//                hsr1.addHeader("tokenverified", "true");
            }
//                if (!verifiedToken.equals("")) {
//                hsr1.setHeader("renewToken", verifiedToken);
//            }
        }
        hsr1.setHeader("Access-Control-Allow-Origin", "*");
        hsr1.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        hsr1.setHeader("Access-Control-Max-Age", "3600");
        hsr1.setHeader("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        hsr1.addHeader("headerName", "headerValue");
        hsr1.setHeader("setheaderName", "setheaderValue");
        hsr.setAttribute("test", "test");
        fc.doFilter(hsr, hsr1);
        System.out.println(hsr1.getHeader("setheaderName"));
        System.out.println(hsr1.getHeader("headerName"));
//        if (myResponse != null) {
//            System.out.println("đ hiểu");
//            myResponse.addHeader("Access-Control-Allow-Origin", "*");
//	
//            fc.doFilter(hsr, responseWrapper);
//            myResponse.addHeader("seta", "seta");
//            myResponse.setHeader("seta", "seta");
//            System.out.println(myResponse.getHeader("seta"));
//        } else {
//            fc.doFilter(hsr, hsr1);
//            System.out.println("test");
//            hsr1.addHeader("a", "b");
//            hsr1.setHeader("seta", "setb");
//        }
    }
}

class MyResponseRequestWrapper extends HttpServletResponseWrapper {

    public MyResponseRequestWrapper(HttpServletResponse response) {
        super(response);
        response.addHeader("test", "test");
    }
}

//@Component
//public class JwtRequestFilter implements Filter {
//
////    public void doFilter(HttpServletRequest req, HttpServletResponse res,
////            FilterChain chain) throws IOException, ServletException {
////        // you can modify your response here before the call of chain method
////        //example 
////        res.setHeader("key", "value");
////        System.out.println("filtered");
////        chain.doFilter(req, res);
////        System.out.println("filter3");
////        res.setHeader("a", "b");
////    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) sr;
//        System.out.println("author "+req.getHeader("Authorization"));
//        HttpServletResponse res = (HttpServletResponse) sr1;
//        System.out.println("filter2");
//        res.setHeader("a", "b");
//        res.addHeader("b", "a");
//        fc.doFilter(sr, res);
//        System.out.println("filter3");
//        System.out.println(res.getHeader("a"));
//        System.out.println(res.getHeader("b"));
//        res.addHeader("b", "a");
//    }
//
//}
