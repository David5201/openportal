package com.leeson.core.utils;

import com.leeson.core.bean.Portaldepartment;
import java.util.ArrayList;
import java.util.List;

public class DepartmentUtils
{
  public static List<Portaldepartment> getAllDepartments(List<Portaldepartment> departmentList)
  {
    List list = new ArrayList();
    List topList = new ArrayList();
    for (Portaldepartment t : departmentList) {
      if (t.getParentId() == null) {
        topList.add(t);
      }
    }

    walkDepartmentTreeList(topList, departmentList, "|—", list);
    return list;
  }

  private static void walkDepartmentTreeList(List<Portaldepartment> topList, List<Portaldepartment> AllList, String prefix, List<Portaldepartment> list)
  {
    for (Portaldepartment top : topList)
    {
      Portaldepartment copy = new Portaldepartment();
      copy.setId(top.getId());
      copy.setName(prefix + top.getName());
      copy.setDescription(top.getDescription());
      copy.setParentId(top.getParentId());
      list.add(copy);
      List childrenList = new ArrayList();
      for (Portaldepartment t : AllList) {
        if ((t.getParentId() != null) && (t.getParentId() == copy.getId())) {
          childrenList.add(t);
        }

      }

      walkDepartmentTreeList(childrenList, AllList, "|　" + prefix, list);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.DepartmentUtils
 * JD-Core Version:    0.6.2
 */