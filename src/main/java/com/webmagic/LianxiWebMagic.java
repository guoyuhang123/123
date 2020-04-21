package com.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.selector.Html;

public class LianxiWebMagic implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        page.addTargetRequests(html.css(".content__list--item--title twoline a").links().all());
        String string = html.xpath("//p[@class='content__title']//text()").toString();
        page.putField("title",string);
        if(page.getResultItems().get("title")==null){
            page.setSkip(true);
        }


    }

    @Override
    public Site getSite() {

        return  site;
    }

    public static void main(String[] args) {
        Spider.create(new LianxiWebMagic()).addUrl("https://bj.lianjia.com/zufang/").thread(1).run();
    }
}
