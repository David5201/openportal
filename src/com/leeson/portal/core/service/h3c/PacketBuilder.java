/*     */ package com.leeson.portal.core.service.h3c;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.service.utils.PortalUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PacketBuilder
/*     */ {
/*  19 */   private static Config config = Config.getInstance();
/*  20 */   private static Logger log = Logger.getLogger(PacketBuilder.class);
/*     */   private Number remoteIp;
/*     */   private int remotePort;
/*  23 */   private byte headVer = 2;
/*  24 */   private byte headType = 1;
/*  25 */   private byte headChapOrPap = 0;
/*  26 */   private byte headReserved = 0;
/*  27 */   private short headSerialNo = 0;
/*  28 */   private byte headReqID0 = 0;
/*  29 */   private byte headReqID1 = 0;
/*  30 */   private int headUserIPv4 = 0;
/*  31 */   private BigInteger headUserIPv6 = null;
/*  32 */   private short headUserPort = 0;
/*  33 */   private byte headErrCode = 0;
/*  34 */   private byte headAttrNum = 0;
/*  35 */   private byte[] sAuthenticator = new byte[16];
/*  36 */   private byte[] dAuthenticator = new byte[16];
/*  37 */   private int destinePort = 0;
/*     */   private InetAddress destineAddress;
/*  39 */   private byte[] sharedSecret = "hello".getBytes();
/*  40 */   private ByteArrayOutputStream requestAttributes = new ByteArrayOutputStream();
/*     */ 
/*     */   public int setHead(byte paramByte1, byte paramByte2)
/*     */   {
/*  44 */     int i = 1;
/*  45 */     switch (paramByte1)
/*     */     {
/*     */     case 80:
/*  48 */       this.headVer = paramByte2;
/*  49 */       i = 0;
/*  50 */       break;
/*     */     case 81:
/*  52 */       this.headType = paramByte2;
/*  53 */       i = 0;
/*  54 */       break;
/*     */     case 82:
/*  56 */       this.headChapOrPap = paramByte2;
/*  57 */       i = 0;
/*  58 */       break;
/*     */     case 83:
/*  60 */       this.headReserved = paramByte2;
/*  61 */       i = 0;
/*  62 */       break;
/*     */     case 85:
/*  64 */       this.headReqID0 = paramByte2;
/*  65 */       i = 0;
/*  66 */       break;
/*     */     case 86:
/*  68 */       this.headReqID1 = paramByte2;
/*  69 */       i = 0;
/*  70 */       break;
/*     */     case 89:
/*  72 */       this.headErrCode = paramByte2;
/*  73 */       i = 0;
/*  74 */       break;
/*     */     case 84:
/*     */     case 87:
/*     */     case 88:
/*     */     default:
/*  79 */       i = 1;
/*     */     }
/*  81 */     return i;
/*     */   }
/*     */ 
/*     */   public int setHead(byte paramByte, short paramShort)
/*     */   {
/*  86 */     int i = 1;
/*  87 */     switch (paramByte)
/*     */     {
/*     */     case 84:
/*  90 */       this.headSerialNo = paramShort;
/*  91 */       i = 0;
/*  92 */       break;
/*     */     case 88:
/*  94 */       this.headUserPort = paramShort;
/*  95 */       i = 0;
/*  96 */       break;
/*     */     case 85:
/*     */     case 86:
/*     */     case 87:
/*     */     default:
/*  98 */       i = 1;
/*     */     }
/* 100 */     return i;
/*     */   }
/*     */ 
/*     */   public int setHead(byte paramByte, int paramInt)
/*     */   {
/* 105 */     int i = 1;
/* 106 */     switch (paramByte)
/*     */     {
/*     */     case 87:
/* 109 */       this.headUserIPv4 = paramInt;
/* 110 */       i = 0;
/* 111 */       break;
/*     */     case 68:
/* 113 */       this.remotePort = paramInt;
/*     */     default:
/* 115 */       i = 1;
/*     */     }
/* 117 */     return i;
/*     */   }
/*     */ 
/*     */   public int setHead(byte paramByte, BigInteger paramBigInteger)
/*     */   {
/* 122 */     int i = 1;
/* 123 */     switch (paramByte)
/*     */     {
/*     */     case 87:
/* 126 */       this.headUserIPv4 = paramBigInteger.intValue();
/* 127 */       i = 0;
/* 128 */       break;
/*     */     case 92:
/* 130 */       this.headUserIPv6 = paramBigInteger;
/* 131 */       i = 0;
/* 132 */       break;
/*     */     case 67:
/* 134 */       this.remoteIp = paramBigInteger;
/* 135 */       break;
/*     */     default:
/* 137 */       i = 1;
/*     */     }
/* 139 */     return i;
/*     */   }
/*     */ 
/*     */   public int setHead(byte paramByte, Number paramNumber)
/*     */   {
/* 144 */     if ((paramNumber instanceof Byte))
/* 145 */       return setHead(paramByte, paramNumber.byteValue());
/* 146 */     int i = 1;
/* 147 */     switch (paramByte)
/*     */     {
/*     */     case 87:
/* 150 */       this.headUserIPv4 = paramNumber.intValue();
/* 151 */       i = 0;
/* 152 */       break;
/*     */     case 92:
/* 154 */       this.headUserIPv6 = ((BigInteger)paramNumber);
/* 155 */       i = 0;
/* 156 */       break;
/*     */     case 67:
/* 158 */       this.remoteIp = paramNumber;
/* 159 */       break;
/*     */     default:
/* 161 */       i = 1;
/*     */     }
/* 163 */     return i;
/*     */   }
/*     */ 
/*     */   public int setHead(byte paramByte, byte[] paramArrayOfByte)
/*     */   {
/* 168 */     int i = 1;
/* 169 */     if (paramArrayOfByte == null)
/* 170 */       return 1;
/* 171 */     switch (paramByte)
/*     */     {
/*     */     case 91:
/* 174 */       System.arraycopy(paramArrayOfByte, 0, this.sAuthenticator, 0, 16);
/* 175 */       i = 0;
/* 176 */       break;
/*     */     default:
/* 178 */       i = 1;
/*     */     }
/* 180 */     return i;
/*     */   }
/*     */ 
/*     */   public void setDestinePort(int paramInt)
/*     */   {
/* 185 */     this.destinePort = paramInt;
/*     */   }
/*     */ 
/*     */   public void setDestineAddress(InetAddress paramInetAddress)
/*     */   {
/* 190 */     this.destineAddress = paramInetAddress;
/*     */   }
/*     */ 
/*     */   public void setDestineAddress(int paramInt)
/*     */   {
/* 195 */     this.destineAddress = CommonFunctions.convertIntToInetAddress(paramInt);
/*     */   }
/*     */ 
/*     */   public void setShareSecret(byte[] paramArrayOfByte)
/*     */   {
/* 200 */     this.sharedSecret = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   private void setAttribute(byte paramByte, int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 205 */     if (paramInt > 253)
/* 206 */       paramInt = 253;
/* 207 */     this.requestAttributes.write(paramByte);
/* 208 */     this.requestAttributes.write(paramInt + 2);
/* 209 */     this.requestAttributes.write(paramArrayOfByte, 0, paramInt);
/*     */   }
/*     */ 
/*     */   public void setAttribute(byte paramByte)
/*     */   {
/* 214 */     this.headAttrNum = ((byte)(this.headAttrNum + 1));
/* 215 */     this.requestAttributes.write(paramByte);
/* 216 */     this.requestAttributes.write(2);
/*     */   }
/*     */ 
/*     */   public void setAttribute(byte paramByte, byte[] paramArrayOfByte)
/*     */   {
/* 221 */     if (paramArrayOfByte == null)
/* 222 */       return;
/* 223 */     this.headAttrNum = ((byte)(this.headAttrNum + 1));
/* 224 */     setAttribute(paramByte, paramArrayOfByte.length, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public void setAttribute(byte paramByte1, byte paramByte2)
/*     */   {
/* 229 */     this.headAttrNum = ((byte)(this.headAttrNum + 1));
/* 230 */     this.requestAttributes.write(paramByte1);
/* 231 */     this.requestAttributes.write(3);
/* 232 */     this.requestAttributes.write(paramByte2);
/*     */   }
/*     */ 
/*     */   private byte[] makeAuthenticator(byte[] paramArrayOfByte)
/*     */   {
/* 237 */     MD5 localMD5 = new MD5();
/* 238 */     localMD5.update(paramArrayOfByte);
/* 239 */     localMD5.update(this.sharedSecret);
/* 240 */     return localMD5.finalFunc();
/*     */   }
/*     */ 
/*     */   public DatagramPacket composeWebPacket()
/*     */     throws IOException
/*     */   {
/* 246 */     return composePacketData(true);
/*     */   }
/*     */ 
/*     */   public DatagramPacket composePacket()
/*     */   {
/*     */     try
/*     */     {
/* 253 */       return composePacketData(false);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 257 */       localException.printStackTrace();
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */   private DatagramPacket composePacketData(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 265 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 266 */     ByteArrayOutputStream localByteArrayOutputStream = null;
/* 267 */     DataOutputStream localDataOutputStream = null;
/* 268 */     DatagramPacket localDatagramPacket = null;
/* 269 */     localByteArrayOutputStream = new ByteArrayOutputStream();
/* 270 */     localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
/* 271 */     localDataOutputStream.writeByte(this.headVer);
/* 272 */     localDataOutputStream.writeByte(this.headType);
/* 273 */     localDataOutputStream.writeByte(this.headChapOrPap);
/* 274 */     localDataOutputStream.writeByte(this.headReserved);
/* 275 */     localDataOutputStream.writeShort(this.headSerialNo);
/* 276 */     localDataOutputStream.writeByte(this.headReqID0);
/* 277 */     localDataOutputStream.writeByte(this.headReqID1);
/* 278 */     localDataOutputStream.writeInt(this.headUserIPv4);
/* 279 */     localDataOutputStream.writeShort(this.headUserPort);
/* 280 */     localDataOutputStream.writeByte(this.headErrCode);
/* 281 */     localDataOutputStream.writeByte(this.headAttrNum);
/* 282 */     if (3 == this.headVer)
/*     */     {
/* 285 */       if ((this.headUserIPv6 != null) && (this.headUserIPv6.bitLength() > 32))
/*     */       {
/* 287 */         byte[] arrayOfByte2 = CommonFunctions.convertBigIntegerTo16Bytes(this.headUserIPv6);
/* 288 */         localDataOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
/*     */       }
/*     */       else
/*     */       {
/* 292 */         byte[] arrayOfByte2 = new byte[16];
/* 293 */         Arrays.fill(arrayOfByte2, (byte)0);
/* 294 */         localDataOutputStream.write(arrayOfByte2, 0, 16);
/*     */       }
/*     */     }
/* 297 */     if ((2 == this.headVer) || (3 == this.headVer)) {
/* 298 */       if (basConfig.getIsdebug().equals("1")) {
/* 299 */         log.info("sAuthenticator包：" + PortalUtil.Getbyte2HexString(this.sAuthenticator));
/*     */       }
/*     */ 
/* 302 */       localDataOutputStream.write(this.sAuthenticator, 0, 16);
/*     */     }
/*     */ 
/* 305 */     if (this.requestAttributes.size() != 0) {
/* 306 */       localDataOutputStream.write(this.requestAttributes.toByteArray(), 0, this.requestAttributes.size());
/* 307 */       if (basConfig.getIsdebug().equals("1")) {
/* 308 */         log.info("requestAttributes包：" + PortalUtil.Getbyte2HexString(this.requestAttributes.toByteArray()));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 313 */     this.requestAttributes.close();
/* 314 */     localDataOutputStream.flush();
/* 315 */     localDataOutputStream.close();
/* 316 */     byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
/* 317 */     if (basConfig.getIsdebug().equals("1")) {
/* 318 */       log.info("基础包：" + PortalUtil.Getbyte2HexString(arrayOfByte1));
/*     */     }
/*     */ 
/* 322 */     localByteArrayOutputStream.close();
/* 323 */     int i = 3 == this.headVer ? 32 : 16;
/* 324 */     if ((2 == this.headVer) || (3 == this.headVer))
/*     */     {
/* 326 */       this.dAuthenticator = makeAuthenticator(arrayOfByte1);
/* 327 */       if (basConfig.getIsdebug().equals("1")) {
/* 328 */         log.info("dAuthenticator包：" + PortalUtil.Getbyte2HexString(this.dAuthenticator));
/*     */       }
/*     */ 
/* 331 */       System.arraycopy(this.dAuthenticator, 0, arrayOfByte1, i, 16);
/*     */     }
/* 333 */     if (basConfig.getIsdebug().equals("1")) {
/* 334 */       log.info("原包：" + PortalUtil.Getbyte2HexString(arrayOfByte1));
/*     */     }
/*     */ 
/* 338 */     int j = arrayOfByte1.length;
/* 339 */     if (!paramBoolean)
/*     */     {
/* 341 */       byte[] arrayOfByte3 = new byte[j + 20];
/* 342 */       Arrays.fill(arrayOfByte3, (byte)0);
/*     */       byte[] arrayOfByte4;
/* 344 */       if ((this.remoteIp instanceof BigInteger)) {
/* 345 */          arrayOfByte4 = ((BigInteger)this.remoteIp).toByteArray();
/* 346 */         if (basConfig.getIsdebug().equals("1")) {
/* 347 */           log.info("remoteIp包：" + PortalUtil.Getbyte2HexString(arrayOfByte4));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 352 */         arrayOfByte4 = CommonFunctions.convertIntToByteArray(this.remoteIp.intValue());
/* 353 */         if (basConfig.getIsdebug().equals("1")) {
/* 354 */           log.info("remoteIp包：" + PortalUtil.Getbyte2HexString(arrayOfByte4));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 359 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 16 - arrayOfByte4.length, arrayOfByte4.length);
/* 360 */       if (basConfig.getIsdebug().equals("1")) {
/* 361 */         log.info("remotePort包：" + PortalUtil.Getbyte2HexString(CommonFunctions.convertIntToByteArray(this.remotePort)));
/*     */       }
/*     */ 
/* 364 */       System.arraycopy(CommonFunctions.convertIntToByteArray(this.remotePort), 0, arrayOfByte3, 16, 4);
/* 365 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 20, j);
/* 366 */       localDatagramPacket = new DatagramPacket(new byte[j + 20], j + 20);
/* 367 */       localDatagramPacket.setLength(j + 20);
/* 368 */       if (basConfig.getIsdebug().equals("1")) {
/* 369 */         log.info("REQ " + 
/* 370 */           PortalUtil.Getbyte2HexString(arrayOfByte3));
/*     */       }
/*     */ 
/* 373 */       localDatagramPacket.setData(arrayOfByte3);
/*     */     }
/*     */     else
/*     */     {
/* 377 */       localDatagramPacket = new DatagramPacket(new byte[j], j);
/* 378 */       localDatagramPacket.setLength(j);
/* 379 */       if (basConfig.getIsdebug().equals("1")) {
/* 380 */         log.info("REQ " + 
/* 381 */           PortalUtil.Getbyte2HexString(arrayOfByte1));
/*     */       }
/*     */ 
/* 384 */       localDatagramPacket.setData(arrayOfByte1);
/*     */     }
/* 386 */     localDatagramPacket.setPort(this.destinePort);
/* 387 */     localDatagramPacket.setAddress(this.destineAddress);
/* 388 */     return localDatagramPacket;
/*     */   }
/*     */ 
/*     */   public byte[] getAuthenticatior()
/*     */   {
/* 393 */     return this.dAuthenticator;
/*     */   }
/*     */ 
/*     */   public byte getHeadType()
/*     */   {
/* 398 */     return this.headType;
/*     */   }
/*     */ 
/*     */   public short getHeadSerialNo()
/*     */   {
/* 403 */     return this.headSerialNo;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.PacketBuilder
 * JD-Core Version:    0.6.2
 */