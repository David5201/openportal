package weixin.entity;

import java.io.Serializable;
import java.util.Date;

public class WeixinAccessToken
  implements Serializable
{
  private static final long serialVersionUID = -6299728470079522663L;
  private Long id;
  private String access_token;
  private int expires_in;
  private Date addTime;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getAccess_token() {
    return this.access_token;
  }
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  public int getExpires_in() {
    return this.expires_in;
  }
  public void setExpires_in(int expires_in) {
    this.expires_in = expires_in;
  }
  public Date getAddTime() {
    return this.addTime;
  }
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public String toString() {
    return "AccessTokenYw [id=" + this.id + ", access_token=" + this.access_token + 
      ", expires_in=" + this.expires_in + ", addTime=" + this.addTime + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.entity.WeixinAccessToken
 * JD-Core Version:    0.6.2
 */