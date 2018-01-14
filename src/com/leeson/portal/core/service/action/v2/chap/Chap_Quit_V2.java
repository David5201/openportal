package com.leeson.portal.core.service.action.v2.chap;

import com.leeson.portal.core.service.action.v2.PublicV2;

public class Chap_Quit_V2
{
  public static boolean quit(int type, String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, byte[] ReqID, String sharedSecret)
  {
    byte[] Req_Quit = new byte[32];
    Req_Quit[0] = 2;
    Req_Quit[1] = 5;
    Req_Quit[2] = 0;
    Req_Quit[3] = 0;
    Req_Quit[4] = SerialNo[0];
    Req_Quit[5] = SerialNo[1];
    Req_Quit[6] = ReqID[0];
    Req_Quit[7] = ReqID[1];
    Req_Quit[8] = UserIP[0];
    Req_Quit[9] = UserIP[1];
    Req_Quit[10] = UserIP[2];
    Req_Quit[11] = UserIP[3];
    Req_Quit[12] = 0;
    Req_Quit[13] = 0;
    Req_Quit[14] = 0;
    Req_Quit[15] = 0;
    return PublicV2.choose(type, Req_Quit, timeout_Sec, Bas_IP, bas_PORT, 
      sharedSecret);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.chap.Chap_Quit_V2
 * JD-Core Version:    0.6.2
 */