package com.stock.xMarket.controller;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class BaseApiController {


    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex) {

        CommonReturnType commonReturnType = new CommonReturnType();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            commonReturnType.setCode(businessException.getErrCode());
            commonReturnType.setMessage(businessException.getErrMsg());
        } else {
            commonReturnType.setCode(EmBusinessError.UNKNOWN_ERROR.getErrCode());
            commonReturnType.setMessage(EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }

        return commonReturnType;
    }


}