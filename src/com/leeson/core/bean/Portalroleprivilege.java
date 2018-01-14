package com.leeson.core.bean;

import java.io.Serializable;

public class Portalroleprivilege
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long privilegeId;
  private Long roleId;

  public Long getPrivilegeId()
  {
    return this.privilegeId;
  }
  public void setPrivilegeId(Long privilegeId) {
    this.privilegeId = privilegeId;
  }
  public Long getRoleId() {
    return this.roleId;
  }
  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
  public String toString() {
    return "Portalroleprivilege [privilegeId=" + this.privilegeId + ",roleId=" + this.roleId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portalroleprivilege
 * JD-Core Version:    0.6.2
 */