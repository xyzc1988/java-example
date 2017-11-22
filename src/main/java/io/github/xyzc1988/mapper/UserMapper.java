package io.github.xyzc1988.mapper;

import io.github.xyzc1988.model.User;

import java.util.List;

/**
 * Created by zhangcheng on 2017/11/22.
 */
public interface UserMapper {
    void save(User user);
    boolean update(User user);
    boolean delete(int id);
    User findById(int id);
    List<User> findAll();
}