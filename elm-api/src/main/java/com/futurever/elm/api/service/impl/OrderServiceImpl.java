package com.futurever.elm.api.service.impl;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:45
 **/

import com.futurever.elm.api.dao.OrderMapper;
import com.futurever.elm.api.dto.OrderDTO;
import com.futurever.elm.api.model.Order;
import com.futurever.elm.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:45
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public boolean delete(Long orderId) {
        return orderMapper.delete(orderId);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public OrderDTO getOrderById(Long userId) {
        return orderMapper.getOrderById(userId);
    }

    @Override
    public List<OrderDTO> getOrderListByUserId(Long userId) {
        return orderMapper.getOrderListByUserId(userId);
    }
}
