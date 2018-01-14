/*     */ package com.leeson.common.web.springmvc;
/*     */ 
/*     */ import freemarker.core.ParseException;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.BeanFactoryUtils;
/*     */ import org.springframework.beans.factory.NoSuchBeanDefinitionException;
/*     */ import org.springframework.context.ApplicationContextException;
/*     */ import org.springframework.web.servlet.view.AbstractTemplateView;
/*     */ import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
/*     */ 
/*     */ public class SimpleFreeMarkerView extends AbstractTemplateView
/*     */ {
/*     */   public static final String CONTEXT_PATH = "base";
/*     */   private Configuration configuration;
/*     */ 
/*     */   public void setConfiguration(Configuration configuration)
/*     */   {
/*  33 */     this.configuration = configuration;
/*     */   }
/*     */ 
/*     */   protected Configuration getConfiguration() {
/*  37 */     return this.configuration;
/*     */   }
/*     */ 
/*     */   protected FreeMarkerConfig autodetectConfiguration()
/*     */     throws BeansException
/*     */   {
/*     */     try
/*     */     {
/*  48 */       return 
/*  49 */         (FreeMarkerConfig)BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), 
/*  50 */         FreeMarkerConfig.class, true, false);
/*     */     } catch (NoSuchBeanDefinitionException ex) {
/*  52 */       throw new ApplicationContextException(
/*  53 */         "Must define a single FreeMarkerConfig bean in this web application context (may be inherited): FreeMarkerConfigurer is the usual implementation. This bean may be given any name.", 
/*  55 */         ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void initApplicationContext()
/*     */     throws BeansException
/*     */   {
/*  70 */     super.initApplicationContext();
/*     */ 
/*  72 */     if (getConfiguration() == null) {
/*  73 */       FreeMarkerConfig config = autodetectConfiguration();
/*  74 */       setConfiguration(config.getConfiguration());
/*     */     }
/*  76 */     checkTemplate();
/*     */   }
/*     */ 
/*     */   protected void checkTemplate()
/*     */     throws ApplicationContextException
/*     */   {
/*     */     try
/*     */     {
/*  93 */       getConfiguration().getTemplate(getUrl());
/*     */     } catch (ParseException ex) {
/*  95 */       throw new ApplicationContextException(
/*  96 */         "Failed to parse FreeMarker template for URL [" + getUrl() + 
/*  97 */         "]", ex);
/*     */     } catch (IOException ex) {
/*  99 */       throw new ApplicationContextException(
/* 100 */         "Could not load FreeMarker template for URL [" + getUrl() + 
/* 101 */         "]", ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 110 */     model.put("base", request.getContextPath());
/* 111 */     getConfiguration().getTemplate(getUrl()).process(model, 
/* 112 */       response.getWriter());
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.SimpleFreeMarkerView
 * JD-Core Version:    0.6.2
 */