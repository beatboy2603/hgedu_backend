/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author admin
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionCodeException extends RuntimeException{

    public QuestionCodeException(String message) {
        super(message);
    }
}
