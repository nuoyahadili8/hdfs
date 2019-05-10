package com.github.al.es.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.al.es.dao.EsDao;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/12/012 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@RestController
@RequestMapping("/es")
public class DemoESController {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private EsDao esDao;

    /**
     * 创建索引
     * @return
     */
    @RequestMapping("create")
    public String createIndexOne() {
        try {
            String index="testdb";  //必须为小写
            String type="userinfo";
            JSONObject obj=new JSONObject();
            obj.put("name","qxw");
            obj.put("age",25);
            obj.put("sex","男");
            String [] tags={"标签1","标签2"};
            obj.put("tags",tags);
            esDao.createIndexOne(index,type,obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "创建成功！";
    }

    /**
     * 批量创建索引
     */
    @RequestMapping("batch")
    public String bacthIndex(){
        String index="testdb";  //必须为小写
        String type="userinfo";
        List<JSONObject> list=new ArrayList<>();
        JSONObject obj=null;
        for (int i = 0; i <10 ; i++) {
            obj=new JSONObject();
            obj.put("name","qxw"+i);
            obj.put("age",25+i);
            list.add(obj);
        }
        esDao.bacthIndex(index,type,list);
        return "批量创建索引成功！";
    }

    /**
     * 根据ID查询
     * @return
     */
    @RequestMapping("find")
    public String findById(){
        String index="testdb";  //必须为小写
        String type="userinfo";
        String id="7DelpWcBzYSDGIrHXAbq";
        GetResponse result=esDao.findById(index,type,id);
        System.out.println("查询结果："+result.toString());
        return result.toString();
    }

    /**
     * 根据ID查询 指定过滤字段
     */
    @RequestMapping("filter")
    public String findByIdexcludes(){
        String index="testdb";  //必须为小写
        String type="userinfo";
        String id="NWrCg2UBU-HvVB1XZxe1";
        String [] includes={"name","sex","age"};//不过滤
        String [] excludes={"tags"}; //过滤字段

        System.out.println("查询结果："+esDao.findById(index,type,id,includes,excludes));
        return esDao.findById(index,type,id,includes,excludes).toString();
    }

    /**
     * 查询所有
     */
    @RequestMapping("all")
    public String getAllIndex(){
        String index="testdb";  //必须为小写
        String type="userinfo";
        SearchResponse result = esDao.getAllIndex(index, type);
        System.out.println("查询结果："+result);

        String [] includes={"name","sex",};//不过滤
        String [] excludes={"tags","age"}; //过滤字段
        SearchResponse result2=esDao.getAllIndex(index,type,includes,excludes);
        System.out.println("指定过滤字段查询结果："+result2);
        return result2.toString();
    }

    /**
     * 条件查询 /匹配所有
     * @return
     */
    @RequestMapping("search")
    public String getAllIndexByFiled(){
        String index="testdb";
        String type="userinfo";
        /**
         * 使用QueryBuilder
         * termQuery("key", obj) 完全匹配
         * termsQuery("key", obj1, obj2..)   一次匹配多个值
         * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
         * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
         * matchAllQuery();         匹配所有文件
         */

        //匹配所有文件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        SearchResponse result=esDao.getAllIndex(index,type,searchSourceBuilder);
        System.out.println("匹配所有查询结果："+result);
        return result.toString();
    }

    /**
     * 模糊、排序查询
     */
    @RequestMapping("sortFind")
    public String getAllIndexByFiled3(){
        String index="testdb";
        String type="userinfo";

        SearchSourceBuilder search3 = new SearchSourceBuilder();
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name","qxw");
        //在匹配查询上启用模糊匹配
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
//        //在匹配查询上设置前缀长度选项
//        matchQueryBuilder.prefixLength(3);
//        //设置最大扩展选项以控制查询的模糊过程
//        matchQueryBuilder.maxExpansions(10);


        //默认情况下，搜索请求会返回文档的内容,设置fasle不会返回窝
//        search3.fetchSource(false);

        //也接受一个或多个通配符模式的数组，以控制以更精细的方式包含或排除哪些字段
        String[] includeFields = new String[] {"name", "age", "tags"};
        String[] excludeFields = new String[] {"_type","_index"};
        search3.fetchSource(includeFields, excludeFields);

        //指定排序
        search3.sort(new FieldSortBuilder("age").order(SortOrder.DESC));




        //启用模糊查询 fuzziness(Fuzziness.AUTO)
//        search3.query(QueryBuilders.matchQuery("name","qxw").fuzziness(Fuzziness.AUTO));

        //模糊查询，?匹配单个字符，*匹配多个字符
//        search3.query(QueryBuilders.wildcardQuery("name","*qxw*"));

        //搜索name中或tags  中包含有qxw的文档（必须与music一致)
//        search3.query(QueryBuilders.multiMatchQuery("qxw","name","tags"));



        //多条件查询 相当于and
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //查询age=32
        TermQueryBuilder termQuery=QueryBuilders.termQuery("age",32);

        //匹配多个值  相当于sql 中in(....)操作
        TermsQueryBuilder termQuerys=QueryBuilders.termsQuery("_id","PWrIg2UBU-HvVB1XzRce","XWqYhGUBU-HvVB1Xahct");

        //模糊查询name中包含qxw
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name", "*qxw*");

        boolQueryBuilder.must(termQuery);
        boolQueryBuilder.must(queryBuilder);
        boolQueryBuilder.must(termQuerys);

//        //设置from确定结果索引的选项以开始搜索。默认为0。
//        search3.from(0);
//        //设置size确定要返回的搜索命中数的选项。默认为10。
//        search3.size(1);

        search3.query(boolQueryBuilder);

        SearchResponse result=esDao.getAllIndex(index,type,search3);
        //解析SearchHits
        SearchHits hits = result.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String indexs = hit.getIndex();
            String types = hit.getType();
            String ids = hit.getId();

            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println("id ："+ids+sourceAsMap.toString());
        }

        System.out.println("查询结果："+esDao.getAllIndex(index,type,search3));
        return esDao.getAllIndex(index,type,search3).toString();
    }

    @RequestMapping("aggregation")
    public String AggregationsTest() throws IOException {
        String index="emptydb";
        String  type="empty";
//        List<JSONObject>  list=new ArrayList<>();
//        JSONObject obj=new JSONObject();
//        obj.put("name","小明"); obj.put("age",25); obj.put("salary",10000); obj.put("detpty","技术部");
//        list.add(obj);
//
//        JSONObject obj2=new JSONObject();
//        obj2.put("name","小蛋"); obj2.put("age",22); obj2.put("salary",5000); obj2.put("detpty","技术部");
//        list.add(obj2);
//
//        JSONObject obj3=new JSONObject();
//        obj3.put("name","张三"); obj3.put("age",24); obj3.put("salary",300); obj3.put("detpty","销售部");
//        list.add(obj3);
//
//        JSONObject obj4=new JSONObject();
//        obj4.put("name","李四"); obj4.put("age",22); obj4.put("salary",4000); obj4.put("detpty","采购部");
//        list.add(obj4);
//
//          //添加测试数据
//        esDao.bacthIndex(index,type,list);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(searchSourceBuilder);

        //计算所有员工的平均年龄
        //terms(查询字段别名).field(分组字段)
        searchSourceBuilder.aggregation(AggregationBuilders.avg("average_age").field("age"));
        SearchResponse res=client.search(searchRequest);
        System.out.println("聚合操作查询结果："+res.toString());


        Aggregations aggregations = res.getAggregations();
        Map<String, Aggregation> aggregationMap = aggregations.getAsMap();
        System.out.println("聚合操作解析："+aggregationMap.toString());
        return aggregationMap.toString();
    }

}
