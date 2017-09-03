package com.futurever.elm.api.service;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:45
 **/

import com.futurever.elm.api.dto.OrderDTO;
import com.futurever.elm.api.model.Order;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:45
 **/
public interface OrderService {
    boolean insert(Order order);

    boolean delete(Long orderId);

    List<OrderDTO> findAll();

    OrderDTO getOrderById(Long userId);

    List<OrderDTO> getOrderListByUserId(Long userId);
}
