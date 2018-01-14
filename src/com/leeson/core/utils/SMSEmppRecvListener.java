package com.leeson.core.utils;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import org.wyrsoft.smsapi.MessageClient;
import org.wyrsoft.smsapi.MessageClientFactory;
import org.wyrsoft.smsapi.MessageListener;
import org.wyrsoft.smsapi.SmsDR;
import org.wyrsoft.smsapi.SmsMO;
import org.wyrsoft.smsapi.SmsMT;
import org.wyrsoft.smsapi.SmsUtil;

public class SMSEmppRecvListener
  implements MessageListener
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(SMSEmppRecvListener.class);

  private static MessageClient client = null;

  private int drCount = 0;

  private int mtCount = 0;

  private static SMSEmppRecvListener listener = new SMSEmppRecvListener();

  private SMSEmppRecvListener() {
    client = MessageClientFactory.createClient("empp2.0", this);
  }

  public static SMSEmppRecvListener getListener() {
    return listener;
  }

  public MessageClient getClient() {
    return client;
  }

  public void initialize(String serverAddress, int port, String username, String password)
    throws Exception
  {
    client.setUsername(username);
    client.setPassword(password);
    client.setRemoteAddr(serverAddress);
    client.setRemotePort(port);
    client.setVersion(32);

    client.connect();
  }

  public void onBindAck(int arg0)
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
      log.info("onBound: " + arg0);
  }

  public void onClosed(ArrayList arg0)
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
      log.info("onClosed:" + arg0);
  }

  public void onSmsDR(SmsDR dr)
  {
    this.drCount += 1;
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      log.info("onSmsDR " + this.drCount);
      log.info("Source-Addr: " + dr.getSourceAddr());
      log.info("Message-ID : " + dr.getMessageID());
      log.info("ErrorCode  : " + dr.getErrorCode());
      log.info("Receipt    : " + dr.getReceipt());
      log.info("Status     : " + dr.getStatus());
    }
  }

  public void onSmsMO(SmsMO mo)
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      log.info("onSmsMO");
      log.info("Data-Coding  : " + mo.getDataCoding());
      log.info("Dest-Addr    : " + mo.getDestAddr());
      log.info("Content      : " + SmsUtil.getLetter(mo));
      log.info("Service-Code : " + mo.getServiceCode());
      log.info("Source-Addr  : " + mo.getSourceAddr());
    }
  }

  public void onSmsMTAck(SmsMT ack)
  {
    this.mtCount += 1;
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      log.info("onSmsMTAck " + this.mtCount);
      log.info("Message-ID: " + ack.getMessageID());
      log.info("Result    : " + ack.getResult());
    }
  }

  public void onMmsDR(SmsDR mmsdr)
  {
  }

  public void onMmsMO(SmsMO mmsdr)
  {
  }

  public void onMmsRR(SmsDR mmsdr)
  {
  }

  public void onMmsMTAck(SmsMT mmsmt)
  {
  }

  public int send(String number, String content)
  {
    SmsMT mt = new SmsMT();

    mt.setRegisteredDelivery(0);

    mt.setUnicodeMessage(content);

    String[] addresses = number.split(";");
    for (int i = 0; i < addresses.length; i++) {
      mt.addDestAddr(addresses[i]);
    }

    int timeout = 0;
    try {
      client.send(mt, timeout);
    } catch (Exception e) {
      if ((((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) && 
        (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }

      return -1;
    }

    if (timeout == 0) {
      return 0;
    }

    return mt.getResult();
  }

  public void close()
  {
    client.close();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSEmppRecvListener
 * JD-Core Version:    0.6.2
 */