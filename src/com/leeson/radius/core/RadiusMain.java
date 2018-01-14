package com.leeson.radius.core;

public class RadiusMain
{
  private static RadiusServer radiusServer = null;
  private static Thread radiusThread = null;

  private static RadiusAccountServer radiusAccountServer = null;
  private static Thread radiusAccountThread = null;

  public static void radiusServer()
  {
    new RadiusMain().start();
  }

  public Integer start()
  {
    try
    {
      radiusServer = new RadiusServer();
      radiusThread = new Thread(radiusServer);
      radiusThread.start();

      radiusAccountServer = new RadiusAccountServer();
      radiusAccountThread = new Thread(radiusAccountServer);
      radiusAccountThread.start();
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
    return null;
  }

  public static int stop(int exitCode)
  {
    try
    {
      radiusServer.stop();
      radiusAccountServer.stop();
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
    return exitCode;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusMain
 * JD-Core Version:    0.6.2
 */