package com.jsouplianxi;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JdCrawlerTest2 {


    public void start(){
                    //入口地址
                    String startUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&page=1";

                    //获取到网页源码
                    String html = DoGet(startUrl);

                    //获得页面
                    System.out.println(html);

                    Document root = Jsoup.parse(html);

                    Elements select = root.select("#J_goodsList li.gl-item");
        for (Element li : select){
            //获取id
            String id = li.attr("data-sku");
            //获取价格
            String price = li.select(".J_" + id).text();
            //获取当前活动
            String activity = li.select(".promo-words").text();
            //商品介绍
            String introduce  = li.select("em").text();
            //商品图片
            String img ="http:"+li.select(".p-img img").attr("source-data-lazy-img");
            System.out.println(id);
            System.out.println(price);
            System.out.println(activity);
            System.out.println(introduce);
            System.out.println(img);
        }


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
                System.out.println("-------------------"+content);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
