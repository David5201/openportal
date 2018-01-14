/*    */ package weixin.service.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Service;
/*    */ import weixin.entity.AccessTokensMap;
/*    */ import weixin.entity.WeixinAccessToken;
/*    */ import weixin.service.WeixinAccessTokenService;
/*    */ 
/*    */ @Service
/*    */ public class WeixinAccessTokenServiceImpl
/*    */   implements WeixinAccessTokenService
/*    */ {
/*    */   public void save(WeixinAccessToken accessTocken)
/*    */   {
/* 17 */     AccessTokensMap.getInstance().setAccessTocken(accessTocken);
/*    */   }
/*    */ 
/*    */   public List<WeixinAccessToken> getAccessTockens()
/*    */   {
/* 22 */     WeixinAccessToken token = AccessTokensMap.getInstance().getAccessTocken();
/* 23 */     List tokens = new ArrayList();
/* 24 */     tokens.add(token);
/* 25 */     return tokens;
/*    */   }
/*    */ 
/*    */   public void updateAccessToken(WeixinAccessToken accessTocken)
/*    */   {
/* 30 */     AccessTokensMap.getInstance().setAccessTocken(accessTocken);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinAccessTokenServiceImpl
 * JD-Core Version:    0.6.2
 */