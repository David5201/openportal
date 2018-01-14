package com.leeson.radius.core;

import java.net.DatagramPacket;

public class ReceiveAccountThread
  implements Runnable
{
  private RadiusAccountServer radiusAccountServer = null;

  private DatagramPacket in = null;

  public ReceiveAccountThread(RadiusAccountServer radiusAccountServer, DatagramPacket in)
  {
    this.radiusAccountServer = radiusAccountServer;
    this.in = in;
    try
    {
      new Thread(this).start();
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
  }

  public void run()
  {
    try
    {
      this.radiusAccountServer.receive(this.in);
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.ReceiveAccountThread
 * JD-Core Version:    0.6.2
 */