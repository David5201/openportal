package com.leeson.common.web.springmvc;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public final class MessageResolver
{
  public static String getMessage(HttpServletRequest request, String code, Object[] args)
  {
    WebApplicationContext messageSource = 
      RequestContextUtils.getWebApplicationContext(request);
    if (messageSource == null) {
      throw new IllegalStateException("WebApplicationContext not found!");
    }
    LocaleResolver localeResolver = 
      RequestContextUtils.getLocaleResolver(request);
    Locale locale;
    if (localeResolver != null)
      locale = localeResolver.resolveLocale(request);
    else {
      locale = request.getLocale();
    }
    return messageSource.getMessage(code, args, locale);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.MessageResolver
 * JD-Core Version:    0.6.2
 */