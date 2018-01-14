package weixin.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import weixin.entity.WeixinAccount;

public abstract interface WeixinAccountService
{
  public abstract List<WeixinAccount> getList(HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.WeixinAccountService
 * JD-Core Version:    0.6.2
 */