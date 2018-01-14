/*    */ package weixin.guanjia.core.entity.common;
/*    */ 
/*    */ public class Location
/*    */ {
/*    */   private Float lat;
/*    */   private Float lng;
/*    */ 
/*    */   public Location()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Location(Float lat, Float lng)
/*    */   {
/* 12 */     this.lat = lat;
/* 13 */     this.lng = lng;
/*    */   }
/*    */ 
/*    */   public void setLat(Float lat) {
/* 17 */     this.lat = lat;
/*    */   }
/*    */ 
/*    */   public Float getLat() {
/* 21 */     return this.lat;
/*    */   }
/*    */ 
/*    */   public Float getLng() {
/* 25 */     return this.lng;
/*    */   }
/*    */ 
/*    */   public void setLng(Float lng) {
/* 29 */     this.lng = lng;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.Location
 * JD-Core Version:    0.6.2
 */