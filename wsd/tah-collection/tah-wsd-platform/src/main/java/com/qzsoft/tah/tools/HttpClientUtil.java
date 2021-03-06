 package com.qzsoft.tah.tools;

 import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.NameValuePair;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.CloseableHttpResponse;
 import org.apache.http.client.methods.HttpGet;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.client.methods.HttpUriRequest;
 import org.apache.http.client.utils.URIBuilder;
 import org.apache.http.entity.ContentType;
 import org.apache.http.entity.StringEntity;
 import org.apache.http.impl.client.CloseableHttpClient;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.impl.client.HttpClients;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.util.EntityUtils;

 import javax.validation.constraints.NotNull;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.net.URI;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;


 public class HttpClientUtil
 {
     public static String doGet(String url, Map<String, String> param) {
         CloseableHttpClient httpclient = HttpClients.createDefault();
         String resultString = "";
         CloseableHttpResponse response = null;
         try {
             URIBuilder builder = new URIBuilder(url);
             if (param != null)
                 for (String key : param.keySet())
                     builder.addParameter(key, param.get(key));
             URI uri = builder.build();
             HttpGet httpGet = new HttpGet(uri);
             response = httpclient.execute((HttpUriRequest)httpGet);
             if (response.getStatusLine().getStatusCode() == 200)
                 resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 if (response != null)
                     response.close();
                 httpclient.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return resultString;
     }

     public static String doGet(String url) {
         return doGet(url, null);
     }

     public static String doPost(String url, Map<String, String> param) {
         CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = null;
         String resultString = "";
         try {
             HttpPost httpPost = new HttpPost(url);
             if (param != null) {
                 List<NameValuePair> paramList = new ArrayList<>();
                 for (String key : param.keySet())
                     paramList.add(new BasicNameValuePair(key, param.get(key)));
                 UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                 httpPost.setEntity((HttpEntity)entity);
             }
             response = httpClient.execute((HttpUriRequest)httpPost);
             resultString = EntityUtils.toString(response.getEntity(), "utf-8");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 response.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return resultString;
     }

     public static String doPost(String url) {
         return doPost(url, null);
     }

     public static String doPostJson(String url, String json) {
         CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = null;
         String resultString = "";
         try {
             HttpPost httpPost = new HttpPost(url);
             StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
             httpPost.setEntity((HttpEntity)entity);
             response = httpClient.execute((HttpUriRequest)httpPost);
             resultString = EntityUtils.toString(response.getEntity(), "utf-8");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 response.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return resultString;
     }

     /**
      * post?????? ?????????????????????body???
      * @param url    ????????????
      * @param params  ??????
      * @author zhaoHongwei
      * @date 2019???4???20???
      */
     public static String doPostBodyData(String url, @NotNull Map<String,Object> params){
         CloseableHttpClient httpClient = HttpClients.createDefault();
         HttpPost httpPost = new HttpPost(url);
         //??????header
         httpPost.addHeader("X-Easymi-AppCode", "AppCode");
         httpPost.addHeader("X-Easymi-UserName", "UserName");
         //??????body
         List list = new ArrayList();
         Set<String> keySet = params.keySet();
         for (String key:keySet) {
             list.add(new BasicNameValuePair(key, params.get(key).toString()));
         }
         try {
             httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         //??????post??????
         HttpResponse response = null;
         try {
             response = httpClient.execute(httpPost);
             return EntityUtils.toString(response.getEntity(), "utf-8");
         }catch (Exception e) {
             throw new RuntimeException("??????????????????????????????http??????,??????post????????????", e);
         }
     }

 }
