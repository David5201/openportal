package com.leeson.portal.core.service.h3c;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;
import org.apache.log4j.Logger;

public class PacketBuilder
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(PacketBuilder.class);
  private Number remoteIp;
  private int remotePort;
  private byte headVer = 2;
  private byte headType = 1;
  private byte headChapOrPap = 0;
  private byte headReserved = 0;
  private short headSerialNo = 0;
  private byte headReqID0 = 0;
  private byte headReqID1 = 0;
  private int headUserIPv4 = 0;
  private BigInteger headUserIPv6 = null;
  private short headUserPort = 0;
  private byte headErrCode = 0;
  private byte headAttrNum = 0;
  private byte[] sAuthenticator = new byte[16];
  private byte[] dAuthenticator = new byte[16];
  private int destinePort = 0;
  private InetAddress destineAddress;
  private byte[] sharedSecret = "hello".getBytes();
  private ByteArrayOutputStream requestAttributes = new ByteArrayOutputStream();

  public int setHead(byte paramByte1, byte paramByte2)
  {
    int i = 1;
    switch (paramByte1)
    {
    case 80:
      this.headVer = paramByte2;
      i = 0;
      break;
    case 81:
      this.headType = paramByte2;
      i = 0;
      break;
    case 82:
      this.headChapOrPap = paramByte2;
      i = 0;
      break;
    case 83:
      this.headReserved = paramByte2;
      i = 0;
      break;
    case 85:
      this.headReqID0 = paramByte2;
      i = 0;
      break;
    case 86:
      this.headReqID1 = paramByte2;
      i = 0;
      break;
    case 89:
      this.headErrCode = paramByte2;
      i = 0;
      break;
    case 84:
    case 87:
    case 88:
    default:
      i = 1;
    }
    return i;
  }

  public int setHead(byte paramByte, short paramShort)
  {
    int i = 1;
    switch (paramByte)
    {
    case 84:
      this.headSerialNo = paramShort;
      i = 0;
      break;
    case 88:
      this.headUserPort = paramShort;
      i = 0;
      break;
    case 85:
    case 86:
    case 87:
    default:
      i = 1;
    }
    return i;
  }

  public int setHead(byte paramByte, int paramInt)
  {
    int i = 1;
    switch (paramByte)
    {
    case 87:
      this.headUserIPv4 = paramInt;
      i = 0;
      break;
    case 68:
      this.remotePort = paramInt;
    default:
      i = 1;
    }
    return i;
  }

  public int setHead(byte paramByte, BigInteger paramBigInteger)
  {
    int i = 1;
    switch (paramByte)
    {
    case 87:
      this.headUserIPv4 = paramBigInteger.intValue();
      i = 0;
      break;
    case 92:
      this.headUserIPv6 = paramBigInteger;
      i = 0;
      break;
    case 67:
      this.remoteIp = paramBigInteger;
      break;
    default:
      i = 1;
    }
    return i;
  }

  public int setHead(byte paramByte, Number paramNumber)
  {
    if ((paramNumber instanceof Byte))
      return setHead(paramByte, paramNumber.byteValue());
    int i = 1;
    switch (paramByte)
    {
    case 87:
      this.headUserIPv4 = paramNumber.intValue();
      i = 0;
      break;
    case 92:
      this.headUserIPv6 = ((BigInteger)paramNumber);
      i = 0;
      break;
    case 67:
      this.remoteIp = paramNumber;
      break;
    default:
      i = 1;
    }
    return i;
  }

  public int setHead(byte paramByte, byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramArrayOfByte == null)
      return 1;
    switch (paramByte)
    {
    case 91:
      System.arraycopy(paramArrayOfByte, 0, this.sAuthenticator, 0, 16);
      i = 0;
      break;
    default:
      i = 1;
    }
    return i;
  }

  public void setDestinePort(int paramInt)
  {
    this.destinePort = paramInt;
  }

  public void setDestineAddress(InetAddress paramInetAddress)
  {
    this.destineAddress = paramInetAddress;
  }

  public void setDestineAddress(int paramInt)
  {
    this.destineAddress = CommonFunctions.convertIntToInetAddress(paramInt);
  }

  public void setShareSecret(byte[] paramArrayOfByte)
  {
    this.sharedSecret = paramArrayOfByte;
  }

  private void setAttribute(byte paramByte, int paramInt, byte[] paramArrayOfByte)
  {
    if (paramInt > 253)
      paramInt = 253;
    this.requestAttributes.write(paramByte);
    this.requestAttributes.write(paramInt + 2);
    this.requestAttributes.write(paramArrayOfByte, 0, paramInt);
  }

  public void setAttribute(byte paramByte)
  {
    this.headAttrNum = ((byte)(this.headAttrNum + 1));
    this.requestAttributes.write(paramByte);
    this.requestAttributes.write(2);
  }

  public void setAttribute(byte paramByte, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return;
    this.headAttrNum = ((byte)(this.headAttrNum + 1));
    setAttribute(paramByte, paramArrayOfByte.length, paramArrayOfByte);
  }

  public void setAttribute(byte paramByte1, byte paramByte2)
  {
    this.headAttrNum = ((byte)(this.headAttrNum + 1));
    this.requestAttributes.write(paramByte1);
    this.requestAttributes.write(3);
    this.requestAttributes.write(paramByte2);
  }

  private byte[] makeAuthenticator(byte[] paramArrayOfByte)
  {
    MD5 localMD5 = new MD5();
    localMD5.update(paramArrayOfByte);
    localMD5.update(this.sharedSecret);
    return localMD5.finalFunc();
  }

  public DatagramPacket composeWebPacket()
    throws IOException
  {
    return composePacketData(true);
  }

  public DatagramPacket composePacket()
  {
    try
    {
      return composePacketData(false);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private DatagramPacket composePacketData(boolean paramBoolean)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ByteArrayOutputStream localByteArrayOutputStream = null;
    DataOutputStream localDataOutputStream = null;
    DatagramPacket localDatagramPacket = null;
    localByteArrayOutputStream = new ByteArrayOutputStream();
    localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    localDataOutputStream.writeByte(this.headVer);
    localDataOutputStream.writeByte(this.headType);
    localDataOutputStream.writeByte(this.headChapOrPap);
    localDataOutputStream.writeByte(this.headReserved);
    localDataOutputStream.writeShort(this.headSerialNo);
    localDataOutputStream.writeByte(this.headReqID0);
    localDataOutputStream.writeByte(this.headReqID1);
    localDataOutputStream.writeInt(this.headUserIPv4);
    localDataOutputStream.writeShort(this.headUserPort);
    localDataOutputStream.writeByte(this.headErrCode);
    localDataOutputStream.writeByte(this.headAttrNum);
    if (3 == this.headVer)
    {
      if ((this.headUserIPv6 != null) && (this.headUserIPv6.bitLength() > 32))
      {
        byte[] arrayOfByte2 = CommonFunctions.convertBigIntegerTo16Bytes(this.headUserIPv6);
        localDataOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
      }
      else
      {
        byte[] arrayOfByte2 = new byte[16];
        Arrays.fill(arrayOfByte2, (byte)0);
        localDataOutputStream.write(arrayOfByte2, 0, 16);
      }
    }
    if ((2 == this.headVer) || (3 == this.headVer)) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("sAuthenticator包：" + PortalUtil.Getbyte2HexString(this.sAuthenticator));
      }

      localDataOutputStream.write(this.sAuthenticator, 0, 16);
    }

    if (this.requestAttributes.size() != 0) {
      localDataOutputStream.write(this.requestAttributes.toByteArray(), 0, this.requestAttributes.size());
      if (basConfig.getIsdebug().equals("1")) {
        log.info("requestAttributes包：" + PortalUtil.Getbyte2HexString(this.requestAttributes.toByteArray()));
      }

    }

    this.requestAttributes.close();
    localDataOutputStream.flush();
    localDataOutputStream.close();
    byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
    if (basConfig.getIsdebug().equals("1")) {
      log.info("基础包：" + PortalUtil.Getbyte2HexString(arrayOfByte1));
    }

    localByteArrayOutputStream.close();
    int i = 3 == this.headVer ? 32 : 16;
    if ((2 == this.headVer) || (3 == this.headVer))
    {
      this.dAuthenticator = makeAuthenticator(arrayOfByte1);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("dAuthenticator包：" + PortalUtil.Getbyte2HexString(this.dAuthenticator));
      }

      System.arraycopy(this.dAuthenticator, 0, arrayOfByte1, i, 16);
    }
    if (basConfig.getIsdebug().equals("1")) {
      log.info("原包：" + PortalUtil.Getbyte2HexString(arrayOfByte1));
    }

    int j = arrayOfByte1.length;
    if (!paramBoolean)
    {
      byte[] arrayOfByte3 = new byte[j + 20];
      Arrays.fill(arrayOfByte3, (byte)0);
      byte[] arrayOfByte4;
      if ((this.remoteIp instanceof BigInteger)) {
         arrayOfByte4 = ((BigInteger)this.remoteIp).toByteArray();
        if (basConfig.getIsdebug().equals("1")) {
          log.info("remoteIp包：" + PortalUtil.Getbyte2HexString(arrayOfByte4));
        }
      }
      else
      {
        arrayOfByte4 = CommonFunctions.convertIntToByteArray(this.remoteIp.intValue());
        if (basConfig.getIsdebug().equals("1")) {
          log.info("remoteIp包：" + PortalUtil.Getbyte2HexString(arrayOfByte4));
        }

      }

      System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 16 - arrayOfByte4.length, arrayOfByte4.length);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("remotePort包：" + PortalUtil.Getbyte2HexString(CommonFunctions.convertIntToByteArray(this.remotePort)));
      }

      System.arraycopy(CommonFunctions.convertIntToByteArray(this.remotePort), 0, arrayOfByte3, 16, 4);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 20, j);
      localDatagramPacket = new DatagramPacket(new byte[j + 20], j + 20);
      localDatagramPacket.setLength(j + 20);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("REQ " + 
          PortalUtil.Getbyte2HexString(arrayOfByte3));
      }

      localDatagramPacket.setData(arrayOfByte3);
    }
    else
    {
      localDatagramPacket = new DatagramPacket(new byte[j], j);
      localDatagramPacket.setLength(j);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("REQ " + 
          PortalUtil.Getbyte2HexString(arrayOfByte1));
      }

      localDatagramPacket.setData(arrayOfByte1);
    }
    localDatagramPacket.setPort(this.destinePort);
    localDatagramPacket.setAddress(this.destineAddress);
    return localDatagramPacket;
  }

  public byte[] getAuthenticatior()
  {
    return this.dAuthenticator;
  }

  public byte getHeadType()
  {
    return this.headType;
  }

  public short getHeadSerialNo()
  {
    return this.headSerialNo;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.PacketBuilder
 * JD-Core Version:    0.6.2
 */