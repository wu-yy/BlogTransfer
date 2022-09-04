package com.blog.markdown.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;

import com.blog.markdown.entity.MarkDown;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 工具类
 *
 */
@Slf4j
public class MarkDownUtil {

    /**
     * 判断 Elements 是否为空
     * @param elements
     * @return
     */
    public static Boolean elementsNotEmpty(Elements elements){
        if(elements != null && elements.size() > 0){
            return true;
        }

        return false;
    }

    /**
     * 获取 图片 file
     *
     * @param imageFilePath
     * @param imageFileName
     * @return
     */
    public static File getImageFile(String imageFilePath,String imageFileName) throws IOException {
       File imageFile =  new File(imageFilePath + File.separator + imageFileName);
       imageFile.createNewFile();
       return imageFile;
    }


    public static String getUrlOrigin(MarkDown markDown) {
        String net = "";
        String url = markDown.getBlogUrl();
        String[] httpsSplit = url.split("://");
        int i = httpsSplit[1].length() - httpsSplit[1].replaceAll("\\.", "").length();
        if (i >= 2) {
            String[] split = httpsSplit[1].split("\\.");
            net = split[1];
        } else {
            String[] split = httpsSplit[1].split("\\.");
            net = split[0];
        }
        log.info("请求链接>>> {} 来源解析为:{}",url,net);
        if (net.equals("blog")) {
            net = "csdn";
            log.info("请求链接2>>> {} 来源解析为:{}",url,net);
        } else {
            log.info("请求链接3>>> {} 来源解析为:{}",url,net);
        }
        return net;
    }
    public static String generatorFileName() {
        String filename = UUID.randomUUID().toString().split("-")[0]+".md";
        return filename;
    }

    public static void main(String[] args) {
        String a = "https://www.cnblogs.com/duanxz/p/4901437.html";
        MarkDown markDown = new MarkDown();
        markDown.setBlogUrl(a);
        String urlOrigin = getUrlOrigin(markDown);
        System.out.println(urlOrigin);
    }
}
