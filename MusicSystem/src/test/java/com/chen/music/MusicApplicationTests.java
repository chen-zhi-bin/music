package com.chen.music;

import com.chen.music.pojo.Music;
import com.chen.music.utils.Constants;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Constants.User.DEFAULT_AVATAR);
    }


    @Autowired
    SolrTemplate solrTemplate;

    @Autowired
    private SolrClient solrClient;



    @Test
    void solrTest(){

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q","name:火星伟大");
        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            for (SolrDocument result : results) {
                System.out.println(result.toString());
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
