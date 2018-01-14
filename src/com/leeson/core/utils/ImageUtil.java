/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.CropImageFilter;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageFilter;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ public class ImageUtil
/*     */ {
/*     */   public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean equalProportion)
/*     */   {
/*  30 */     int type = source.getType();
/*  31 */     BufferedImage target = null;
/*  32 */     double sx = targetW / source.getWidth();
/*  33 */     double sy = targetH / source.getHeight();
/*     */ 
/*  35 */     if (equalProportion) {
/*  36 */       if (sx < sy) {
/*  37 */         sx = sy;
/*  38 */         targetW = (int)(sx * source.getWidth());
/*     */       } else {
/*  40 */         sy = sx;
/*  41 */         targetH = (int)(sx * source.getHeight());
/*     */       }
/*     */     }
/*  44 */     if (type == 0) {
/*  45 */       ColorModel cm = source.getColorModel();
/*  46 */       WritableRaster raster = cm.createCompatibleWritableRaster(targetW, 
/*  47 */         targetH);
/*  48 */       boolean alphaPremultiplied = cm.isAlphaPremultiplied();
/*  49 */       target = new BufferedImage(cm, raster, alphaPremultiplied, null);
/*     */     } else {
/*  51 */       target = new BufferedImage(targetW, targetH, type);
/*  52 */       Graphics2D g = target.createGraphics();
/*  53 */       g.setRenderingHint(RenderingHints.KEY_RENDERING, 
/*  54 */         RenderingHints.VALUE_RENDER_QUALITY);
/*  55 */       g.drawRenderedImage(source, 
/*  56 */         AffineTransform.getScaleInstance(sx, sy));
/*  57 */       g.dispose();
/*     */     }
/*  59 */     return target;
/*     */   }
/*     */ 
/*     */   public static Map<String, Integer> getImageWH(String imgSource) throws FileNotFoundException, IOException
/*     */   {
/*  64 */     File picture = new File(imgSource);
/*  65 */     BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
/*     */ 
/*  67 */     Map map = new HashMap();
/*  68 */     map.put("w", Integer.valueOf(sourceImg.getWidth()));
/*  69 */     map.put("h", Integer.valueOf(sourceImg.getHeight()));
/*  70 */     map.put("size", Integer.valueOf((int)picture.length() / 1024));
/*  71 */     return map;
/*     */   }
/*     */ 
/*     */   public static Map<String, Integer> getImgWH(String imgSource) throws FileNotFoundException, IOException
/*     */   {
/*  76 */     Map map = new HashMap();
/*     */     try {
/*  78 */       File picture = new File(imgSource);
/*  79 */       String suffix = imgSource.substring(imgSource.lastIndexOf(".") + 1);
/*  80 */       Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
/*  81 */       ImageReader reader = (ImageReader)readers.next();
/*  82 */       ImageInputStream iis = ImageIO.createImageInputStream(picture);
/*  83 */       reader.setInput(iis, true);
/*     */ 
/*  85 */       map.put("w", Integer.valueOf(reader.getWidth(0)));
/*  86 */       map.put("h", Integer.valueOf(reader.getHeight(0)));
/*  87 */       map.put("size", Integer.valueOf((int)picture.length() / 1024));
/*  88 */       return map;
/*     */     } catch (IOException e) {
/*  90 */       e.printStackTrace();
/*     */     }
/*  92 */     return map;
/*     */   }
/*     */ 
/*     */   public static boolean cutImage(int x, int y, int width, int height, String srcImagePath, String newImagePath)
/*     */   {
/*  97 */     File imageFile = new File(srcImagePath);
/*  98 */     BufferedImage bi = null;
/*  99 */     ImageFilter cropFilter = null;
/* 100 */     Image img = null;
/*     */     try {
/* 102 */       bi = ImageIO.read(imageFile);
/* 103 */       int srcWidth = bi.getWidth();
/* 104 */       int srcHeight = bi.getHeight();
/* 105 */       if ((srcWidth >= width) && (srcHeight >= height)) {
/* 106 */         Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
/* 107 */         cropFilter = new CropImageFilter(x, y, width, height);
/* 108 */         img = Toolkit.getDefaultToolkit().createImage(
/* 109 */           new FilteredImageSource(image.getSource(), cropFilter));
/* 110 */         BufferedImage tag = new BufferedImage(width, height, 1);
/* 111 */         Graphics g = tag.getGraphics();
/* 112 */         g.drawImage(img, 0, 0, null);
/* 113 */         g.dispose();
/* 114 */         return ImageIO.write(tag, "JPEG", new File(newImagePath));
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 119 */       return false;
/*     */     }
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   public static int newImageUrl(String imageUrl, int imageMinWidth, int imageMinHeight) throws FileNotFoundException, IOException
/*     */   {
/* 126 */     Map m = getImgWH(imageUrl);
/* 127 */     if ((m != null) && (m.get("w") != null) && (m.get("h") != null)) {
/* 128 */       BufferedImage newSrcImage = null;
/* 129 */       if (((Integer)m.get("w")).intValue() < imageMinWidth) {
/* 130 */         BufferedImage srcImage = ImageIO.read(new File(imageUrl));
/* 131 */         newSrcImage = resize(srcImage, imageMinWidth + 50, 
/* 132 */           ((Integer)m.get("h")).intValue(), true);
/* 133 */         File saveFile = new File(imageUrl);
/* 134 */         ImageIO.write(newSrcImage, "jpg", saveFile);
/* 135 */       } else if (((Integer)m.get("h")).intValue() < imageMinHeight) {
/* 136 */         BufferedImage srcImage = ImageIO.read(new File(imageUrl));
/* 137 */         newSrcImage = resize(srcImage, 
/* 138 */           ((Integer)m.get("w")).intValue(), imageMinHeight + 50, 
/* 139 */           true);
/* 140 */         File saveFile = new File(imageUrl);
/* 141 */         ImageIO.write(newSrcImage, "jpg", saveFile);
/*     */       }
/*     */ 
/* 144 */       if (((Integer)m.get("w")).intValue() / 
/* 145 */         ((Integer)m.get("h")).intValue() != imageMinWidth / 
/* 146 */         imageMinHeight)
/*     */       {
/* 148 */         return 1;
/*     */       }
/* 150 */       return 0;
/*     */     }
/*     */ 
/* 153 */     return -1;
/*     */   }
/*     */ 
/*     */   public static int checkImgHW(String imageUrl, int imageMinWidth, int imageMinHeight) throws FileNotFoundException, IOException
/*     */   {
/* 158 */     Map m = getImgWH(imageUrl);
/* 159 */     if ((m != null) && (m.get("w") != null) && (m.get("h") != null)) {
/* 160 */       if ((((Integer)m.get("w")).intValue() == imageMinWidth) && (((Integer)m.get("h")).intValue() == imageMinHeight)) {
/* 161 */         return 0;
/*     */       }
/*     */ 
/* 164 */       return 1;
/*     */     }
/*     */ 
/* 167 */     return -1;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.ImageUtil
 * JD-Core Version:    0.6.2
 */