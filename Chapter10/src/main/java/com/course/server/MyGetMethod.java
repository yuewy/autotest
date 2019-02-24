package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@Api(value="/",description = "这是我全部的get方法")
public class MyGetMethod {

    @RequestMapping(value="/getCookies",method= RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest  装请求信息
        //HttpServletResponse  装返回信息

        Cookie cookie =new Cookie("login","true");
        response.addCookie(cookie);
        return  "恭喜你获得Cookies信息成功";
    }

    /**
     * y要求客户端携带cookies访问
     * 这是一个需要携带cookies信息才能访问的get请求
     * */
    @RequestMapping(value="/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "你必须携带cookies信息";
        }

        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login")&&cookie.getValue().equals("true")){
                return  "恭喜你访问成功";
            }
        }

        return "你必须携带cookies信息";

    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url： key=value&key=value
     * w我们来模仿获取商品列表
     * */
    @RequestMapping(value="/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求一",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList= new HashMap<>();
        myList.put("shoes",400);
        myList.put("clothes",100);
        return myList;
    }


    /***
     * 第二种需要携带参数访问的get请求
     * url:ip:port/get/with/param
     */
    @RequestMapping(value = "get/with/param/{start}/{end}")
    @ApiOperation(value = "需要携带参数才能访问的get请求二",httpMethod = "GET")
    public Map<String,Integer> myFetList(@PathVariable Integer start,
                                         @PathVariable Integer end){
        Map<String,Integer> myList= new HashMap<>();
        myList.put("shoes",400);
        myList.put("clothes",100);
        return myList;
    }

}
