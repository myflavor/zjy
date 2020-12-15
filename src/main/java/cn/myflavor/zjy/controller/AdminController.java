package cn.myflavor.zjy.controller;

import cn.myflavor.zjy.check.Morning;
import cn.myflavor.zjy.entity.*;
import cn.myflavor.zjy.schedule.Zjy;
import cn.myflavor.zjy.service.AdminService;
import cn.myflavor.zjy.service.CdkeyService;
import cn.myflavor.zjy.service.UserService;
import cn.myflavor.zjy.util.DateUtil;
import cn.myflavor.zjy.util.SendMailUtil;
import cn.myflavor.zjy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired
    CdkeyService cdkeyService;
    @Autowired
    Zjy zjy;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String user, String pwd, String remember) {
        if (user != null && pwd != null) {
            Admin admin = adminService.findByUser(user);
            if (admin != null) {
                if (admin.getPwd().equals(pwd)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", admin);
                    Cookie cookie = new Cookie("JSESSIONID", session.getId());
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    response.addCookie(cookie);
                    return "success";
                }
            }
        }
        return "failed";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "success";
    }

    @RequestMapping("/info")
    public Admin info(HttpServletRequest request) {
        return (Admin) request.getSession().getAttribute("user");
    }

    @RequestMapping("/userinfo")
    public List<User> userInfo(HttpServletRequest request) {
        return userService.findAllUser();
    }

    @RequestMapping("/users/{page}/{no}")
    public Page<User> userPage(@PathVariable("page") Integer page, @PathVariable("no") Integer no) {
        Page page1 = new Page();
        page1.setCount(userService.userCount());
        page1.setData(userService.findCdkeysLimit(page, (no - 1) * page));
        return page1;
    }

    @RequestMapping("/simpleinfo")
    public SimpleInfo userCount() {
        SimpleInfo simpleInfo = new SimpleInfo();
        simpleInfo.setUsercount(userService.userCount());
        simpleInfo.setCdkcount(cdkeyService.cdkCount());
        return simpleInfo;
    }

    @RequestMapping("/cdkinfo")
    public List<Cdk> cdkInfo() {
        return cdkeyService.findAllCdkey();
    }

    @RequestMapping("/cdks/{page}/{no}")
    public Page<Cdk> cdkPage(@PathVariable("page") Integer page, @PathVariable("no") Integer no) {
        Page page1 = new Page();
        page1.setCount(cdkeyService.cdkCount());
        page1.setData(cdkeyService.findCdkeysLimit(page, (no - 1) * page));
        return page1;
    }


    @RequestMapping("/useradd")
    public boolean userAdd(User user) {
        return userService.addUser(user);
    }

    @RequestMapping("/usersdel")
    public int usersDel(String idstr) {
        String[] ids = idstr.split(",");
        int delSum = 0;
        for (String id : ids) {
            if (userService.delById(id)) {
                delSum += 1;
            }
        }
        return delSum;
    }

    @RequestMapping("/cdkadd")
    public boolean cdkAdd(Cdk cdk) {
        return cdkeyService.addCdk(cdk);
    }

    @RequestMapping("/cdksdel")
    public int cdksDel(String idstr) {
        String[] ids = idstr.split(",");
        int delSum = 0;
        for (String id : ids) {
            if (cdkeyService.delById(id)) {
                delSum += 1;
            }
        }
        return delSum;
    }

    @RequestMapping("/cdkdel")
    public boolean cdkDel(String cdkey) {
        try {
            cdkeyService.delByCdkey(cdkey);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RequestMapping("/user/{openid}")
    public User userbyopenid(@PathVariable("openid") String openid) {
        return userService.findByOpenId(openid);
    }

    @RequestMapping("/user/index/{id}")
    public User userbyid(HttpServletRequest request, @PathVariable("id") String id, String openid, String mail, @DateTimeFormat(pattern = "yyyy-MM-dd") Date expiry) {
        User user = new User(Integer.parseInt(id), openid, mail, expiry);
        boolean b;
        if (request.getMethod().equals("PUT")) {
            b = userService.updateById(user);
        } else if (request.getMethod().equals("DELETE")) {
            b = !userService.delById(id);
        } else {
            return userService.findById(id);
        }
        return b ? user : userService.findById(id);
    }

    @RequestMapping("/cdk/{cdkey}")
    public Cdk cdkByCdkey(@PathVariable("cdkey") String cdkey) {
        return cdkeyService.findByCdkey(cdkey);
    }

    @RequestMapping("/cdk/index/{id}")
    public Cdk cdkById(HttpServletRequest request, @PathVariable("id") String id, String cdkey, Integer month) {
        Cdk cdk = new Cdk(Integer.parseInt(id), cdkey, month);
        boolean b;
        if (request.getMethod().equals("PUT")) {
            b = cdkeyService.updataById(cdk);
        } else if (request.getMethod().equals("DELETE")) {
            b = !cdkeyService.delById(id);
        } else {
            return cdkeyService.findById(id);
        }
        return b ? cdk : cdkeyService.findById(id);
    }

    @RequestMapping("/cdkrand/{month}/{count}")
    public List<Cdk> cdkRand(@PathVariable("count") Integer count, @PathVariable("month") Integer month) {
        List<Cdk> cdks = new ArrayList<>();
        for (Integer i = 0; i < count; i++) {
            String cdkey = StringUtil.getRandomString(24);
            Cdk cdk = new Cdk(cdkey, month);
            boolean b = cdkeyService.addCdk(cdk);
            if (b) {
                cdks.add(cdk);
            }
        }
        return cdks;
    }

    @RequestMapping("/docheck")
    public String doCheck(String notice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Morning morning = new Morning();
                if (notice != null & !notice.equals("")) morning.setNotice(notice);
                zjy.doCheck(morning);
            }
        }).start();
        return "OK!";
    }

    @RequestMapping("/sendnotice")
    public String sendNotice(String notice) {
        if (notice == null | notice.equals("")) {
            return "请输入公告!";
        }
        ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<cn.myflavor.zjy.entity.User> users = userService.findAllUser();
                SendMailUtil.login();
                for (cn.myflavor.zjy.entity.User user : users) {
                    String mail = user.getMail();
                    SendMailUtil.sendMail(mail, "公告", notice);
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SendMailUtil.logout();
            }
        }).start();
        return "OK!";
    }
}
