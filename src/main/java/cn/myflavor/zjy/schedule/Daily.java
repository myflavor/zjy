package cn.myflavor.zjy.schedule;

import cn.myflavor.zjy.entity.User;
import cn.myflavor.zjy.service.UserService;
import cn.myflavor.zjy.util.DateUtil;
import cn.myflavor.zjy.util.SendMailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Daily {
    @Autowired
    private UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(Daily.class);

    @Scheduled(cron = " 0 30 9 * * *")
    public void userCheck() {
        List<User> users = userService.findAllUser();
        SendMailUtil.login();
        for (cn.myflavor.zjy.entity.User user : users) {
            String openid = user.getOpenid();
            String mail = user.getMail();
            Date expiry = user.getExpiry();
            if (DateUtil.sameDate(DateUtil.addDay(expiry, 1), new Date())) {
                SendMailUtil.sendMail(mail, "到期提醒", "自动填报服务今日到期");
            } else if (DateUtil.addDay(expiry, 1).compareTo(new Date()) < 0) {
                if (DateUtil.sameDate(DateUtil.addDay(expiry, 7), new Date())) {
                    //到期7天，删除用户
                    userService.delByOpenId(openid);
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.info("延迟执行失败");
            }
        }
        SendMailUtil.logout();
    }
}
