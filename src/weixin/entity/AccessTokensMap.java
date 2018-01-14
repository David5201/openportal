package weixin.entity;

import java.io.Serializable;

public class AccessTokensMap
  implements Serializable
{
  private static final long serialVersionUID = -4969660566777371344L;
  private WeixinAccessToken accessTocken = new WeixinAccessToken();

  private static AccessTokensMap instance = new AccessTokensMap();

  public WeixinAccessToken getAccessTocken()
  {
    return this.accessTocken;
  }

  public void setAccessTocken(WeixinAccessToken accessToken) {
    this.accessTocken = accessToken;
  }

  public static AccessTokensMap getInstance()
  {
    return instance;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.entity.AccessTokensMap
 * JD-Core Version:    0.6.2
 */