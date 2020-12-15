package cn.myflavor.zjy.schedule;

import cn.myflavor.zjy.check.BaseCheck;
import cn.myflavor.zjy.check.Morning;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Zjy {
    @Autowired
    private UserService userService;
    private int errorCount = 0;
    private final static Logger logger = LoggerFactory.getLogger(Zjy.class);

    @Scheduled(cron = " 0 10 8 * * *")
    public void morningCheck() {
        doCheck(new Morning());
    }


    public void doCheck(BaseCheck Check) {
        List<cn.myflavor.zjy.entity.User> users = userService.findAllUser();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        SendMailUtil.login();
        for (cn.myflavor.zjy.entity.User user : users) {
            sleep(5000);
            String openId = user.getOpenid();
            String mail = user.getMail();
            Date expiry = user.getExpiry();
            if (DateUtil.addDay(expiry, 1).compareTo(new Date()) >= 0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        if (!Check.check(openId, mail)) {
                            synchronized (this) {
                                errorCount++;
                            }
                        }
                    }
                };
                Thread t = new Thread(r);
                executorService.submit(t);
            }
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                break;
            }
            sleep(1000);
        }
        if (errorCount > 0) {
            sleep(1000);
            SendMailUtil.sendAdmin("执行结果", errorCount + "次提交失败");
            errorCount = 0;
        }
        SendMailUtil.logout();
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.warn("线程延迟失败");
        }
    }
}
