package com.leeson.core.utils;

import com.leeson.portal.core.model.PhoneCodeMap;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;

public class SMSVirtualRequest
{
  public static boolean send(String phone)
  {
    try
    {
      String yzm = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
      Object[] objs = new Object[2];
      objs[0] = yzm;
      objs[1] = new Date();
      PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
      return true;
    } catch (Exception e) {
      System.out.println("error" + e);
    }return false;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSVirtualRequest
 * JD-Core Version:    0.6.2
 */