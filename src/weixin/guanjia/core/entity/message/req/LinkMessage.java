package weixin.guanjia.core.entity.message.req;

public class LinkMessage extends BaseMessage
{
  private String Title;
  private String Description;
  private String Url;

  public String getTitle()
  {
    return this.Title;
  }

  public void setTitle(String title) {
    this.Title = title;
  }

  public String getDescription() {
    return this.Description;
  }

  public void setDescription(String description) {
    this.Description = description;
  }

  public String getUrl() {
    return this.Url;
  }

  public void setUrl(String url) {
    this.Url = url;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.req.LinkMessage
 * JD-Core Version:    0.6.2
 */