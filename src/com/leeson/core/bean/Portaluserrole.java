package com.leeson.core.bean;

import java.io.Serializable;

public class Portaluserrole
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long roleId;
  private Long userId;

  public Long getRoleId()
  {
    return this.roleId;
  }
  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
  public Long getUserId() {
    return this.userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  public String toString() {
    return "Portaluserrole [roleId=" + this.roleId + ",userId=" + this.userId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaluserrole
 * JD-Core Version:    0.6.2
 */