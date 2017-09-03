package com.futurever.elm.api.dao;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:53
 **/

import com.futurever.elm.api.dto.OrderDTO;
import com.futurever.elm.api.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:53
 **/
@Repository
public interface OrderMapper {
    boolean insert(Order order);

    boolean delete(@Param("id") Long orderId);

    List<OrderDTO> findAll();

    OrderDTO getOrderById(@Param("userId") Long userId);

    List<OrderDTO> getOrderListByUserId(@Param("userId") Long userId);
}
