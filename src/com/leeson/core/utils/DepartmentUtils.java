/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portaldepartment;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DepartmentUtils
/*    */ {
/*    */   public static List<Portaldepartment> getAllDepartments(List<Portaldepartment> departmentList)
/*    */   {
/* 18 */     List list = new ArrayList();
/* 19 */     List topList = new ArrayList();
/* 20 */     for (Portaldepartment t : departmentList) {
/* 21 */       if (t.getParentId() == null) {
/* 22 */         topList.add(t);
/*    */       }
/*    */     }
/*    */ 
/* 26 */     walkDepartmentTreeList(topList, departmentList, "|—", list);
/* 27 */     return list;
/*    */   }
/*    */ 
/*    */   private static void walkDepartmentTreeList(List<Portaldepartment> topList, List<Portaldepartment> AllList, String prefix, List<Portaldepartment> list)
/*    */   {
/* 38 */     for (Portaldepartment top : topList)
/*    */     {
/* 40 */       Portaldepartment copy = new Portaldepartment();
/* 41 */       copy.setId(top.getId());
/* 42 */       copy.setName(prefix + top.getName());
/* 43 */       copy.setDescription(top.getDescription());
/* 44 */       copy.setParentId(top.getParentId());
/* 45 */       list.add(copy);
/* 46 */       List childrenList = new ArrayList();
/* 47 */       for (Portaldepartment t : AllList) {
/* 48 */         if ((t.getParentId() != null) && (t.getParentId() == copy.getId())) {
/* 49 */           childrenList.add(t);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 54 */       walkDepartmentTreeList(childrenList, AllList, "|　" + prefix, list);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.DepartmentUtils
 * JD-Core Version:    0.6.2
 */