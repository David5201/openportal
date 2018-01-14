/*     */ package AC.Server;
/*     */ 
/*     */ import AC.Actions.Ack_Auth;
/*     */ import AC.Actions.Ack_Challenge;
/*     */ import AC.Actions.Ack_InfoH3C;
/*     */ import AC.Actions.Ack_Quit;
/*     */ import AC.Actions.Ack_Unknow;
/*     */ import AC.Utils.WR;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.io.PrintStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ACServer extends Thread
/*     */ {
/*  51 */   private static Config config = Config.getInstance();
/*  52 */   private static Logger log = Logger.getLogger(ACServer.class);
/*     */ 
/*  54 */   private static Boolean isRun = Boolean.valueOf(true);
/*  55 */   private static DatagramSocket socket = null;
/*     */ 
/*  57 */   DatagramPacket data = null;
/*     */ 
/*     */   public ACServer(DatagramPacket data)
/*     */   {
/*  61 */     this.data = data;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  66 */     System.out.println("Rececie Packet Size: " + this.data.getLength());
/*  67 */     byte[] Req_Data_Base = new byte[this.data.getLength()];
/*  68 */     for (int l = 0; l < Req_Data_Base.length; l++) {
/*  69 */       Req_Data_Base[l] = this.data.getData()[l];
/*     */     }
/*     */ 
/*  73 */     String ip = this.data.getAddress().getHostAddress();
/*  74 */     int port = this.data.getPort();
/*     */ 
/*  76 */     byte[] Ver = new byte[1];
/*  77 */     byte[] Type = new byte[1];
/*  78 */     byte[] Mod = new byte[1];
/*  79 */     byte[] Rsvd = new byte[1];
/*  80 */     byte[] SerialNo = new byte[2];
/*  81 */     byte[] ReqID = new byte[2];
/*  82 */     byte[] UserIP = new byte[4];
/*  83 */     byte[] UserPort = new byte[2];
/*  84 */     byte[] ErrCode = new byte[1];
/*  85 */     byte[] AttrNum = new byte[1];
/*     */ 
/*  88 */     Ver[0] = Req_Data_Base[0];
/*  89 */     Type[0] = Req_Data_Base[1];
/*  90 */     Mod[0] = Req_Data_Base[2];
/*  91 */     Rsvd[0] = Req_Data_Base[3];
/*  92 */     SerialNo[0] = Req_Data_Base[4];
/*  93 */     SerialNo[1] = Req_Data_Base[5];
/*  94 */     ReqID[0] = Req_Data_Base[6];
/*  95 */     ReqID[1] = Req_Data_Base[7];
/*  96 */     UserIP[0] = Req_Data_Base[8];
/*  97 */     UserIP[1] = Req_Data_Base[9];
/*  98 */     UserIP[2] = Req_Data_Base[10];
/*  99 */     UserIP[3] = Req_Data_Base[11];
/* 100 */     UserPort[0] = Req_Data_Base[12];
/* 101 */     UserPort[1] = Req_Data_Base[13];
/* 102 */     ErrCode[0] = Req_Data_Base[14];
/* 103 */     AttrNum[0] = Req_Data_Base[15];
/*     */ 
/* 105 */     if ((Ver[0] & 0xFF) == 0) {
/* 106 */       Ack_Unknow.Ack(ip, port, Req_Data_Base);
/* 107 */       WR.space();
/*     */     }
/*     */     else
/*     */     {
/* 112 */       String username = null;
/* 113 */       String userpass = null;
/* 114 */       String chappass = null;
/* 115 */       int pos = 0;
/* 116 */       if ((Ver[0] & 0xFF) == 1) {
/* 117 */         pos = 16;
/*     */       }
/* 119 */       if ((Ver[0] & 0xFF) == 2) {
/* 120 */         pos = 32;
/*     */       }
/*     */ 
/* 123 */       int AN = AttrNum[0] & 0xFF;
/* 124 */       if (AN > 0) {
/* 125 */         int num = 1;
/* 126 */         while (num <= AN) {
/* 127 */           int type = Req_Data_Base[pos] & 0xFF;
/* 128 */           pos++;
/* 129 */           int len = (Req_Data_Base[pos] & 0xFF) - 2;
/* 130 */           pos++;
/* 131 */           byte[] buf = new byte[len];
/* 132 */           for (int l = 0; l < buf.length; l++) {
/* 133 */             buf[l] = Req_Data_Base[(pos + l)];
/*     */           }
/* 135 */           pos += len;
/* 136 */           if (type == 1)
/* 137 */             username = new String(buf);
/* 138 */           else if (type == 2)
/* 139 */             userpass = new String(buf);
/* 140 */           else if (type == 4) {
/* 141 */             chappass = WR.Getbyte2HexString(buf);
/*     */           }
/* 143 */           num++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 159 */       if ((Type[0] & 0xFF) == 9) {
/* 160 */         Ack_InfoH3C.Info(ip, port, Req_Data_Base);
/*     */       }
/* 164 */       else if ((Type[0] & 0xFF) == 1) {
/* 165 */         Ack_Challenge.Challenge(ip, port, Req_Data_Base);
/*     */       }
/* 169 */       else if ((Type[0] & 0xFF) == 3) {
/* 170 */         Show_User(Mod, username, userpass, chappass);
/* 171 */         Ack_Auth.Auth(ip, port, Req_Data_Base);
/*     */       }
/* 175 */       else if (((Type[0] & 0xFF) == 5) && ((ErrCode[0] & 0xFF) == 0)) {
/* 176 */         Ack_Quit.Quit(ip, port, Req_Data_Base);
/*     */       }
/* 180 */       else if (((Type[0] & 0xFF) == 5) && ((ErrCode[0] & 0xFF) == 1))
/*     */       {
/* 182 */         if (((ReqID[0] & 0xFF) == 0) && 
/* 183 */           ((ReqID[1] & 0xFF) == 0))
/* 184 */           System.out.println("Challenge request TimeOut Request OR Logout Requst TimeOut Request !!");
/* 185 */         else if (((ReqID[0] & 0xFF) != 0) || ((ReqID[1] & 0xFF) != 0))
/* 186 */           System.out.println("Auth request TimeOut Request !!");
/*     */         else {
/* 188 */           System.out.println("Other request TimeOut Request !!");
/*     */         }
/*     */       }
/* 191 */       else if ((Type[0] & 0xFF) == 7) {
/* 192 */         System.out.println("Rececie " + ip + ":" + port + ":" + "Auth Success Packet " + 
/* 193 */           WR.Getbyte2HexString(Req_Data_Base));
/* 194 */         if ((Req_Data_Base[15] & 0xFF) == 0) {
/* 195 */           System.out.println("Host: " + ip + " Login Success !!");
/*     */         }
/* 197 */         WR.space();
/*     */       }
/*     */       else
/*     */       {
/* 201 */         Ack_Unknow.Ack(ip, port, Req_Data_Base);
/* 202 */         WR.space();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void Show_User(byte[] Mod, String username, String userpass, String chappass)
/*     */   {
/* 211 */     System.out.println("Username: " + username);
/* 212 */     if ((Mod[0] & 0xFF) == 0)
/* 213 */       System.out.println("CHAP-Password: " + chappass);
/* 214 */     else if ((Mod[0] & 0xFF) == 1)
/* 215 */       System.out.println("PAP Password: " + userpass);
/*     */   }
/*     */ 
/*     */   public static void openServer()
/*     */   {
/*     */     try {
/* 221 */       socket = new DatagramSocket(2000);
/* 222 */       System.out.println("AC Service Start OK , Listen Portal UDP 2000 !!");
/* 223 */       WR.space();
/* 224 */       while (isRun.booleanValue()) {
/* 225 */         byte[] b = new byte[100];
/* 226 */         DatagramPacket data = new DatagramPacket(b, b.length);
/* 227 */         socket.receive(data);
/* 228 */         new ACServer(data).start();
/*     */       }
/*     */     } catch (Exception e) {
/* 231 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 232 */         log.error("==============ERROR Start=============");
/* 233 */         log.error(e);
/* 234 */         log.error("ERROR INFO ", e);
/* 235 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeServer()
/*     */   {
/*     */     try
/*     */     {
/* 244 */       isRun = Boolean.valueOf(false);
/* 245 */       if (socket != null) {
/* 246 */         socket.close();
/* 247 */         socket = null;
/*     */       }
/*     */     } catch (Exception e) {
/* 250 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 251 */         log.error("==============ERROR Start=============");
/* 252 */         log.error(e);
/* 253 */         log.error("ERROR INFO ", e);
/* 254 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Server.ACServer
 * JD-Core Version:    0.6.2
 */