package com.leeson.core.utils.TwoDimensionCode;

import com.swetake.util.Qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class makeImg
{
  public static void main(String[] args)
  {
	  make("http://www.openportal.com.cn", "d:/1.png", "d:/a.jpg");
  }

  public static int make(String content, String logo, String imgPath) {
    try {
      Qrcode qrcodeHandler = new Qrcode();

      qrcodeHandler.setQrcodeErrorCorrect('M');
      qrcodeHandler.setQrcodeEncodeMode('B');

      int size = 10;
      int imgSize = 67 + 12 * (size - 1);
      int pos = imgSize / 2 - 20;
      qrcodeHandler.setQrcodeVersion(size);

      byte[] contentBytes = content.getBytes("utf-8");

      BufferedImage bufImg = new BufferedImage(imgSize, imgSize, 1);
      Graphics2D gs = bufImg.createGraphics();

      gs.setBackground(Color.WHITE);
      gs.clearRect(0, 0, imgSize, imgSize);

      gs.setColor(Color.BLACK);

      int pixoff = 2;

      if ((contentBytes.length > 0) && (contentBytes.length < 800)) {
        boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
        for (int i = 0; i < codeOut.length; i++) {
          for (int j = 0; j < codeOut.length; j++)
            if (codeOut[j][i] != false)
              gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
        }
      }
      else
      {
        System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,800 ]. ");
        return -1;
      }

      Image img = ImageIO.read(new File(logo));
      gs.drawImage(img, pos, pos, 40, 40, null);
      gs.dispose();
      bufImg.flush();

      File imgFile = new File(imgPath);
      ImageIO.write(bufImg, "jpg", imgFile);
    } catch (Exception e) {
      e.printStackTrace();
      return -100;
    }
    return 1;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.TwoDimensionCode.makeImg
 * JD-Core Version:    0.6.2
 */