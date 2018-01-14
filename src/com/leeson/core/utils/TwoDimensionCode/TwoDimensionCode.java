/*     */ package com.leeson.core.utils.TwoDimensionCode;
/*     */ 
/*     */ import com.swetake.util.Qrcode;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import jp.sourceforge.qrcode.QRCodeDecoder;
/*     */ import jp.sourceforge.qrcode.exception.DecodingFailedException;
/*     */ 
/*     */ public class TwoDimensionCode
/*     */ {
/*  24 */   private static String imgType = "png";
/*  25 */   private static int size = 7;
/*  26 */   private String codeType = "utf-8";
/*     */ 
/*  29 */   private int imageWidth = 30;
/*  30 */   private int imageHeight = 30;
/*     */ 
/*     */   public void encoderQRCode(String content, String imgPath, String imgType, int size)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       BufferedImage bufImg = qRCodeCommon(content, imgType, size);
/*     */ 
/* 128 */       File imgFile = new File(imgPath);
/*     */ 
/* 130 */       ImageIO.write(bufImg, imgType, imgFile);
/*     */     } catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encoderQRCode(String content, OutputStream output, String imgType, int size)
/*     */   {
/*     */     try
/*     */     {
/* 149 */       BufferedImage bufImg = qRCodeCommon(content, imgType, size);
/*     */ 
/* 156 */       ImageIO.write(bufImg, imgType, output);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 160 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private BufferedImage qRCodeCommon(String content, String imgType, int size)
/*     */   {
/* 183 */     BufferedImage bufImg = null;
/*     */     try {
/* 185 */       Qrcode qrcodeHandler = new Qrcode();
/*     */ 
/* 187 */       qrcodeHandler.setQrcodeErrorCorrect('M');
/* 188 */       qrcodeHandler.setQrcodeEncodeMode('B');
/*     */ 
/* 190 */       qrcodeHandler.setQrcodeVersion(size);
/*     */ 
/* 192 */       byte[] contentBytes = content.toString().getBytes(this.codeType);
/*     */ 
/* 194 */       int imgSize = 67 + 12 * (size - 1);
/* 195 */       bufImg = new BufferedImage(imgSize, imgSize, 1);
/* 196 */       Graphics2D gs = bufImg.createGraphics();
/*     */ 
/* 198 */       gs.setBackground(Color.WHITE);
/* 199 */       gs.clearRect(0, 0, imgSize, imgSize);
/*     */ 
/* 201 */       gs.setColor(Color.BLACK);
/*     */ 
/* 203 */       int pixoff = 2;
/*     */ 
/* 205 */       if ((contentBytes.length > 0) && (contentBytes.length < 800)) {
/* 206 */         boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
/* 207 */         for (int i = 0; i < codeOut.length; i++) {
/* 208 */           for (int j = 0; j < codeOut.length; j++)
/* 209 */             if (codeOut[j][i] != false)
/* 210 */               gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 215 */         throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
/*     */       }
/* 217 */       gs.dispose();
/* 218 */       bufImg.flush();
/*     */     } catch (Exception e) {
/* 220 */       e.printStackTrace();
/*     */     }
/* 222 */     return bufImg;
/*     */   }
/*     */ 
/*     */   public String decoderQRCode(String imgPath)
/*     */   {
/* 237 */     File imageFile = new File(imgPath);
/* 238 */     BufferedImage bufImg = null;
/* 239 */     String content = null;
/*     */     try {
/* 241 */       bufImg = ImageIO.read(imageFile);
/* 242 */       QRCodeDecoder decoder = new QRCodeDecoder();
/* 243 */       content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), this.codeType);
/*     */     } catch (IOException e) {
/* 245 */       System.out.println("Error: " + e.getMessage());
/* 246 */       e.printStackTrace();
/*     */     } catch (DecodingFailedException dfe) {
/* 248 */       System.out.println("Error: " + dfe.getMessage());
/* 249 */       dfe.printStackTrace();
/*     */     }
/* 251 */     return content;
/*     */   }
/*     */ 
/*     */   public String decoderQRCode(InputStream input)
/*     */   {
/* 262 */     BufferedImage bufImg = null;
/* 263 */     String content = null;
/*     */     try {
/* 265 */       bufImg = ImageIO.read(input);
/* 266 */       QRCodeDecoder decoder = new QRCodeDecoder();
/* 267 */       content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), this.codeType);
/*     */     } catch (IOException e) {
/* 269 */       System.out.println("Error: " + e.getMessage());
/* 270 */       e.printStackTrace();
/*     */     } catch (DecodingFailedException dfe) {
/* 272 */       System.out.println("Error: " + dfe.getMessage());
/* 273 */       dfe.printStackTrace();
/*     */     }
/* 275 */     return content;
/*     */   }
/*     */ 
/*     */   private BufferedImage createPhotoAtCenter(BufferedImage bufImg)
/*     */     throws Exception
/*     */   {
/* 285 */     Image im = ImageIO.read(new File("d:/youku.jpg"));
/* 286 */     Graphics2D g = bufImg.createGraphics();
/*     */ 
/* 288 */     int centerX = bufImg.getMinX() + bufImg.getWidth() / 2 - this.imageWidth / 2;
/* 289 */     int centerY = bufImg.getMinY() + bufImg.getHeight() / 2 - this.imageHeight / 2;
/* 290 */     g.drawImage(im, centerX, centerY, this.imageWidth, this.imageHeight, null);
/* 291 */     g.dispose();
/* 292 */     bufImg.flush();
/* 293 */     return bufImg;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 299 */     String imgPath = "d:/a.jpg";
/*     */ 
/* 302 */     String encoderContent = "13663865897";
/*     */ 
/* 306 */     TwoDimensionCode handler = new TwoDimensionCode();
/*     */ 
/* 308 */     handler.encoderQRCode(encoderContent, imgPath, imgType, size);
/*     */ 
/* 312 */     String decoderContent = handler.decoderQRCode(imgPath);
/*     */ 
/* 314 */     System.out.println("解析结果如下：");
/*     */ 
/* 316 */     System.out.println(decoderContent);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.TwoDimensionCode.TwoDimensionCode
 * JD-Core Version:    0.6.2
 */