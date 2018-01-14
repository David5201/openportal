package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalprivilege;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeUtils
{
  public static List<Portalprivilege> getAllDepartments(List<Portalprivilege> privilegeList)
  {
    List list = new ArrayList();
    List topList = new ArrayList();
    for (Portalprivilege t : privilegeList) {
      if ((t.getUrl() != null) && (stringUtils.isBlank(t.getUrl()))) {
        t.setUrl(null);
      }
      if (t.getParentId() == null) {
        topList.add(t);
      }
    }

    walkDepartmentTreeList(topList, privilegeList, "|—", list);
    return list;
  }

  private static void walkDepartmentTreeList(List<Portalprivilege> topList, List<Portalprivilege> AllList, String prefix, List<Portalprivilege> list)
  {
    for (Portalprivilege top : topList)
    {
      Portalprivilege copy = new Portalprivilege();
      copy.setId(top.getId());
      copy.setName(prefix + top.getName());
      copy.setUrl(top.getUrl());
      if ((copy.getUrl() != null) && (stringUtils.isBlank(copy.getUrl()))) {
        copy.setUrl(null);
      }
      copy.setParentId(top.getParentId());
      copy.setPosition(top.getPosition());
      list.add(copy);
      List childrenList = new ArrayList();
      for (Portalprivilege t : AllList) {
        if (t.getParentId() != null) {
          long tpid = t.getParentId().longValue();
          long cid = copy.getId().longValue();
          if (tpid == cid) {
            childrenList.add(t);
          }
        }

      }

      walkDepartmentTreeList(childrenList, AllList, "|　" + prefix, list);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.PrivilegeUtils
 * JD-Core Version:    0.6.2
 */