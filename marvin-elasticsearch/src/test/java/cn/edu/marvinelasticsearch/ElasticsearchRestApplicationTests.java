package cn.edu.marvinelasticsearch;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class ElasticsearchRestApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchRestApplicationTests.class);
    RestClient restClient =null;
    @Before
    public void setUp(){
        restClient=RestClient.builder(
                new HttpHost("47.96.107.63", 9200, "http"))
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
                .build();
    }

    @After
    public void endPoint() throws IOException {
       if(null!=restClient) restClient.close();
    }

    @Test
    public void resetClinetGet() throws IOException {
        Request request = new Request(
                "GET",
                "/");
        request.addParameter("pretty", "true");
        request.setEntity(new NStringEntity(
                "{\"json\":\"text\"}",
                ContentType.APPLICATION_JSON));
        Response response = restClient.performRequest(request);
        echoRepose(response);
    }

    @Test
    public void testGetSsynchronous() {
        final CountDownLatch latch = new CountDownLatch(1);
        Request request = new Request(
                "GET",
                "/");
        restClient.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                echoRepose(response);
            }
            @Override
            public void onFailure(Exception e) {
                logger.error("请求出错");
            }
        });
        System.out.printf("-----------");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void echoRepose(Response response){
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        try {
            String responseBody =EntityUtils.toString(response.getEntity());
            System.out.printf(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
