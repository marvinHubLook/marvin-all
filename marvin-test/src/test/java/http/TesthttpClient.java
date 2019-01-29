package http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: marvin-all
 * @description: httpClient 测试
 * @author: Mr.Wang
 * @create: 2018-12-11 17:06
 **/
public class TesthttpClient {

    /**
     * 最简单 测试
     */
    @Test
    public  void test1() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://httpbin.org/");
            System.out.println("Executing request " + httpget.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }

    }

    /**
     * 共享上下文
     */
    @Test
    public  void test2() throws IOException {
        CloseableHttpClient httpclient=null;
        try {
            httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                            .setSocketTimeout(1000)
                            .setConnectTimeout(1000)
                            .build();
            // Create a local instance of cookie store
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setCookieStore(cookieStore);


            HttpGet httpget1 = new HttpGet("http://localhost:8080/http/context1");
            httpget1.setConfig(requestConfig);
            CloseableHttpResponse response1 = httpclient.execute(httpget1, localContext);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response1.getStatusLine());
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("Local cookie: " + cookies.get(i));
                }
                EntityUtils.consume(response1.getEntity());
            } finally {
                response1.close();
            }

            HttpGet httpget2 = new HttpGet("http://localhost:8080/http/context2");
            CloseableHttpResponse response2 = httpclient.execute(httpget2, localContext);
            try {
                HttpEntity entity2 = response2.getEntity();
            } finally {
                response2.close();
            }
        }finally {
            httpclient.close();
        }


    }

    /**
     * http 拦截器
     */
    @Test
    public void test3() throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom()
            .addInterceptorLast(new HttpRequestInterceptor() {
                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws IOException {
                    AtomicInteger count = (AtomicInteger) context.getAttribute("count");
                    request.addHeader("Count", Integer.toString(count.getAndIncrement()));
                }

            })
            .build();

        AtomicInteger count = new AtomicInteger(1);
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute("count", count);

        HttpGet httpget = new HttpGet("http://localhost:8080/http/interceptor");
        for (int i = 0; i < 10; i++) {
            CloseableHttpResponse response = httpclient.execute(httpget, localContext);
            try {
                HttpEntity entity = response.getEntity();
            } finally {
                response.close();
            }
        }
        httpclient.close();
    }

    /**
     * 请求重试
     * 默认 : StandardHttpRequestRetryHandler
     */
    @Test
    public void test4(){
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if(executionCount>= 5){
                    //如果超过最大重试次数
                }
                if(exception instanceof InterruptedIOException){
                    //超时
                    return false;
                }
                if(exception instanceof UnknownHostException){
                    //未知主机
                    return false;
                }
                if(exception instanceof ConnectTimeoutException){
                    //拒绝连接
                    return false;
                }
                if(exception instanceof SSLException){
                    // SSL握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if(idempotent){
                    //如果请求被认为是幂等的，则重试
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRetryHandler(myRetryHandler)
                .build();

    }

    /**
     * 重定向
     */
    @Test
    public void test5() throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("http://localhost:8080/http/redirect");
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try {
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
        } finally {
            response.close();
        }
        httpclient.close();
    }

    /**
     * 自动重定向
     */
    @Test
    public void test6() throws IOException {
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(redirectStrategy)
                .build();
        HttpGet httpget = new HttpGet("http://localhost:8080/http/redirect");
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println(EntityUtils.toString(entity));
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpclient.close();
        }
    }

    /**
     *
     */
    @Test
    public void test7(){
        /**
         * MinimalHttpClient:
         * HttpExecutionAware： 一个类可能发生blocking I/O可以继承这个接口，这样当blocking I/O被取消的时候，继承这个接口的类会收到通知。
         * HttpRequestWrapper：HttpRequest的包装类，可以保证在修改Request的时候原始请求对象不会被改变。
         * HttpRoute：指示对方服务器地址。HttpRoutePlanner用来创建HttpRoute。后者代表客户端request的对端服务器，主要包含rout的host以及proxy信息。
         * RequestConfig：一些与HTTP请求相关的基础设置。
         *
         *
         * MinimalClientExec:
         * ConnectionRequest：是由ConnectionManager来管理的Request。
         * HttpClientConnection：Http连接，用于做请求发送和响应收取。
         * HttpContext：存储KV型的数据。主要用于HTTP请求过程中，多个逻辑过程之间的数据共享。
         * ImmutableHttpProcessor：是HttpProcessor的一个实现，从这个接口的名字就可以看出来，为的是处理Http协议。其中包含了多个协议拦截器，分开处理HTTP协议中的不同部分，这是责任链模式的一个典范实现。单纯到ImmutableHttpProcessor，就是一系列HttpRequestInterceptor 和 HttpResponseInterceptor。然后按照次序调用这些拦截器，处理Request或者Response这两个过程
         *
         */



    }

    /**
     * fluent API
     * 啊啊啊，Credentials不知道适用场景啊
     */
    @Test
    public void test8() throws IOException {
        Executor executor = Executor.newInstance()
                .auth(new HttpHost("somehost"), "name", "password")
                .auth(new HttpHost("localhost", 8080), "name", "password")
                .authPreemptive(new HttpHost("myproxy", 8080));

        executor.execute(Request.Post("http://localhost:8080/http/login"))
                .returnContent().asString();

        /*executor.execute(Request.Post("http://localhost:8080/http/login")
                .useExpectContinue()
                .bodyString("Important stuff", ContentType.DEFAULT_TEXT))
                .returnContent().asString();*/
    }
}
