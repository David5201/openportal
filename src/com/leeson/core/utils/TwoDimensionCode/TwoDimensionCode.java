package com.leeson.core.utils.TwoDimensionCode;

import com.swetake.util.Qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class TwoDimensionCode
{
  private static String imgType = "png";
  private static int size = 7;
  private String codeType = "utf-8";

  private int imageWidth = 30;
  private int imageHeight = 30;

  public void encoderQRCode(String content, String imgPath, String imgType, int size)
  {
    try
    {
      BufferedImage bufImg = qRCodeCommon(content, imgType, size);

      File imgFile = new File(imgPath);

      ImageIO.write(bufImg, imgType, imgFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void encoderQRCode(String content, OutputStream output, String imgType, int size)
  {
    try
    {
      BufferedImage bufImg = qRCodeCommon(content, imgType, size);

      ImageIO.write(bufImg, imgType, output);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private BufferedImage qRCodeCommon(String content, String imgType, int size)
  {
    BufferedImage bufImg = null;
    try {
      Qrcode qrcodeHandler = new Qrcode();

      qrcodeHandler.setQrcodeErrorCorrect('M');
      qrcodeHandler.setQrcodeEncodeMode('B');

      qrcodeHandler.setQrcodeVersion(size);

      byte[] contentBytes = content.toString().getBytes(this.codeType);

      int imgSize = 67 + 12 * (size - 1);
      bufImg = new BufferedImage(imgSize, imgSize, 1);
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
        throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
      }
      gs.dispose();
      bufImg.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bufImg;
  }

  public String decoderQRCode(String imgPath)
  {
    File imageFile = new File(imgPath);
    BufferedImage bufImg = null;
    String content = null;
    try {
      bufImg = ImageIO.read(imageFile);
      QRCodeDecoder decoder = new QRCodeDecoder();
      content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), this.codeType);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    } catch (DecodingFailedException dfe) {
      System.out.println("Error: " + dfe.getMessage());
      dfe.printStackTrace();
    }
    return content;
  }

  public String decoderQRCode(InputStream input)
  {
    BufferedImage bufImg = null;
    String content = null;
    try {
      bufImg = ImageIO.read(input);
      QRCodeDecoder decoder = new QRCodeDecoder();
      content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), this.codeType);
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    } catch (DecodingFailedException dfe) {
      System.out.println("Error: " + dfe.getMessage());
      dfe.printStackTrace();
    }
    return content;
  }

  private BufferedImage createPhotoAtCenter(BufferedImage bufImg)
    throws Exception
  {
    Image im = ImageIO.read(new File("d:/youku.jpg"));
    Graphics2D g = bufImg.createGraphics();

    int centerX = bufImg.getMinX() + bufImg.getWidth() / 2 - this.imageWidth / 2;
    int centerY = bufImg.getMinY() + bufImg.getHeight() / 2 - this.imageHeight / 2;
    g.drawImage(im, centerX, centerY, this.imageWidth, this.imageHeight, null);
    g.dispose();
    bufImg.flush();
    return bufImg;
  }

  public static void main(String[] args)
    throws IOException
  {
    String imgPath = "d:/a.jpg";

    String encoderContent = "13663865897";

    TwoDimensionCode handler = new TwoDimensionCode();

    handler.encoderQRCode(encoderContent, imgPath, imgType, size);

    String decoderContent = handler.decoderQRCode(imgPath);

    System.out.println("解析结果如下：");

    System.out.println(decoderContent);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.TwoDimensionCode.TwoDimensionCode
 * JD-Core Version:    0.6.2
 */