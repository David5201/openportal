/*     */ package com.leeson.common.template;
/*     */ 
/*     */ import com.leeson.common.data.DataDaoImpl;
/*     */ import com.leeson.common.data.DataMng;
/*     */ import com.leeson.common.data.Table;
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
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
/*     */ 
/*     */ @Service
/*     */ public class Generator
/*     */ {
/*  27 */   public String outPath = "";
/*  28 */   public String entity_p = ".bean";
/*  29 */   public String query_p = ".query";
/*  30 */   public String entity_xml_p = "mapper";
/*  31 */   public String sqlmap_config_xml = "mapper";
/*     */ 
/*  33 */   public String action_p = ".controller";
/*  34 */   public String dao_p = ".dao";
/*  35 */   public String dao_impl_p = ".dao.impl";
/*  36 */   public String service_p = ".service";
/*  37 */   public String service_impl_p = ".service.impl";
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
/*     */   public static final boolean is_bean = true;
/*     */   public static final boolean is_query = true;
/*     */   public static final boolean is_mappperXML = true;
/*     */   public static final boolean is_action = true;
/*     */   public static final boolean is_service = true;
/*     */   public static final boolean is_dao = true;
/*     */   public static final boolean is_mybatisConfig = true;
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
/* 294 */   private String[] tableNames = new String[0];
/*     */ 
/*     */   private void loadData(String entity)
/*     */   {
/*  76 */     String Entity = upperCaseFirst(entity);
/*  77 */     this.data.put("Entity", Entity);
/*  78 */     this.data.put("entity", entity);
/*  79 */     this.data.put("entity_p", this.entity_p);
/*  80 */     this.data.put("query_p", this.query_p);
/*  81 */     this.data.put("entity_xml_p", this.entity_xml_p);
/*  82 */     this.data.put("action_p", this.action_p);
/*  83 */     this.data.put("dao_p", this.dao_p);
/*  84 */     this.data.put("dao_impl_p", this.dao_impl_p);
/*  85 */     this.data.put("service_p", this.service_p);
/*  86 */     this.data.put("service_impl_p", this.service_impl_p);
/*  87 */     this.data.put("template_dir", "com.leeson.common.template");
/*     */   }
/*     */ 
/*     */   public void generatorAll()
/*     */     throws TemplateException, IOException
/*     */   {
/*  97 */     this.f = new File(getFilePath((String)this.data.get("entity_p"), this.data.get("Entity") + ".java"));
/*  98 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "entity.txt");
/*  99 */     index(this.tpl, this.f);
/*     */ 
/* 104 */     this.f = new File(getFilePath((String)this.data.get("query_p"), this.data.get("Entity") + "Query.java"));
/* 105 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "query.txt");
/* 106 */     index(this.tpl, this.f);
/*     */ 
/* 111 */     this.f = new File(getFilePath((String)this.data.get("entity_xml_p"), this.data.get("entity") + "-sqlmap.xml"));
/* 112 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "entity-sqlmap.txt");
/* 113 */     index(this.tpl, this.f);
/*     */ 
/* 118 */     this.f = new File(getFilePath((String)this.data.get("action_p"), this.data.get("Entity") + "Controller.java"));
/* 119 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "action.txt");
/* 120 */     index(this.tpl, this.f);
/*     */ 
/* 125 */     this.f = new File(getFilePath((String)this.data.get("service_p"), this.data.get("Entity") + "Service.java"));
/* 126 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "service.txt");
/* 127 */     index(this.tpl, this.f);
/* 128 */     this.f = new File(getFilePath((String)this.data.get("service_impl_p"), this.data.get("Entity") + "ServiceImpl.java"));
/* 129 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "service_impl.txt");
/* 130 */     index(this.tpl, this.f);
/*     */ 
/* 135 */     this.f = new File(getFilePath((String)this.data.get("dao_p"), this.data.get("Entity") + "Dao.java"));
/* 136 */     this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "dao.txt");
/* 137 */     index(this.tpl, this.f);
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
/* 178 */     return this.outPath + "FastOutputGenerator/" + path + "/" + name;
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
/* 236 */     List entitys = new ArrayList();
/* 237 */     if ((this.tableNames != null) && (this.tableNames.length > 0))
/*     */     {
/* 239 */       for (String tablename : this.tableNames) {
/* 240 */         Table t = this.dataMng.findTable(tablename);
/* 241 */         String entity = replaceLine(trimFirst(t.getName()));
/* 242 */         this.data.put("table", t.getName());
/* 243 */         this.data.put("fields", this.dataMng.listFields(t.getName()));
/* 244 */         loadData(entity);
/* 245 */         generatorAll();
/* 246 */         entitys.add(entity);
/*     */       }
/*     */     }
/*     */     else {
/* 250 */       List<Table> tabels = this.dataMng.listTabels();
/* 251 */       for (Table t : tabels) {
/* 252 */         String entity = replaceLine(trimFirst(t.getName()));
/* 253 */         this.data.put("table", t.getName());
/* 254 */         this.data.put("fields", this.dataMng.listFields(t.getName()));
/* 255 */         loadData(entity);
/* 256 */         generatorAll();
/* 257 */         entitys.add(entity);
/*     */       }
/*     */     }
/* 260 */     this.data.put("entitys", entitys);
/*     */ 
/* 264 */     this.f = new File(getFilePath(this.sqlmap_config_xml, "SqlMapConfig.xml"));
/* 265 */     this.tpl = (converString("com.leeson.common.template") + SPT + "sqlmap-config.txt");
/* 266 */     index(this.tpl, this.f);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer)
/*     */   {
/* 291 */     this.conf = freeMarkerConfigurer.getConfiguration();
/*     */   }
/*     */ 
/*     */   public void generator(String db, String packageName, String path)
/*     */     throws TemplateException, IOException
/*     */   {
/* 301 */     this.outPath = path;
/* 302 */     this.entity_p = (packageName + this.entity_p);
/* 303 */     this.query_p = (packageName + this.query_p);
/* 304 */     this.action_p = (packageName + this.action_p);
/* 305 */     this.dao_p = (packageName + this.dao_p);
/* 306 */     this.dao_impl_p = (packageName + this.dao_impl_p);
/* 307 */     this.service_p = (packageName + this.service_p);
/* 308 */     this.service_impl_p = (packageName + this.service_impl_p);
/*     */ 
/* 310 */     DataDaoImpl.setDb(db);
/* 311 */     start();
/*     */   }
/*     */ 
/*     */   public void generator(String db, String[] tables, String packageName, String path)
/*     */     throws TemplateException, IOException
/*     */   {
/* 319 */     this.outPath = path;
/* 320 */     this.entity_p = (packageName + this.entity_p);
/* 321 */     this.query_p = (packageName + this.query_p);
/* 322 */     this.action_p = (packageName + this.action_p);
/* 323 */     this.dao_p = (packageName + this.dao_p);
/* 324 */     this.dao_impl_p = (packageName + this.dao_impl_p);
/* 325 */     this.service_p = (packageName + this.service_p);
/* 326 */     this.service_impl_p = (packageName + this.service_impl_p);
/* 327 */     this.tableNames = tables;
/* 328 */     DataDaoImpl.setDb(db);
/* 329 */     start();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.template.Generator
 * JD-Core Version:    0.6.2
 */