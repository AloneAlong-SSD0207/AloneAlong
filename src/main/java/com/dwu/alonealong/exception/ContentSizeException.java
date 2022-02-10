package com.dwu.alonealong.exception;

public class ContentSizeException extends Exception{
    public ContentSizeException(){
        super("입력 가능한 글자 수를 초과하였습니다.");
    }
}
