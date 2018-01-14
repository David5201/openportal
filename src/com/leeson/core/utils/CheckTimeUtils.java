package com.leeson.core.utils;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.UserLimitMap;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class CheckTimeUtils
{
  private static Logger logger = Logger.getLogger(CheckTimeUtils.class);

  public static void recordTime(String[] onlineInfo) {
    try {
      String phone = onlineInfo[0];
      String ikmac = onlineInfo[4];
      String time = onlineInfo[3];

      Date loginTime = ThreadLocalDateUtil.parse(time);
      String nowString = ThreadLocalDateUtil.format(new Date());
      Date nowTime = ThreadLocalDateUtil.parse(nowString);
      Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());

      String[] userTimeInfo = (String[])UserLimitMap.getInstance().getUserLimitMap().get(phone);
      if (userTimeInfo != null) {
        Long oldcostTime = Long.valueOf(userTimeInfo[1]);
        userTimeInfo[0] = "";
        userTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());
        userTimeInfo[2] = "0";
        UserLimitMap.getInstance().getUserLimitMap().put(phone, userTimeInfo);
      }

      if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
        String[] macTimeInfo = (String[])MacLimitMap.getInstance().getMacLimitMap().get(ikmac);
        if (macTimeInfo != null) {
          Long oldcostTime = Long.valueOf(macTimeInfo[1]);
          macTimeInfo[0] = "";
          macTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());
          macTimeInfo[2] = "0";
          MacLimitMap.getInstance().getMacLimitMap().put(ikmac, macTimeInfo);
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
 * Qualified Name:     com.leeson.core.utils.CheckTimeUtils
 * JD-Core Version:    0.6.2
 */