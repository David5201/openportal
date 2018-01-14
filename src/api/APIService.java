package api;

import java.io.PrintStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class APIService
  implements ServletContextListener
{
  private String path;

  public void contextDestroyed(ServletContextEvent arg0)
  {
    try
    {
      ServiceSocket.closeServer();
    }
    catch (Exception localException) {
    }
    System.out.println("API Service Stop !!");
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    this.path = servletContextEvent.getServletContext().getRealPath("/");

    new Thread()
    {
      public void run()
      {
        try {
          ServiceSocket.openServer(APIService.this.path);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    .start();

    System.out.println("API Service Start !!");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.APIService
 * JD-Core Version:    0.6.2
 */