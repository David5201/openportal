package AC.Server;

public class StartServer
{
  public static void main(String[] args)
  {
    new Thread()
    {
      public void run()
      {
        try {
          ACServer.openServer();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    .start();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Server.StartServer
 * JD-Core Version:    0.6.2
 */