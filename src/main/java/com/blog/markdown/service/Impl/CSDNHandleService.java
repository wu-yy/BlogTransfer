package com.blog.markdown.service.Impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.blog.markdown.utils.MarkDownUtil;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSDNHandleService extends MarkDownService {


    @Override
    protected Document getHtmlContent(Document document) {
        log.info("CSDNHandleService getHtmlContent");
        Element mainElement = document.getElementById("mainBox");

        Document new_doc = document;

        //*[@id="mainBox"]/main/div[1]/div[1]/div/div[2]/div[2]/div/a[3]

        // 不是 Markdown，则获取 HTML
        if(mainElement == null){
            mainElement = new_doc.getElementById("htmledit_views");
        }

        Elements tags = mainElement.getElementsByClass("artic-tag-box");

        String article_lable = "";
        String fenlei_lable = "";
        if (tags.size() > 0) {
            Elements lables = tags.get(0).getElementsByTag("a");
            if (lables.size() > 2) {
                article_lable = lables.get(0).text();
                List<String> feilei_lable_s = new ArrayList<String>();
                for (Element iterable_element : lables.subList(1, lables.size())) {
                    feilei_lable_s.add(iterable_element.text());
                }
                fenlei_lable  =  String.join(",", feilei_lable_s);
            }
        }
        log.info("article_lable:" + article_lable + " fenlei_lable:" + fenlei_lable);

        String htmlContent = mainElement.getElementById("content_views").html();

        new_doc = Jsoup.parse(htmlContent);

        Element title_ele = mainElement.getElementById("articleContentId");
        String title = title_ele.text();
        log.info("文章标题:" + title);

        // 去掉代码块中的行号
        Elements elements = new_doc.getElementsByTag("pre");
        if(MarkDownUtil.elementsNotEmpty(elements)){

            Elements preNumbers = null;
            for(Element element : elements){
                preNumbers = element.getElementsByClass("pre-numbering");
                if(MarkDownUtil.elementsNotEmpty(preNumbers)){
                    for(Element preNumber : preNumbers){
                        // 删掉换行号
                        preNumber.remove();
                    }
                }
            }
        }

        return new_doc;
    }
}
