/*    */ package com.leeson.core.controller;
/*    */ 
/*    */ import com.leeson.common.data.DataMng;
/*    */ import com.leeson.common.data.Table;
/*    */ import com.leeson.common.template.Generator;
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ 
/*    */ @Controller
/*    */ public class GeneratorController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private Generator generator;
/*    */ 
/*    */   @Autowired
/*    */   private DataMng dataMng;
/*    */ 
/*    */   @ResponseBody
/*    */   @RequestMapping({"/ajax_biao.action"})
/*    */   public List<String> getBiao(@RequestParam String db, ModelMap model)
/*    */   {
/* 40 */     if ((db != null) && (stringUtils.isNotBlank(db))) {
/* 41 */       this.dataMng.setDb(db);
/* 42 */       List<Table> tables = this.dataMng.listTabels();
/* 43 */       List tableNames = new ArrayList();
/* 44 */       for (Table table : tables) {
/* 45 */         tableNames.add(table.getName());
/*    */       }
/* 47 */       return tableNames;
/*    */     }
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/generatorAction/generator.action"})
/*    */   public String generatorV(ModelMap model) {
/* 54 */     model.addAttribute("dbs", this.dataMng.listDBs());
/* 55 */     return "generatorAction/generator";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/generatorAction/generator.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String generator(@RequestParam String db, String[] lie, @RequestParam String packageName, HttpServletRequest request, ModelMap model)
/*    */   {
/* 61 */     if (stringUtils.isBlank(db)) {
/* 62 */       model.addAttribute("msg", "请选择源数据库！");
/* 63 */       model.addAttribute("dbs", this.dataMng.listDBs());
/* 64 */       return "generatorAction/generator";
/*    */     }
/* 66 */     String path = request.getServletContext().getRealPath("/");
/*    */     try {
/* 68 */       if (lie == null)
/* 69 */         this.generator.generator(db, packageName, path);
/*    */       else
/* 71 */         this.generator.generator(db, lie, packageName, path);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 75 */       model.addAttribute("dbs", this.dataMng.listDBs());
/* 76 */       model.addAttribute("msg", "生成失败，发生错误！请联系管理员！");
/* 77 */       return "generatorAction/generator";
/*    */     }
/* 79 */     model.addAttribute("dbs", this.dataMng.listDBs());
/* 80 */     model.addAttribute("msg", "生成成功！生成的文件在项目目录中的FastOutputGenerator下面！！");
/* 81 */     return "generatorAction/generator";
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.GeneratorController
 * JD-Core Version:    0.6.2
 */