package weixin.entity;

import java.io.Serializable;

public class WeixinAccount
  implements Serializable
{
  private static final long serialVersionUID = -5017448386429976932L;
  private String accounttoken;
  private String appid;
  private String appsecret;
  private String encodingAESKey;

  public String getAccounttoken()
  {
    return this.accounttoken;
  }
  public void setAccounttoken(String accounttoken) {
    this.accounttoken = accounttoken;
  }
  public String getAppid() {
    return this.appid;
  }
  public void setAppid(String appid) {
    this.appid = appid;
  }
  public String getAppsecret() {
    return this.appsecret;
  }
  public void setAppsecret(String appsecret) {
    this.appsecret = appsecret;
  }
  public String getEncodingAESKey() {
    return this.encodingAESKey;
  }
  public void setEncodingAESKey(String encodingAESKey) {
    this.encodingAESKey = encodingAESKey;
  }

  public String toString() {
    return "WeixinAccount [accounttoken=" + this.accounttoken + ", appid=" + 
      this.appid + ", appsecret=" + this.appsecret + ", encodingAESKey=" + 
      this.encodingAESKey + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.entity.WeixinAccount
 * JD-Core Version:    0.6.2
 */