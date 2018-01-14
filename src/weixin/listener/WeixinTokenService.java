/*    */ package weixin.listener;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.apache.log4j.Logger;
/*    */ import weixin.entity.AccessTokensMap;
/*    */ import weixin.entity.WeixinAccessToken;
/*    */ 
/*    */ public class WeixinTokenService
/*    */   implements ServletContextListener
/*    */ {
/* 32 */   private static Logger log = Logger.getLogger(WeixinTokenService.class);
/* 33 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent servletContextEvent)
/*    */   {
/* 41 */     TokenToDisk(servletContextEvent);
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*    */   {
/* 49 */     TokenFromDisk(servletContextEvent);
/*    */   }
/*    */ 
/*    */   private void TokenToDisk(ServletContextEvent servletContextEvent)
/*    */   {
/* 55 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 56 */     ObjectOutputStream os = null;
/*    */     try {
/* 58 */       os = new ObjectOutputStream(
/* 59 */         new FileOutputStream(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat"));
/* 60 */       os.writeObject(AccessTokensMap.getInstance().getAccessTocken());
/* 61 */       if (basConfig.getIsdebug().equals("1"))
/* 62 */         log.info("Weixin TokenToDisk !!");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 66 */       e.printStackTrace();
/*    */       try
/*    */       {
/* 69 */         if (os != null)
/* 70 */           os.close();
/*    */       }
/*    */       catch (IOException e1)
/*    */       {
/* 74 */         e1.printStackTrace();
/*    */       }
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 69 */         if (os != null)
/* 70 */           os.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 74 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/* 79 */   private void TokenFromDisk(ServletContextEvent servletContextEvent) { Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 80 */     ObjectInputStream is = null;
/*    */ 
/* 82 */     label223: 
/*    */     try { File parent = new File(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat");
/* 83 */       if (parent.exists())
/*    */       {
/* 85 */         is = new ObjectInputStream(new FileInputStream(servletContextEvent.getServletContext().getRealPath("/") + "/Token.dat"));
/* 86 */         WeixinAccessToken token = (WeixinAccessToken)is.readObject();
/* 87 */         AccessTokensMap.getInstance().setAccessTocken(token);
/* 88 */         if (basConfig.getIsdebug().equals("1")) {
/* 89 */           log.info("Weixin TokenFromDisk !!");
/*    */ 
/* 91 */           break label223;
/*    */         } } else if (basConfig.getIsdebug().equals("1")) {
/* 93 */         log.info("Weixin Token File is Not exists !!");
/*    */       }
/*    */     } catch (Exception e)
/*    */     {
/* 97 */       e.printStackTrace();
/*    */       try
/*    */       {
/* 100 */         if (is != null)
/* 101 */           is.close();
/*    */       }
/*    */       catch (IOException e1)
/*    */       {
/* 105 */         e1.printStackTrace();
/*    */       }
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 100 */         if (is != null)
/* 101 */           is.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 105 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.listener.WeixinTokenService
 * JD-Core Version:    0.6.2
 */