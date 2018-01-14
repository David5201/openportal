/*    */ package weixin.guanjia.core.entity.message.resp;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class NewsMessageResp extends BaseMessageResp
/*    */ {
/*    */   private int ArticleCount;
/*    */   private List<Article> Articles;
/*    */ 
/*    */   public int getArticleCount()
/*    */   {
/* 12 */     return this.ArticleCount;
/*    */   }
/*    */ 
/*    */   public void setArticleCount(int articleCount) {
/* 16 */     this.ArticleCount = articleCount;
/*    */   }
/*    */ 
/*    */   public List<Article> getArticles() {
/* 20 */     return this.Articles;
/*    */   }
/*    */ 
/*    */   public void setArticles(List<Article> articles) {
/* 24 */     this.Articles = articles;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.resp.NewsMessageResp
 * JD-Core Version:    0.6.2
 */