package com.chen.music.controller;

import com.chen.music.pojo.solrInfo.MusicInfo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISettingsService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestApi {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ISettingsService settingsService;
    @GetMapping("/histhory")
    public void history(){
       settingsService.updateHistoryViewCount();
    }

    @GetMapping("/seven_history")
    public ResponseResult getHistory(){
        return settingsService.getSevenHistory();
    }

    @GetMapping
    public void testsolr() throws IOException, SolrServerException {

        // 创建query对象
        HighlightQuery query = new SimpleHighlightQuery(new SimpleStringCriteria("name:伟大流浪"));

// 配置高亮选项
        HighlightOptions options = new HighlightOptions();
// 高亮域
        options.addField("name");
// 前缀和后缀
        options.setSimplePrefix("<span style=\"color:blue;\">");
        options.setSimplePostfix("</span>");

// 设置高亮设置
        query.setHighlightOptions(options);

        // 分页
        query.setOffset(0L);
        query.setRows(1);

        //  执行查询 高亮
        HighlightPage<MusicInfo> highlightPage  = solrTemplate.queryForHighlightPage("lk_core",query, MusicInfo.class);
// 获取高亮数据和普通数据
        List<HighlightEntry<MusicInfo>> highlighted = highlightPage.getHighlighted();
        System.out.println(highlighted.toString());
        System.out.println(highlightPage.getTotalPages());
// 遍历多个item数据
        for (HighlightEntry<MusicInfo> itemHighlightEntry : highlighted) {
            MusicInfo item = itemHighlightEntry.getEntity();
            // 获取高亮数据
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();
            if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets() != null
                    && highlights.get(0).getSnipplets().size() > 0) {
                //可能存在多个高亮域
                HighlightEntry.Highlight highlight = highlights.get(0);
                // 获取分片数据
                List<String> snipplets = highlight.getSnipplets();
                String data = snipplets.get(0);
                //替换高亮数据
                item.setName(data);
            }
        }

        Map<String, Object> pageMap = new HashMap<>();

        pageMap.put("rows", highlightPage.getContent());
        pageMap.put("total", highlightPage.getTotalElements());

        System.out.println(pageMap.toString());

//        Query query = new SimpleQuery("name:火星伟大");
//        // 指定偏移量，从0开始
//        query.setOffset(0L);
//        // 查询的size数量
//        query.setRows(1);
//        ScoredPage<MusicInfo> ans = solrTemplate.queryForPage("lk_core", query, MusicInfo.class);
//
//        // 文档数量
//        long totalDocNum = ans.getTotalElements();
//        List<MusicInfo> docList = ans.getContent();
//        Pageable pageable = ans.getPageable();
//        System.out.println(pageable.toString());
//        System.out.println(ans.getMaxScore());
//        System.out.println(ans.getTotalPages());

    }
}
