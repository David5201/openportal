/*    */ package weixin.guanjia.core.entity.message.customer;
/*    */ 
/*    */ public abstract class BaseMessage
/*    */ {
/*    */   private String touser;
/*    */   private String msgtype;
/*    */ 
/*    */   public String getTouser()
/*    */   {
/*  8 */     return this.touser;
/*    */   }
/*    */ 
/*    */   public void setTouser(String touser) {
/* 12 */     this.touser = touser;
/*    */   }
/*    */ 
/*    */   public String getMsgtype() {
/* 16 */     return this.msgtype;
/*    */   }
/*    */ 
/*    */   public void setMsgtype(String msgtype) {
/* 20 */     this.msgtype = msgtype;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.customer.BaseMessage
 * JD-Core Version:    0.6.2
 */