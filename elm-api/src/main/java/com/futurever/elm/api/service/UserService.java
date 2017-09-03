package com.futurever.elm.api.service;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:39
 **/

import com.futurever.elm.api.dto.UserDTO;
import com.futurever.elm.api.model.User;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:39
 **/
public interface UserService {
    boolean insert(User user);

    boolean update(User user);

    boolean delete(Long userId);

    List<UserDTO> findAll();

    UserDTO getUserById(Long userId);

    List<UserDTO> getUserListByUser(User user);

    User getUserByUserName(String userName);

}
