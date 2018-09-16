package com.ch.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtil {
    public static Logger logger = LogManager.getLogger(HttpUtil.class);
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * GET 请求
     * @param url
     * @return
     */
    public static String get(URL url){
        String theString = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "utf-8");
            theString = writer.toString();
            writer.close();
            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }

        return theString;
    }

    /**
     * POST 请求
     * @param url
     * @param data
     * @return
     */
    public static String post(URL url, String data){
        String theString=null;
        HttpURLConnection connection = null;
        try {
            trustAllHosts();
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(3000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(data.getBytes("UTF-8"));
            out.flush();
            out.close();

            InputStream inputStream = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "utf-8");
            theString = writer.toString();
            writer.close();
            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }catch(Exception e){
            logger.error(e);
        }finally{
            connection.disconnect();
        }

        return theString;
    }
}
