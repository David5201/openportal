package weixin.service;

import java.util.List;
import weixin.entity.WeixinAccessToken;

public abstract interface WeixinAccessTokenService
{
  public abstract void save(WeixinAccessToken paramWeixinAccessToken);

  public abstract List<WeixinAccessToken> getAccessTockens();

  public abstract void updateAccessToken(WeixinAccessToken paramWeixinAccessToken);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.WeixinAccessTokenService
 * JD-Core Version:    0.6.2
 */