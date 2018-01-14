package com.wxpay.utils.http;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class HttpConnect
{
  private static HttpConnect httpConnect = new HttpConnect();

  MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

  public static HttpConnect getInstance()
  {
    return httpConnect;
  }

  public HttpResponse doGetStr(String url)
  {
    String CONTENT_CHARSET = "GBK";
    long time1 = System.currentTimeMillis();
    HttpClient client = new HttpClient(this.connectionManager);
    client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
    client.getHttpConnectionManager().getParams().setSoTimeout(55000);
    client.getParams().setParameter("http.protocol.content-charset", CONTENT_CHARSET);
    HttpMethod method = new GetMethod(url);
    HttpResponse response = new HttpResponse();
    try {
      client.executeMethod(method);
      System.out.println("调接口返回的时间:" + (System.currentTimeMillis() - time1));
      response.setStringResult(method.getResponseBodyAsString());
    } catch (HttpException e) {
      method.releaseConnection();
      return null;
    } catch (IOException e) {
      method.releaseConnection();
      return null;
    } finally {
      method.releaseConnection(); } method.releaseConnection();

    return response;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.HttpConnect
 * JD-Core Version:    0.6.2
 */