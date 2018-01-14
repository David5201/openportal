/*      */ package com.leeson.radius.core;
/*      */ 
/*      */ import com.leeson.core.bean.Portalbas;
/*      */ import com.leeson.portal.core.model.Config;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.URL;
/*      */ import java.security.MessageDigest;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class Tool
/*      */ {
/*   18 */   private static Logger log = Logger.getLogger(Tool.class);
/*      */ 
/*   20 */   private static Config config = Config.getInstance();
/*      */ 
/*      */   public static void writeLog(String name, String content) {
/*   23 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */     try
/*      */     {
/*   28 */       if (basConfig.getIsdebug().equals("1"))
/*   29 */         log.info("[" + name + "]" + content);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void writeErrorLog(String name, Exception content)
/*      */   {
/*   38 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*      */     try
/*      */     {
/*   43 */       if (basConfig.getIsdebug().equals("1"))
/*   44 */         log.error("[" + name + "]" + content);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String getAttributeIP(String ip)
/*      */   {
/*   54 */     byte[] UserIP = new byte[4];
/*   55 */     String[] ips = ip.split("[.]");
/*      */ 
/*   57 */     for (int i = 0; i < 4; i++) {
/*   58 */       int m = Integer.valueOf(ips[i]).intValue();
/*   59 */       byte b = (byte)m;
/*   60 */       UserIP[i] = b;
/*      */     }
/*   62 */     String info = ByteToHex(UserIP);
/*      */ 
/*   64 */     String ret = null;
/*      */     try
/*      */     {
/*   67 */       String len = Integer.toHexString(6);
/*   68 */       while (len.length() < 2)
/*   69 */         len = "0" + len;
/*   70 */       String types = Integer.toHexString(8);
/*   71 */       while (types.length() < 2)
/*   72 */         types = "0" + types;
/*   73 */       ret = types + len + info;
/*      */     } catch (Exception localException) {
/*      */     }
/*   76 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeNasIP(String ip) {
/*   80 */     byte[] UserIP = new byte[4];
/*   81 */     String[] ips = ip.split("[.]");
/*      */ 
/*   83 */     for (int i = 0; i < 4; i++) {
/*   84 */       int m = Integer.valueOf(ips[i]).intValue();
/*   85 */       byte b = (byte)m;
/*   86 */       UserIP[i] = b;
/*      */     }
/*   88 */     String info = ByteToHex(UserIP);
/*      */ 
/*   90 */     String ret = null;
/*      */     try
/*      */     {
/*   93 */       String len = Integer.toHexString(6);
/*   94 */       while (len.length() < 2)
/*   95 */         len = "0" + len;
/*   96 */       String types = Integer.toHexString(4);
/*   97 */       while (types.length() < 2)
/*   98 */         types = "0" + types;
/*   99 */       ret = types + len + info;
/*      */     } catch (Exception localException) {
/*      */     }
/*  102 */     return ret;
/*      */   }
/*      */ 
/*      */   public static void openProxy(String proxyhost, String proxyport)
/*      */   {
/*      */     try {
/*  108 */       System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
/*  109 */       System.setProperty("sun.net.client.defaultReadTimeout", "10000");
/*  110 */       if ((proxyhost != null) && (proxyport != null)) {
/*  111 */         Properties prop = System.getProperties();
/*  112 */         prop.put("http.proxyHost", proxyhost);
/*  113 */         prop.put("http.proxyPort", proxyport);
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String sendURL(String url) {
/*  121 */     StringBuffer ret = new StringBuffer();
/*  122 */     InputStream is = null;
/*  123 */     InputStreamReader isr = null;
/*  124 */     BufferedReader br = null;
/*      */     try {
/*  126 */       URL su = new URL(url);
/*  127 */       is = su.openStream();
/*  128 */       isr = new InputStreamReader(is);
/*  129 */       br = new BufferedReader(isr);
/*  130 */       for (String line = br.readLine(); line != null; line = br
/*  131 */         .readLine())
/*      */       {
/*  132 */         ret.append(line + "\r\n");
/*      */       }
/*      */     } catch (Exception localException) {
/*      */     }
/*      */     try { if (br != null) {
/*  137 */         br.close();
/*  138 */         br = null;
/*      */       }
/*      */     } catch (Exception localException1) {
/*      */     }
/*      */     try {
/*  143 */       if (isr != null) {
/*  144 */         isr.close();
/*  145 */         isr = null;
/*      */       }
/*      */     } catch (Exception localException2) {
/*      */     }
/*      */     try {
/*  150 */       if (is != null) {
/*  151 */         is.close();
/*  152 */         is = null;
/*      */       }
/*      */     } catch (Exception localException3) {
/*      */     }
/*  156 */     return ret.toString();
/*      */   }
/*      */ 
/*      */   public static String encodeMD5(String str)
/*      */   {
/*  161 */     String ret = "";
/*      */     try {
/*  163 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*  164 */       md.update(HexToByte(str));
/*      */ 
/*  166 */       ret = ByteToHex(md.digest());
/*      */     } catch (Exception localException) {
/*      */     }
/*  169 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String decodeMD5(String sharedSecret, String authenticator, String password)
/*      */   {
/*  175 */     String ret = "";
/*      */     try {
/*  177 */       byte[] pwd = HexToByte(password);
/*  178 */       if ((pwd != null) && (pwd.length >= 16))
/*      */       {
/*  180 */         byte[] temp = HexToByte(encodeMD5(sharedSecret + authenticator));
/*  181 */         for (int i = 0; i < 16; i++) {
/*  182 */           pwd[i] = ((byte)(temp[i] ^ pwd[i]));
/*      */         }
/*  184 */         if (pwd.length > 16) {
/*  185 */           for (int i = 16; i < pwd.length; i += 16) {
/*  186 */             temp = HexToByte(encodeMD5(sharedSecret + 
/*  187 */               ByteToHex(pwd, i - 16, 16)));
/*  188 */             for (int j = 0; j < 16; j++) {
/*  189 */               pwd[(i + j)] = ((byte)(temp[j] ^ pwd[(i + j)]));
/*      */             }
/*      */           }
/*      */         }
/*  193 */         int len = pwd.length;
/*  194 */         while ((len > 0) && (pwd[(len - 1)] == 0))
/*  195 */           len--;
/*  196 */         ret = new String(pwd, 0, len);
/*      */       }
/*      */     } catch (Exception localException) {
/*      */     }
/*  200 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String ByteToHex(byte[] buf) {
/*  204 */     StringBuffer ret = new StringBuffer();
/*  205 */     String result = null;
/*      */     try {
/*  207 */       for (int i = 0; i < buf.length; i++) {
/*  208 */         String temp = Integer.toHexString(buf[i] & 0xFF);
/*  209 */         while (temp.length() < 2)
/*  210 */           temp = "0" + temp;
/*  211 */         ret.append(temp);
/*      */       }
/*  213 */       result = ret.toString().toUpperCase();
/*      */     } catch (Exception localException) {
/*      */     }
/*  216 */     return result;
/*      */   }
/*      */ 
/*      */   public static String ByteToHex(byte[] buf, int begin, int end) {
/*  220 */     StringBuffer ret = new StringBuffer();
/*  221 */     String result = null;
/*      */     try {
/*  223 */       for (int i = begin; i < end; i++) {
/*  224 */         String temp = Integer.toHexString(buf[i] & 0xFF);
/*  225 */         while (temp.length() < 2)
/*  226 */           temp = "0" + temp;
/*  227 */         ret.append(temp);
/*      */       }
/*  229 */       result = ret.toString().toUpperCase();
/*      */     } catch (Exception localException) {
/*      */     }
/*  232 */     return result;
/*      */   }
/*      */ 
/*      */   public static byte[] HexToByte(String value) {
/*  236 */     byte[] ret = null;
/*      */     try {
/*  238 */       int len = value.length() / 2;
/*  239 */       ret = new byte[len];
/*  240 */       for (int i = 0; i < len; i++)
/*  241 */         ret[i] = ((byte)Integer.parseInt(
/*  242 */           value.substring(i * 2, i * 2 + 2), 16));
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*  246 */     return ret;
/*      */   }
/*      */ 
/*      */   public static long ByteToLong(byte[] value) {
/*  250 */     long ret = 0L;
/*      */     try {
/*  252 */       for (int i = 0; i < value.length; i++)
/*  253 */         ret = ret << 8 | value[i] & 0xFF;
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*  257 */     return ret;
/*      */   }
/*      */ 
/*      */   public static int ByteToInt(byte[] value) {
/*  261 */     int ret = value[0] & 0xFF;
/*  262 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String ByteToIP(byte[] value) {
/*  266 */     String ret = null;
/*      */     try {
/*  268 */       if ((value != null) && (value.length >= 4))
/*      */       {
/*  274 */         ret = (value[0] & 0xFF) + '.' + (
/*  275 */           value[1] & 0xFF) + '.' + (
/*  276 */           value[2] & 0xFF) + '.' + (
/*  277 */           value[3] & 0xFF)+"";
/*      */       }
/*      */     } catch (Exception localException) {
/*      */     }
/*  281 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String[][] getAttributes(String attributes)
/*      */   {
/*  286 */     String[][] ret = null;
/*  287 */     Vector list = null;
/*      */     try {
/*  289 */       list = new Vector();
/*  290 */       while (attributes.length() >= 4) {
/*  291 */         int len = 4;
/*      */         try {
/*  293 */           len = Integer.parseInt(attributes.substring(2, 4), 16) * 2;
/*  294 */           if (len >= 4)
/*  295 */             list.add(attributes.substring(0, len));
/*      */           else
/*  297 */             len = 4;
/*      */         }
/*      */         catch (Exception localException) {
/*      */         }
/*  301 */         attributes = attributes.substring(len);
/*      */       }
/*      */     } catch (Exception localException1) {
/*      */     }
/*      */     try {
/*  306 */       if ((list != null) && (list.size() > 0)) {
/*  307 */         int len = list.size();
/*  308 */         ret = new String[len][2];
/*  309 */         for (int i = 0; i < len; i++)
/*      */           try {
/*  311 */             String temp = (String)list.get(i);
/*  312 */             ret[i][0] = temp.substring(0, 2);
/*  313 */             ret[i][1] = temp.substring(4);
/*      */           }
/*      */           catch (Exception localException2) {
/*      */           }
/*  317 */         list.clear();
/*      */       }
/*      */     } catch (Exception localException3) {
/*      */     }
/*  321 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeValue(String ip, int type, String value)
/*      */   {
/*  330 */     String ret = null;
/*  331 */     String name = null;
/*      */     try {
/*  333 */       switch (type)
/*      */       {
/*      */       case 1:
/*  336 */         name = "User-Name";
/*  337 */         ret = new String(HexToByte(value));
/*      */ 
/*  339 */         break;
/*      */       case 2:
/*  342 */         name = "User-Password";
/*  343 */         ret = value;
/*      */ 
/*  345 */         break;
/*      */       case 3:
/*  348 */         name = "Chap-Password";
/*  349 */         ret = value;
/*      */ 
/*  351 */         break;
/*      */       case 4:
/*  354 */         name = "Nas-IP-Address";
/*  355 */         ret = ByteToIP(HexToByte(value));
/*      */ 
/*  357 */         break;
/*      */       case 5:
/*  360 */         name = "Nas-Port";
/*  361 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  363 */         break;
/*      */       case 6:
/*  366 */         name = "Service-Type";
/*  367 */         ret = getServiceType((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  369 */         break;
/*      */       case 7:
/*  372 */         name = "Framed-Protocol";
/*  373 */         ret = getFramedProtocol((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  375 */         break;
/*      */       case 8:
/*  378 */         name = "Framed-IP-Address";
/*  379 */         ret = ByteToIP(HexToByte(value));
/*      */ 
/*  381 */         break;
/*      */       case 9:
/*  384 */         name = "Framed-IP-Netmask";
/*  385 */         ret = ByteToIP(HexToByte(value));
/*      */ 
/*  387 */         break;
/*      */       case 10:
/*  390 */         name = "Framed-Routing";
/*  391 */         ret = getFramedRouting((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  393 */         break;
/*      */       case 11:
/*  396 */         name = "Filter-Id";
/*  397 */         ret = new String(HexToByte(value));
/*      */ 
/*  399 */         break;
/*      */       case 12:
/*  402 */         name = "Framed-MTU";
/*  403 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  405 */         break;
/*      */       case 13:
/*  408 */         name = "Framed-Compression";
/*  409 */         ret = getFramedCompression((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  411 */         break;
/*      */       case 14:
/*  414 */         name = "Login-IP-Host";
/*  415 */         ret = ByteToIP(HexToByte(value));
/*      */ 
/*  417 */         break;
/*      */       case 15:
/*  420 */         name = "Login-Service";
/*  421 */         ret = getLoginService((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  423 */         break;
/*      */       case 16:
/*  426 */         name = "Login-TCP-Port";
/*  427 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  429 */         break;
/*      */       case 18:
/*  432 */         name = "Reply-Message";
/*  433 */         ret = new String(HexToByte(value));
/*      */ 
/*  435 */         break;
/*      */       case 19:
/*  438 */         name = "Callback-Number";
/*  439 */         ret = new String(HexToByte(value));
/*      */ 
/*  441 */         break;
/*      */       case 20:
/*  444 */         name = "Callback-Id";
/*  445 */         ret = new String(HexToByte(value));
/*      */ 
/*  447 */         break;
/*      */       case 22:
/*  450 */         name = "Framed-Route";
/*  451 */         ret = new String(HexToByte(value));
/*      */ 
/*  453 */         break;
/*      */       case 23:
/*  456 */         name = "Framed-IPX-Network";
/*  457 */         ret = ByteToIP(HexToByte(value));
/*      */ 
/*  459 */         break;
/*      */       case 24:
/*  462 */         name = "State";
/*  463 */         ret = value;
/*      */ 
/*  465 */         break;
/*      */       case 25:
/*  468 */         name = "Class";
/*  469 */         ret = value;
/*      */ 
/*  471 */         break;
/*      */       case 26:
/*  474 */         name = "Vendor-Specific";
/*  475 */         ret = value;
/*      */ 
/*  477 */         break;
/*      */       case 27:
/*  480 */         name = "Session-Timeout";
/*  481 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  483 */         break;
/*      */       case 28:
/*  486 */         name = "Idle-Timeout";
/*  487 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  489 */         break;
/*      */       case 29:
/*  492 */         name = "Termination-Action";
/*  493 */         ret = getTerminationAction((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  495 */         break;
/*      */       case 30:
/*  498 */         name = "Called-Station-Id";
/*  499 */         ret = new String(HexToByte(value));
/*      */ 
/*  501 */         break;
/*      */       case 31:
/*  504 */         name = "Calling-Station-Id";
/*  505 */         ret = new String(HexToByte(value));
/*      */ 
/*  507 */         break;
/*      */       case 32:
/*  510 */         name = "Nas-Identifier";
/*  511 */         ret = new String(HexToByte(value));
/*      */ 
/*  513 */         break;
/*      */       case 33:
/*  516 */         name = "Proxy-State";
/*  517 */         ret = value;
/*      */ 
/*  519 */         break;
/*      */       case 34:
/*  522 */         name = "Login-LAT-Service";
/*  523 */         ret = new String(HexToByte(value));
/*      */ 
/*  525 */         break;
/*      */       case 35:
/*  528 */         name = "Login-LAT-Node";
/*  529 */         ret = new String(HexToByte(value));
/*      */ 
/*  531 */         break;
/*      */       case 36:
/*  534 */         name = "Login-LAT-Group";
/*  535 */         ret = value;
/*      */ 
/*  537 */         break;
/*      */       case 37:
/*  540 */         name = "Framed-AppleTalk-Link";
/*  541 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  543 */         break;
/*      */       case 38:
/*  546 */         name = "Framed-AppleTalk-Network";
/*  547 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  549 */         break;
/*      */       case 39:
/*  552 */         name = "Framed-AppleTalk-Zone";
/*  553 */         ret = new String(HexToByte(value));
/*      */ 
/*  555 */         break;
/*      */       case 40:
/*  558 */         name = "Acct-Status-Type";
/*  559 */         ret = getAcctStatusType((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  561 */         break;
/*      */       case 41:
/*  564 */         name = "Acct-Delay-Time";
/*  565 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  567 */         break;
/*      */       case 42:
/*  570 */         name = "Acct-Input-Octets";
/*  571 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  573 */         break;
/*      */       case 43:
/*  576 */         name = "Acct-Output-Octets";
/*  577 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  579 */         break;
/*      */       case 44:
/*  582 */         name = "Acct-Session-Id";
/*  583 */         ret = new String(HexToByte(value));
/*      */ 
/*  585 */         break;
/*      */       case 45:
/*  588 */         name = "Acct-Authentic";
/*  589 */         ret = getAcctAuthentic((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  591 */         break;
/*      */       case 46:
/*  594 */         name = "Acct-Session-Time";
/*  595 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  597 */         break;
/*      */       case 47:
/*  600 */         name = "Acct-Input-Packets";
/*  601 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  603 */         break;
/*      */       case 48:
/*  606 */         name = "Acct-Output-Packets";
/*  607 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  609 */         break;
/*      */       case 49:
/*  612 */         name = "Acct-Terminate-Cause";
/*  613 */         ret = getAcctTerminateCause((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  615 */         break;
/*      */       case 50:
/*  618 */         name = "Acct-Multi-Session-Id";
/*  619 */         ret = new String(HexToByte(value));
/*      */ 
/*  621 */         break;
/*      */       case 51:
/*  624 */         name = "Acct-Link-Count";
/*  625 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  627 */         break;
/*      */       case 52:
/*  630 */         name = "Acct-Input-Gigawords";
/*  631 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  633 */         break;
/*      */       case 53:
/*  636 */         name = "Acct-Output-Gigawords";
/*  637 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  639 */         break;
/*      */       case 55:
/*  642 */         name = "Event-Timestamp";
/*  643 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  645 */         break;
/*      */       case 60:
/*  648 */         name = "CHAP-Challenge";
/*  649 */         ret = value;
/*      */ 
/*  651 */         break;
/*      */       case 61:
/*  654 */         name = "NAS-Port-Type";
/*  655 */         ret = getNASPortType((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  657 */         break;
/*      */       case 62:
/*  660 */         name = "Port-Limit";
/*  661 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  663 */         break;
/*      */       case 63:
/*  666 */         name = "Login-LAT-Port";
/*  667 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  669 */         break;
/*      */       case 68:
/*  672 */         name = "Acct-Tunnel-Connection";
/*  673 */         ret = new String(HexToByte(value));
/*      */ 
/*  675 */         break;
/*      */       case 70:
/*  678 */         name = "ARAP-Password";
/*  679 */         ret = new String(HexToByte(value));
/*      */ 
/*  681 */         break;
/*      */       case 71:
/*  684 */         name = "ARAP-Features";
/*  685 */         ret = new String(HexToByte(value));
/*      */ 
/*  687 */         break;
/*      */       case 72:
/*  690 */         name = "ARAP-Zone-Access";
/*  691 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  693 */         break;
/*      */       case 73:
/*  696 */         name = "ARAP-Security";
/*  697 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  699 */         break;
/*      */       case 74:
/*  702 */         name = "ARAP-Security-Data";
/*  703 */         ret = new String(HexToByte(value));
/*      */ 
/*  705 */         break;
/*      */       case 75:
/*  708 */         name = "Password-Retry";
/*  709 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  711 */         break;
/*      */       case 76:
/*  714 */         name = "Prompt";
/*  715 */         ret = getPrompt((int)ByteToLong(HexToByte(value)));
/*      */ 
/*  717 */         break;
/*      */       case 77:
/*  720 */         name = "Connect-Info";
/*  721 */         ret = new String(HexToByte(value));
/*      */ 
/*  723 */         break;
/*      */       case 78:
/*  726 */         name = "Configuration-Token";
/*  727 */         ret = new String(HexToByte(value));
/*      */ 
/*  729 */         break;
/*      */       case 79:
/*  732 */         name = "EAP-Message";
/*  733 */         ret = value;
/*      */ 
/*  735 */         break;
/*      */       case 80:
/*  738 */         name = "Message-Authenticator";
/*  739 */         ret = value;
/*      */ 
/*  741 */         break;
/*      */       case 84:
/*  744 */         name = "ARAP-Challenge-Response";
/*  745 */         ret = new String(HexToByte(value));
/*      */ 
/*  747 */         break;
/*      */       case 85:
/*  750 */         name = "Acct-Interim-Interval";
/*  751 */         ret = Long.toString(ByteToLong(HexToByte(value)));
/*      */ 
/*  753 */         break;
/*      */       case 87:
/*  756 */         name = "NAS-Port-Id";
/*  757 */         ret = new String(HexToByte(value));
/*      */ 
/*  759 */         break;
/*      */       case 88:
/*  762 */         name = "Framed-Pool";
/*  763 */         ret = new String(HexToByte(value));
/*      */ 
/*  765 */         break;
/*      */       case 95:
/*  768 */         name = "NAS-IPv6-Address";
/*  769 */         ret = value;
/*      */ 
/*  771 */         break;
/*      */       case 96:
/*  774 */         name = "Framed-Interface-Id";
/*  775 */         ret = value;
/*      */ 
/*  777 */         break;
/*      */       case 97:
/*  780 */         name = "Framed-IPv6-Prefix";
/*  781 */         ret = value;
/*      */ 
/*  783 */         break;
/*      */       case 98:
/*  786 */         name = "Login-IPv6-Host";
/*  787 */         ret = value;
/*      */ 
/*  789 */         break;
/*      */       case 99:
/*  792 */         name = "Framed-IPv6-Route";
/*  793 */         ret = new String(HexToByte(value));
/*      */ 
/*  795 */         break;
/*      */       case 100:
/*  798 */         name = "Framed-IPv6-Pool";
/*  799 */         ret = new String(HexToByte(value));
/*      */ 
/*  801 */         break;
/*      */       case 206:
/*  804 */         name = "Digest-Response";
/*  805 */         ret = new String(HexToByte(value));
/*      */ 
/*  807 */         break;
/*      */       case 207:
/*  810 */         name = "Digest-Attributes";
/*  811 */         ret = value;
/*      */ 
/*  813 */         break;
/*      */       default:
/*  815 */         name = "";
/*  816 */         ret = value;
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*  822 */     if (ret == null)
/*  823 */       ret = value;
/*  824 */     writeLog(ip, ">> " + name + "(" + type + ")=" + ret);
/*  825 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getServiceType(int value) {
/*  829 */     String ret = null;
/*      */     try {
/*  831 */       switch (value)
/*      */       {
/*      */       case 1:
/*  834 */         ret = "Login-User";
/*      */ 
/*  836 */         break;
/*      */       case 2:
/*  839 */         ret = "Framed-User";
/*      */ 
/*  841 */         break;
/*      */       case 3:
/*  844 */         ret = "Callback-Login-User";
/*      */ 
/*  846 */         break;
/*      */       case 4:
/*  849 */         ret = "Callback-Framed-User";
/*      */ 
/*  851 */         break;
/*      */       case 5:
/*  854 */         ret = "Outbound-User";
/*      */ 
/*  856 */         break;
/*      */       case 6:
/*  859 */         ret = "Administrative-User";
/*      */ 
/*  861 */         break;
/*      */       case 7:
/*  864 */         ret = "NAS-Prompt-User";
/*      */ 
/*  866 */         break;
/*      */       case 8:
/*  869 */         ret = "Authenticate-Only";
/*      */ 
/*  871 */         break;
/*      */       case 9:
/*  874 */         ret = "Callback-NAS-Prompt";
/*      */ 
/*  876 */         break;
/*      */       case 10:
/*  879 */         ret = "Call-Check";
/*      */ 
/*  881 */         break;
/*      */       case 11:
/*  884 */         ret = "Callback-Administrative";
/*      */ 
/*  886 */         break;
/*      */       case 12:
/*  889 */         ret = "Voice";
/*      */ 
/*  891 */         break;
/*      */       case 13:
/*  894 */         ret = "Fax";
/*      */ 
/*  896 */         break;
/*      */       case 14:
/*  899 */         ret = "Modem-Relay";
/*      */ 
/*  901 */         break;
/*      */       case 15:
/*  904 */         ret = "IAPP-Register";
/*      */ 
/*  906 */         break;
/*      */       case 16:
/*  909 */         ret = "IAPP-AP-Check";
/*      */ 
/*  911 */         break;
/*      */       default:
/*  913 */         ret = "";
/*      */       }
/*      */ 
/*  917 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/*  920 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getFramedProtocol(int value) {
/*  924 */     String ret = null;
/*      */     try {
/*  926 */       switch (value)
/*      */       {
/*      */       case 1:
/*  929 */         ret = "PPP";
/*      */ 
/*  931 */         break;
/*      */       case 2:
/*  934 */         ret = "SLIP";
/*      */ 
/*  936 */         break;
/*      */       case 3:
/*  939 */         ret = "ARAP";
/*      */ 
/*  941 */         break;
/*      */       case 4:
/*  944 */         ret = "Gandalf-SLML";
/*      */ 
/*  946 */         break;
/*      */       case 5:
/*  949 */         ret = "Xylogics-IPX-SLIP";
/*      */ 
/*  951 */         break;
/*      */       case 6:
/*  954 */         ret = "X.75-Synchronous";
/*      */ 
/*  956 */         break;
/*      */       case 7:
/*  959 */         ret = "GPRS-PDP-Context";
/*      */ 
/*  961 */         break;
/*      */       default:
/*  963 */         ret = "";
/*      */       }
/*      */ 
/*  967 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/*  970 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getFramedRouting(int value) {
/*  974 */     String ret = null;
/*      */     try {
/*  976 */       switch (value)
/*      */       {
/*      */       case 0:
/*  979 */         ret = "None";
/*      */ 
/*  981 */         break;
/*      */       case 1:
/*  984 */         ret = "Broadcast";
/*      */ 
/*  986 */         break;
/*      */       case 2:
/*  989 */         ret = "Listen";
/*      */ 
/*  991 */         break;
/*      */       case 3:
/*  994 */         ret = "Broadcast-Listen";
/*      */ 
/*  996 */         break;
/*      */       default:
/*  998 */         ret = "";
/*      */       }
/*      */ 
/* 1002 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1005 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getFramedCompression(int value) {
/* 1009 */     String ret = null;
/*      */     try {
/* 1011 */       switch (value)
/*      */       {
/*      */       case 0:
/* 1014 */         ret = "None";
/*      */ 
/* 1016 */         break;
/*      */       case 1:
/* 1019 */         ret = "Van-Jacobson-TCP-IP";
/*      */ 
/* 1021 */         break;
/*      */       case 2:
/* 1024 */         ret = "IPX-Header-Compression";
/*      */ 
/* 1026 */         break;
/*      */       case 3:
/* 1029 */         ret = "Stac-LZS";
/*      */ 
/* 1031 */         break;
/*      */       default:
/* 1033 */         ret = "";
/*      */       }
/*      */ 
/* 1037 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1040 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getLoginService(int value) {
/* 1044 */     String ret = null;
/*      */     try {
/* 1046 */       switch (value)
/*      */       {
/*      */       case 0:
/* 1049 */         ret = "Telnet";
/*      */ 
/* 1051 */         break;
/*      */       case 1:
/* 1054 */         ret = "Rlogin";
/*      */ 
/* 1056 */         break;
/*      */       case 2:
/* 1059 */         ret = "TCP-Clear";
/*      */ 
/* 1061 */         break;
/*      */       case 3:
/* 1064 */         ret = "PortMaster";
/*      */ 
/* 1066 */         break;
/*      */       case 4:
/* 1069 */         ret = "LAT";
/*      */ 
/* 1071 */         break;
/*      */       case 5:
/* 1074 */         ret = "X25-PAD";
/*      */ 
/* 1076 */         break;
/*      */       case 6:
/* 1079 */         ret = "X25-T3POS";
/*      */ 
/* 1081 */         break;
/*      */       case 7:
/* 1084 */         ret = "TCP-Clear-Quiet";
/*      */ 
/* 1086 */         break;
/*      */       default:
/* 1088 */         ret = "";
/*      */       }
/*      */ 
/* 1092 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1095 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getAcctStatusType(int value) {
/* 1099 */     String ret = null;
/*      */     try {
/* 1101 */       switch (value)
/*      */       {
/*      */       case 1:
/* 1104 */         ret = "Start";
/*      */ 
/* 1106 */         break;
/*      */       case 2:
/* 1109 */         ret = "Stop";
/*      */ 
/* 1111 */         break;
/*      */       case 3:
/* 1114 */         ret = "Interim-Update";
/*      */ 
/* 1116 */         break;
/*      */       case 4:
/* 1119 */         ret = "Alive";
/*      */ 
/* 1121 */         break;
/*      */       case 7:
/* 1124 */         ret = "Accounting-On";
/*      */ 
/* 1126 */         break;
/*      */       case 8:
/* 1129 */         ret = "Accounting-Off";
/*      */ 
/* 1131 */         break;
/*      */       case 9:
/* 1134 */         ret = "Tunnel-Start";
/*      */ 
/* 1136 */         break;
/*      */       case 10:
/* 1139 */         ret = "Tunnel-Stop";
/*      */ 
/* 1141 */         break;
/*      */       case 11:
/* 1144 */         ret = "Tunnel-Reject";
/*      */ 
/* 1146 */         break;
/*      */       case 12:
/* 1149 */         ret = "Tunnel-Link-Start";
/*      */ 
/* 1151 */         break;
/*      */       case 13:
/* 1154 */         ret = "Tunnel-Link-Stop";
/*      */ 
/* 1156 */         break;
/*      */       case 14:
/* 1159 */         ret = "Tunnel-Link-Reject";
/*      */ 
/* 1161 */         break;
/*      */       case 15:
/* 1164 */         ret = "Failed";
/*      */ 
/* 1166 */         break;
/*      */       case 5:
/*      */       case 6:
/*      */       default:
/* 1168 */         ret = "";
/*      */       }
/*      */ 
/* 1172 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1175 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getAcctAuthentic(int value) {
/* 1179 */     String ret = null;
/*      */     try {
/* 1181 */       switch (value)
/*      */       {
/*      */       case 1:
/* 1184 */         ret = "RADIUS";
/*      */ 
/* 1186 */         break;
/*      */       case 2:
/* 1189 */         ret = "Local";
/*      */ 
/* 1191 */         break;
/*      */       case 3:
/* 1194 */         ret = "Remote";
/*      */ 
/* 1196 */         break;
/*      */       case 4:
/* 1199 */         ret = "Diameter";
/*      */ 
/* 1201 */         break;
/*      */       default:
/* 1203 */         ret = "";
/*      */       }
/*      */ 
/* 1207 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1210 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getTerminationAction(int value) {
/* 1214 */     String ret = null;
/*      */     try {
/* 1216 */       switch (value)
/*      */       {
/*      */       case 0:
/* 1219 */         ret = "Default";
/*      */ 
/* 1221 */         break;
/*      */       case 1:
/* 1224 */         ret = "RADIUS-Request";
/*      */ 
/* 1226 */         break;
/*      */       default:
/* 1228 */         ret = "";
/*      */       }
/*      */ 
/* 1232 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1235 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getNASPortType(int value) {
/* 1239 */     String ret = null;
/*      */     try {
/* 1241 */       switch (value)
/*      */       {
/*      */       case 0:
/* 1244 */         ret = "Async";
/*      */ 
/* 1246 */         break;
/*      */       case 1:
/* 1249 */         ret = "Sync";
/*      */ 
/* 1251 */         break;
/*      */       case 2:
/* 1254 */         ret = "ISDN";
/*      */ 
/* 1256 */         break;
/*      */       case 3:
/* 1259 */         ret = "ISDN-V120";
/*      */ 
/* 1261 */         break;
/*      */       case 4:
/* 1264 */         ret = "ISDN-V110";
/*      */ 
/* 1266 */         break;
/*      */       case 5:
/* 1269 */         ret = "Virtual";
/*      */ 
/* 1271 */         break;
/*      */       case 6:
/* 1274 */         ret = "PIAFS";
/*      */ 
/* 1276 */         break;
/*      */       case 7:
/* 1279 */         ret = "HDLC-Clear-Channel";
/*      */ 
/* 1281 */         break;
/*      */       case 8:
/* 1284 */         ret = "X.25";
/*      */ 
/* 1286 */         break;
/*      */       case 9:
/* 1289 */         ret = "X.75";
/*      */ 
/* 1291 */         break;
/*      */       case 10:
/* 1294 */         ret = "G.3-Fax";
/*      */ 
/* 1296 */         break;
/*      */       case 11:
/* 1299 */         ret = "SDSL";
/*      */ 
/* 1301 */         break;
/*      */       case 12:
/* 1304 */         ret = "ADSL-CAP";
/*      */ 
/* 1306 */         break;
/*      */       case 13:
/* 1309 */         ret = "ADSL-DMT";
/*      */ 
/* 1311 */         break;
/*      */       case 14:
/* 1314 */         ret = "IDSL";
/*      */ 
/* 1316 */         break;
/*      */       case 15:
/* 1319 */         ret = "Ethernet";
/*      */ 
/* 1321 */         break;
/*      */       case 16:
/* 1324 */         ret = "xDSL";
/*      */ 
/* 1326 */         break;
/*      */       case 17:
/* 1329 */         ret = "Cable";
/*      */ 
/* 1331 */         break;
/*      */       case 18:
/* 1334 */         ret = "Wireless-Other";
/*      */ 
/* 1336 */         break;
/*      */       case 19:
/* 1339 */         ret = "Wireless-802.11";
/*      */ 
/* 1341 */         break;
/*      */       case 20:
/* 1344 */         ret = "Token-Ring";
/*      */ 
/* 1346 */         break;
/*      */       case 21:
/* 1349 */         ret = "FDDI";
/*      */ 
/* 1351 */         break;
/*      */       case 22:
/* 1354 */         ret = "Wireless-CDMA2000";
/*      */ 
/* 1356 */         break;
/*      */       case 23:
/* 1359 */         ret = "Wireless-UMTS";
/*      */ 
/* 1361 */         break;
/*      */       case 24:
/* 1364 */         ret = "Wireless-1X-EV";
/*      */ 
/* 1366 */         break;
/*      */       case 25:
/* 1369 */         ret = "IAPP";
/*      */ 
/* 1371 */         break;
/*      */       default:
/* 1373 */         ret = "";
/*      */       }
/*      */ 
/* 1377 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1380 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getAcctTerminateCause(int value) {
/* 1384 */     String ret = null;
/*      */     try {
/* 1386 */       switch (value)
/*      */       {
/*      */       case 1:
/* 1389 */         ret = "User-Request";
/*      */ 
/* 1391 */         break;
/*      */       case 2:
/* 1394 */         ret = "Lost-Carrier";
/*      */ 
/* 1396 */         break;
/*      */       case 3:
/* 1399 */         ret = "Lost-Service";
/*      */ 
/* 1401 */         break;
/*      */       case 4:
/* 1404 */         ret = "Idle-Timeout";
/*      */ 
/* 1406 */         break;
/*      */       case 5:
/* 1409 */         ret = "Session-Timeout";
/*      */ 
/* 1411 */         break;
/*      */       case 6:
/* 1414 */         ret = "Admin-Reset";
/*      */ 
/* 1416 */         break;
/*      */       case 7:
/* 1419 */         ret = "Admin-Reboot";
/*      */ 
/* 1421 */         break;
/*      */       case 8:
/* 1424 */         ret = "Port-Error";
/*      */ 
/* 1426 */         break;
/*      */       case 9:
/* 1429 */         ret = "NAS-Error";
/*      */ 
/* 1431 */         break;
/*      */       case 10:
/* 1434 */         ret = "NAS-Request";
/*      */ 
/* 1436 */         break;
/*      */       case 11:
/* 1439 */         ret = "NAS-Reboot";
/*      */ 
/* 1441 */         break;
/*      */       case 12:
/* 1444 */         ret = "Port-Unneeded";
/*      */ 
/* 1446 */         break;
/*      */       case 13:
/* 1449 */         ret = "Port-Preempted";
/*      */ 
/* 1451 */         break;
/*      */       case 14:
/* 1454 */         ret = "Port-Suspended";
/*      */ 
/* 1456 */         break;
/*      */       case 15:
/* 1459 */         ret = "Service-Unavailable";
/*      */ 
/* 1461 */         break;
/*      */       case 16:
/* 1464 */         ret = "Callback";
/*      */ 
/* 1466 */         break;
/*      */       case 17:
/* 1469 */         ret = "User-Error";
/*      */ 
/* 1471 */         break;
/*      */       case 18:
/* 1474 */         ret = "Host-Request";
/*      */ 
/* 1476 */         break;
/*      */       case 19:
/* 1479 */         ret = "Supplicant-Restart";
/*      */ 
/* 1481 */         break;
/*      */       case 20:
/* 1484 */         ret = "Reauthentication-Failure";
/*      */ 
/* 1486 */         break;
/*      */       case 21:
/* 1489 */         ret = "Port-Reinit";
/*      */ 
/* 1491 */         break;
/*      */       case 22:
/* 1494 */         ret = "Port-Disabled";
/*      */ 
/* 1496 */         break;
/*      */       default:
/* 1498 */         ret = "";
/*      */       }
/*      */ 
/* 1502 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1505 */     return ret;
/*      */   }
/*      */ 
/*      */   private static String getPrompt(int value) {
/* 1509 */     String ret = null;
/*      */     try {
/* 1511 */       switch (value)
/*      */       {
/*      */       case 0:
/* 1514 */         ret = "No-Echo";
/*      */ 
/* 1516 */         break;
/*      */       case 1:
/* 1519 */         ret = "Echo";
/*      */ 
/* 1521 */         break;
/*      */       default:
/* 1523 */         ret = "";
/*      */       }
/*      */ 
/* 1527 */       ret = ret + "(" + value + ")";
/*      */     } catch (Exception localException) {
/*      */     }
/* 1530 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeString(int type, String value) {
/* 1534 */     String ret = null;
/*      */     try {
/* 1536 */       String info = ByteToHex(value.getBytes());
/* 1537 */       String len = Integer.toHexString(2 + info.length() / 2);
/* 1538 */       while (len.length() < 2)
/* 1539 */         len = "0" + len;
/* 1540 */       String types = Integer.toHexString(type);
/* 1541 */       while (types.length() < 2)
/* 1542 */         types = "0" + types;
/* 1543 */       ret = types + len + info;
/*      */     } catch (Exception localException) {
/*      */     }
/* 1546 */     return ret;
/*      */   }
/*      */ 
/*      */   public static int getAttributeStringLen(String value) {
/* 1550 */     int len = 0;
/*      */     try {
/* 1552 */       String info = ByteToHex(value.getBytes());
/* 1553 */       len = 2 + info.length() / 2;
/*      */     } catch (Exception localException) {
/*      */     }
/* 1556 */     return len;
/*      */   }
/*      */ 
/*      */   public static String getAttributeVendor(int type, int value, int valueLen)
/*      */   {
/* 1563 */     String ret = null;
/*      */     try
/*      */     {
/* 1566 */       byte[] b = new byte[4];
/* 1567 */       for (int i = 0; i < 4; i++) {
/* 1568 */         int offset = (b.length - 1 - i) * 8;
/* 1569 */         b[i] = ((byte)(value >>> offset & 0xFF));
/*      */       }
/* 1571 */       String info = ByteToHex(b);
/* 1572 */       String len = Integer.toHexString(6 + valueLen);
/* 1573 */       while (len.length() < 2)
/* 1574 */         len = "0" + len;
/* 1575 */       String types = Integer.toHexString(type);
/* 1576 */       while (types.length() < 2)
/* 1577 */         types = "0" + types;
/* 1578 */       ret = types + len + info;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1583 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeVendor(int type, int value) {
/* 1587 */     String ret = null;
/*      */     try
/*      */     {
/* 1590 */       byte[] b = new byte[4];
/* 1591 */       for (int i = 0; i < 4; i++) {
/* 1592 */         int offset = (b.length - 1 - i) * 8;
/* 1593 */         b[i] = ((byte)(value >>> offset & 0xFF));
/*      */       }
/* 1595 */       String info = ByteToHex(b);
/* 1596 */       String len = Integer.toHexString(12);
/* 1597 */       while (len.length() < 2)
/* 1598 */         len = "0" + len;
/* 1599 */       String types = Integer.toHexString(type);
/* 1600 */       while (types.length() < 2)
/* 1601 */         types = "0" + types;
/* 1602 */       ret = types + len + info;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1607 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeSpeed(int type, int value) {
/* 1611 */     String ret = null;
/*      */     try
/*      */     {
/* 1614 */       byte[] b = new byte[4];
/* 1615 */       for (int i = 0; i < 4; i++) {
/* 1616 */         int offset = (b.length - 1 - i) * 8;
/* 1617 */         b[i] = ((byte)(value >>> offset & 0xFF));
/*      */       }
/* 1619 */       String info = ByteToHex(b);
/* 1620 */       String len = Integer.toHexString(6);
/* 1621 */       while (len.length() < 2)
/* 1622 */         len = "0" + len;
/* 1623 */       String types = Integer.toHexString(type);
/* 1624 */       while (types.length() < 2)
/* 1625 */         types = "0" + types;
/* 1626 */       ret = types + len + info;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1631 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String getAttributeInt(int type, int value)
/*      */   {
/* 1637 */     String ret = null;
/*      */     try
/*      */     {
/* 1640 */       byte[] b = new byte[4];
/* 1641 */       for (int i = 0; i < 4; i++) {
/* 1642 */         int offset = (b.length - 1 - i) * 8;
/* 1643 */         b[i] = ((byte)(value >>> offset & 0xFF));
/*      */       }
/* 1645 */       String info = ByteToHex(b);
/* 1646 */       String len = Integer.toHexString(6);
/* 1647 */       while (len.length() < 2)
/* 1648 */         len = "0" + len;
/* 1649 */       String types = Integer.toHexString(type);
/* 1650 */       while (types.length() < 2)
/* 1651 */         types = "0" + types;
/* 1652 */       ret = types + len + info;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1657 */     return ret;
/*      */   }
/*      */ 
/*      */   public static byte[] getOutData(String name, String sharedSecret, String ip, int port, int code, int identifier, String authenticator, String attributes)
/*      */   {
/* 1663 */     byte[] ret = null;
/*      */     try {
/* 1665 */       int length = 20 + attributes.length() / 2;
/* 1666 */       String str = Integer.toHexString(code);
/* 1667 */       while (str.length() < 2)
/* 1668 */         str = "0" + str;
/* 1669 */       String temp = Integer.toHexString(identifier);
/* 1670 */       while (temp.length() < 2)
/* 1671 */         temp = "0" + temp;
/* 1672 */       str = str + temp;
/* 1673 */       temp = Integer.toHexString(length);
/* 1674 */       while (temp.length() < 4)
/* 1675 */         temp = "0" + temp;
/* 1676 */       str = str + temp;
/* 1677 */       authenticator = encodeMD5(str + authenticator + attributes + 
/* 1678 */         sharedSecret);
/* 1679 */       str = str + authenticator;
/* 1680 */       str = str + attributes;
/* 1681 */       ret = HexToByte(str);
/* 1682 */       writeLog(name, "ip=" + ip + ",port=" + port + ",code=" + code + 
/* 1683 */         ",identifier=" + identifier + ",length=" + length + 
/* 1684 */         ",authenticator=" + authenticator + ",attributes=" + 
/* 1685 */         attributes);
/*      */     } catch (Exception localException) {
/*      */     }
/* 1688 */     return ret;
/*      */   }
/*      */ 
/*      */   public static String sendServer(String ip, String url, String str) {
/* 1692 */     String ret = null;
/*      */     try {
/* 1694 */       str = url + "?" + str;
/* 1695 */       String result = sendURL(str);
/* 1696 */       writeLog(ip, str);
/* 1697 */       int i = result.indexOf("<result>");
/* 1698 */       if (i >= 0) {
/* 1699 */         i += 8;
/* 1700 */         int j = result.indexOf("</result>", i);
/* 1701 */         if (j > i) {
/* 1702 */           ret = result.substring(i, j).trim();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1712 */       writeLog(ip, "result=" + ret);
/*      */     } catch (Exception localException) {
/*      */     }
/* 1715 */     return ret;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.Tool
 * JD-Core Version:    0.6.2
 */