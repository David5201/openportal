package weixin.listener;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import weixin.entity.AccessTokensMap;
import weixin.entity.WeixinAccessToken;

public class WeixinTokenService
  implements ServletContextListener
{
  private static Logger log = Logger.getLogger(WeixinTokenService.class);
  private static Config config = Config.getInstance();

  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    TokenToDisk(servletContextEvent);
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    TokenFromDisk(servletContextEvent);
  }

  private void TokenToDisk(ServletContextEvent servletContextEvent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(
    		  new FileOutputStream(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat"));
      os.writeObject(AccessTokensMap.getInstance().getAccessTocken());
      if (basConfig.getIsdebug().equals("1"))
        log.info("Weixin TokenToDisk !!");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  private void TokenFromDisk(ServletContextEvent servletContextEvent) { Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;

    label223: 
    	try { File parent = new File(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat");
      if (parent.exists())
      {
    	  is = new ObjectInputStream(new FileInputStream(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat"));
        WeixinAccessToken token = (WeixinAccessToken)is.readObject();
        AccessTokensMap.getInstance().setAccessTocken(token);
        if (basConfig.getIsdebug().equals("1")) {
          log.info("Weixin TokenFromDisk !!");

          break label223;
        } } else if (basConfig.getIsdebug().equals("1")) {
        log.info("Weixin Token File is Not exists !!");
      }
    } catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (is != null)
          is.close();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (is != null)
          is.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.listener.WeixinTokenService
 * JD-Core Version:    0.6.2
 */