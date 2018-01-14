package com.leeson.common.web.session.id;

import java.io.PrintStream;
import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class JugUUIDGenerator
  implements SessionIdGenerator
{
  public String get()
  {
    UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
    return StringUtils.remove(uuid.toString(), '-');
  }

  public static void main(String[] args) {
    UUIDGenerator.getInstance().generateRandomBasedUUID();
    long time = System.currentTimeMillis();
    int count = 1;
    for (int i = 0; i < 100000; i++) {
      UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
      System.out.println(count++ + ":" + uuid.toString());
    }
    time = System.currentTimeMillis() - time;
    System.out.println(time);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.session.id.JugUUIDGenerator
 * JD-Core Version:    0.6.2
 */