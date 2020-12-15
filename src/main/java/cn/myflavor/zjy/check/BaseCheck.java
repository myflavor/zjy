package cn.myflavor.zjy.check;

import cn.myflavor.zjy.util.HttpUtil;
import cn.myflavor.zjy.util.JSJUtil;
import cn.myflavor.zjy.util.SendMailUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseCheck {
    public final static String url_host = "https://zjyxsc.jinshuju.net";

    public abstract String getUrlCore();

    public abstract String getName();

    public static final Logger logger = LoggerFactory.getLogger(BaseCheck.class);

    public abstract RequestBody getPostData();

    public static Document document;
    public String result = "";
    public String tips = "";
    public String notice = "";

    public boolean check(String openId, String mail) {
        result = "";
        tips = "";
        try {
            String url = url_host + getUrlCore() + "?q[0][x_field_weixin_openid]=" + openId;
            HttpUtil.newClientWithCookies();
            String pageCheck = HttpUtil.getData(url);
            Document docCheck = Jsoup.parse(pageCheck);
            setDocument(docCheck);
            RequestBody body = new FormBody.Builder()
                    .add("authenticity_token", JSJUtil.getToken())
                    .build();
            String pageAlter = HttpUtil.postData(url_host + JSJUtil.getUrlChange(), body);
            Document docAlter = Jsoup.parse(pageAlter);
            setDocument(docAlter);
            body = getPostData();
            String url_post = JSJUtil.getUrlResult();
            String pageResult = HttpUtil.postData(url_host + url_post, body);
//            result = "数据提交";
            if (pageResult.indexOf("数据详情") != -1) {
                result += "成功";
                if (!tips.equals("")) tips = "提示: " + tips + " <br>";
//                result += tips;
//                result += notice;
            } else {
                throw new Exception("失败");
            }
        } catch (Exception e) {
            result = "失败";
            logger.warn(openId + " 数据提交" + result);
            return false;
        } finally {
            String checkUrl = url_host + "/f/J30YUa/s/TIXbCy?q[0][x_field_weixin_openid]=" + openId;
            String mailContent = "<div style=\"font-size: 1.1em\">" + notice + tips + "<a href=\"" + checkUrl + "\">点我查询确认状态</a></div>";
            SendMailUtil.sendMail(mail, getName() + "填报" + result, mailContent);
        }
        return true;
    }

    public void setNotice(String notice) {
        this.notice = "公告: " + notice + " <br>";
    }

    public static String getAnswer(String element, String keyValue, int value, String attr) {
        String select = element + "[name=\"" + keyValue + "\"]";
        return document.select(select).eq(value).attr(attr);
    }

    public static String getAnswer(String element, String keyValue, boolean isChecked, int value, String attr) {
        String select = element + "[name=\"" + keyValue + "\"]";
        if (isChecked) {
            select += "[checked=\"checked\"]";
        }
        return document.select(select).eq(value).attr(attr);
    }

    public static String getAnswer(String element, String keyValue, boolean isChecked, String attr) {
        return getAnswer(element, keyValue, isChecked, 0, attr);
    }

    public static String getAnswer(String element, String keyValue, String attr) {
        return getAnswer(element, keyValue, false, 0, attr);
    }

    public static void setDocument(Document document) {
        JSJUtil.setDocument(document);
        BaseCheck.document = document;
    }

    public static String input(String key, int index) {
        return getAnswer("input", key, index, "value");
    }

    public static String input(String key) {
        return input(key, 0);
    }

    public static String select(String key, int index, String attr) {
        return getAnswer("select", key, index, attr);
    }

    public static String select(String key, int index) {
        return select(key, index, "data-value");
    }

    public static String select(String key) {
        return select(key, 0);
    }
}
