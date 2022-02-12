package com.dwu.alonealong.exception;

public class NotLoginException extends Exception{
    public NotLoginException(){
        super("로그인이 필요합니다.");
    }
}
