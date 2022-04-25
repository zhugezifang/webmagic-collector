package org.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Arrays;
import java.util.List;

public class HuxiuProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

    public static final String url = "https://www.huxiu.com/article";

    @Override
    public void process(Page page) {
        if (page.getUrl().get().indexOf(url) >= 0) {
            page.putField("url",page.getUrl().get());
            page.putField("title",page.getHtml().css("h1.article__title").get());
        } else {
            List<Selectable> urls = page.getHtml().css("div.article-item").nodes();
            int i = 0;
            for (Selectable url : urls) {
                //page.putField("url_"+i, url.css("a").links().get());
                page.addTargetRequest(url.css("a").links().get());
                //page.putField("title_"+i, url.css("h5.article-item__content__title").get());
                //i++;
            }
        }


    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("=======start=========");
        Spider.create(new HuxiuProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://www.huxiu.com/channel/22.html")
                .addPipeline(new ConsolePipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
