package com.leeson.core.bean;

import java.io.Serializable;

public class Portaluser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String loginName;
  private String password;
  private String name;
  private String gender;
  private String phoneNumber;
  private String email;
  private String description;
  private Long departmentId;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getLoginName() {
    return this.loginName;
  }
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getGender() {
    return this.gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getDepartmentId() {
    return this.departmentId;
  }
  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }
  public String toString() {
    return "Portaluser [id=" + this.id + ",loginName=" + this.loginName + ",password=" + this.password + ",name=" + this.name + ",gender=" + this.gender + ",phoneNumber=" + this.phoneNumber + ",email=" + this.email + ",description=" + this.description + ",departmentId=" + this.departmentId + "]";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.bean.Portaluser
 * JD-Core Version:    0.6.2
 */