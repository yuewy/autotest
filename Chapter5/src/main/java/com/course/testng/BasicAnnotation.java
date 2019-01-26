package com.course.testng;

import org.testng.annotations.*;

public class BasicAnnotation {

    //最基本的注解
    @Test
    public void testcase1(){
        System.out.println("这是测试用例1");
    }

    @Test
    public void testcase2(){
        System.out.println("这是测试用例2");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod:在方法之前运行");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod:在方法之后运行");
    }

    @BeforeClass
    public  void beforeClass(){
        System.out.println("beforeClass:在类运行之前运行");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass:在类运行之后运行");
    }

    @BeforeSuite
    public void  beforeSuite(){
        System.out.println("beforeSuite:在测试套件之前");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("afterSuite:在测试套件之后");
    }

    @AfterGroups
    public void afterGroups(){
        System.out.println("afterGroups:在组方法之前");
    }

    @BeforeGroups
    public void beforeGroups(){
        System.out.println("beforeGroups:在组方法之后");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("beforeTest:在测试方法之前");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("afterTest:在测试方法之后");
    }

}

