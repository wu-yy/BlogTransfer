package com.blog.markdown.utils;

import com.blog.markdown.entity.MarkDown;
import com.blog.markdown.service.HandleService;
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
import java.io.Serializable;

public class DocInfo  implements Serializable {

    public String TITLE;

    public String ARTICLE_LABLE;

    public String FENLEI_LABLE;
}