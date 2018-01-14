package com.leeson.common.web;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class WebErrors
{
  public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");

  public static final Pattern PHONE_PATTERN = Pattern.compile("^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
  public static final String ERROR_EMAIL = "邮箱格式不正确";
  public static final String ERROR_REQUIRED = "此处不能为空";
  private List<String> errors;

  public static void main(String[] args)
  {
    WebErrors we = new WebErrors();

    boolean vp = we.ifNotPhone("13465379415");
    System.out.println(vp);
  }

  public boolean ifNotEmail(String email)
  {
    if (!ifBlank(email)) {
      return false;
    }
    Matcher m = EMAIL_PATTERN.matcher(email);
    if (!m.matches()) {
      addErrorCode("邮箱格式不正确");
      return false;
    }
    return true;
  }

  public boolean ifNotPhone(String phone) {
    Matcher matcher = PHONE_PATTERN.matcher(phone);
    if (matcher.matches()) {
      return true;
    }
    return false;
  }

  public boolean ifBlank(String s) {
    if (StringUtils.isBlank(s)) {
      addErrorCode("此处不能为空");
      return false;
    }
    return true;
  }
  public boolean ifMaxLength(String s, int maxLength) {
    if ((s != null) && (s.length() > maxLength))
    {
      return true;
    }
    return false;
  }

  public void addErrorCode(String code)
  {
    getErrors().add(code);
  }

  public List<String> getErrors()
  {
    if (this.errors == null) {
      this.errors = new ArrayList();
    }
    return this.errors;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.WebErrors
 * JD-Core Version:    0.6.2
 */