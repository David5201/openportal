package com.leeson.common.util;

import com.leeson.core.bean.Portalaccount;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class UserExcelView extends AbstractExcelView
{
  protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Date date = new Date();
    String filename = Tools.date2Str(date, "yyyyMMddHHmmss");

    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
    HSSFSheet sheet = workbook.createSheet("用户");

    List titles = (List)model.get("titles");
    int len = titles.size();
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment((short)2);
    headerStyle.setVerticalAlignment((short)1);
    HSSFFont headerFont = workbook.createFont();
    headerFont.setBoldweight((short)700);
    headerFont.setFontHeightInPoints((short)11);
    headerStyle.setFont(headerFont);
    short width = 20; short height = 500;
    sheet.setDefaultColumnWidth(width);
    for (int i = 0; i < len; i++) {
      String title = (String)titles.get(i);
      HSSFCell cell = getCell(sheet, 0, i);
      cell.setCellStyle(headerStyle);
      setText(cell, title);
    }
    sheet.getRow(0).setHeight(height);

    HSSFCellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment((short)2);
    List userList = (List)model.get("userList");
    int userCount = userList.size();
    for (int i = 0; i < userCount; i++) {
      Portalaccount user = (Portalaccount)userList.get(i);
      String username = user.getName();
      HSSFCell cell = getCell(sheet, i + 1, 0);
      cell.setCellStyle(contentStyle);
      setText(cell, username);

      String loginname = user.getLoginName();
      cell = getCell(sheet, i + 1, 1);
      cell.setCellStyle(contentStyle);
      setText(cell, loginname);

      String roleName = (String)(user.getTime() != null ? user.getTime() : "");
      cell = getCell(sheet, i + 1, 2);
      cell.setCellStyle(contentStyle);
      setText(cell, roleName);

      Date lastLogin = user.getDate() != null ? user.getDate() : null;
      cell = getCell(sheet, i + 1, 3);
      cell.setCellStyle(contentStyle);
      setText(cell, Tools.date2Str(lastLogin));
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.UserExcelView
 * JD-Core Version:    0.6.2
 */