package com.futurever.elm.api.service.impl;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:39
 **/

import com.futurever.elm.api.dao.UserMapper;
import com.futurever.elm.api.dto.UserDTO;
import com.futurever.elm.api.model.User;
import com.futurever.elm.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 17:39
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user);
    }

    @Override
    public boolean delete(Long userId) {
        return userMapper.delete(userId);
    }

    @Override
    public List<UserDTO> findAll() {
        return userMapper.findAll();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public List<UserDTO> getUserListByUser(User user) {
        return userMapper.getUserListByUser(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

}
