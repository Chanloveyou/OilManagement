package com.chan.oilmanagement.utils;

import com.chan.oilmanagement.exception.MailSendingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;


@Data
public class VerCodeSendingUtil {

    private VerCodeGenerateUtil verCodeGenerateUtil;

    /**
     * 验证码
     */
    private String code;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 发送者邮箱
     */

    @Value("${spring.mail.username}")
    private String from = "zymikker@163.com";


    public String getCode() {
        return this.code;
    }

    public Date getSendTime() {
        return this.sendTime;
    }


    public void sendMail(String to, JavaMailSender mailSender) {
        SimpleMailMessage msg = new SimpleMailMessage();
        //创建6位验证码
        code = VerCodeGenerateUtil.generateVerCode();
        //发送时间
        sendTime = new Date();
        //邮箱文本
        msg.setText("尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");
        //谁发
        msg.setFrom(from);
        //发送给谁
        msg.setTo(to);
        //邮箱标题
        msg.setSubject("验证码");

        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            throw new MailSendingException();
        }

    }

    /**
     *计算两个日期的分钟差
     */
    public int getMinute(Date fromDate, Date toDate) {
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }

}
