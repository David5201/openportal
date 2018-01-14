/*     */ package com.leeson.common.template;
/*     */ 
/*     */ import com.leeson.common.data.DataMng;
/*     */ import com.leeson.common.data.Table;
/*     */ import com.leeson.common.junit.Generator;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import freemarker.template.TemplateException;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.junit.Test;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
/*     */ 
/*     */ public class ModuleGenerator extends Generator
/*     */ {
/*     */   public static final String entity_p = "com.leeson.core.bean";
/*     */   public static final String query_p = "com.leeson.core.query";
/*     */   public static final String entity_xml_p = "mybatis";
/*     */   public static final String sqlmap_config_xml = "";
/*     */   public static final String action_p = "com.leeson.core.controller";
/*     */   public static final String dao_p = "com.leeson.core.dao";
/*     */   public static final String dao_impl_p = "com.leeson.core.dao.impl";
/*     */   public static final String service_p = "com.leeson.core.service";
/*     */   public static final String service_impl_p = "com.leeson.core.service.impl";
/*     */   public static final String template_dir = "com.leeson.common.template";
/*     */   public static final String dao_tpl = "dao.txt";
/*     */   public static final String dao_impl_tpl = "dao_impl.txt";
/*     */   public static final String service_tpl = "service.txt";
/*     */   public static final String service_impl_tpl = "service_impl.txt";
/*     */   public static final String action_tpl = "action.txt";
/*     */   public static final String entity_tpl = "entity.txt";
/*     */   public static final String query_tpl = "query.txt";
/*     */   public static final String entity_xml_tpl = "entity-sqlmap.txt";
/*     */   public static final String sqlmap_config_tpl = "sqlmap-config.txt";
/*     */   public static final String sharding_rules_on_namespace_p = "dbRule";
/*     */   public static final String hash_function_p = "dbRule";
/*     */   public static final String sharding_rules_on_namespace_tpl = "sharding-rules-on-namespace.txt";
/*     */   public static final String hash_function_tpl = "hash-function.txt";
/*     */   public static final boolean is_bean = false;
/*     */   public static final boolean is_query = false;
/*     */   public static final boolean is_mappperXML = true;
/*     */   public static final boolean is_action = false;
/*     */   public static final boolean is_service = false;
/*     */   public static final boolean is_dao = false;
/*     */   public static final boolean is_mybatisConfig = false;
/*     */   public static final boolean is_hash = false;
/*     */   public static final boolean is_routerXML = false;
/*  70 */   public static final String SPT = File.separator;
/*     */   public static final String ENCODING = "UTF-8";
/*  74 */   Map<String, Object> data = new HashMap();
/*     */ 
/* 228 */   File f = null;
/* 229 */   String tpl = null;
/*     */ 
/*     */   @Autowired
/*     */   private DataMng dataMng;
/*     */   private Configuration conf;
/*     */ 
/*     */   private void loadData(String entity)
/*     */   {
/*  76 */     String Entity = upperCaseFirst(entity);
/*  77 */     this.data.put("Entity", Entity);
/*  78 */     this.data.put("entity", entity);
/*  79 */     this.data.put("entity_p", "com.leeson.core.bean");
/*  80 */     this.data.put("query_p", "com.leeson.core.query");
/*  81 */     this.data.put("entity_xml_p", "mybatis");
/*  82 */     this.data.put("action_p", "com.leeson.core.controller");
/*  83 */     this.data.put("dao_p", "com.leeson.core.dao");
/*  84 */     this.data.put("dao_impl_p", "com.leeson.core.dao.impl");
/*  85 */     this.data.put("service_p", "com.leeson.core.service");
/*  86 */     this.data.put("service_impl_p", "com.leeson.core.service.impl");
/*  87 */     this.data.put("template_dir", "com.leeson.common.template");
/*     */   }
/*     */ 
/*     */   public void generatorAll()
/*     */     throws TemplateException, IOException
/*     */   {
/* 111 */     this.f = new File(getFilePath((String)this.data.get("entity_xml_p"), this.data.get("entity") + "-sqlmap.xml"));
/* 112 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "entity-sqlmap.txt");
/* 113 */     index(this.tpl, this.f);
/*     */   }
/*     */ 
/*     */   public void index(String tpl, File f)
/*     */     throws TemplateException, IOException
/*     */   {
/* 152 */     File parent = f.getParentFile();
/* 153 */     if (!parent.exists()) {
/* 154 */       parent.mkdirs();
/*     */     }
/* 156 */     Writer out = null;
/*     */     try
/*     */     {
/* 159 */       out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
/* 160 */       Template template = this.conf.getTemplate(tpl);
/* 161 */       template.process(this.data, out);
/*     */     } finally {
/* 163 */       if (out != null) {
/* 164 */         out.flush();
/* 165 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getFilePath(String packageName, String name)
/*     */   {
/* 177 */     String path = converString(packageName);
/* 178 */     return "src/" + path + "/" + name;
/*     */   }
/*     */ 
/*     */   private String converString(String s)
/*     */   {
/* 186 */     s = s.replaceAll("\\.", "/");
/* 187 */     return s;
/*     */   }
/*     */ 
/*     */   private String upperCaseFirst(String s)
/*     */   {
/* 195 */     return s.substring(0, 1).toUpperCase() + s.substring(1);
/*     */   }
/*     */ 
/*     */   private String replaceLine(String s)
/*     */   {
/* 203 */     return s.replaceAll("_", "");
/*     */   }
/*     */ 
/*     */   private String trimFirst(String tableName)
/*     */   {
/* 210 */     String[] splits = tableName.split("_");
/* 211 */     if ("psm".equalsIgnoreCase(splits[0]))
/* 212 */       tableName = tableName.substring(4);
/* 213 */     else if ("mm".equalsIgnoreCase(splits[0]))
/* 214 */       tableName = tableName.substring(3);
/* 215 */     else if ("dm".equalsIgnoreCase(splits[0]))
/* 216 */       tableName = tableName.substring(3);
/* 217 */     else if ("pm".equalsIgnoreCase(splits[0]))
/* 218 */       tableName = tableName.substring(3);
/* 219 */     else if ("sm".equalsIgnoreCase(splits[0]))
/* 220 */       tableName = tableName.substring(3);
/* 221 */     else if ("tm".equalsIgnoreCase(splits[0]))
/* 222 */       tableName = tableName.substring(3);
/* 223 */     else if ("um".equalsIgnoreCase(splits[0])) {
/* 224 */       tableName = tableName.substring(3);
/*     */     }
/* 226 */     return tableName;
/*     */   }
/*     */ 
/*     */   public void start()
/*     */     throws TemplateException, IOException
/*     */   {
/* 236 */     String[] tableNames = new String[0];
/* 237 */     List entitys = new ArrayList();
/* 238 */     if ((tableNames != null) && (tableNames.length > 0))
/*     */     {
/* 240 */       for (String tablename : tableNames) {
/* 241 */         Table t = this.dataMng.findTable(tablename);
/* 242 */         String entity = replaceLine(trimFirst(t.getName()));
/* 243 */         this.data.put("table", t.getName());
/* 244 */         this.data.put("fields", this.dataMng.listFields(t.getName()));
/* 245 */         loadData(entity);
/* 246 */         generatorAll();
/* 247 */         entitys.add(entity);
/*     */       }
/*     */     }
/*     */     else {
/* 251 */       List<Table> tabels = this.dataMng.listTabels();
/* 252 */       for (Table t : tabels) {
/* 253 */         String entity = replaceLine(trimFirst(t.getName()));
/* 254 */         this.data.put("table", t.getName());
/* 255 */         this.data.put("fields", this.dataMng.listFields(t.getName()));
/* 256 */         loadData(entity);
/* 257 */         generatorAll();
/* 258 */         entitys.add(entity);
/*     */       }
/*     */     }
/* 261 */     this.data.put("entitys", entitys);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer)
/*     */   {
/* 292 */     this.conf = freeMarkerConfigurer.getConfiguration();
/*     */   }
/*     */ 
/*     */   @Test
/*     */   public void generator()
/*     */     throws TemplateException, IOException
/*     */   {
/* 301 */     start();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.template.ModuleGenerator
 * JD-Core Version:    0.6.2
 */