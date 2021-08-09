package com.qzsoft.tah.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author aq
 * @version 1.0 2021/7/3
 */
public class HttpClientsUtils {

    public static HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public static String postUrl(String url , Map<String,String> paramMap){
        CloseableHttpClient httpClient = (CloseableHttpClient)wrapClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try{
            List<NameValuePair> formalparams = new ArrayList<>();
            Iterator<Map.Entry<String,String>> iterator =  paramMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                formalparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formalparams,"utf-8");
            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = httpResponse.getEntity();
                if(httpEntity != null){
                    result = EntityUtils.toString(httpEntity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpResponse.close();
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String get(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try{
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                result = EntityUtils.toString(httpEntity);
            }
            httpResponse.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
