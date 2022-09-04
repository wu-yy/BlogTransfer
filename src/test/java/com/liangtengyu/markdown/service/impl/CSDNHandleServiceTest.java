package com.liangtengyu.markdown.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.liangtengyu.markdown.service.ResolveService;

@SpringBootTest
public class CSDNHandleServiceTest {
    // @Autowired
    
    private ResolveService stockCrawlerService;

    @Test
    public void testStockstate() {
        System.out.println("name:");
//         List<StockInfo> stockList =stockCrawlerService.getStockList();
//         stockList.forEach(info->{
//             String code = info.getCode();
//             StockConsts.StockState state = stockCrawlerService.getStockState(code);
//             int value = state.value();
// //            System.out.println("name:" + info.getName() + " state:" + value);
//         });
    }
}
