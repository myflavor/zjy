package cn.myflavor.zjy.service;

import cn.myflavor.zjy.entity.User;
import cn.myflavor.zjy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired()
    private UserMapper userMapper;

    public boolean addUser(User user) {
        return userMapper.addUser(user);
    }

    public User findByOpenId(String openid) {
        return userMapper.findByOpenId(openid);
    }

    public void delByOpenId(String openid) {
        userMapper.delByOpenId(openid);
    }

    public boolean updateByOpenId(String openid, int id, String mail, Date expiry) {
        return userMapper.updataByOpenId(openid, id, mail, expiry);
    }

    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    public User findById(String id) {
        return userMapper.findById(id);
    }

    public boolean updateById(User user) {
        return userMapper.updateById(user);
    }

    public boolean delById(String id) {
        return userMapper.delById(id);
    }

    public String userCount() {
        return userMapper.userCount();
    }

    public List<User> findCdkeysLimit(int page, int no) {
        return userMapper.findCdkeysLimit(page, no);
    }
}
