package com.leeson.core.bean;

import java.io.Serializable;

public class Portaldepartment
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String description;
  private Long parentId;

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
  public Long getParentId() {
    return this.parentId;
  }
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
  public String toString() {
    return "Portaldepartment [id=" + this.id + ",name=" + this.name + ",description=" + this.description + ",parentId=" + this.parentId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaldepartment
 * JD-Core Version:    0.6.2
 */