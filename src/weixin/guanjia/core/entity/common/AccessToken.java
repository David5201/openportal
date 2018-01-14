package weixin.guanjia.core.entity.common;

public class AccessToken
{
  private String token;
  private int expiresIn;

  public String getToken()
  {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getExpiresIn() {
    return this.expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.AccessToken
 * JD-Core Version:    0.6.2
 */