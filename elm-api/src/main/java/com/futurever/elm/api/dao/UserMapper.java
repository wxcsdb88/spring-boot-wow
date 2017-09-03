package com.futurever.elm.api.dao;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:43
 **/

import com.futurever.elm.api.dto.UserDTO;
import com.futurever.elm.api.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:43
 **/
@Repository
public interface UserMapper {
    boolean insert(User user);

    boolean update(User user);

    boolean delete(@Param("id") Long userId);

    List<UserDTO> findAll();

    UserDTO getUserById(@Param("id") Long userId);

    List<UserDTO> getUserListByUser(User user);

    User getUserByUserName(@Param("username") String userName);
}
