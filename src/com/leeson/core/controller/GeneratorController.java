package com.leeson.core.controller;

import com.leeson.common.data.DataMng;
import com.leeson.common.data.Table;
import com.leeson.common.template.Generator;
import com.leeson.common.utils.stringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneratorController
{

  @Autowired
  private Generator generator;

  @Autowired
  private DataMng dataMng;

  @ResponseBody
  @RequestMapping({"/ajax_biao.action"})
  public List<String> getBiao(@RequestParam String db, ModelMap model)
  {
    if ((db != null) && (stringUtils.isNotBlank(db))) {
      this.dataMng.setDb(db);
      List<Table> tables = this.dataMng.listTabels();
      List tableNames = new ArrayList();
      for (Table table : tables) {
        tableNames.add(table.getName());
      }
      return tableNames;
    }
    return null;
  }

  @RequestMapping({"enerator.action"})
  public String generatorV(ModelMap model) {
    model.addAttribute("dbs", this.dataMng.listDBs());
    return "generatorAction/generator";
  }

  @RequestMapping(value={"enerator.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String generator(@RequestParam String db, String[] lie, @RequestParam String packageName, HttpServletRequest request, ModelMap model)
  {
    if (stringUtils.isBlank(db)) {
      model.addAttribute("msg", "请选择源数据库！");
      model.addAttribute("dbs", this.dataMng.listDBs());
      return "generatorAction/generator";
    }
    String path = request.getServletContext().getRealPath("/");
    try {
      if (lie == null)
        this.generator.generator(db, packageName, path);
      else
        this.generator.generator(db, lie, packageName, path);
    }
    catch (Exception e)
    {
      model.addAttribute("dbs", this.dataMng.listDBs());
      model.addAttribute("msg", "生成失败，发生错误！请联系管理员！");
      return "generatorAction/generator";
    }
    model.addAttribute("dbs", this.dataMng.listDBs());
    model.addAttribute("msg", "生成成功！生成的文件在项目目录中的FastOutputGenerator下面！！");
    return "generatorAction/generator";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.GeneratorController
 * JD-Core Version:    0.6.2
 */