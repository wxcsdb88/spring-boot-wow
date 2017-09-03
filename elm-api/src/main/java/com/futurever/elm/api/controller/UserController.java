package com.futurever.elm.api.controller;

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.constants.RequestField;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.dto.UserDTO;
import com.futurever.elm.api.exception.BusinessException;
import com.futurever.elm.api.model.User;
import com.futurever.elm.api.service.UserService;
import com.futurever.elm.api.utils.CommonUtils;
import com.futurever.elm.api.utils.DateUtils;
import com.futurever.elm.api.utils.MD5Util;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wxcsdb88 on 2017/8/29 14:46.
 */
@Api(protocols = "http,https", value = "/users", description = "用户注册及管理接口")

@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户注册", notes = "")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "操作成功"),
    })
    @ApiImplicitParam(
            name = "token", value = "用户标识", paramType = "header", required = true, dataTypeClass = String.class
    )
    @PutMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData register(HttpServletRequest request, Model model,
                                 @ApiParam(name = RequestField.USERNAME, required = true, value = "用户名") @RequestParam(name = RequestField.USERNAME, required = true) String username,
                                 @ApiParam(name = RequestField.PASSWD, required = true, value = "密码", defaultValue = "123456") @RequestParam(name = RequestField.PASSWD, required = true) String password,
                                 @ApiParam(name = RequestField.NAME, value = "用户名字", required = false) @RequestParam(name = RequestField.NAME, required = false) String name
    ) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (CommonUtils.isEmpty(username, password)) {
            throw new BusinessException("username or password 输入错误！");
        }
        User user = new User();
        user.setUsername(username);
        if (!CommonUtils.isEmpty(name)) {
            user.setName(name);
        }
        user.setOriginPassword(password);
        user.setPassword(MD5Util.generate(password));
        user.setCreateTime(DateUtils.currentDateTime());
        user.setLocked(false);

        User oldContractUser = userService.getUserByUserName(username);
        if (oldContractUser != null) {
            throw new BusinessException("用户名已存在！");
        }
        boolean ok = userService.insert(user);
        if (ok) {
            responseData.setOK("注册成功！", true);
        } else {
            responseData.setError("注册失败！", false);
        }
        return responseData;
    }

    @ApiOperation(value = "用户列表", notes = "")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "操作成功"),
    })
    @ApiImplicitParam(
            name = "token", value = "用户标识", paramType = "header", required = true, dataTypeClass = String.class
    )
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData list(HttpServletRequest request, Model model) {
        ResponseData<List<UserDTO>> responseData = new ResponseData<>();

        List<UserDTO> users = userService.findAll();

        responseData.setOK(Response.OK, users);
        return responseData;
    }

}
