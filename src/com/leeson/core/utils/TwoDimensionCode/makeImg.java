/*    */ package com.leeson.core.utils.TwoDimensionCode;
/*    */ 
/*    */ import com.swetake.util.Qrcode;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ public class makeImg
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 15 */     make("http://www.openportal.com.cn", "d:/1.png", "d:/a.jpg");
/*    */   }
/*    */ 
/*    */   public static int make(String content, String logo, String imgPath) {
/*    */     try {
/* 20 */       Qrcode qrcodeHandler = new Qrcode();
/*    */ 
/* 22 */       qrcodeHandler.setQrcodeErrorCorrect('M');
/* 23 */       qrcodeHandler.setQrcodeEncodeMode('B');
/*    */ 
/* 25 */       int size = 10;
/* 26 */       int imgSize = 67 + 12 * (size - 1);
/* 27 */       int pos = imgSize / 2 - 20;
/* 28 */       qrcodeHandler.setQrcodeVersion(size);
/*    */ 
/* 30 */       byte[] contentBytes = content.getBytes("utf-8");
/*    */ 
/* 33 */       BufferedImage bufImg = new BufferedImage(imgSize, imgSize, 1);
/* 34 */       Graphics2D gs = bufImg.createGraphics();
/*    */ 
/* 36 */       gs.setBackground(Color.WHITE);
/* 37 */       gs.clearRect(0, 0, imgSize, imgSize);
/*    */ 
/* 39 */       gs.setColor(Color.BLACK);
/*    */ 
/* 41 */       int pixoff = 2;
/*    */ 
/* 43 */       if ((contentBytes.length > 0) && (contentBytes.length < 800)) {
/* 44 */         boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
/* 45 */         for (int i = 0; i < codeOut.length; i++) {
/* 46 */           for (int j = 0; j < codeOut.length; j++)
/* 47 */             if (codeOut[j][i] != false)
/* 48 */               gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 53 */         System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,800 ]. ");
/* 54 */         return -1;
/*    */       }
/*    */ 
/* 57 */       Image img = ImageIO.read(new File(logo));
/* 58 */       gs.drawImage(img, pos, pos, 40, 40, null);
/* 59 */       gs.dispose();
/* 60 */       bufImg.flush();
/*    */ 
/* 62 */       File imgFile = new File(imgPath);
/* 63 */       ImageIO.write(bufImg, "jpg", imgFile);
/*    */     } catch (Exception e) {
/* 65 */       e.printStackTrace();
/* 66 */       return -100;
/*    */     }
/* 68 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.TwoDimensionCode.makeImg
 * JD-Core Version:    0.6.2
 */