package com.wxpay.utils.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class TrustAnyTrustManager
  implements X509TrustManager
{
  public void checkClientTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {
  }

  public void checkServerTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {
  }

  public X509Certificate[] getAcceptedIssuers()
  {
    return null;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.http.TrustAnyTrustManager
 * JD-Core Version:    0.6.2
 */