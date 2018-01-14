package com.leeson.core.utils;

class MyTask
  implements Runnable
{
  private String host;

  public MyTask(String host)
  {
    this.host = host;
  }

  public void run()
  {
    Kick.kickUserByAdmin(this.host);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.MyTask
 * JD-Core Version:    0.6.2
 */