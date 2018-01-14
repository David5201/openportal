/*     */ package weixin.guanjia.core.util;
/*     */ 
/*     */ import com.thoughtworks.xstream.XStream;
/*     */ import com.thoughtworks.xstream.core.util.QuickWriter;
/*     */ import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
/*     */ import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
/*     */ import com.thoughtworks.xstream.io.xml.XppDriver;
/*     */ import java.io.InputStream;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import weixin.guanjia.core.entity.message.resp.Article;
/*     */ import weixin.guanjia.core.entity.message.resp.MusicMessageResp;
/*     */ import weixin.guanjia.core.entity.message.resp.NewsMessageResp;
/*     */ import weixin.guanjia.core.entity.message.resp.TextMessageResp;
/*     */ 
/*     */ public class MessageUtil
/*     */ {
/*     */   public static final String RESP_MESSAGE_TYPE_TEXT = "text";
/*     */   public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
/*     */   public static final String RESP_MESSAGE_TYPE_NEWS = "news";
/*     */   public static final String REQ_MESSAGE_TYPE_TEXT = "text";
/*     */   public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
/*     */   public static final String REQ_MESSAGE_TYPE_LINK = "link";
/*     */   public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
/*     */   public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
/*     */   public static final String REQ_MESSAGE_TYPE_EVENT = "event";
/*     */   public static final String EVENT_TYPE_WIFI = "WifiConnected";
/*     */   public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
/*     */   public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
/*     */   public static final String EVENT_TYPE_CLICK = "CLICK";
/*     */   public static final String EVENT_TYPE_VIEW = "VIEW";
/* 168 */   private static XStream xstream = new XStream(new XppDriver() {
/*     */     public HierarchicalStreamWriter createWriter(Writer out) {
/* 170 */       return new PrettyPrintWriter(out)
/*     */       {
/* 172 */         boolean cdata = true;
/*     */ 
/*     */         public void startNode(String name, Class clazz)
/*     */         {
/* 176 */           super.startNode(name, clazz);
/*     */         }
/*     */ 
/*     */         protected void writeText(QuickWriter writer, String text) {
/* 180 */           if (this.cdata) {
/* 181 */             writer.write("<![CDATA[");
/* 182 */             writer.write(text);
/* 183 */             writer.write("]]>");
/*     */           } else {
/* 185 */             writer.write(text);
/*     */           }
/*     */         }
/*     */       };
/*     */     }
/*     */   });
/*     */ 
/*     */   public static Map<String, String> parseXml(HttpServletRequest request)
/*     */     throws Exception
/*     */   {
/* 106 */     Map map = new HashMap();
/*     */ 
/* 109 */     InputStream inputStream = request.getInputStream();
/*     */ 
/* 111 */     SAXReader reader = new SAXReader();
/* 112 */     Document document = reader.read(inputStream);
/*     */ 
/* 114 */     Element root = document.getRootElement();
/*     */ 
/* 116 */     List<Element> elementList = root.elements();
/*     */ 
/* 119 */     for (Element e : elementList) {
/* 120 */       map.put(e.getName(), e.getText());
/*     */     }
/*     */ 
/* 123 */     inputStream.close();
/* 124 */     inputStream = null;
/*     */ 
/* 126 */     return map;
/*     */   }
/*     */ 
/*     */   public static String textMessageToXml(TextMessageResp textMessage)
/*     */   {
/* 136 */     xstream.alias("xml", textMessage.getClass());
/* 137 */     return xstream.toXML(textMessage);
/*     */   }
/*     */ 
/*     */   public static String musicMessageToXml(MusicMessageResp musicMessage)
/*     */   {
/* 147 */     xstream.alias("xml", musicMessage.getClass());
/* 148 */     return xstream.toXML(musicMessage);
/*     */   }
/*     */ 
/*     */   public static String newsMessageToXml(NewsMessageResp newsMessage)
/*     */   {
/* 158 */     xstream.alias("xml", newsMessage.getClass());
/* 159 */     xstream.alias("item", new Article().getClass());
/* 160 */     return xstream.toXML(newsMessage);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.MessageUtil
 * JD-Core Version:    0.6.2
 */