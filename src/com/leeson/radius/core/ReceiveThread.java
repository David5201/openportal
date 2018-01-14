package com.leeson.radius.core;

import java.net.DatagramPacket;

public class ReceiveThread
  implements Runnable
{
  private RadiusServer radiusServer = null;

  private DatagramPacket in = null;

  public ReceiveThread(RadiusServer radiusServer, DatagramPacket in)
  {
    this.radiusServer = radiusServer;
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
      this.radiusServer.receive(this.in);
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.ReceiveThread
 * JD-Core Version:    0.6.2
 */