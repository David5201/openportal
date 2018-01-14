package com.alipay.util;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.leeson.core.bean.Payapi;
import com.leeson.core.service.PayapiService;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class AlipaySubmit
{
  private static PayapiService payapiService = (PayapiService)
    SpringContextHelper.getBean("payapiServiceImpl");
  private static final String ALIPAY_GATEWAY_NEW = "https:ateway.do?";

  public static String buildRequestMysign(Map<String, String> sPara)
  {
    String prestr = AlipayCore.createLinkString(sPara);
    String mysign = "";
    if (AlipayConfig.sign_type.equals("MD5")) {
      mysign = MD5.sign(prestr, payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayKey(), AlipayConfig.input_charset);
    }
    return mysign;
  }

  private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
  {
    Map sPara = AlipayCore.paraFilter(sParaTemp);

    String mysign = buildRequestMysign(sPara);

    sPara.put("sign", mysign);
    sPara.put("sign_type", AlipayConfig.sign_type);

    return sPara;
  }

  public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName)
  {
    Map sPara = buildRequestPara(sParaTemp);
    List keys = new ArrayList(sPara.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https:ateway.do?_input_charset=" + 
      AlipayConfig.input_charset + "\" method=\"" + strMethod + 
      "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);

      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

    return sbHtml.toString();
  }

  public static String query_timestamp()
    throws MalformedURLException, DocumentException, IOException
  {
    String strUrl = "https:ateway.do?service=query_timestamp&partner=" + payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayPartner() + "&_input_charset" + AlipayConfig.input_charset;
    StringBuffer result = new StringBuffer();

    SAXReader reader = new SAXReader();
    Document doc = reader.read(new URL(strUrl).openStream());

    List nodeList = doc.selectNodes("");
    Iterator localIterator2;
    label194: for (Iterator localIterator1 = nodeList.iterator(); localIterator1.hasNext(); 
      localIterator2.hasNext())
    {
      Node node = (Node)localIterator1.next();

      if ((!node.getName().equals("is_success")) || (!node.getText().equals("T")))
        break label194;
      List nodeList1 = doc.selectNodes("imestamp/*");
      localIterator2 = nodeList1.iterator();
				Node node1 = (Node)localIterator2.next();
      result.append(node1.getText());
				continue;
    }

    return result.toString();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.AlipaySubmit
 * JD-Core Version:    0.6.2
 */