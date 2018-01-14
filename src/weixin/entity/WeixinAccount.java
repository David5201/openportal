/*    */ package weixin.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WeixinAccount
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -5017448386429976932L;
/*    */   private String accounttoken;
/*    */   private String appid;
/*    */   private String appsecret;
/*    */   private String encodingAESKey;
/*    */ 
/*    */   public String getAccounttoken()
/*    */   {
/* 16 */     return this.accounttoken;
/*    */   }
/*    */   public void setAccounttoken(String accounttoken) {
/* 19 */     this.accounttoken = accounttoken;
/*    */   }
/*    */   public String getAppid() {
/* 22 */     return this.appid;
/*    */   }
/*    */   public void setAppid(String appid) {
/* 25 */     this.appid = appid;
/*    */   }
/*    */   public String getAppsecret() {
/* 28 */     return this.appsecret;
/*    */   }
/*    */   public void setAppsecret(String appsecret) {
/* 31 */     this.appsecret = appsecret;
/*    */   }
/*    */   public String getEncodingAESKey() {
/* 34 */     return this.encodingAESKey;
/*    */   }
/*    */   public void setEncodingAESKey(String encodingAESKey) {
/* 37 */     this.encodingAESKey = encodingAESKey;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 41 */     return "WeixinAccount [accounttoken=" + this.accounttoken + ", appid=" + 
/* 42 */       this.appid + ", appsecret=" + this.appsecret + ", encodingAESKey=" + 
/* 43 */       this.encodingAESKey + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.entity.WeixinAccount
 * JD-Core Version:    0.6.2
 */