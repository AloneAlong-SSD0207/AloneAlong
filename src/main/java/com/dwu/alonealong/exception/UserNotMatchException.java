package com.dwu.alonealong.exception;

public class UserNotMatchException extends Exception{
    public UserNotMatchException(){
        super("로그인한 사용자의 정보와 일치하지 않습니다.");
    }
}
