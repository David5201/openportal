package weixin.guanjia.core.util;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import weixin.entity.WeixinAccessToken;
import weixin.guanjia.core.entity.common.AccessToken;
import weixin.service.WeixinAccessTokenService;

public class WeixinUtil
{
  public static final String access_token_url = "https:gi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
  public static String menu_create_url = "https:gi-bin/menu/create?access_token=ACCESS_TOKEN";

  public static String send_message_url = "https:gi-binustom/send?access_token=ACCESS_TOKEN";

  private static Logger log = Logger.getLogger(WeixinUtil.class);
  private static Config config = Config.getInstance();

  public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    JSONObject jsonObject = null;
    StringBuffer buffer = new StringBuffer();
    try
    {
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());

      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
      httpUrlConn.setSSLSocketFactory(ssf);

      httpUrlConn.setDoOutput(true);
      httpUrlConn.setDoInput(true);
      httpUrlConn.setUseCaches(false);

      httpUrlConn.setRequestMethod(requestMethod);

      if ("GET".equalsIgnoreCase(requestMethod)) {
        httpUrlConn.connect();
      }

      if (outputStr != null) {
        OutputStream outputStream = httpUrlConn.getOutputStream();

        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }

      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();

      inputStream.close();
      inputStream = null;
      httpUrlConn.disconnect();
      jsonObject = JSONObject.fromObject(buffer.toString());
    }
    catch (ConnectException ce) {
      if (basConfig.getIsdebug().equals("1"))
        log.info("Weixin server connection timed out.");
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("https request error:{}" + e.getMessage());
      }
    }
    return jsonObject;
  }

  public static AccessToken getAccessToken(WeixinAccessTokenService weixinAccessTokenService, String appid, String appsecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    WeixinAccessToken accessTocken = getRealAccessToken(weixinAccessTokenService);

    if (accessTocken.getAccess_token() != null) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("==========is have tocken============" + accessTocken);
      }
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date end = new Date();
      Date oldATTime = new Date(accessTocken.getAddTime().getTime() + accessTocken.getExpires_in() * 1000);
      String oldTime = format.format(oldATTime);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("have tocken to Date: " + oldTime);
      }
      if (end.getTime() - accessTocken.getAddTime().getTime() > accessTocken.getExpires_in() * 1000) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("have tocken is out of Date !");
        }
        AccessToken accessToken = null;
        String requestUrl = "https:gi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
          try {
            accessToken = new AccessToken();
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));

            WeixinAccessToken at = new WeixinAccessToken();
            at.setId(Long.valueOf(1L));
            at.setAddTime(new Date());
            at.setExpires_in(jsonObject.getInt("expires_in"));
            at.setAccess_token(jsonObject.getString("access_token"));
            updateAccessToken(at, weixinAccessTokenService);
            if (basConfig.getIsdebug().equals("1"))
              log.info("Try get AccessToken : " + at);
          }
          catch (Exception e)
          {
            accessToken = null;

            String wrongMessage = "Get token error errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
            if (basConfig.getIsdebug().equals("1")) {
              log.info(wrongMessage);
            }
          }
        }
        return accessToken;
      }

      AccessToken accessToken = new AccessToken();
      accessToken.setToken(accessTocken.getAccess_token());
      accessToken.setExpiresIn(accessTocken.getExpires_in());
      return accessToken;
    }

    AccessToken accessToken = null;
    String requestUrl = "https:gi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

    if (jsonObject != null) {
      try {
        accessToken = new AccessToken();
        accessToken.setToken(jsonObject.getString("access_token"));
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));

        WeixinAccessToken at = new WeixinAccessToken();
        at.setId(Long.valueOf(1L));
        at.setAddTime(new Date());
        at.setExpires_in(jsonObject.getInt("expires_in"));
        at.setAccess_token(jsonObject.getString("access_token"));
        updateAccessToken(at, weixinAccessTokenService);
        if (basConfig.getIsdebug().equals("1"))
          log.info("Get token First , AccessToken : " + at);
      }
      catch (Exception e) {
        accessToken = null;

        String wrongMessage = "Get token error errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
        if (basConfig.getIsdebug().equals("1")) {
          log.info(wrongMessage);
        }
      }
    }
    return accessToken;
  }

  private static WeixinAccessToken getRealAccessToken(WeixinAccessTokenService weixinAccessTokenService)
  {
    List accessTockenList = weixinAccessTokenService.getAccessTockens();
    return (WeixinAccessToken)accessTockenList.get(0);
  }

  private static void saveAccessToken(WeixinAccessToken accessTocken, WeixinAccessTokenService weixinAccessTokenService)
  {
    weixinAccessTokenService.save(accessTocken);
  }

  private static void updateAccessToken(WeixinAccessToken accessTocken, WeixinAccessTokenService weixinAccessTokenService)
  {
    weixinAccessTokenService.updateAccessToken(accessTocken);
  }

  public static String encode(byte[] bstr)
  {
    return new BASE64Encoder().encode(bstr);
  }

  public static byte[] decode(String str)
  {
    byte[] bt = null;
    try {
      BASE64Decoder decoder = new BASE64Decoder();
      bt = decoder.decodeBuffer(str);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bt;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.WeixinUtil
 * JD-Core Version:    0.6.2
 */