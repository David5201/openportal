package weixin.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import weixin.entity.AccessTokensMap;
import weixin.entity.WeixinAccessToken;
import weixin.service.WeixinAccessTokenService;

@Service
public class WeixinAccessTokenServiceImpl
  implements WeixinAccessTokenService
{
  public void save(WeixinAccessToken accessTocken)
  {
    AccessTokensMap.getInstance().setAccessTocken(accessTocken);
  }

  public List<WeixinAccessToken> getAccessTockens()
  {
    WeixinAccessToken token = AccessTokensMap.getInstance().getAccessTocken();
    List tokens = new ArrayList();
    tokens.add(token);
    return tokens;
  }

  public void updateAccessToken(WeixinAccessToken accessTocken)
  {
    AccessTokensMap.getInstance().setAccessTocken(accessTocken);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinAccessTokenServiceImpl
 * JD-Core Version:    0.6.2
 */