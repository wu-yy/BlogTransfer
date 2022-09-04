package com.blog.markdown.service;


import org.springframework.stereotype.Service;

import com.blog.markdown.entity.MarkDown;
import com.blog.markdown.utils.DocInfo;

import org.jsoup.nodes.Document;
import java.util.Map;

public interface HandleService {

    /**
     * 获取博客内容
     *
     * @param markDown
     * @return
     */
    String getBlogContent(MarkDown markDown);

    DocInfo GetDocInfo(Document document);


}
