package ikuaiAPI;

import java.io.PrintStream;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ikuaiAPIService
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent arg0)
  {
    System.out.println("IkuaiAPI Service Stop !!");
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    new Thread()
    {
      public void run()
      {
        try {
          RoamAutoAuth.openServer();
        }
        catch (Exception e) {
          System.out.println("IkuaiAPI Service Start Error : " + e);
        }
      }
    }
    .start();

    System.out.println("IkuaiAPI Service Start !!");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.ikuaiAPIService
 * JD-Core Version:    0.6.2
 */