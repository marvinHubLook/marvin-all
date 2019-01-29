package cn.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/10/29 10:45
 **/
public class QueryTest {
    public final static String HOST = "192.168.10.58";
    // http请求的端口是9200，客户端是9300
    public final static int PORT = 9300;

    private TransportClient client = null;

    @Before
    public void getConnection() throws Exception {
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "nmtx-cluster").build();
        // 创建client
       /* client = new PreBuiltTransportClient(settings)addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));*/
    }

   /* private void testQuery(){
        QueryBuilder qb = termQuery("multi", "test");
       *//* SearchResponse scrollResp = client.prepareSearch(test)
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get();*//* //max of 100 hits will be returned for each scroll
//Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
    }*/
}
