package com.blog.markdown.service.Impl;

import com.blog.markdown.entity.MarkDown;
import com.blog.markdown.service.HandleService;
import com.blog.markdown.utils.DocInfo;
import com.blog.markdown.utils.MarkDownUtil;
import com.overzealous.remark.Remark;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public abstract class MarkDownService implements HandleService {


    @Override
    public String getBlogContent(MarkDown markDown) {
        // 1. 获取 Document
        String blog_url = markDown.getBlogUrl();
        log.info("1.获取document..."+ blog_url);
        Document document = getDocument(blog_url);

        log.info("2.获取Document的信息");
        // 2.提取 Document 中的博文信息
        Document new_document = getHtmlContent(document);

        // 2.1 提取文章的标题和其他的信息
        log.info("2.1 提取文章的标题和其他的信息");
        DocInfo info = GetDocInfo(document);
        markDown.setTitle(info.TITLE);
        markDown.setArticleLable(info.ARTICLE_LABLE);
        markDown.setFenleiLable(info.FENLEI_LABLE);

        log.info("3.下载图片，并进行替换");
        // 3.下载图片，并进行替换
        String htmlContent = convertHtml(markDown, new_document);

        // 转换为 markdown
        Remark remark = new Remark();

        return remark.convert(htmlContent);
    }


    /**
     * <p>1、转换为 HTML 类型</p>
     * <p>2、需要将文件中的图片下载到本地，且替换图片地址</p>
     * <p>3、处理代码块，因为 markdown 转换时没有给代码块加 ```</p>
     */
    protected String convertHtml(MarkDown markDown, Document document){
        if((markDown.getImagePath() != null && !"".equals(markDown.getImagePath())) &&
                (markDown.getImageUrl() != null && !"".equals(markDown.getImageUrl()))){

            // 处理图片
            handleImg(markDown,document);

//            // 处理代码块
//            handlePre(document);
        }

        return document.html();
    }

    /**
     * 处理代码块，主要是在代码块中的前面增加 `<p>```</p>` 即可
     * @param document
     */
    protected void handlePre(Document document){

    }

    /**
     * 处理图片
     * @param markDown
     * @param document
     */
    private void handleImg(MarkDown markDown, Document document) {
        // 获取所有的 img 标签
        Elements elements = document.getElementsByTag("img");

        if(elements != null && elements.size() >  0){

            // 新建文件
            File pathFile = new File(markDown.getImagePath());

            if(!pathFile.exists() && !pathFile.mkdirs()){
                throw new RuntimeException("新建目录失败...");
            }

            doHandleImg(elements,markDown);
        }
    }

    private void doHandleImg(Elements elements, MarkDown markDown){
        String imageUrl,imageSrc = "";

        for(Element element : elements){
            String url = element.attr("src");
            if (url.startsWith("data:image/svg+xml;utf8")) {
                continue;
            }
            try {
                String name = UUID.randomUUID().toString().split("-")[0];

                // 下载图片
                String fileName = downImage(markDown,element,name);


                imageUrl = markDown.getImageUrl() + "/" + fileName;
                // 替换地址
                element.attr("src",imageUrl);
                element.attr("alt",fileName);

            } catch (IOException e) {
                System.out.println(imageSrc + "下载图片失败,cause by :" + e.getMessage());

                e.printStackTrace();
            }
        }
    }

    /**
     *
     * 下载图片
     *
     * @param markDown
     * @param element
     * @param name
     * @return
     * @throws IOException
     */
    private String downImage(MarkDown markDown, Element element, String name) throws IOException {
        String fileName = markDown.getImageName() + "_" + name + ".png";

        File imageFile = MarkDownUtil.getImageFile(markDown.getImagePath(),fileName);

        String imageSrc = element.attr("src");
        if (imageSrc.startsWith("data:image/svg+xml;utf8")) {
            log.info("ignore svg type images");
            return "";
        }



        // 如果不存在 src，则尝试获取 data-src
        if(imageSrc == null || "".equals(imageSrc.trim())){
            imageSrc = element.attr("data-src");
        }

        // 如果不存在 data-src，则尝试获取 data-original-src
        if(imageSrc == null || "".equals(imageSrc.trim())){
            imageSrc = element.attr("data-original-src");
            imageSrc = "https:" + imageSrc;//简书 Https
        }

        // 有些图片没有 http
        if(imageSrc.startsWith("//")){
            imageSrc = "http:" + imageSrc;

        }
        log.info("catch picture :{}",imageSrc);
        URL url = new URL(imageSrc);

        // 打开网络
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        InputStream is = null;
        OutputStream out = null;
        try {
            //获取链接的输出流
            is = connection.getInputStream();

            //根据输入流写入文件
            out = new FileOutputStream(imageFile);
            int i;
            while((i = is.read()) != -1){
                out.write(i);
            }

            return imageFile.getName();
        } finally {
            try {
                if(out != null){
                    out.close();
                }

                if(is != null){
                    is.close();
                }
            } catch (IOException e) {

            }
        }
    }

    /**
     * 获取 Document 对象
     * @param blogUrl
     * @return
     */
    private Document getDocument(String blogUrl) {
        try {
            return Jsoup.connect(blogUrl).get();
        } catch (IOException e) {
            throw new RuntimeException("解析地址，获取 Document 对象失败..",e);
        }
    }

    @Override
    public DocInfo GetDocInfo(Document document) {
        DocInfo info = new DocInfo();
        log.info("CSDNHandleService getHtmlContent");
        Document new_doc = document;
        Element mainElement = new_doc.getElementById("mainBox");

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
        info.ARTICLE_LABLE = article_lable;
        info.FENLEI_LABLE = fenlei_lable;

        String htmlContent = mainElement.getElementById("content_views").html();

        new_doc = Jsoup.parse(htmlContent);

        Element title_ele = mainElement.getElementById("articleContentId");
        String title = title_ele.text();
        log.info("文章标题:" + title);
        info.TITLE = title;

        return info;
    }


    /**
     * 每个网站的结构不同，需要各个子类完成解析
     * @param document
     * @return
     */
    protected abstract Document getHtmlContent(Document document);
}
