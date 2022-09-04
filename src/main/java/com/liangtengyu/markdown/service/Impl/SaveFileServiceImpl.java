package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.dao.MDDao;
import com.liangtengyu.markdown.dao.SETTINGDao;
import com.liangtengyu.markdown.entity.MD;
import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.entity.SETTING;
import com.liangtengyu.markdown.service.SaveFileService;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
/**
 * @Author: lty
 * @Date: 2020/12/28 14:40
 */
@Slf4j
@Service
public class SaveFileServiceImpl implements SaveFileService {

    @Autowired
    MDDao mdDao;

    @Autowired
    SETTINGDao settingDao;
    @Override
    public String saveToFile(String result, MarkDown markDown) throws IOException {
        SETTING mdSavePath = settingDao.findbyname("MD_Save_Path");
        if (mdSavePath == null) {
            log.info("mdSavePath is null");
        }
        System.out.println(mdSavePath);
        //通过此接口,将markdown保存为文本
        File f;
        if (mdSavePath ==null) {
            f = new File("/Users/wuyongyu/PycharmProjects/to_markdown/mds");
        } else {
            f = new File(mdSavePath.getConfigValue());
        }
        if (!f.exists()) {
            f.mkdirs();
        }
        String markdown_filename = MarkDownUtil.generatorFileName();
        if (!markDown.getTitle().equals("")) {
            List<String> ss = new ArrayList<String>();
            ss.add(markDown.getTitle());
            ss.add(markDown.getArticleLable());
            ss.add(markDown.getFenleiLable());
            markdown_filename = String.join("_", ss);
        }
        log.info("markdown保存文件名:" + markdown_filename);
        File mdFile = new File(f, markdown_filename);
        if (!mdFile.exists()) {
            mdFile.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(mdFile);
        outputStream.write(result.getBytes());
        outputStream.close();
        saveToDatabase(result, markDown);
        return "markdown file saveToFile success ";
    }

    @Override
    public void saveToDatabase(String result, MarkDown markDown) throws IOException {
        MD md = new MD();
        md.setCreateTime(new Date());
        md.setCONTEXT(result);

        md.setTITLE(getTitle(result));
        mdDao.save(md);
        log.info("保存到数据库成功!");
    }

    private String getTitle(String result) {
        return result.substring(0, 50);
    }

    @Override
    public void saveImagePath(String path) {

    }
}
