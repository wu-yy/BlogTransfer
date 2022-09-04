package com.blog.markdown.service;

import com.alibaba.fastjson.JSONObject;
import com.blog.markdown.entity.SETTING;

import java.util.List;
import java.util.Map;

/**
 * @Author: lty
 * @Date: 2021/3/3 14:23
 */
public interface SettingService {
    Map<String,String> getSettings();
    int setSettings(JSONObject j);
}
