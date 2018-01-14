/*    */ package com.leeson.common.web;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class WebErrors
/*    */ {
/* 16 */   public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
/*    */ 
/* 22 */   public static final Pattern PHONE_PATTERN = Pattern.compile("^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
/*    */   public static final String ERROR_EMAIL = "邮箱格式不正确";
/*    */   public static final String ERROR_REQUIRED = "此处不能为空";
/*    */   private List<String> errors;
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 28 */     WebErrors we = new WebErrors();
/*    */ 
/* 30 */     boolean vp = we.ifNotPhone("13465379415");
/* 31 */     System.out.println(vp);
/*    */   }
/*    */ 
/*    */   public boolean ifNotEmail(String email)
/*    */   {
/* 45 */     if (!ifBlank(email)) {
/* 46 */       return false;
/*    */     }
/* 48 */     Matcher m = EMAIL_PATTERN.matcher(email);
/* 49 */     if (!m.matches()) {
/* 50 */       addErrorCode("邮箱格式不正确");
/* 51 */       return false;
/*    */     }
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean ifNotPhone(String phone) {
/* 57 */     Matcher matcher = PHONE_PATTERN.matcher(phone);
/* 58 */     if (matcher.matches()) {
/* 59 */       return true;
/*    */     }
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean ifBlank(String s) {
/* 65 */     if (StringUtils.isBlank(s)) {
/* 66 */       addErrorCode("此处不能为空");
/* 67 */       return false;
/*    */     }
/* 69 */     return true;
/*    */   }
/*    */   public boolean ifMaxLength(String s, int maxLength) {
/* 72 */     if ((s != null) && (s.length() > maxLength))
/*    */     {
/* 74 */       return true;
/*    */     }
/* 76 */     return false;
/*    */   }
/*    */ 
/*    */   public void addErrorCode(String code)
/*    */   {
/* 85 */     getErrors().add(code);
/*    */   }
/*    */ 
/*    */   public List<String> getErrors()
/*    */   {
/* 93 */     if (this.errors == null) {
/* 94 */       this.errors = new ArrayList();
/*    */     }
/* 96 */     return this.errors;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.WebErrors
 * JD-Core Version:    0.6.2
 */