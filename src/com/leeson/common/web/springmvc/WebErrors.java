package com.leeson.common.web.springmvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public abstract class WebErrors
{
  public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");

  public static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");
  private MessageSource messageSource;
  private Locale locale;
  private List<String> errors;

  public WebErrors(HttpServletRequest request)
  {
    WebApplicationContext webApplicationContext = 
      RequestContextUtils.getWebApplicationContext(request);
    if (webApplicationContext != null) {
      LocaleResolver localeResolver = 
        RequestContextUtils.getLocaleResolver(request);

      if (localeResolver != null) {
        Locale locale = localeResolver.resolveLocale(request);
        this.messageSource = webApplicationContext;
        this.locale = locale;
      }
    }
  }

  public WebErrors()
  {
  }

  public WebErrors(MessageSource messageSource, Locale locale)
  {
    this.messageSource = messageSource;
    this.locale = locale;
  }

  public String getMessage(String code, Object[] args) {
    if (this.messageSource == null) {
      throw new IllegalStateException("MessageSource cannot be null.");
    }
    return this.messageSource.getMessage(code, args, this.locale);
  }

  public void addErrorCode(String code, Object[] args)
  {
    getErrors().add(getMessage(code, args));
  }

  public void addErrorCode(String code)
  {
    getErrors().add(getMessage(code, new Object[0]));
  }

  public void addErrorString(String error)
  {
    getErrors().add(error);
  }

  public void addError(String error)
  {
    if (this.messageSource != null) {
      error = this.messageSource.getMessage(error, null, error, this.locale);
    }
    getErrors().add(error);
  }

  public boolean hasErrors()
  {
    return (this.errors != null) && (this.errors.size() > 0);
  }

  public int getCount()
  {
    return this.errors == null ? 0 : this.errors.size();
  }

  public List<String> getErrors()
  {
    if (this.errors == null) {
      this.errors = new ArrayList();
    }
    return this.errors;
  }

  public String showErrorPage(ModelMap model)
  {
    toModel(model);
    return getErrorPage();
  }

  public void toModel(Map<String, Object> model)
  {
    Assert.notNull(model);
    if (!hasErrors()) {
      throw new IllegalStateException("no errors found!");
    }
    model.put(getErrorAttrName(), getErrors());
  }

  public boolean ifNull(Object o, String field) {
    if (o == null) {
      addErrorCode("error.required", new Object[] { field });
      return true;
    }
    return false;
  }

  public boolean ifEmpty(Object[] o, String field)
  {
    if ((o == null) || (o.length <= 0)) {
      addErrorCode("error.required", new Object[] { field });
      return true;
    }
    return false;
  }

  public boolean ifBlank(String s, String field, int maxLength)
  {
    if (StringUtils.isBlank(s)) {
      addErrorCode("error.required", new Object[] { field });
      return true;
    }
    if (ifMaxLength(s, field, maxLength)) {
      return true;
    }
    return false;
  }

  public boolean ifMaxLength(String s, String field, int maxLength) {
    if ((s != null) && (s.length() > maxLength)) {
      addErrorCode("error.maxLength", new Object[] { field, Integer.valueOf(maxLength) });
      return true;
    }
    return false;
  }

  public boolean ifOutOfLength(String s, String field, int minLength, int maxLength)
  {
    if (s == null) {
      addErrorCode("error.required", new Object[] { field });
      return true;
    }
    int len = s.length();
    if ((len < minLength) || (len > maxLength)) {
      addErrorCode("error.outOfLength", new Object[] { field, Integer.valueOf(minLength), Integer.valueOf(maxLength) });
      return true;
    }
    return false;
  }

  public boolean ifNotEmail(String email, String field, int maxLength) {
    if (ifBlank(email, field, maxLength)) {
      return true;
    }
    Matcher m = EMAIL_PATTERN.matcher(email);
    if (!m.matches()) {
      addErrorCode("error.email", new Object[] { field });
      return true;
    }
    return false;
  }

  public boolean ifNotUsername(String username, String field, int minLength, int maxLength)
  {
    if (ifOutOfLength(username, field, minLength, maxLength)) {
      return true;
    }
    Matcher m = USERNAME_PATTERN.matcher(username);
    if (!m.matches()) {
      addErrorCode("error.username", new Object[] { field });
      return true;
    }
    return false;
  }

  public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
    if (o == null) {
      addErrorCode("error.notExist", new Object[] { clazz.getSimpleName(), id });
      return true;
    }
    return false;
  }

  public void noPermission(Class<?> clazz, Serializable id)
  {
    addErrorCode("error.noPermission", new Object[] { clazz.getSimpleName(), id });
  }

  public MessageSource getMessageSource()
  {
    return this.messageSource;
  }

  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public Locale getLocale()
  {
    return this.locale;
  }

  public void setLocale(Locale locale)
  {
    this.locale = locale;
  }

  protected abstract String getErrorPage();

  protected abstract String getErrorAttrName();
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.WebErrors
 * JD-Core Version:    0.6.2
 */