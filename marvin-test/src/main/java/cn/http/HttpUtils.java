package cn.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

public final class HttpUtils {
    // http请求工具代理对象
    private static final HttpDelegate delegate;
    static {
        delegate = new HttpUtilDelegate();
    }
    private HttpUtils() {}
    public static String get(String url) {
        return delegate.get(url);
    }
    public static String get(String url, Map<String, String> queryParas) {
        return delegate.get(url, queryParas);
    }
    public static String get(String url,String ip,int port){return delegate.get(url, ip, port);};
    public static String post(String url, String data) {
        return delegate.post(url, data);
    }
    public static String post(String url, Map<String, String> queryParas) {
        return delegate.post(url, queryParas);
    }
    public static String post(String url, Map<String, String> queryParas,String type) {
        return delegate.post(url, queryParas,type);
    }
    public static String postSSL(String url, String data, String certPath, String certPass) {
        return delegate.postSSL(url, data, certPath, certPass);
    }

	/*public static InputStream download(String url, String params){
		return delegate.download(url, params);
	}

	public static String upload(String url, File file, String params) {
		return delegate.upload(url, file, params);
	}*/

    /**
     * http请求工具 委托
     * 优先使用OkHttp
     * 最后使用JFinal HttpKit
     */
    private interface HttpDelegate {
        /**
         * get 请求
         * @param url 请求连接
         * */
        String get(String url);
        /**
         * get 请求
         * @param url 请求连接
         * @param queryParas 请求参数
         * */
        String get(String url, Map<String, String> queryParas);

        /**
         * 代理请求
         * @param url
         * @param ip
         * @param port
         * @return
         */
        String get(String url,String ip,int port);

        String post(String url, String data);
        /**
         * post 请求
         * @param url 请求链接
         * @param queryParas 请求参数
         * @return
         * */
        String post(String url, Map<String, String> queryParas);
        String post(String url, Map<String, String> queryParas,String type);

        String postSSL(String url, String data, String certPath, String certPass);
		/*InputStream download(String url, String params);
		String upload(String url, File file, String params);*/
    }

    private static class HttpUtilDelegate implements HttpDelegate {
        @Override
        public String get(String url) {
            // TODO Auto-generated method stub
            return get(url,null);
        }
        @Override
        public String get(String url, Map<String, String> params) {
            url = mapToString(params,url);
            return sendGetRequest(url);
        }

        @Override
        public String get(String url, String ip, int port) {
            return sendGetProxtRequest(url,ip,port);
        }



        @Override
        public String post(String url, String data) {
            return sendPostRequest(url, data,null);
        }
        @Override
        public String post(String url, Map<String, String> params) {
            NameValuePair[] mapToPairs = mapToPairs(params);
            return sendPostRequest(url, mapToPairs, null);
        }
        @Override
        public String post(String url, Map<String, String> params,String type) {
            NameValuePair[] mapToPairs = mapToPairs(params);
            return sendPostRequest(url, mapToPairs, type);
        }
        @Override
        public String postSSL(String url, String data, String certPath, String certPass) {
            // TODO Auto-generated method stub
            return sendSSLPostRequest(url, data, certPath, certPass, null);
        }

        private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
        private final static int CONNECT_TIMEOUT = 4000;// 连接超时毫秒
        private final static int SOCKET_TIMEOUT = 10000;// 传输超时毫秒
        private final static int REQUESTCONNECT_TIMEOUT = 3000;// 获取请求超时毫秒
        private final static int CONNECT_TOTAL = 50;// 最大连接数
        private final static int CONNECT_ROUTE = 5;// 每个路由基础的连接数
        private final static String ENCODE_CHARSET = "UTF-8";// 响应报文解码字符集
        private final static String RESP_CONTENT = "连接失败";
        private static PoolingHttpClientConnectionManager connManager = null;
        private static CloseableHttpClient httpClient = null;
        private static final String CONFIG_SSL_PATH="";
        private static final String CONFIG_SSL_PASSWORD="1441177602";

        static {
			/*ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
			LayeredConnectionSocketFactory sslsf = createSSLConnSocketFactory();*/
			/*Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
														.register("http", plainsf)
														.register("https", sslsf)
														.build();*/

            connManager = new PoolingHttpClientConnectionManager();
            // 最大连接数
            connManager.setMaxTotal(CONNECT_TOTAL);
            // 每个路由基础的连接
            connManager.setDefaultMaxPerRoute(CONNECT_ROUTE);
            // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
            connManager.setValidateAfterInactivity(30000);
            // 设置socket超时时间
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
            connManager.setDefaultSocketConfig(socketConfig);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    /*if (executionCount >= 3) { */ // 如果已经重试了3次，就放弃
                    /*}*/
                    if(true) {return false;}
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return true;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// ssl握手异常
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };
            httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(httpRequestRetryHandler).build();
            if (connManager != null && connManager.getTotalStats() != null) {
                logger.info("now client pool " + connManager.getTotalStats().toString());
            }
        }

        /**
         * 发送HTTP_GET请求
         *
         * @see 1)该方法会自动关闭连接,释放资源
         * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"连接失败"字符串
         * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决 定传入前是否转码
         * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html;charset=UTF-8]的charset值作为响应报文的 解码字符集
         * @see 5)若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符 集
         * @param url  请求地址(含参数)
         * @return 远程主机响应正文
         */
        private static String sendGetRequest(String url) {
            String respContent = RESP_CONTENT; // 响应内容
            //url = URLDecoder.decode(url, ENCODE_CHARSET);
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpget, HttpClientContext.create()); // 执行GET请求
                HttpEntity entity = response.getEntity(); // 获取响应实体
                if (null != entity) {
                    Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                    respContent = EntityUtils.toString(entity, respCharset);
                    EntityUtils.consume(entity);
                }
            } catch (ConnectTimeoutException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (SocketTimeoutException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (ClientProtocolException e) {
                // 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (ParseException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (IOException e) {
                // 该异常通常是网络原因引起的,如HTTP服务器未启动等
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (Exception e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } finally {
                try {
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (httpget != null) {
                    httpget.releaseConnection();
                }
            }
            return respContent;
        }

        private String sendGetProxtRequest(String url, String ip, int port) {
            String respContent = RESP_CONTENT; // 响应内容
            HttpHost proxy = new HttpHost(ip,port);
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(config);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpget, HttpClientContext.create()); // 执行GET请求
                HttpEntity entity = response.getEntity(); // 获取响应实体
                if (null != entity) {
                    Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                    respContent = EntityUtils.toString(entity, respCharset);
                    EntityUtils.consume(entity);
                }
            } catch (ConnectTimeoutException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (SocketTimeoutException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (ClientProtocolException e) {
                // 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (ParseException e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (IOException e) {
                // 该异常通常是网络原因引起的,如HTTP服务器未启动等
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } catch (Exception e) {
                logger.error("get请求异常,url is :{} \t exception is:{}",url,e);
            } finally {
                try {
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (httpget != null) {
                    httpget.releaseConnection();
                }
            }
            return respContent;
        }


        /**
         * 发送HTTP_POST请求 type: 默认是表单请求，
         *
         * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
         * @see 2)该方法会自动关闭连接,释放资源
         * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
         * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自 动对其转码
         * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的charset值
         * @see 6)若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
         * @param reqURL
         *            请求地址
         * @param data
         *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
         * @param type
         *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
         * @return 远程主机响应正文
         */
        public static String sendPostRequest(String reqURL, String data,String type) {
            String result = RESP_CONTENT;
            CloseableHttpResponse response = null;
            try {
                HttpUriRequest httpPost = RequestBuilder.post()
                        .setUri(new URI(reqURL))
                        .setEntity(new StringEntity(data, ENCODE_CHARSET))
                        .build();
                if (type != null && type.length() > 0) {
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=" + ENCODE_CHARSET);
                } else {
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + ENCODE_CHARSET);
                }
                logger.info("开始执行请求：" + reqURL);
                // reqURL = URLDecoder.decode(reqURL, ENCODE_CHARSET);
                response = httpClient.execute(httpPost, HttpClientContext.create());
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                    logger.info("执行请求完毕：" + result);
                    EntityUtils.consume(entity);
                }
            } catch (ConnectTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (SocketTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ClientProtocolException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ParseException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (IOException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (Exception e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } finally {
                try {
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 发送HTTP_POST请求 type: 默认是表单请求，
         *
         * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
         * @see 2)该方法会自动关闭连接,释放资源
         * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
         * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自 动对其转码
         * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的charset值
         * @see 6)若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
         * @param reqURL
         *            请求地址
         * @param mapToPairs
         *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
         * @param type
         *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
         * @return 远程主机响应正文
         */
        public static String sendPostRequest(String reqURL, NameValuePair[] mapToPairs,String type) {
            String result = RESP_CONTENT;
            CloseableHttpResponse response = null;
            try {
                HttpUriRequest httpPost = RequestBuilder.post()
                        .setUri(new URI(reqURL))
                        .addParameters(mapToPairs)
                        .build();
                if (type != null && type.length() > 0) {
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=" + ENCODE_CHARSET);
                } else {
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + ENCODE_CHARSET);
                }
                logger.info("开始执行请求：" + reqURL);
                // reqURL = URLDecoder.decode(reqURL, ENCODE_CHARSET);
                response = httpClient.execute(httpPost, HttpClientContext.create());
                System.out.println(response.getStatusLine().toString());
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                    logger.info("执行请求完毕：" + result);
                    EntityUtils.consume(entity);
                }
            } catch (ConnectTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (SocketTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ClientProtocolException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ParseException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (IOException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (Exception e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } finally {
                try {
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 发送HTTP_POST请求 type: 默认是表单请求，
         *
         * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
         * @see 2)该方法会自动关闭连接,释放资源
         * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
         * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自 动对其转码
         * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的charset值
         * @see 6) 若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
         * @param reqURL
         *            请求地址
         * @param data
         *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
         * @param type
         *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
         * @return 远程主机响应正文
         */
        public static String sendSSLPostRequest(String reqURL, String data,String sslPath,String password,String type) {
            String result = RESP_CONTENT;
            CloseableHttpResponse response = null;
            CloseableHttpClient client=null;
            try {
                KeyStore keyStore  = KeyStore.getInstance("PKCS12");
                FileInputStream instream = new FileInputStream(new File(sslPath));
                keyStore.load(instream, password.toCharArray());
                instream.close();
                SSLContext sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(keyStore, password.toCharArray())
                        .build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[] { "TLSv1" },
                        null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
                client = HttpClients.custom()
                        .setSSLSocketFactory(sslsf)
                        .build();
                HttpUriRequest httpPost = RequestBuilder.post()
                        .setUri(new URI(reqURL))
                        .setEntity(new StringEntity(data, ENCODE_CHARSET))
                        .build();
                logger.info("开始执行请求：" + reqURL);
                // reqURL = URLDecoder.decode(reqURL, ENCODE_CHARSET);
                response = client.execute(httpPost, HttpClientContext.create());
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                    logger.info("执行请求完毕：" + result);
                    EntityUtils.consume(entity);
                }
            } catch (ConnectTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (SocketTimeoutException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ClientProtocolException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (ParseException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (IOException e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } catch (Exception e) {
                logger.error("post请求异常,url is :{} \t exception is:{}",reqURL,e);
            } finally {
                if(null!=client){try {client.close();
                } catch (IOException e) {logger.error("client 关闭失败,exception is : {}",e);}};
                try {if (response != null)response.close();
                } catch (IOException e) {logger.error("response 关闭失败,exception is : {}",e);};
            }
            return result;
        }
        //SSL的socket工厂创建
        private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
            SSLContext sslcontext;
            SSLConnectionSocketFactory sslsf=null;
            try {
                sslcontext = SSLContexts.custom()
                        .loadTrustMaterial(new File(CONFIG_SSL_PATH),CONFIG_SSL_PASSWORD.toCharArray(),
                                new TrustSelfSignedStrategy())
                        .build();
                sslsf = new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[] { "TLSv1" },
                        null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            } catch (KeyManagementException e) {
                logger.error("ssl 配置出错,exception is {}",e);
            } catch (NoSuchAlgorithmException e) {
                logger.error("ssl 配置出错,exception is {}",e);
            } catch (KeyStoreException e) {
                logger.error("ssl 配置出错,exception is {}",e);
            } catch (CertificateException e) {
                logger.error("ssl 配置出错,exception is {}",e);
            } catch (IOException e) {
                logger.error("ssl 配置出错,exception is {}",e);
            }
            return sslsf;
        }

        /**
         * map 转  QueryString url
         * */
        private static String mapToString(Map<String, String> params,String url){
            if(params != null && params.size() > 0) {
                Iterator paramKeys = params.keySet().iterator();
                StringBuffer getUrl = new StringBuffer(url.trim());
                if(url.trim().indexOf("?") > -1) {
                    if(url.trim().indexOf("?") < url.trim().length()-1 && url.trim().indexOf("&")  < url.trim().length()-1) {
                        getUrl.append("&");
                    }
                } else {
                    getUrl.append("?");
                }
                while(paramKeys.hasNext()) {
                    String key = (String)paramKeys.next();
                    Object value = params.get(key);
                    if(value != null && value instanceof String && !value.equals("")) {
                        getUrl.append(key).append("=").append(value).append("&");
                    } else if(value != null && value instanceof String[] &&
                            ((String[])value).length > 0) {
                        for(String v : (String[])value) {
                            getUrl.append(key).append("=").append(v).append("&");
                        }
                    }
                }
                return getUrl.toString();
            }
            return url;
        }
        /***
         * map 转 NameValuePair
         * */
        private static NameValuePair[] mapToPairs(Map<String, String> params){
            NameValuePair[] pairs=new NameValuePair[0];
            if(params != null && params.size() > 0) {
                List<BasicNameValuePair> lists=new ArrayList<>(params.size());
                Iterator paramKeys = params.keySet().iterator();
                int i=0;
                while(paramKeys.hasNext()){
                    String key= (String) paramKeys.next();
                    String data=params.get(key);
                    logger.info("NameValuePair is {}:{}",key,data);
                    if(StringUtils.isNotBlank(key)){
                        lists.add(new BasicNameValuePair(key, params.get(key)));
                    }
                }
                pairs=lists.toArray(new NameValuePair[lists.size()]);
            }
            return pairs;
        }
        public static Map<HttpRoute, PoolStats> getConnManagerStats() {
            if (connManager != null) {
                Set<HttpRoute> routeSet = connManager.getRoutes();
                if (routeSet != null && !routeSet.isEmpty()) {
                    Map<HttpRoute, PoolStats> routeStatsMap = new HashMap<HttpRoute, PoolStats>();
                    for (HttpRoute route : routeSet) {
                        PoolStats stats = connManager.getStats(route);
                        routeStatsMap.put(route, stats);
                    }
                    return routeStatsMap;
                }
            }
            return null;
        }

        public static PoolStats getConnManagerTotalStats() {
            if (connManager != null) {
                return connManager.getTotalStats();
            }
            return null;
        }

        /**
         * 关闭系统时关闭httpClient
         */
        public static void releaseHttpClient() {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常" + e);
            } finally {
                if (connManager != null) {
                    connManager.shutdown();
                }
            }
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
/*       Map<String, String> params=new HashMap<>();
        params.put("req_id", "20180214115427");
        params.put("sec_id", "MD5");
        params.put("req_data","<direct_trade_create_req><notify_url>http://m.green007.cn/lyxz/notify_url</notify_url><call_back_url>http://m.green007.cn/lyxz/call_back_url</call_back_url><seller_account_name>green_yuanyi@163.com</seller_account_name><out_trade_no>E18570475749919</out_trade_no><subject>义务植树</subject><total_fee>0.10</total_fee><merchant_url>http://m.green007.cn</merchant_url></direct_trade_create_req>");
        params.put("partner","2088211671860260");
        params.put("service","alipay.wap.trade.create.direct");
        params.put("_input_charset","utf-8");
        params.put("v","2.0");
        params.put("format","xml");
        params.put("sign","84811e285bea5a938c7ce26f620201a5");
        String url="http://wappaygw.alipay.com/service/rest.htm?";
        String result=HttpUtils.post(url, params);
        result=URLDecoder.decode(result,"utf-8");
        System.out.println(result);*/
        String s = HttpUtils.get("http://47.97.171.192:8087/zyz/medium/off/detail?mediumId=36");
        System.out.printf("--"+s);
    }


}
