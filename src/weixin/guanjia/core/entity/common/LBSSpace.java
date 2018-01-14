package weixin.guanjia.core.entity.common;

public class LBSSpace
{
  private String name;
  private String address;
  private Location location;
  private String telphone;
  private String distance;

  public LBSSpace()
  {
  }

  public LBSSpace(String name, Location location, String address, String telphone, String distance)
  {
    this.address = address;
    this.name = name;
    this.location = location;
    this.telphone = telphone;
    this.distance = distance;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return this.address;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return this.location;
  }

  public String getTelphone() {
    return this.telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getDistance() {
    return this.distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.entity.common.LBSSpace
 * JD-Core Version:    0.6.2
 */