package com.leeson.common.utils.sysinfo;

public class Bytes
{
  public static String substring(String src, int start_idx, int end_idx)
  {
    byte[] b = src.getBytes();
    String tgt = "";
    for (int i = start_idx; i <= end_idx; i++) {
      tgt = tgt + (char)b[i];
    }
    return tgt;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.sysinfo.Bytes
 * JD-Core Version:    0.6.2
 */