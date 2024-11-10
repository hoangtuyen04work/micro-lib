package com.library.book_service.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends  Exception{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode){
        this.errorCode  = errorCode;
    }

}
