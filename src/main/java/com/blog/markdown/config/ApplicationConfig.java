package com.blog.markdown.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blog.markdown.dao.SETTINGDao;
import com.blog.markdown.entity.SETTING;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Slf4j
@Configuration
public class ApplicationConfig implements WebMvcConfigurer, InitializingBean {
    @Autowired
    SETTINGDao settingDao;

    public  String dynamicAddress;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+dynamicAddress);
        log.info("图片服务器映射成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SETTING set = settingDao.findbyname("Image_Save_Path");
        if (set != null) {
            String configValue = set.getConfigValue();
                 //少/的加/
            if (!configValue.endsWith("/")) {
                configValue += "/";
            }
            dynamicAddress= configValue;
            log.info("加载用户图片保存路径:"+dynamicAddress);
        } else {
            dynamicAddress =  "/Users/wuyongyu/PycharmProjects/to_markdown/";
            log.info("加载用户图片保存路径:"+dynamicAddress);
        }
    }
}
