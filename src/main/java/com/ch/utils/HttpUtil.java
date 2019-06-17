package com.ch.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtil {

    public static Logger logger = LogManager.getLogger(HttpUtil.class);

    private static final int CONNECT_TIMEOUT = 3 * 1000;    // 连接主机服务超时时间

    private static final int REQUEST_TIMEOUT = 3 * 1000;    // 请求超时时间

    private static final int SOCKET_TIMEOUT = 5 * 1000;     // 数据读取超时时间

    /**
     * GET请求
     *
     */
    public static String doGet(String url) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 创建httpClient实例
            httpClient = HttpClients.createDefault();

            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);

            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(REQUEST_TIMEOUT)// 请求超时时间
                    .setSocketTimeout(SOCKET_TIMEOUT)// 数据读取超时时间
                    .build();

            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);

            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);

            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();

            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * POST请求
     *
     */
    public static String doPost(String url, String data) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpPost post = new HttpPost(url);
        String result = "";
        try (CloseableHttpClient closeableHttpClient = httpClientBuilder.build()) {
            // 设置编码
            HttpEntity entity = new StringEntity(data, "UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            HttpResponse resp = closeableHttpClient.execute(post);
            try {
                InputStream respIs = resp.getEntity().getContent();
                byte[] respBytes = IOUtils.toByteArray(respIs);
                result = new String(respBytes, Charset.forName("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * POST请求(表单提交)
     *
     *
     */
    public static String doPostForm(String url, Map<String, Object> paramMap) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try{
            // 创建httpClient实例
            httpClient = HttpClients.createDefault();

            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);

            // 配置请求参数实例
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();

            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);

            // 设置请求头
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            // 封装post请求参数
            if (null != paramMap && paramMap.size() > 0) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
                Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> mapEntry = iterator.next();
                    nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
                }

                // 为httpPost设置封装好的请求参数
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);

            // 通过返回对象获取返回数据
            HttpEntity entity = httpResponse.getEntity();

            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
