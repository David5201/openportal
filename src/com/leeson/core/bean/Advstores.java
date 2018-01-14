package com.leeson.core.bean;

import java.io.Serializable;
import java.util.Date;

public class Advstores
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Date creatDate;
  private Long uid;
  private String address;
  private String phone;
  private String img;
  private Integer showInfo;
  private String x;
  private String y;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getCreatDate() {
    return this.creatDate;
  }
  public void setCreatDate(Date creatDate) {
    this.creatDate = creatDate;
  }
  public Long getUid() {
    return this.uid;
  }
  public void setUid(Long uid) {
    this.uid = uid;
  }
  public String getAddress() {
    return this.address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getImg() {
    return this.img;
  }
  public void setImg(String img) {
    this.img = img;
  }
  public Integer getShowInfo() {
    return this.showInfo;
  }
  public void setShowInfo(Integer showInfo) {
    this.showInfo = showInfo;
  }
  public String getX() {
    return this.x;
  }
  public void setX(String x) {
    this.x = x;
  }
  public String getY() {
    return this.y;
  }
  public void setY(String y) {
    this.y = y;
  }
  public String toString() {
    return "Advstores [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",creatDate=" + this.creatDate + ",uid=" + this.uid + ",address=" + this.address + ",phone=" + this.phone + ",img=" + this.img + ",showInfo=" + this.showInfo + ",x=" + this.x + ",y=" + this.y + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Advstores
 * JD-Core Version:    0.6.2
 */