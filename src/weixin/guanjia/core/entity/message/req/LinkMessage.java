/*    */ package weixin.guanjia.core.entity.message.req;
/*    */ 
/*    */ public class LinkMessage extends BaseMessage
/*    */ {
/*    */   private String Title;
/*    */   private String Description;
/*    */   private String Url;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 18 */     return this.Title;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title) {
/* 22 */     this.Title = title;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 26 */     return this.Description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 30 */     this.Description = description;
/*    */   }
/*    */ 
/*    */   public String getUrl() {
/* 34 */     return this.Url;
/*    */   }
/*    */ 
/*    */   public void setUrl(String url) {
/* 38 */     this.Url = url;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.req.LinkMessage
 * JD-Core Version:    0.6.2
 */