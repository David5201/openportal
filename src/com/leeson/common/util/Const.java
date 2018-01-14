package com.leeson.common.util;

import org.springframework.context.ApplicationContext;

public class Const
{
  public static final String SESSION_SECURITY_CODE = "sessionSecCode";
  public static final String SESSION_USER = "sessionUser";
  public static final String SESSION_USER_RIGHTS = "sessionUserRights";
  public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
  public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*";
  public static ApplicationContext WEB_APP_CONTEXT = null;
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.Const
 * JD-Core Version:    0.6.2
 */