package weixin.guanjia.core.entity.message.resp;

public class Music
{
  private String Title;
  private String Description;
  private String MusicUrl;
  private String HQMusicUrl;
  private String ThumbMediaId;

  public String getThumbMediaId()
  {
    return this.ThumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    this.ThumbMediaId = thumbMediaId;
  }

  public String getTitle() {
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

  public String getMusicUrl() {
    return this.MusicUrl;
  }

  public void setMusicUrl(String musicUrl) {
    this.MusicUrl = musicUrl;
  }

  public String getHQMusicUrl() {
    return this.HQMusicUrl;
  }

  public void setHQMusicUrl(String musicUrl) {
    this.HQMusicUrl = musicUrl;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.resp.Music
 * JD-Core Version:    0.6.2
 */