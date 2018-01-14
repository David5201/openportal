/*    */ package weixin.guanjia.core.entity.message.req;
/*    */ 
/*    */ public class BaseMessage
/*    */ {
/*    */   private String ToUserName;
/*    */   private String FromUserName;
/*    */   private long CreateTime;
/*    */   private String MsgType;
/*    */   private long MsgId;
/*    */ 
/*    */   public String getToUserName()
/*    */   {
/* 22 */     return this.ToUserName;
/*    */   }
/*    */ 
/*    */   public void setToUserName(String toUserName) {
/* 26 */     this.ToUserName = toUserName;
/*    */   }
/*    */ 
/*    */   public String getFromUserName() {
/* 30 */     return this.FromUserName;
/*    */   }
/*    */ 
/*    */   public void setFromUserName(String fromUserName) {
/* 34 */     this.FromUserName = fromUserName;
/*    */   }
/*    */ 
/*    */   public long getCreateTime() {
/* 38 */     return this.CreateTime;
/*    */   }
/*    */ 
/*    */   public void setCreateTime(long createTime) {
/* 42 */     this.CreateTime = createTime;
/*    */   }
/*    */ 
/*    */   public String getMsgType() {
/* 46 */     return this.MsgType;
/*    */   }
/*    */ 
/*    */   public void setMsgType(String msgType) {
/* 50 */     this.MsgType = msgType;
/*    */   }
/*    */ 
/*    */   public long getMsgId() {
/* 54 */     return this.MsgId;
/*    */   }
/*    */ 
/*    */   public void setMsgId(long msgId) {
/* 58 */     this.MsgId = msgId;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.req.BaseMessage
 * JD-Core Version:    0.6.2
 */