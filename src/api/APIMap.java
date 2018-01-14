/*    */ package api;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class APIMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7285326997979887068L;
/* 10 */   private HashMap<String, String> apiMap = new HashMap();
/*    */ 
/* 12 */   private static APIMap instance = new APIMap();
/*    */ 
/*    */   public static APIMap getInstance()
/*    */   {
/* 18 */     return instance;
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getApiMap() {
/* 22 */     return this.apiMap;
/*    */   }
/*    */ 
/*    */   public void setApiMap(HashMap<String, String> apiMap) {
/* 26 */     this.apiMap = apiMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.APIMap
 * JD-Core Version:    0.6.2
 */