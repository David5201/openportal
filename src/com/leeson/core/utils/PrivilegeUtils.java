/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalprivilege;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PrivilegeUtils
/*    */ {
/*    */   public static List<Portalprivilege> getAllDepartments(List<Portalprivilege> privilegeList)
/*    */   {
/* 18 */     List list = new ArrayList();
/* 19 */     List topList = new ArrayList();
/* 20 */     for (Portalprivilege t : privilegeList) {
/* 21 */       if ((t.getUrl() != null) && (stringUtils.isBlank(t.getUrl()))) {
/* 22 */         t.setUrl(null);
/*    */       }
/* 24 */       if (t.getParentId() == null) {
/* 25 */         topList.add(t);
/*    */       }
/*    */     }
/*    */ 
/* 29 */     walkDepartmentTreeList(topList, privilegeList, "|—", list);
/* 30 */     return list;
/*    */   }
/*    */ 
/*    */   private static void walkDepartmentTreeList(List<Portalprivilege> topList, List<Portalprivilege> AllList, String prefix, List<Portalprivilege> list)
/*    */   {
/* 41 */     for (Portalprivilege top : topList)
/*    */     {
/* 43 */       Portalprivilege copy = new Portalprivilege();
/* 44 */       copy.setId(top.getId());
/* 45 */       copy.setName(prefix + top.getName());
/* 46 */       copy.setUrl(top.getUrl());
/* 47 */       if ((copy.getUrl() != null) && (stringUtils.isBlank(copy.getUrl()))) {
/* 48 */         copy.setUrl(null);
/*    */       }
/* 50 */       copy.setParentId(top.getParentId());
/* 51 */       copy.setPosition(top.getPosition());
/* 52 */       list.add(copy);
/* 53 */       List childrenList = new ArrayList();
/* 54 */       for (Portalprivilege t : AllList) {
/* 55 */         if (t.getParentId() != null) {
/* 56 */           long tpid = t.getParentId().longValue();
/* 57 */           long cid = copy.getId().longValue();
/* 58 */           if (tpid == cid) {
/* 59 */             childrenList.add(t);
/*    */           }
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 65 */       walkDepartmentTreeList(childrenList, AllList, "|　" + prefix, list);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.PrivilegeUtils
 * JD-Core Version:    0.6.2
 */