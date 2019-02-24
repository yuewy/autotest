package com.course.httpclient.cookies;

import javafx.beans.binding.StringExpression;
import netscape.javascript.JSObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
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


    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException{
        String uri =bundle.getString("test.post.with.cookies");
        String testUrl = this.url+uri;
        HttpPost post = new HttpPost(testUrl);
        DefaultHttpClient client =new DefaultHttpClient();

        //添加参数
        JSONObject param =new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        post.setHeader("content-type","application/json");
        StringEntity entity =new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        client.setCookieStore(this.store);
        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode="+statusCode);

        if (statusCode==200){
            String result= EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }

}
