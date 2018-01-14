/*    */ package weixin.guanjia.core.entity.message.req;
/*    */ 
/*    */ public class VoiceMessage extends BaseMessage
/*    */ {
/*    */   private String MediaId;
/*    */   private String Format;
/*    */ 
/*    */   public String getMediaId()
/*    */   {
/* 16 */     return this.MediaId;
/*    */   }
/*    */ 
/*    */   public void setMediaId(String mediaId) {
/* 20 */     this.MediaId = mediaId;
/*    */   }
/*    */ 
/*    */   public String getFormat() {
/* 24 */     return this.Format;
/*    */   }
/*    */ 
/*    */   public void setFormat(String format) {
/* 28 */     this.Format = format;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.req.VoiceMessage
 * JD-Core Version:    0.6.2
 */