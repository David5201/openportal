package api;

import java.io.Serializable;
import java.util.HashMap;

public class APIMap
  implements Serializable
{
  private static final long serialVersionUID = 7285326997979887068L;
  private HashMap<String, String> apiMap = new HashMap();

  private static APIMap instance = new APIMap();

  public static APIMap getInstance()
  {
    return instance;
  }

  public HashMap<String, String> getApiMap() {
    return this.apiMap;
  }

  public void setApiMap(HashMap<String, String> apiMap) {
    this.apiMap = apiMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.APIMap
 * JD-Core Version:    0.6.2
 */