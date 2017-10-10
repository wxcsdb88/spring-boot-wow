package com.futurever.demo.api.controller;

import com.futurever.core.utils.CommonUtils;
import com.futurever.demo.api.common.ResponseData;
import com.futurever.demo.api.constants.Response;
import com.futurever.demo.api.exception.BusinessException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */
@Api(protocols = "http,https", tags = {"DemoController"}, description = "gradle 多环境配置 demo")
@RestController
public class DemoController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @ApiOperation(value = "demo", notes = "", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok!"),
    })
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = false, value = "用户名", defaultValue = "小明"),
                    @ApiImplicitParam(paramType = "query", name = "age", dataType = "long", required = false, value = "年龄", defaultValue = "18"),
            }
    )
    @RequestMapping(value = "/hello", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData hello(HttpServletRequest request,
                              @RequestParam(name = "name", required = true) String name,
                              @RequestParam(name = "age", required = true) Integer age) {
        if (CommonUtils.isEmpty(name)) {
            logger.error("参数[name]不能为空！");
            throw new BusinessException(Response.ERROR, "参数不能为空！", false);
        }
        int nameMinLength = 2;
        if (name.length() < nameMinLength) {
            logger.error(String.format("参数[name]长度必须大于等于 %d ！", nameMinLength));
            throw new BusinessException(Response.ERROR, String.format("参数[name]长度必须大于等于 %d ！", nameMinLength), false);
        }
        ResponseData<Object> responseData = new ResponseData<>();
        Map<String, Object> result = new HashMap<>(2);
        result.put("name", name);
        result.put("age", age);
        responseData.set(Response.OK, result);
        return responseData;
    }
}
