package com.chan.oilmanagement.utils;

import com.chan.oilmanagement.OilManagementApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class VerCodeSendingUtilTest extends OilManagementApplicationTests {

    @Autowired
    JavaMailSender mailSender;

}