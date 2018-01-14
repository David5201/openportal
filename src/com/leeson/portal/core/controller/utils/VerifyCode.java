package com.leeson.portal.core.controller.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class VerifyCode
{
  private int w = 70;
  private int h = 35;
  private Random r = new Random();

  private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };

  private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

  private Color bgColor = new Color(255, 255, 255);
  private String text;

  private Color randomColor()
  {
    int red = this.r.nextInt(150);
    int green = this.r.nextInt(150);
    int blue = this.r.nextInt(150);
    return new Color(red, green, blue);
  }

  private Font randomFont()
  {
    int index = this.r.nextInt(this.fontNames.length);
    String fontName = this.fontNames[index];
    int style = this.r.nextInt(4);
    int size = this.r.nextInt(5) + 24;
    return new Font(fontName, style, size);
  }

  private void drawLine(BufferedImage image)
  {
    int num = 3;
    Graphics2D g2 = (Graphics2D)image.getGraphics();
    for (int i = 0; i < num; i++) {
      int x1 = this.r.nextInt(this.w);
      int y1 = this.r.nextInt(this.h);
      int x2 = this.r.nextInt(this.w);
      int y2 = this.r.nextInt(this.h);
      g2.setStroke(new BasicStroke(1.5F));
      g2.setColor(Color.BLUE);
      g2.drawLine(x1, y1, x2, y2);
    }
  }

  private char randomChar()
  {
    int index = this.r.nextInt(this.codes.length());
    return this.codes.charAt(index);
  }

  private BufferedImage createImage()
  {
    BufferedImage image = new BufferedImage(this.w, this.h, 
      1);
    Graphics2D g2 = (Graphics2D)image.getGraphics();
    g2.setColor(this.bgColor);
    g2.fillRect(0, 0, this.w, this.h);
    return image;
  }

  public BufferedImage getImage()
  {
    BufferedImage image = createImage();
    Graphics2D g2 = (Graphics2D)image.getGraphics();
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 4; i++) {
      String s = randomChar()+"";
      sb.append(s);
      float x = i * 1.0F * this.w / 4.0F;
      g2.setFont(randomFont());
      g2.setColor(randomColor());
      g2.drawString(s, x, this.h - 5);
    }
    this.text = sb.toString();
    drawLine(image);
    return image;
  }

  public String getText()
  {
    return this.text;
  }

  public static void output(BufferedImage image, OutputStream out)
    throws IOException
  {
    ImageIO.write(image, "JPEG", out);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.utils.VerifyCode
 * JD-Core Version:    0.6.2
 */