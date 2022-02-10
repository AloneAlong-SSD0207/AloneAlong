package com.dwu.alonealong.exception;

public class StockException extends Exception{
    public StockException(){
        super("올바르지 않은 수량이 입력되었습니다.");
    }
}