import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetBugList {

    //hibug列表地址
    private String listUrl;
    //hibug登陆地址
    private String loginUrl;
    private ResourceBundle bundle;
    private CookieStore store;
    private List<String> bugId = new ArrayList<String>();
    private List<String> bugExpress = new ArrayList<String>();
    private List<String> bugImgUrl = new ArrayList<String>();
    private List<String> bugStatus = new ArrayList<String>();



    @BeforeTest
    public void getUrl(){
        //读取配置文件
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        //拼接uri和url
        listUrl = bundle.getString("testList.url") + bundle.getString("testList.uri");
        loginUrl = bundle.getString("testList.url") + bundle.getString("testLogin.uri");
    }


    //模拟登陆获取cookie
    @Test
    public void testGetCookie() throws Exception{
        HttpPost post =new HttpPost(loginUrl);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("charset", "UTF-8");

        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("email", ""));
        formparams.add(new BasicNameValuePair("password", ""));
        StringEntity entity =new UrlEncodedFormEntity(formparams, "UTF-8");
        post.setEntity(entity);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        System.out.println("登陆获取cookie："+response.getStatusLine().getStatusCode());

        //获取cookie信息
        this.store = client.getCookieStore();
        List<Cookie> list = store.getCookies();
        for(Cookie cookie:list){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name="+name+"   value="+value);
        }
    }

    @Test(dependsOnMethods = "testGetCookie")
    public void  getBugList() throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //发起请求
        HttpPost post = new HttpPost(listUrl);
        post.setHeader("content-type","application/x-www-form-urlencoded");
        // 创建参数队列
        List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
        formParams.add(new BasicNameValuePair("draw", "1"));
        formParams.add(new BasicNameValuePair("start", "0"));
        formParams.add(new BasicNameValuePair("length", "467"));
        StringEntity entity =new UrlEncodedFormEntity(formParams, "UTF-8");
        post.setEntity(entity);

        httpClient.setCookieStore(this.store);
        HttpResponse response = httpClient.execute(post);
        //response code
        System.out.println(post.getURI());
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("获取bugList："+statusCode);

        if (statusCode==200){
            String result= EntityUtils.toString(response.getEntity());
            String[] data = result.split("\"data\":");
            String dataString = data[1].substring(0,data[1].length()-1);
            System.out.println(dataString);
            JSONArray json = JSONArray.fromObject(dataString); // 首先把字符串转成 JSONArray  对象
            System.out.println(json.size());
            if(json.size()>0){
                for(int i=0;i<json.size();i++){
                    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    String bug_id = job.get("bug_id")+"";
                    bugId.add(bug_id);
                    String description= job.get("description")+"";
                    if (description.contains("src")){
                        String imgUrl = description.split("src=\"")[1].split("\"")[0];
                        System.out.println("imgUrl:"+imgUrl);
                        bugImgUrl.add(imgUrl);
                    }else{
                        bugImgUrl.add("");
                    }
                    bugStatus.add(job.get("last_status_name")+"");
                    String bugTitle = job.get("bug_title")+"";
                    bugExpress.add(bugTitle);
                    System.out.println(job.get("bug_title")+"") ;  // 得到 每个对象中的属性值
                }
            }
            WriteDataIntoFile.writeDataIntoFile(bugId,bugExpress,bugImgUrl,bugStatus);
        }
    }


}
