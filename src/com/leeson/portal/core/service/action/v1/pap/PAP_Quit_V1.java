package com.leeson.portal.core.service.action.v1.pap;

import com.leeson.portal.core.service.action.v1.PublicV1;

public class PAP_Quit_V1
{
  public static boolean quit(int type, String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP)
  {
    byte[] Req_Quit = new byte[16];
    Req_Quit[0] = 1;
    Req_Quit[1] = 5;
    Req_Quit[2] = 1;
    Req_Quit[3] = 0;
    Req_Quit[4] = SerialNo[0];
    Req_Quit[5] = SerialNo[1];
    Req_Quit[6] = 0;
    Req_Quit[7] = 0;
    Req_Quit[8] = UserIP[0];
    Req_Quit[9] = UserIP[1];
    Req_Quit[10] = UserIP[2];
    Req_Quit[11] = UserIP[3];
    Req_Quit[12] = 0;
    Req_Quit[13] = 0;
    Req_Quit[14] = 0;
    Req_Quit[15] = 0;
    return PublicV1.choose(type, Req_Quit, timeout_Sec, Bas_IP, bas_PORT);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.pap.PAP_Quit_V1
 * JD-Core Version:    0.6.2
 */