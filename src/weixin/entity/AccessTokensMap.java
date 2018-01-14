/*    */ package weixin.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class AccessTokensMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4969660566777371344L;
/* 18 */   private WeixinAccessToken accessTocken = new WeixinAccessToken();
/*    */ 
/* 28 */   private static AccessTokensMap instance = new AccessTokensMap();
/*    */ 
/*    */   public WeixinAccessToken getAccessTocken()
/*    */   {
/* 21 */     return this.accessTocken;
/*    */   }
/*    */ 
/*    */   public void setAccessTocken(WeixinAccessToken accessToken) {
/* 25 */     this.accessTocken = accessToken;
/*    */   }
/*    */ 
/*    */   public static AccessTokensMap getInstance()
/*    */   {
/* 34 */     return instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.entity.AccessTokensMap
 * JD-Core Version:    0.6.2
 */