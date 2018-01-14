/*     */ package com.leeson.portal.core.controller.utils;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Random;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ public class VerifyCode
/*     */ {
/*  22 */   private int w = 70;
/*  23 */   private int h = 35;
/*  24 */   private Random r = new Random();
/*     */ 
/*  26 */   private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
/*     */ 
/*  28 */   private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
/*     */ 
/*  30 */   private Color bgColor = new Color(255, 255, 255);
/*     */   private String text;
/*     */ 
/*     */   private Color randomColor()
/*     */   {
/*  36 */     int red = this.r.nextInt(150);
/*  37 */     int green = this.r.nextInt(150);
/*  38 */     int blue = this.r.nextInt(150);
/*  39 */     return new Color(red, green, blue);
/*     */   }
/*     */ 
/*     */   private Font randomFont()
/*     */   {
/*  44 */     int index = this.r.nextInt(this.fontNames.length);
/*  45 */     String fontName = this.fontNames[index];
/*  46 */     int style = this.r.nextInt(4);
/*  47 */     int size = this.r.nextInt(5) + 24;
/*  48 */     return new Font(fontName, style, size);
/*     */   }
/*     */ 
/*     */   private void drawLine(BufferedImage image)
/*     */   {
/*  53 */     int num = 3;
/*  54 */     Graphics2D g2 = (Graphics2D)image.getGraphics();
/*  55 */     for (int i = 0; i < num; i++) {
/*  56 */       int x1 = this.r.nextInt(this.w);
/*  57 */       int y1 = this.r.nextInt(this.h);
/*  58 */       int x2 = this.r.nextInt(this.w);
/*  59 */       int y2 = this.r.nextInt(this.h);
/*  60 */       g2.setStroke(new BasicStroke(1.5F));
/*  61 */       g2.setColor(Color.BLUE);
/*  62 */       g2.drawLine(x1, y1, x2, y2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private char randomChar()
/*     */   {
/*  68 */     int index = this.r.nextInt(this.codes.length());
/*  69 */     return this.codes.charAt(index);
/*     */   }
/*     */ 
/*     */   private BufferedImage createImage()
/*     */   {
/*  74 */     BufferedImage image = new BufferedImage(this.w, this.h, 
/*  75 */       1);
/*  76 */     Graphics2D g2 = (Graphics2D)image.getGraphics();
/*  77 */     g2.setColor(this.bgColor);
/*  78 */     g2.fillRect(0, 0, this.w, this.h);
/*  79 */     return image;
/*     */   }
/*     */ 
/*     */   public BufferedImage getImage()
/*     */   {
/*  84 */     BufferedImage image = createImage();
/*  85 */     Graphics2D g2 = (Graphics2D)image.getGraphics();
/*  86 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*  88 */     for (int i = 0; i < 4; i++) {
/*  89 */       String s = randomChar()+"";
/*  90 */       sb.append(s);
/*  91 */       float x = i * 1.0F * this.w / 4.0F;
/*  92 */       g2.setFont(randomFont());
/*  93 */       g2.setColor(randomColor());
/*  94 */       g2.drawString(s, x, this.h - 5);
/*     */     }
/*  96 */     this.text = sb.toString();
/*  97 */     drawLine(image);
/*  98 */     return image;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 103 */     return this.text;
/*     */   }
/*     */ 
/*     */   public static void output(BufferedImage image, OutputStream out)
/*     */     throws IOException
/*     */   {
/* 109 */     ImageIO.write(image, "JPEG", out);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.utils.VerifyCode
 * JD-Core Version:    0.6.2
 */