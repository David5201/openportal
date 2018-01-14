/*    */ package com.leeson.common.util;
/*    */ 
/*    */ import com.leeson.core.bean.Portalaccount;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*    */ import org.apache.poi.hssf.usermodel.HSSFFont;
/*    */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */ import org.springframework.web.servlet.view.document.AbstractExcelView;
/*    */ 
/*    */ public class UserExcelView extends AbstractExcelView
/*    */ {
/*    */   protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 27 */     Date date = new Date();
/* 28 */     String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
/*    */ 
/* 31 */     response.setContentType("application/octet-stream");
/* 32 */     response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
/* 33 */     HSSFSheet sheet = workbook.createSheet("用户");
/*    */ 
/* 35 */     List titles = (List)model.get("titles");
/* 36 */     int len = titles.size();
/* 37 */     HSSFCellStyle headerStyle = workbook.createCellStyle();
/* 38 */     headerStyle.setAlignment((short)2);
/* 39 */     headerStyle.setVerticalAlignment((short)1);
/* 40 */     HSSFFont headerFont = workbook.createFont();
/* 41 */     headerFont.setBoldweight((short)700);
/* 42 */     headerFont.setFontHeightInPoints((short)11);
/* 43 */     headerStyle.setFont(headerFont);
/* 44 */     short width = 20; short height = 500;
/* 45 */     sheet.setDefaultColumnWidth(width);
/* 46 */     for (int i = 0; i < len; i++) {
/* 47 */       String title = (String)titles.get(i);
/* 48 */       HSSFCell cell = getCell(sheet, 0, i);
/* 49 */       cell.setCellStyle(headerStyle);
/* 50 */       setText(cell, title);
/*    */     }
/* 52 */     sheet.getRow(0).setHeight(height);
/*    */ 
/* 54 */     HSSFCellStyle contentStyle = workbook.createCellStyle();
/* 55 */     contentStyle.setAlignment((short)2);
/* 56 */     List userList = (List)model.get("userList");
/* 57 */     int userCount = userList.size();
/* 58 */     for (int i = 0; i < userCount; i++) {
/* 59 */       Portalaccount user = (Portalaccount)userList.get(i);
/* 60 */       String username = user.getName();
/* 61 */       HSSFCell cell = getCell(sheet, i + 1, 0);
/* 62 */       cell.setCellStyle(contentStyle);
/* 63 */       setText(cell, username);
/*    */ 
/* 65 */       String loginname = user.getLoginName();
/* 66 */       cell = getCell(sheet, i + 1, 1);
/* 67 */       cell.setCellStyle(contentStyle);
/* 68 */       setText(cell, loginname);
/*    */ 
/* 70 */       String roleName = (String)(user.getTime() != null ? user.getTime() : "");
/* 71 */       cell = getCell(sheet, i + 1, 2);
/* 72 */       cell.setCellStyle(contentStyle);
/* 73 */       setText(cell, roleName);
/*    */ 
/* 75 */       Date lastLogin = user.getDate() != null ? user.getDate() : null;
/* 76 */       cell = getCell(sheet, i + 1, 3);
/* 77 */       cell.setCellStyle(contentStyle);
/* 78 */       setText(cell, Tools.date2Str(lastLogin));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.UserExcelView
 * JD-Core Version:    0.6.2
 */