/*    */ package weixin.guanjia.core.entity.message.resp;
/*    */ 
/*    */ public class Article
/*    */ {
/*    */   private String Title;
/*    */   private String Description;
/*    */   private String PicUrl;
/*    */   private String Url;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 20 */     return this.Title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 24 */     this.Title = title;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 28 */     return this.Description == null ? "" : this.Description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 32 */     this.Description = description;
/*    */   }
/*    */ 
/*    */   public String getPicUrl() {
/* 36 */     return this.PicUrl == null ? "" : this.PicUrl;
/*    */   }
/*    */ 
/*    */   public void setPicUrl(String picUrl) {
/* 40 */     this.PicUrl = picUrl;
/*    */   }
/*    */ 
/*    */   public String getUrl() {
/* 44 */     return this.Url == null ? "" : this.Url;
/*    */   }
/*    */ 
/*    */   public void setUrl(String url) {
/* 48 */     this.Url = url;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.resp.Article
 * JD-Core Version:    0.6.2
 */