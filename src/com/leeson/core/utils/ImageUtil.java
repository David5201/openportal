package com.leeson.core.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil
{
  public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean equalProportion)
  {
    int type = source.getType();
    BufferedImage target = null;
    double sx = targetW / source.getWidth();
    double sy = targetH / source.getHeight();

    if (equalProportion) {
      if (sx < sy) {
        sx = sy;
        targetW = (int)(sx * source.getWidth());
      } else {
        sy = sx;
        targetH = (int)(sx * source.getHeight());
      }
    }
    if (type == 0) {
      ColorModel cm = source.getColorModel();
      WritableRaster raster = cm.createCompatibleWritableRaster(targetW, 
        targetH);
      boolean alphaPremultiplied = cm.isAlphaPremultiplied();
      target = new BufferedImage(cm, raster, alphaPremultiplied, null);
    } else {
      target = new BufferedImage(targetW, targetH, type);
      Graphics2D g = target.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_RENDERING, 
        RenderingHints.VALUE_RENDER_QUALITY);
      g.drawRenderedImage(source, 
        AffineTransform.getScaleInstance(sx, sy));
      g.dispose();
    }
    return target;
  }

  public static Map<String, Integer> getImageWH(String imgSource) throws FileNotFoundException, IOException
  {
    File picture = new File(imgSource);
    BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));

    Map map = new HashMap();
    map.put("w", Integer.valueOf(sourceImg.getWidth()));
    map.put("h", Integer.valueOf(sourceImg.getHeight()));
    map.put("size", Integer.valueOf((int)picture.length() / 1024));
    return map;
  }

  public static Map<String, Integer> getImgWH(String imgSource) throws FileNotFoundException, IOException
  {
    Map map = new HashMap();
    try {
      File picture = new File(imgSource);
      String suffix = imgSource.substring(imgSource.lastIndexOf(".") + 1);
      Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
      ImageReader reader = (ImageReader)readers.next();
      ImageInputStream iis = ImageIO.createImageInputStream(picture);
      reader.setInput(iis, true);

      map.put("w", Integer.valueOf(reader.getWidth(0)));
      map.put("h", Integer.valueOf(reader.getHeight(0)));
      map.put("size", Integer.valueOf((int)picture.length() / 1024));
      return map;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return map;
  }

  public static boolean cutImage(int x, int y, int width, int height, String srcImagePath, String newImagePath)
  {
    File imageFile = new File(srcImagePath);
    BufferedImage bi = null;
    ImageFilter cropFilter = null;
    Image img = null;
    try {
      bi = ImageIO.read(imageFile);
      int srcWidth = bi.getWidth();
      int srcHeight = bi.getHeight();
      if ((srcWidth >= width) && (srcHeight >= height)) {
        Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
        cropFilter = new CropImageFilter(x, y, width, height);
        img = Toolkit.getDefaultToolkit().createImage(
          new FilteredImageSource(image.getSource(), cropFilter));
        BufferedImage tag = new BufferedImage(width, height, 1);
        Graphics g = tag.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return ImageIO.write(tag, "JPEG", new File(newImagePath));
      }
    }
    catch (IOException e)
    {
      return false;
    }
    return false;
  }

  public static int newImageUrl(String imageUrl, int imageMinWidth, int imageMinHeight) throws FileNotFoundException, IOException
  {
    Map m = getImgWH(imageUrl);
    if ((m != null) && (m.get("w") != null) && (m.get("h") != null)) {
      BufferedImage newSrcImage = null;
      if (((Integer)m.get("w")).intValue() < imageMinWidth) {
        BufferedImage srcImage = ImageIO.read(new File(imageUrl));
        newSrcImage = resize(srcImage, imageMinWidth + 50, 
          ((Integer)m.get("h")).intValue(), true);
        File saveFile = new File(imageUrl);
        ImageIO.write(newSrcImage, "jpg", saveFile);
      } else if (((Integer)m.get("h")).intValue() < imageMinHeight) {
        BufferedImage srcImage = ImageIO.read(new File(imageUrl));
        newSrcImage = resize(srcImage, 
          ((Integer)m.get("w")).intValue(), imageMinHeight + 50, 
          true);
        File saveFile = new File(imageUrl);
        ImageIO.write(newSrcImage, "jpg", saveFile);
      }

      if (((Integer)m.get("w")).intValue() / 
        ((Integer)m.get("h")).intValue() != imageMinWidth / 
        imageMinHeight)
      {
        return 1;
      }
      return 0;
    }

    return -1;
  }

  public static int checkImgHW(String imageUrl, int imageMinWidth, int imageMinHeight) throws FileNotFoundException, IOException
  {
    Map m = getImgWH(imageUrl);
    if ((m != null) && (m.get("w") != null) && (m.get("h") != null)) {
      if ((((Integer)m.get("w")).intValue() == imageMinWidth) && (((Integer)m.get("h")).intValue() == imageMinHeight)) {
        return 0;
      }

      return 1;
    }

    return -1;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.ImageUtil
 * JD-Core Version:    0.6.2
 */