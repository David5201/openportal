package com.leeson.portal.core.service.h3c;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
  private byte[] md5Buffer;

  public void update(byte[] paramArrayOfByte)
  {
    int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
    int j = paramArrayOfByte == null ? 0 : paramArrayOfByte.length;
    byte[] arrayOfByte = new byte[i + j];
    if (i != 0)
      System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
    if (j != 0)
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, i, j);
    this.md5Buffer = arrayOfByte;
  }

  public void update(byte paramByte)
  {
    int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
    byte[] arrayOfByte = new byte[i + 1];
    if (i != 0)
      System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
    arrayOfByte[i] = paramByte;
    this.md5Buffer = arrayOfByte;
  }

  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2)
      return;
    int i = this.md5Buffer == null ? 0 : this.md5Buffer.length;
    int j = paramArrayOfByte == null ? 0 : paramInt2 - paramInt1;
    byte[] arrayOfByte = new byte[i + j];
    if (i != 0)
      System.arraycopy(this.md5Buffer, 0, arrayOfByte, 0, i);
    if (j != 0)
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, i, j);
    this.md5Buffer = arrayOfByte;
  }

  public byte[] finalFunc()
  {
    MessageDigest localMessageDigest = null;
    if (this.md5Buffer == null)
      return null;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    if (localMessageDigest != null)
    {
      localMessageDigest.update(this.md5Buffer);
      return localMessageDigest.digest();
    }
    return null;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.MD5
 * JD-Core Version:    0.6.2
 */