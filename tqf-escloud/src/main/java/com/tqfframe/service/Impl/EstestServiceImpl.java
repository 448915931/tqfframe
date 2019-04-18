package com.tqfframe.service.Impl;

import com.tqfframe.JsonUtil;
import com.tqfframe.domain.EsTest;
import com.tqfframe.service.EstestService;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tang-QiFeng on 2019/4/17
 */
@Service
public class EstestServiceImpl implements EstestService{


    @Value("${esindex}")
    private String INDEX;
    @Value("${estype}")
    private String TYPE;


    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    // 创建索引
    private void createIndex() {
        elasticsearchTemplate.getClient().admin().indices().prepareCreate(INDEX).get();
    }

    // 删除索引
    private void deleteIndex() {
        elasticsearchTemplate.getClient().admin().indices().prepareDelete(INDEX).get();
    }
    // 批量增加
    public void saveALLByList(List queries) {
        elasticsearchTemplate.bulkIndex(queries);
    }
    /**
     * java方式创建索引和mapping
     */
    @Override
    public void createmapper() {
//        如果是第一次创建，需要注释掉
        deleteIndex();
        createIndex();
        PutMappingRequest mapping =
                Requests.putMappingRequest(INDEX).type(TYPE).source(getMapping());
        elasticsearchTemplate.getClient().admin().indices().putMapping(mapping).actionGet();
    }
    /**
     * 构建mapping
     */
    public XContentBuilder getMapping() {
        XContentBuilder mapBuilder  = null;
        //创建mapping约束字段
        try {
            mapBuilder  = XContentFactory.jsonBuilder();
                 mapBuilder.startObject()
                    .startObject("properties")
                    //性别
                    .startObject("sex")
                    .field("type", "keyword")
                    .endObject()
                    //姓名
                    .startObject("names")
                    .field("type", "keyword")
                    .endObject()
                     //内容//启用ik分词
                    .startObject("content")
                    .field("type", "text")
                    .field("index", true)
                    //使用IK分词器
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .endObject()

                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapBuilder ;
    }


    /**
     * 新增数据
     */
    @Override
    public void saveALLByList() {
        String namelist[]=new String[2];
        String sexlist[]=new String[2];
        String contentlist[]=new String[2];
        namelist[0]="小唐";
        namelist[1]="小红";
        sexlist[0]="男";
        sexlist[1]="女";
        contentlist[0]="我是一个好人";
        contentlist[1]="手机多少钱？";
        List queries = new ArrayList();
        for (int i=0;i<namelist.length;i++){
            EsTest esTest =new EsTest();
            esTest.setNames(namelist[i]);
            esTest.setSex(sexlist[i]);
            esTest.setContent(contentlist[i]);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setIndexName(INDEX);
            indexQuery.setType(TYPE);
            indexQuery.setSource(JSON.toJSONString(esTest));
            queries.add(indexQuery);
        }
        saveALLByList(queries);
    }

}
