/*    */ package weixin.guanjia.core.entity.message.req;
/*    */ 
/*    */ public class LocationMessage extends BaseMessage
/*    */ {
/*    */   private String Location_X;
/*    */   private String Location_Y;
/*    */   private String Scale;
/*    */   private String Label;
/*    */ 
/*    */   public String getLocation_X()
/*    */   {
/* 20 */     return this.Location_X;
/*    */   }
/*    */ 
/*    */   public void setLocation_X(String location_X) {
/* 24 */     this.Location_X = location_X;
/*    */   }
/*    */ 
/*    */   public String getLocation_Y() {
/* 28 */     return this.Location_Y;
/*    */   }
/*    */ 
/*    */   public void setLocation_Y(String location_Y) {
/* 32 */     this.Location_Y = location_Y;
/*    */   }
/*    */ 
/*    */   public String getScale() {
/* 36 */     return this.Scale;
/*    */   }
/*    */ 
/*    */   public void setScale(String scale) {
/* 40 */     this.Scale = scale;
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 44 */     return this.Label;
/*    */   }
/*    */ 
/*    */   public void setLabel(String label) {
/* 48 */     this.Label = label;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.message.req.LocationMessage
 * JD-Core Version:    0.6.2
 */