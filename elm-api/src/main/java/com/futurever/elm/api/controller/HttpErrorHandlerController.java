package com.futurever.elm.api.controller;

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.constants.Response;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wxcsdb88 on 2017/8/25 11:36.
 */
@ApiIgnore
@RestController
public class HttpErrorHandlerController implements ErrorController {
    private final static String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResponseData error(HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        responseData.set(Response.NO_HANDLER_FOUND_EXCEPTION, Response.DEFAULT_ERROR_MSG);
        return responseData;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
