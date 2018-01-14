/*    */ package weixin.guanjia.core.entity.common;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class TranslateResult
/*    */ {
/*    */   private String from;
/*    */   private String to;
/*    */   private List<ResultPair> trans_result;
/*    */ 
/*    */   public String getFrom()
/*    */   {
/* 20 */     return this.from;
/*    */   }
/*    */ 
/*    */   public void setFrom(String from) {
/* 24 */     this.from = from;
/*    */   }
/*    */ 
/*    */   public String getTo() {
/* 28 */     return this.to;
/*    */   }
/*    */ 
/*    */   public void setTo(String to) {
/* 32 */     this.to = to;
/*    */   }
/*    */ 
/*    */   public List<ResultPair> getTrans_result() {
/* 36 */     return this.trans_result;
/*    */   }
/*    */ 
/*    */   public void setTrans_result(List<ResultPair> trans_result) {
/* 40 */     this.trans_result = trans_result;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.TranslateResult
 * JD-Core Version:    0.6.2
 */