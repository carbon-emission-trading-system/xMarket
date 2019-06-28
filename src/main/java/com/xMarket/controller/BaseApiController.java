package com.xMarket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.response.CommonReturnType;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class BaseApiController {

    //定义日志文件对象
    private static Logger LOGGER = LoggerFactory.getLogger(BaseApiController.class);

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
            LOGGER.error(businessException.getErrMsg());
        } else {
            LOGGER.error("出现未知错误");
            commonReturnType.setCode(EmBusinessError.UNKNOWN_ERROR.getErrCode());
            commonReturnType.setMessage(EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }

        return commonReturnType;
    }
    
    
    


}