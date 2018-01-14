package com.leeson.core.utils;

import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;

public class SMSAliRequest
{
  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String url, String phone, String appkey, String secret, String smsFreeSignName, String smsTemplateCode, String company, Long id)
  {
    try
    {
      String text = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
      AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
      req.setExtend("");
      req.setSmsType("normal");
      req.setSmsFreeSignName(smsFreeSignName);
      req.setSmsParamString("{code:'" + text + "'}");
      req.setSmsParamString("{code:'" + text + "',product:'" + company + "'}");
      req.setRecNum(phone);
      req.setSmsTemplateCode(smsTemplateCode);
      AlibabaAliqinFcSmsNumSendResponse rsp = (AlibabaAliqinFcSmsNumSendResponse)client.execute(req);
      String result = rsp.getBody();

      System.out.println(result);

      if ((!result.contains("error_response")) && (result.contains("\"success\":true"))) {
        Object[] objs = new Object[2];
        objs[0] = text;
        objs[1] = new Date();
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("3");
        smslogsService.addPortalsmslogs(smslogs);
        return true;
      }
      return false;
    }
    catch (Exception e)
    {
      System.out.println("error" + e);
    }return false;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSAliRequest
 * JD-Core Version:    0.6.2
 */