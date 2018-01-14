package com.leeson.portal.core.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class kickAllThread
{
  private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 
    2147483647, 3L, TimeUnit.SECONDS, 
    new LinkedBlockingQueue());

  public static void kickAll(String host)
  {
    MyTask myTask = new MyTask(host);
    executor.execute(myTask);
  }

  public static void offThread() {
    executor.shutdown();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.kickAllThread
 * JD-Core Version:    0.6.2
 */