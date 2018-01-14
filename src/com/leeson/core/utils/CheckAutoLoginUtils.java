package com.leeson.core.utils;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.portal.core.model.AutoLoginMacMap;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class CheckAutoLoginUtils
{
  private static Logger logger = Logger.getLogger(CheckAutoLoginUtils.class);

  public static void recordTime(String[] onlineInfo) {
    try {
      String ikmac = onlineInfo[4];
      String time = onlineInfo[3];

      Date loginTime = ThreadLocalDateUtil.parse(time);
      String nowString = ThreadLocalDateUtil.format(new Date());
      Date nowTime = ThreadLocalDateUtil.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());

      if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
        String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(ikmac);
        if (macTimeInfo != null) {
          Long oldcostTime = Long.valueOf(macTimeInfo[1]);
          macTimeInfo[0] = "";
          macTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());

          AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(ikmac, macTimeInfo);
        }
      }
    }
    catch (Exception e) {
      logger.error("==============异常开始=============");
      logger.error(e);
      logger.error("异常信息", e);
      logger.error("==============异常结束=============");
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.CheckAutoLoginUtils
 * JD-Core Version:    0.6.2
 */