/*    */ package weixin.guanjia.core.entity.message.resp;
/*    */ 
/*    */ public class BaseMessageResp
/*    */ {
/*    */   private String ToUserName;
/*    */   private String FromUserName;
/*    */   private long CreateTime;
/*    */   private String MsgType;
/*    */ 
/*    */   public String getToUserName()
/*    */   {
/* 19 */     return this.ToUserName;
/*    */   }
/*    */   public void setToUserName(String toUserName) {
/* 22 */     this.ToUserName = toUserName;
/*    */   }
/*    */   public String getFromUserName() {
/* 25 */     return this.FromUserName;
/*    */   }
/*    */   public void setFromUserName(String fromUserName) {
/* 28 */     this.FromUserName = fromUserName;
/*    */   }
/*    */   public long getCreateTime() {
/* 31 */     return this.CreateTime;
/*    */   }
/*    */   public void setCreateTime(long createTime) {
/* 34 */     this.CreateTime = createTime;
/*    */   }
/*    */   public String getMsgType() {
/* 37 */     return this.MsgType;
/*    */   }
/*    */   public void setMsgType(String msgType) {
/* 40 */     this.MsgType = msgType;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.resp.BaseMessageResp
 * JD-Core Version:    0.6.2
 */