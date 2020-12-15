package cn.myflavor.zjy.controller;

import cn.myflavor.zjy.entity.Cdk;
import cn.myflavor.zjy.entity.User;
import cn.myflavor.zjy.service.CdkeyService;
import cn.myflavor.zjy.service.UserService;
import cn.myflavor.zjy.util.DateUtil;
import cn.myflavor.zjy.util.SendMailUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RequestMapping("/api")
@Controller
public class ApiController {
    @Autowired
    private CdkeyService cdkeyService;
    @Autowired
    private UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @ResponseBody
    @RequestMapping("/verify")
    @Transactional
    public String verify(String cdkey, String openid, String mail) {
        Cdk cdk = cdkeyService.findByCdkey(cdkey);
        if (cdk == null) {
            return "卡密不存在";
        } else {
            int month = cdk.getMonth();
            if (month <= 0) {
                return "卡密已失效";
            } else {
                String url = "https://msj.jinshuju.net/f/J30YUa/s/elVofh?q[0][x_field_weixin_openid]=" + openid;
                String pageCheck = getData(url);
                if (!pageCheck.contains("符合条件")) {
                    return "微信OpenId校验不通过";
                } else {
                    User user = userService.findByOpenId(openid);
                    String result = "";
                    if (user == null) {
                        //新用户
                        user = new User(openid, mail, DateUtil.addMonth(new Date(), month));
                        userService.addUser(user);
                        cdkeyService.delByCdkey(cdkey);
                        result = "新用户添加成功<br>到期时间 " + DateUtil.dateForUser(user.getExpiry());
                    } else {
                        //老用户
                        Date expiry;
                        int id = user.getId();
                        if (user.getExpiry().compareTo(new Date()) > 0) {
                            //在数据库的时间上增加
                            expiry = DateUtil.addMonth(user.getExpiry(), month);
                        } else {
                            //在今天的时间上增加
                            expiry = DateUtil.addMonth(new Date(), month);
                        }
                        userService.updateByOpenId(openid, id, mail, expiry);
                        result = "老用户续期成功<br>到期时间 " + DateUtil.dateForUser(expiry);
                    }
                    SendMailUtil.sendMail(mail, "提交结果", result);
                    cdkeyService.delByCdkey(cdkey);
                    return result;
                }
            }
        }
    }

    public String getData(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            logger.warn("访问页面失败");
        }
        return "访问页面失败";
    }
}
