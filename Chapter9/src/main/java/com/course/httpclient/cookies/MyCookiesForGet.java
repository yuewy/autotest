package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }


    @Test
    public void testGetCookies() throws IOException {
        String result;
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url+uri;

        HttpGet get =new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        //获取cookie信息
        this.store = client.getCookieStore();
        List<Cookie> list = store.getCookies();

        System.out.println("list的大小为："+list.size());
        for(Cookie cookie:list){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name="+name+"   value="+value);
        }
    }

    //该请求的request中需要设置cookies
    @Test(dependsOnMethods = "testGetCookies")
    public void testGetWithCookies() throws  IOException{
        String uri = bundle.getString("test.get.with.cookies");
        String testUrl = this.url+uri;
//        System.out.println(testUrl+"?name=huhansan&age=18");

        //if request with params
//        HttpGet get =new HttpGet(testUrl+"?name=huhansan&age=18");


        HttpGet get =new HttpGet(testUrl);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        //设置cookies store
        defaultHttpClient.setCookieStore(this.store);
        HttpResponse response = defaultHttpClient.execute(get);
        int statusCode=response.getStatusLine().getStatusCode();
        System.out.println("statusCode=" +statusCode);

        if (statusCode==200){
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }

    }
}
