package cn.myflavor.zjy.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class SendMailUtil {

    private static String from;
    private static String user;
    private static String password;
    private static String mailadmin;
    private static String smtphost;
    private static String smtpport;
    private final static Logger logger = LoggerFactory.getLogger(SendMailUtil.class);
    private static Session mailSession;
    private static Transport transport;

    @Value("${zjy.mail.username}")
    public void setFrom(String from) {
        SendMailUtil.from = from;
    }

    @Value("${zjy.mail.username}")
    public void setUser(String user) {
        SendMailUtil.user = user;
    }

    @Value("${zjy.mail.password}")
    public void setPassword(String password) {
        SendMailUtil.password = password;
    }

    @Value("${zjy.mail.admin}")
    public void setMailadmin(String mailadmin) {
        SendMailUtil.mailadmin = mailadmin;
    }

    @Value("${zjy.mail.host}")
    public void setSmtphost(String smtphost) {
        SendMailUtil.smtphost = smtphost;
    }

    @Value("${zjy.mail.port}")
    public void setSmtpport(String smtpport) {
        SendMailUtil.smtpport = smtpport;
    }

    public static void login() {
        try {
            String auth = "true";                     //是否需要进行身份验证
            String protocol = "smtp";                  //传输协议
            String mailDebug = "false";                //是否开启debug
            Properties props = new Properties();
            props.put("mail.smtp.host", smtphost);
            props.put("mail.smtp.auth", auth == null ? "true" : auth);
            props.put("mail.transport.protocol", protocol == null ? "smtp" : protocol);
            props.put("mail.smtp.port", smtpport == null ? "25" : smtpport);
            props.put("mail.debug", mailDebug == null ? "false" : mailDebug);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailSession = Session.getInstance(props);
            transport = mailSession.getTransport();
            transport.connect(smtphost, user, password);
        } catch (Exception e) {
            logger.warn("邮箱登录失败");
        }
    }

    public static void logout() {
        try {
            transport.close();
        } catch (Exception e) {
            logger.warn("邮箱注销失败");
        }
    }

    public static void sendMail(String toEmailAddres, String subject, String content) {
        try {
            boolean flag = false;
            if (mailSession == null && transport == null) {
                login();
                flag = true;
            } else if (transport.isConnected() == false) {
                transport.connect(smtphost, user, password);
            }
            String personalName = "健康填报系统";
            String contentType = null;
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setContent(content, contentType == null ? "text/html;charset=UTF-8" : contentType);
            message.setSentDate(new Date());
            InternetAddress address = new InternetAddress(from, personalName);
            message.setFrom(address);
            message.setRecipients(Message.RecipientType.TO, toEmailAddres);
            message.saveChanges();
            transport.sendMessage(message, message.getAllRecipients());
            if (flag == true) {
                logout();
            }
        } catch (Exception e) {
            logger.warn(toEmailAddres + " 邮件发送失败");
        }

    }

    public static void sendAdmin(String title, String text) {
        sendMail(mailadmin, title, text);
    }

}