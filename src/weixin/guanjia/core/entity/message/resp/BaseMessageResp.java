package weixin.guanjia.core.entity.message.resp;

public class BaseMessageResp
{
  private String ToUserName;
  private String FromUserName;
  private long CreateTime;
  private String MsgType;

  public String getToUserName()
  {
    return this.ToUserName;
  }
  public void setToUserName(String toUserName) {
    this.ToUserName = toUserName;
  }
  public String getFromUserName() {
    return this.FromUserName;
  }
  public void setFromUserName(String fromUserName) {
    this.FromUserName = fromUserName;
  }
  public long getCreateTime() {
    return this.CreateTime;
  }
  public void setCreateTime(long createTime) {
    this.CreateTime = createTime;
  }
  public String getMsgType() {
    return this.MsgType;
  }
  public void setMsgType(String msgType) {
    this.MsgType = msgType;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.resp.BaseMessageResp
 * JD-Core Version:    0.6.2
 */