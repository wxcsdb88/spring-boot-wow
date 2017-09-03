package com.futurever.elm.api.controller;

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.common.TokenUser;
import com.futurever.elm.api.constants.RequestField;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.exception.AuthorizationException;
import com.futurever.elm.api.model.Order;
import com.futurever.elm.api.model.User;
import com.futurever.elm.api.service.UserService;
import com.futurever.elm.api.utils.CommonUtils;
import com.futurever.elm.api.utils.MD5Util;
import com.futurever.elm.api.utils.TokenUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wxcsdb88 on 2017/5/24 0:11.
 */
@Api(protocols = "http,https", description = "登录、登出")
@RestController
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "", httpMethod = "POST", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "登录成功"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData login(HttpServletResponse response, HttpSession session,
                              @ApiParam(name = RequestField.USERNAME, required = true, value = "用户名", defaultValue = "test") @RequestParam(name = RequestField.USERNAME, required = true) String username,
                              @ApiParam(name = RequestField.PASSWD, required = true, value = "密码", defaultValue = "123456") @RequestParam(name = RequestField.PASSWD, required = true) String password) throws Exception {

        ResponseData<TokenUser> responseData = new ResponseData<>();
        if (CommonUtils.isEmpty(username, password)) {
            responseData.setError("username or password is blank!");
            return responseData;
        }
        User user = userService.getUserByUserName(username);
        String md5Pwd;
        if (user != null && user.getPassword() != null) {
            md5Pwd = user.getPassword();
            if (MD5Util.verify(password, md5Pwd)) {
                //todo
                boolean isLocked = user.isLocked();
                if (isLocked) {
                    throw new AuthorizationException(Response.ACCOUNT_LOCKED, "账户未激活或已锁定，请联系管理员进行激活或解锁！");
                }

                TokenUser tokenUser = new TokenUser();
                BeanUtils.copyProperties(user, tokenUser);
                String token = TokenUtils.insertCurrentUser(tokenUser);
                tokenUser.setToken(token);
                responseData.setOK("login success!", tokenUser);
                return responseData;
            } else {
                responseData.setError("username or password error!");
                return responseData;
            }
        } else {
            responseData.setError("user not exist!");// not should display!
            return responseData;
        }
    }

    @ApiOperation(value = "用户登出", notes = "")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "登出成功"),
    })
    @ApiImplicitParam(
            name = "token", value = "用户标识", paramType = "header", required = true, dataTypeClass = String.class
    )
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData logout(HttpServletRequest request) throws Exception {
        ResponseData<Boolean> responseData = new ResponseData<>();
        String token = request.getHeader(RequestField.TOKEN);
        if (TokenUtils.removeCurrentUser(request)) {
            responseData.setError("logout failed!");
        } else {
            responseData.setOK("logout success!");
        }
        return responseData;
    }
}
