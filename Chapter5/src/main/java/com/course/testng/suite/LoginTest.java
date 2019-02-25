package com.course.testng.suite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void loginTaobao(){
        System.out.println("Taobao 登录成功");
    }
}
