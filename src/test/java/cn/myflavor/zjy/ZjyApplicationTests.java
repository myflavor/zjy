package cn.myflavor.zjy;

import cn.myflavor.zjy.entity.Admin;
import cn.myflavor.zjy.service.AdminService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ZjyApplicationTests {
    @Autowired
    AdminService adminService;
    @Test
    void contextLoads() {
        String str="79";
        String[] split = str.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
