package com.leeson.common.template;

import com.leeson.common.data.DataMng;
import com.leeson.common.data.Table;
import com.leeson.common.junit.Generator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

public class ModuleGenerator extends Generator
{
  public static final String entity_p = "com.leeson.core.bean";
  public static final String query_p = "com.leeson.core.query";
  public static final String entity_xml_p = "mybatis";
  public static final String sqlmap_config_xml = "";
  public static final String action_p = "com.leeson.core.controller";
  public static final String dao_p = "com.leeson.core.dao";
  public static final String dao_impl_p = "com.leeson.core.dao.impl";
  public static final String service_p = "com.leeson.core.service";
  public static final String service_impl_p = "com.leeson.core.service.impl";
  public static final String template_dir = "com.leeson.common.template";
  public static final String dao_tpl = "dao.txt";
  public static final String dao_impl_tpl = "dao_impl.txt";
  public static final String service_tpl = "service.txt";
  public static final String service_impl_tpl = "service_impl.txt";
  public static final String action_tpl = "action.txt";
  public static final String entity_tpl = "entity.txt";
  public static final String query_tpl = "query.txt";
  public static final String entity_xml_tpl = "entity-sqlmap.txt";
  public static final String sqlmap_config_tpl = "sqlmap-config.txt";
  public static final String sharding_rules_on_namespace_p = "dbRule";
  public static final String hash_function_p = "dbRule";
  public static final String sharding_rules_on_namespace_tpl = "sharding-rules-on-namespace.txt";
  public static final String hash_function_tpl = "hash-function.txt";
  public static final boolean is_bean = false;
  public static final boolean is_query = false;
  public static final boolean is_mappperXML = true;
  public static final boolean is_action = false;
  public static final boolean is_service = false;
  public static final boolean is_dao = false;
  public static final boolean is_mybatisConfig = false;
  public static final boolean is_hash = false;
  public static final boolean is_routerXML = false;
  public static final String SPT = File.separator;
  public static final String ENCODING = "UTF-8";
  Map<String, Object> data = new HashMap();

  File f = null;
  String tpl = null;

  @Autowired
  private DataMng dataMng;
  private Configuration conf;

  private void loadData(String entity)
  {
    String Entity = upperCaseFirst(entity);
    this.data.put("Entity", Entity);
    this.data.put("entity", entity);
    this.data.put("entity_p", "com.leeson.core.bean");
    this.data.put("query_p", "com.leeson.core.query");
    this.data.put("entity_xml_p", "mybatis");
    this.data.put("action_p", "com.leeson.core.controller");
    this.data.put("dao_p", "com.leeson.core.dao");
    this.data.put("dao_impl_p", "com.leeson.core.dao.impl");
    this.data.put("service_p", "com.leeson.core.service");
    this.data.put("service_impl_p", "com.leeson.core.service.impl");
    this.data.put("template_dir", "com.leeson.common.template");
  }

  public void generatorAll()
    throws TemplateException, IOException
  {
    this.f = new File(getFilePath((String)this.data.get("entity_xml_p"), this.data.get("entity") + "-sqlmap.xml"));
    this.tpl = (converString((String)this.data.get("template_dir")) + SPT + "entity-sqlmap.txt");
    index(this.tpl, this.f);
  }

  public void index(String tpl, File f)
    throws TemplateException, IOException
  {
    File parent = f.getParentFile();
    if (!parent.exists()) {
      parent.mkdirs();
    }
    Writer out = null;
    try
    {
      out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
      Template template = this.conf.getTemplate(tpl);
      template.process(this.data, out);
    } finally {
      if (out != null) {
        out.flush();
        out.close();
      }
    }
  }

  private String getFilePath(String packageName, String name)
  {
    String path = converString(packageName);
    return "src/" + path + "/" + name;
  }

  private String converString(String s)
  {
    s = s.replaceAll("\\.", "/");
    return s;
  }

  private String upperCaseFirst(String s)
  {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private String replaceLine(String s)
  {
    return s.replaceAll("_", "");
  }

  private String trimFirst(String tableName)
  {
    String[] splits = tableName.split("_");
    if ("psm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(4);
    else if ("mm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(3);
    else if ("dm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(3);
    else if ("pm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(3);
    else if ("sm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(3);
    else if ("tm".equalsIgnoreCase(splits[0]))
      tableName = tableName.substring(3);
    else if ("um".equalsIgnoreCase(splits[0])) {
      tableName = tableName.substring(3);
    }
    return tableName;
  }

  public void start()
    throws TemplateException, IOException
  {
    String[] tableNames = new String[0];
    List entitys = new ArrayList();
    if ((tableNames != null) && (tableNames.length > 0))
    {
      for (String tablename : tableNames) {
        Table t = this.dataMng.findTable(tablename);
        String entity = replaceLine(trimFirst(t.getName()));
        this.data.put("table", t.getName());
        this.data.put("fields", this.dataMng.listFields(t.getName()));
        loadData(entity);
        generatorAll();
        entitys.add(entity);
      }
    }
    else {
      List<Table> tabels = this.dataMng.listTabels();
      for (Table t : tabels) {
        String entity = replaceLine(trimFirst(t.getName()));
        this.data.put("table", t.getName());
        this.data.put("fields", this.dataMng.listFields(t.getName()));
        loadData(entity);
        generatorAll();
        entitys.add(entity);
      }
    }
    this.data.put("entitys", entitys);
  }

  @Autowired
  public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer)
  {
    this.conf = freeMarkerConfigurer.getConfiguration();
  }

  @Test
  public void generator()
    throws TemplateException, IOException
  {
    start();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.template.ModuleGenerator
 * JD-Core Version:    0.6.2
 */