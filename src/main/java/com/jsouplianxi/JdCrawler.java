package com.jsouplianxi;

import org.apache.commons.lang3.StringUtils;
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

public class JdCrawler {

     static String URL = "https://search.jd.com/Search?keyword=手机&enc=utf-8&page={page}";

    public void start(){
        for (int i=-1;i <= 7;i++){
            i++;
            System.out.println(i+"-----------");
            //使用replace方法替换字符
            String path = StringUtils.replace(URL,"{page}",String.valueOf(i));
            //获取html的源代码
            String content =  DoGet(path);
            //解析html
            Document root = Jsoup.parse(content);
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
