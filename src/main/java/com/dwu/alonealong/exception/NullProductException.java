package com.dwu.alonealong.exception;

public class NullProductException extends Exception{
    public NullProductException(){
        super("존재하지 않는 상품입니다.");
    }
}
