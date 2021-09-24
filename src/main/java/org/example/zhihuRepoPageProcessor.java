package org.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Arrays;
import java.util.List;

public class zhihuRepoPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";


    @Override
    public void process(Page page) {

        List<String> urls = page.getHtml().css("a.UserLink-link").all();
        page.putField("size", urls.size());
        page.putField("all", Arrays.toString(urls.toArray()));

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("=======start=========");


        Spider.create(new zhihuRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://www.zhihu.com/people/zhugezifang/following?page=1")
                //.addPipeline(new ConsolePipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
