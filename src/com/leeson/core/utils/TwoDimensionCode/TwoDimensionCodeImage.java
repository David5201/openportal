package com.leeson.core.utils.TwoDimensionCode;

import java.awt.image.BufferedImage;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class TwoDimensionCodeImage
  implements QRCodeImage
{
  BufferedImage bufImg;

  public TwoDimensionCodeImage(BufferedImage bufImg)
  {
    this.bufImg = bufImg;
  }

  public int getHeight() {
    return this.bufImg.getHeight();
  }

  public int getPixel(int x, int y) {
    return this.bufImg.getRGB(x, y);
  }

  public int getWidth() {
    return this.bufImg.getWidth();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.TwoDimensionCode.TwoDimensionCodeImage
 * JD-Core Version:    0.6.2
 */