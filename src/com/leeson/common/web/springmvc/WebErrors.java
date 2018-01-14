/*     */ package com.leeson.common.web.springmvc;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.util.Assert;
/*     */ import org.springframework.web.context.WebApplicationContext;
/*     */ import org.springframework.web.servlet.LocaleResolver;
/*     */ import org.springframework.web.servlet.support.RequestContextUtils;
/*     */ 
/*     */ public abstract class WebErrors
/*     */ {
/*  31 */   public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");
/*     */ 
/*  36 */   public static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");
/*     */   private MessageSource messageSource;
/*     */   private Locale locale;
/*     */   private List<String> errors;
/*     */ 
/*     */   public WebErrors(HttpServletRequest request)
/*     */   {
/*  45 */     WebApplicationContext webApplicationContext = 
/*  46 */       RequestContextUtils.getWebApplicationContext(request);
/*  47 */     if (webApplicationContext != null) {
/*  48 */       LocaleResolver localeResolver = 
/*  49 */         RequestContextUtils.getLocaleResolver(request);
/*     */ 
/*  51 */       if (localeResolver != null) {
/*  52 */         Locale locale = localeResolver.resolveLocale(request);
/*  53 */         this.messageSource = webApplicationContext;
/*  54 */         this.locale = locale;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public WebErrors()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WebErrors(MessageSource messageSource, Locale locale)
/*     */   {
/*  69 */     this.messageSource = messageSource;
/*  70 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   public String getMessage(String code, Object[] args) {
/*  74 */     if (this.messageSource == null) {
/*  75 */       throw new IllegalStateException("MessageSource cannot be null.");
/*     */     }
/*  77 */     return this.messageSource.getMessage(code, args, this.locale);
/*     */   }
/*     */ 
/*     */   public void addErrorCode(String code, Object[] args)
/*     */   {
/*  90 */     getErrors().add(getMessage(code, args));
/*     */   }
/*     */ 
/*     */   public void addErrorCode(String code)
/*     */   {
/* 101 */     getErrors().add(getMessage(code, new Object[0]));
/*     */   }
/*     */ 
/*     */   public void addErrorString(String error)
/*     */   {
/* 110 */     getErrors().add(error);
/*     */   }
/*     */ 
/*     */   public void addError(String error)
/*     */   {
/* 120 */     if (this.messageSource != null) {
/* 121 */       error = this.messageSource.getMessage(error, null, error, this.locale);
/*     */     }
/* 123 */     getErrors().add(error);
/*     */   }
/*     */ 
/*     */   public boolean hasErrors()
/*     */   {
/* 132 */     return (this.errors != null) && (this.errors.size() > 0);
/*     */   }
/*     */ 
/*     */   public int getCount()
/*     */   {
/* 141 */     return this.errors == null ? 0 : this.errors.size();
/*     */   }
/*     */ 
/*     */   public List<String> getErrors()
/*     */   {
/* 150 */     if (this.errors == null) {
/* 151 */       this.errors = new ArrayList();
/*     */     }
/* 153 */     return this.errors;
/*     */   }
/*     */ 
/*     */   public String showErrorPage(ModelMap model)
/*     */   {
/* 164 */     toModel(model);
/* 165 */     return getErrorPage();
/*     */   }
/*     */ 
/*     */   public void toModel(Map<String, Object> model)
/*     */   {
/* 174 */     Assert.notNull(model);
/* 175 */     if (!hasErrors()) {
/* 176 */       throw new IllegalStateException("no errors found!");
/*     */     }
/* 178 */     model.put(getErrorAttrName(), getErrors());
/*     */   }
/*     */ 
/*     */   public boolean ifNull(Object o, String field) {
/* 182 */     if (o == null) {
/* 183 */       addErrorCode("error.required", new Object[] { field });
/* 184 */       return true;
/*     */     }
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifEmpty(Object[] o, String field)
/*     */   {
/* 191 */     if ((o == null) || (o.length <= 0)) {
/* 192 */       addErrorCode("error.required", new Object[] { field });
/* 193 */       return true;
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifBlank(String s, String field, int maxLength)
/*     */   {
/* 200 */     if (StringUtils.isBlank(s)) {
/* 201 */       addErrorCode("error.required", new Object[] { field });
/* 202 */       return true;
/*     */     }
/* 204 */     if (ifMaxLength(s, field, maxLength)) {
/* 205 */       return true;
/*     */     }
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifMaxLength(String s, String field, int maxLength) {
/* 211 */     if ((s != null) && (s.length() > maxLength)) {
/* 212 */       addErrorCode("error.maxLength", new Object[] { field, Integer.valueOf(maxLength) });
/* 213 */       return true;
/*     */     }
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifOutOfLength(String s, String field, int minLength, int maxLength)
/*     */   {
/* 220 */     if (s == null) {
/* 221 */       addErrorCode("error.required", new Object[] { field });
/* 222 */       return true;
/*     */     }
/* 224 */     int len = s.length();
/* 225 */     if ((len < minLength) || (len > maxLength)) {
/* 226 */       addErrorCode("error.outOfLength", new Object[] { field, Integer.valueOf(minLength), Integer.valueOf(maxLength) });
/* 227 */       return true;
/*     */     }
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotEmail(String email, String field, int maxLength) {
/* 233 */     if (ifBlank(email, field, maxLength)) {
/* 234 */       return true;
/*     */     }
/* 236 */     Matcher m = EMAIL_PATTERN.matcher(email);
/* 237 */     if (!m.matches()) {
/* 238 */       addErrorCode("error.email", new Object[] { field });
/* 239 */       return true;
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotUsername(String username, String field, int minLength, int maxLength)
/*     */   {
/* 246 */     if (ifOutOfLength(username, field, minLength, maxLength)) {
/* 247 */       return true;
/*     */     }
/* 249 */     Matcher m = USERNAME_PATTERN.matcher(username);
/* 250 */     if (!m.matches()) {
/* 251 */       addErrorCode("error.username", new Object[] { field });
/* 252 */       return true;
/*     */     }
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
/* 258 */     if (o == null) {
/* 259 */       addErrorCode("error.notExist", new Object[] { clazz.getSimpleName(), id });
/* 260 */       return true;
/*     */     }
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public void noPermission(Class<?> clazz, Serializable id)
/*     */   {
/* 267 */     addErrorCode("error.noPermission", new Object[] { clazz.getSimpleName(), id });
/*     */   }
/*     */ 
/*     */   public MessageSource getMessageSource()
/*     */   {
/* 275 */     return this.messageSource;
/*     */   }
/*     */ 
/*     */   public void setMessageSource(MessageSource messageSource) {
/* 279 */     this.messageSource = messageSource;
/*     */   }
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 288 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public void setLocale(Locale locale)
/*     */   {
/* 297 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   protected abstract String getErrorPage();
/*     */ 
/*     */   protected abstract String getErrorAttrName();
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.WebErrors
 * JD-Core Version:    0.6.2
 */