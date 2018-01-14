/*    */ package weixin.guanjia.core.entity.common;
/*    */ 
/*    */ public class LBSSpace
/*    */ {
/*    */   private String name;
/*    */   private String address;
/*    */   private Location location;
/*    */   private String telphone;
/*    */   private String distance;
/*    */ 
/*    */   public LBSSpace()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LBSSpace(String name, Location location, String address, String telphone, String distance)
/*    */   {
/* 18 */     this.address = address;
/* 19 */     this.name = name;
/* 20 */     this.location = location;
/* 21 */     this.telphone = telphone;
/* 22 */     this.distance = distance;
/*    */   }
/*    */ 
/*    */   public void setAddress(String address) {
/* 26 */     this.address = address;
/*    */   }
/*    */ 
/*    */   public String getAddress() {
/* 30 */     return this.address;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 38 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setLocation(Location location) {
/* 42 */     this.location = location;
/*    */   }
/*    */ 
/*    */   public Location getLocation() {
/* 46 */     return this.location;
/*    */   }
/*    */ 
/*    */   public String getTelphone() {
/* 50 */     return this.telphone;
/*    */   }
/*    */ 
/*    */   public void setTelphone(String telphone) {
/* 54 */     this.telphone = telphone;
/*    */   }
/*    */ 
/*    */   public String getDistance() {
/* 58 */     return this.distance;
/*    */   }
/*    */ 
/*    */   public void setDistance(String distance) {
/* 62 */     this.distance = distance;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.LBSSpace
 * JD-Core Version:    0.6.2
 */