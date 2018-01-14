package com.leeson.core.bean;

import java.io.Serializable;

public class Portalspeed
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Long up;
  private Long down;
  private Long mup;
  private Long mdown;

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
  public Long getUp() {
    return this.up;
  }
  public void setUp(Long up) {
    this.up = up;
  }
  public Long getDown() {
    return this.down;
  }
  public void setDown(Long down) {
    this.down = down;
  }
  public Long getMup() {
    return this.mup;
  }
  public void setMup(Long mup) {
    this.mup = mup;
  }
  public Long getMdown() {
    return this.mdown;
  }
  public void setMdown(Long mdown) {
    this.mdown = mdown;
  }
  public String toString() {
    return "Portalspeed [id=" + this.id + ",name=" + this.name + ",up=" + this.up + ",down=" + this.down + ",mup=" + this.mup + ",mdown=" + this.mdown + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalspeed
 * JD-Core Version:    0.6.2
 */