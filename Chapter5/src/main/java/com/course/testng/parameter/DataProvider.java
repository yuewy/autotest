package com.course.testng.parameter;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProvider {

    @Test(dataProvider = "data")
    public void testDataProvider(String name,int age){
        System.out.println("name="+name+"; age="+age);

    }

    @org.testng.annotations.DataProvider(name = "data")
    public Object[][] provider(){
        return new Object[][]{
                {"zhangsan",10},
                {"lisi",20}
        };
    }

    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1111方法name="+name+"; age="+age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2222方法name="+name+"; age="+age);
    }

    @org.testng.annotations.DataProvider
    public Object[][] methodDataTest(Method method) throws Exception{
        Object[][] result=null;
        if (method.getName().equals("test1")){
            result=new Object[][]{
                    {"zhangsan","10"},
                    {"lisi","20"}
            };
        }else if (method.getName().equals("test2")){
            result=new Object[][]{
                    {"wangwu","30"},
                    {"zhaoliu","40"}
            };
        }
        return result;
    }

}
