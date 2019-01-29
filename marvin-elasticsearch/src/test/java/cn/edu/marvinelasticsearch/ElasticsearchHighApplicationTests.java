package cn.edu.marvinelasticsearch;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ElasticsearchHighApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchHighApplicationTests.class);

    public static final String INDEX_IDEA_HIGH="idea";
    public static final String INDEX_IDEA_HIGH_ALIGN="align";
    public static final String TYPE_IDEA="doc";

    RestHighLevelClient restClient=null;
    @Before
    public void setUp(){
        restClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("47.96.107.63", 9200, "http"))
                    .setFailureListener(new RestClient.FailureListener(){
                        @Override
                        public void onFailure(Node node) {
                            super.onFailure(node);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(5000)  //连接超时
                                    .setSocketTimeout(60000);  //套接字超时
                        }
                    })
                    .setMaxRetryTimeoutMillis(60000)  //最大重试超时
                );
    }

    @After
    public void endPoint() throws IOException {
       if(null!=restClient) restClient.close();
    }

    /**
     * 创建索引
     */
    @Test
    public void resetCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX_IDEA_HIGH);//创建索引
        //创建的每个索引都可以有与之关联的特定设置。
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1) //主分片数
                .put("index.number_of_replicas", 3)  //数据分片数,
        );
        //创建索引时创建文档类型映射
        request.mapping(TYPE_IDEA,//类型定义
                "  {\n" +
                        "    \"doc\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"message\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }",//类型映射，需要的是一个JSON字符串
                XContentType.JSON);

        //为索引设置一个别名
        request.alias(new Alias(INDEX_IDEA_HIGH_ALIGN));
        //可选参数
        request.timeout(TimeValue.timeValueMinutes(2));//超时,等待所有节点被确认(使用TimeValue方式)
        //request.timeout("2m");//超时,等待所有节点被确认(使用字符串方式)

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));//连接master节点的超时时间(使用TimeValue方式)
        //request.masterNodeTimeout("1m");//连接master节点的超时时间(使用字符串方式)

        //request.waitForActiveShards(2);//在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示。
        //request.waitForActiveShards(ActiveShardCount.DEFAULT);//在创建索引API返回响应之前等待的活动分片副本的数量，以ActiveShardCount形式表示。

        //同步执行
        CreateIndexResponse createIndexResponse = restClient.indices().create(request, RequestOptions.DEFAULT);
        //异步执行
        //异步执行创建索引请求需要将CreateIndexRequest实例和ActionListener实例传递给异步方法：
        //CreateIndexResponse的典型监听器如下所示：
        //异步方法不会阻塞并立即返回。
//        ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
//            @Override
//            public void onResponse(CreateIndexResponse createIndexResponse) {
//                //返回的CreateIndexResponse允许检索有关执行的操作的信息，如下所示：
//                boolean acknowledged = createIndexResponse.isAcknowledged();//指示是否所有节点都已确认请求
//                boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();//指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
//                //如果执行成功，则调用onResponse方法;
//            }
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//            }
//        };
//        restClient.indices().createAsync(request,RequestOptions.DEFAULT, listener);//要执行的CreateIndexRequest和执行完成时要使用的ActionListener
    }


}
