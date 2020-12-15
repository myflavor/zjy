package cn.myflavor.zjy.service;

import cn.myflavor.zjy.entity.Admin;
import cn.myflavor.zjy.entity.Cdk;
import cn.myflavor.zjy.mapper.AdminMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    public boolean addAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    public Admin findByUser(String user) {
        return adminMapper.findByUser(user);
    }

    public void delByUser(String user) {
        adminMapper.delByUser(user);
    }

    public boolean updataByUser(Admin admin) {
        return adminMapper.updataByUser(admin);
    }

    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }
}
