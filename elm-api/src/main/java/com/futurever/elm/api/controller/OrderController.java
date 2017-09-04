package com.futurever.elm.api.controller;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 18:56
 **/

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.common.TokenUser;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.dto.OrderDTO;
import com.futurever.elm.api.exception.BusinessException;
import com.futurever.elm.api.model.Order;
import com.futurever.elm.api.service.OrderService;
import com.futurever.elm.api.utils.CommonUtils;
import com.futurever.elm.api.utils.TokenUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 18:56
 **/
@Api(protocols = "http,https", value = "/orders", description = "订单管理接口")

@RestController
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "全部订单", notes = "", httpMethod = "GET", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "查询成功"),
    })
    @ApiImplicitParam(
            name = "token", value = "用户标识", paramType = "header", required = true, dataTypeClass = String.class
    )
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData list(HttpServletResponse response, HttpSession session) throws Exception {
        ResponseData<List<OrderDTO>> responseData = new ResponseData<>();
        List<OrderDTO> orders = orderService.findAll();
        responseData.setOK();
        responseData.setResult(orders);
        return responseData;
    }

    @ApiOperation(value = "提交订单", notes = "", httpMethod = "PUT", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "提交订单成功"),
    })
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "token", value = "用户标识", paramType = "header", required = true, dataTypeClass = String.class)
            }
    )

    @RequestMapping(value = "/orders", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData create(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody(required = true) Order order
    ) throws Exception {
        if (CommonUtils.isEmpty(order, order.getContent(), order.getUserId())) {
            throw new BusinessException(Response.DEFAULT_ERROR_CODE, "订单，订单用户，订单内容不能为空！");
        }
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean isOk = orderService.insert(order);
        if (isOk) {
            responseData.setOK("创建订单成功！",false);
        } else {
            responseData.setError("创建订单失败！", true);
        }
        return responseData;
    }

    @ApiOperation(value = "用户订单", notes = "", httpMethod = "GET", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = Response.OK, message = "查询成功"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "user", value = "userId", paramType = "query", required = true, dataTypeClass = String.class)
    }
    )
    @RequestMapping(value = "/orders/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseData get(HttpServletRequest request, @PathVariable(name = "user", required = true) Long userId) throws Exception {

        TokenUser tokenUser = TokenUtils.getCurrentUser(request);
        ResponseData<List<OrderDTO>> responseData = new ResponseData<>();
        List<OrderDTO> orders = orderService.getOrderListByUserId(userId);
        responseData.setOK();
        responseData.setResult(orders);
        return responseData;
    }
}
