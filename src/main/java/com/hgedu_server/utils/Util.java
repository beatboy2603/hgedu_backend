/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.utils;

import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public class Util {
    public static JSONObject decodeToken(String token){

        String[] base64EncodedSegments = token.split("\\.");

//        String base64EncodedHeader = base64EncodedSegments[0];
        String base64EncodedBody = base64EncodedSegments[1];
//        String base64EncodedSignature = base64EncodedSegments[2];

        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String payloadJson = new String(decoder.decode(base64EncodedBody));

        JSONObject jsonObj = new JSONObject(payloadJson);
        return jsonObj;
    }
}
