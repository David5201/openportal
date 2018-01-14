/*     */ package weixin.guanjia.core.entity.common;
/*     */ 
/*     */ public class Face
/*     */   implements Comparable<Face>
/*     */ {
/*     */   private String faceId;
/*     */   private int ageValue;
/*     */   private int ageRange;
/*     */   private String genderValue;
/*     */   private double genderConfidence;
/*     */   private String raceValue;
/*     */   private double raceConfidence;
/*     */   private double smilingValue;
/*     */   private double centerX;
/*     */   private double centerY;
/*     */ 
/*     */   public String getFaceId()
/*     */   {
/*  31 */     return this.faceId;
/*     */   }
/*     */ 
/*     */   public void setFaceId(String faceId) {
/*  35 */     this.faceId = faceId;
/*     */   }
/*     */ 
/*     */   public int getAgeValue() {
/*  39 */     return this.ageValue;
/*     */   }
/*     */ 
/*     */   public void setAgeValue(int ageValue) {
/*  43 */     this.ageValue = ageValue;
/*     */   }
/*     */ 
/*     */   public int getAgeRange() {
/*  47 */     return this.ageRange;
/*     */   }
/*     */ 
/*     */   public void setAgeRange(int ageRange) {
/*  51 */     this.ageRange = ageRange;
/*     */   }
/*     */ 
/*     */   public String getGenderValue() {
/*  55 */     return this.genderValue;
/*     */   }
/*     */ 
/*     */   public void setGenderValue(String genderValue) {
/*  59 */     this.genderValue = genderValue;
/*     */   }
/*     */ 
/*     */   public double getGenderConfidence() {
/*  63 */     return this.genderConfidence;
/*     */   }
/*     */ 
/*     */   public void setGenderConfidence(double genderConfidence) {
/*  67 */     this.genderConfidence = genderConfidence;
/*     */   }
/*     */ 
/*     */   public String getRaceValue() {
/*  71 */     return this.raceValue;
/*     */   }
/*     */ 
/*     */   public void setRaceValue(String raceValue) {
/*  75 */     this.raceValue = raceValue;
/*     */   }
/*     */ 
/*     */   public double getRaceConfidence() {
/*  79 */     return this.raceConfidence;
/*     */   }
/*     */ 
/*     */   public void setRaceConfidence(double raceConfidence) {
/*  83 */     this.raceConfidence = raceConfidence;
/*     */   }
/*     */ 
/*     */   public double getSmilingValue() {
/*  87 */     return this.smilingValue;
/*     */   }
/*     */ 
/*     */   public void setSmilingValue(double smilingValue) {
/*  91 */     this.smilingValue = smilingValue;
/*     */   }
/*     */ 
/*     */   public double getCenterX() {
/*  95 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public void setCenterX(double centerX) {
/*  99 */     this.centerX = centerX;
/*     */   }
/*     */ 
/*     */   public double getCenterY() {
/* 103 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public void setCenterY(double centerY) {
/* 107 */     this.centerY = centerY;
/*     */   }
/*     */ 
/*     */   public int compareTo(Face face)
/*     */   {
/* 112 */     int result = 0;
/* 113 */     if (getCenterX() > face.getCenterX())
/* 114 */       result = 1;
/*     */     else
/* 116 */       result = -1;
/* 117 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.Face
 * JD-Core Version:    0.6.2
 */