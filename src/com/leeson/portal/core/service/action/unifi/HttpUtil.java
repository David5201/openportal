package com.leeson.portal.core.service.action.unifi;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil
{
  private static Logger log = Logger.getLogger(HttpUtil.class);
  private static Config config = Config.getInstance();

  public static void main(String[] args) throws Exception {
    login("", 1, "default", "192.168.0.1");
    loginOut("", "default", "192.168.0.1");
  }

  public static boolean login(String mac, int minutes, String site, String basip) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
    CloseableHttpClient httpclient = null;
    try {
      CookieStore cookieStore = new BasicCookieStore();

      HttpClientContext context = HttpClientContext.create();
      context.setCookieStore(cookieStore);

      RequestConfig globalConfig = RequestConfig.custom()
        .setCookieSpec("compatibility").build();

      SSLContext sslcontext = SSLContexts.custom()
        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
        .build();

      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        sslcontext, new String[] { "TLSv1" }, null, 
        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      httpclient = HttpClients.custom()
        .setDefaultRequestConfig(globalConfig)
        .setDefaultCookieStore(cookieStore)
        .setSSLSocketFactory(sslsf).build();

      HttpHost httpHost = new HttpHost(basConfig.getBasIp(), 8443, "https");
      HttpPost httppost = new HttpPost("/api/login");
      String data = "{'login':'login','username':'" + basConfig.getBasUser() + "','password':'" + basConfig.getBasPwd() + "'}";
      StringEntity stringEntity = new StringEntity(data, "UTF-8");
      stringEntity.setContentEncoding("UTF-8");
      stringEntity.setContentType("application/json");
      httppost.setEntity(stringEntity);

      ResponseHandler responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行登录=============");
      String response = (String)httpclient.execute(httpHost, httppost, 
        responseHandler, context);
      System.out.println(response);
      httppost.releaseConnection();

      httppost = new HttpPost("/api/s/" + site + "/cmd/stamgr");
      data = "{'cmd':'authorize-guest','mac':'" + mac + "','minutes':'" + minutes + "'}";
      List formparams = new ArrayList();
      formparams.add(new BasicNameValuePair("json", data));
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, 
        Consts.UTF_8);
      httppost.setEntity(entity);

      responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行授权===========");
      response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
        context);
      System.out.println(response);
      httppost.releaseConnection();

      httppost = new HttpPost("/api/logout");
      responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行退出=============");
      response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
        context);
      System.out.println(response);
      httppost.releaseConnection();
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("连接UniFi服务器,发生错误:" + e.getMessage());
      }
      return false;
    } finally {
      if (httpclient != null) {
        try {
          httpclient.close();
        } catch (IOException e) {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("关闭资源,发生错误:" + e.getMessage());
          }
        }
      }
    }
    return true;
  }

  public static boolean loginOut(String mac, String site, String basip) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get(basip);
    CloseableHttpClient httpclient = null;
    try {
      CookieStore cookieStore = new BasicCookieStore();

      HttpClientContext context = HttpClientContext.create();
      context.setCookieStore(cookieStore);

      RequestConfig globalConfig = RequestConfig.custom()
        .setCookieSpec("compatibility").build();

      SSLContext sslcontext = SSLContexts.custom()
        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
        .build();

      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        sslcontext, new String[] { "TLSv1" }, null, 
        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      httpclient = HttpClients.custom()
        .setDefaultRequestConfig(globalConfig)
        .setDefaultCookieStore(cookieStore)
        .setSSLSocketFactory(sslsf).build();

      HttpHost httpHost = new HttpHost(basConfig.getBasIp(), 8443, "https");
      HttpPost httppost = new HttpPost("/api/login");
      String data = "{'login':'login','username':'" + basConfig.getBasUser() + "','password':'" + basConfig.getBasPwd() + "'}";
      StringEntity stringEntity = new StringEntity(data, "UTF-8");
      stringEntity.setContentEncoding("UTF-8");
      stringEntity.setContentType("application/json");
      httppost.setEntity(stringEntity);

      ResponseHandler responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行登录=============");
      String response = (String)httpclient.execute(httpHost, httppost, 
        responseHandler, context);
      System.out.println(response);
      httppost.releaseConnection();

      httppost = new HttpPost("/api/s/" + site + "/cmd/stamgr");
      data = "{'cmd':'unauthorize-guest','mac':'" + mac + "'}";
      List formparams = new ArrayList();
      formparams.add(new BasicNameValuePair("json", data));
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, 
        Consts.UTF_8);
      httppost.setEntity(entity);

      responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行下线===========");
      response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
        context);
      System.out.println(response);
      httppost.releaseConnection();

      httppost = new HttpPost("/api/logout");
      responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();

          if ((status >= 200) && (status < 303)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : 
              null;
          }
          throw new ClientProtocolException(
            "Unexpected response status: " + status);
        }
      };
      System.out.println("===========执行退出=============");
      response = (String)httpclient.execute(httpHost, httppost, responseHandler, 
        context);
      System.out.println(response);
      httppost.releaseConnection();
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("连接UniFi服务器,发生错误:" + e.getMessage());
      }
      return false;
    } finally {
      if (httpclient != null) {
        try {
          httpclient.close();
        } catch (IOException e) {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("关闭资源,发生错误:" + e.getMessage());
          }
        }
      }
    }
    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.unifi.HttpUtil
 * JD-Core Version:    0.6.2
 */