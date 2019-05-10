package com.teradata.hadoop.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESUtil {
    //集群名,默认值elasticsearch
    private static final String CLUSTER_NAME = "es-cloud";
    //ES集群中某个节点
    private static final String HOSTNAME = "master";

    private static final String IP_ADDRESS = "106.12.203.3";
    //连接端口号
    private static final int TCP_PORT = 9300;
    //构建Settings对象
    private static Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
    //TransportClient对象，用于连接ES集群
    private static volatile TransportClient client;

    public static void main(String[] args) throws UnknownHostException {
        TransportClient client=getClient();
        System.out.println(isExists("index"));
    }

    /**
     * 同步synchronized(*.class)代码块的作用和synchronized static方法作用一样,
     * 对当前对应的*.class进行持锁,static方法和.class一样都是锁的该类本身,同一个监听器
     * @return
     * @throws UnknownHostException
     */
    public static TransportClient getClient() throws UnknownHostException {
        if(client==null){
            synchronized (TransportClient.class){
                client=new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName(IP_ADDRESS), 9300));
            }
        }
        return client;
    }

    /**
     * 获取索引管理的IndicesAdminClient
     */
    public static IndicesAdminClient getAdminClient() throws UnknownHostException {
        return getClient().admin().indices();
    }

    /**
     * 判定索引是否存在
     * @param indexName
     * @return
     */
    public static boolean isExists(String indexName) throws UnknownHostException {
        IndicesExistsResponse response=getAdminClient().prepareExists(indexName).get();
        return response.isExists()?true:false;
    }
    /**
     * 创建索引
     * @param indexName
     * @return
     */
    public static boolean createIndex(String indexName) throws UnknownHostException {
        CreateIndexResponse createIndexResponse = getAdminClient()
                .prepareCreate(indexName.toLowerCase())
                .get();
        return createIndexResponse.isAcknowledged()?true:false;
    }

    /**
     * 创建索引
     * @param indexName 索引名
     * @param shards   分片数
     * @param replicas  副本数
     * @return
     */
    public static boolean createIndex(String indexName, int shards, int replicas) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .build();
        CreateIndexResponse createIndexResponse = getAdminClient()
                .prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute().actionGet();
        return createIndexResponse.isAcknowledged()?true:false;
    }

    /**
     * 位索引indexName设置mapping
     * @param indexName
     * @param typeName
     * @param mapping
     */
    public static void setMapping(String indexName, String typeName, String mapping) throws UnknownHostException {
        getAdminClient().preparePutMapping(indexName)
                .setType(typeName)
                .setSource(mapping, XContentType.JSON)
                .get();
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     */
//    public static boolean deleteIndex(String indexName) {
//        DeleteIndexResponse deleteResponse = getAdminClient()
//                .prepareDelete(indexName.toLowerCase())
//                .execute()
//                .actionGet();
//        return deleteResponse.isAcknowledged()?true:false;
//    }
}
