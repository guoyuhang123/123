package com.jsouplianxi;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JdCrawlerTest {


    public void start(){
        //入口地址
        String startUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&page=1";

        //获取到网页源码
        String html = DoGet(startUrl);

        //获得页面
        System.out.println(html);

        Document parse = Jsoup.parse(html);

        String text = parse.select("#J_topPage").text();

        System.out.println(text);


    }

    private String DoGet(String url){
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建http get请求
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
                response = httpClient.execute(httpGet);
                //判断返回状态是否是200
                if(response.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");

                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
