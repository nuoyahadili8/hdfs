package com.github.al.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Project: elasticsearch spring-data 目前支持的最高版本为5.5 所以需要自己注入生成客户端
 * @Description: 数据配置，进行初始化操作
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/12/012 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@Configuration
public class ESConfiguration implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ESConfiguration.class);

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void destroy() throws Exception {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            LOG.error("Error closing ElasticSearch client: ", e);
        }
    }

    /**
     * 控制Bean的实例化过程
     * @return
     * @throws Exception
     */
    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restHighLevelClient = buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(clusterNodes.split(":")[0], Integer.parseInt(clusterNodes.split(":")[1]), "http")));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return restHighLevelClient;
    }
}
