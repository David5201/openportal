/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalcard;
/*     */ import com.leeson.core.bean.Portalcardcategory;
/*     */ import com.leeson.core.query.PortalcardQuery;
/*     */ import com.leeson.core.query.PortalcardcategoryQuery;
/*     */ import com.leeson.core.query.PortalspeedQuery;
/*     */ import com.leeson.core.service.PortalcardService;
/*     */ import com.leeson.core.service.PortalcardcategoryService;
/*     */ import com.leeson.core.service.PortalspeedService;
/*     */ import com.leeson.core.utils.ExcelUtils;
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortalcardController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardService portalcardService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalcardcategoryService portalcardcategoryService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalspeedService portalspeedService;
/*     */ 
/*     */   @RequestMapping({"/portalcard/list.action"})
/*     */   public String page(PortalcardQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  53 */     query.setUserDel(Integer.valueOf(0));
/*  54 */     query.setNameLike(true);
/*  55 */     query.setAccountNameLike(true);
/*  56 */     if (stringUtils.isBlank(query.getName())) {
/*  57 */       query.setName(null);
/*     */     }
/*  59 */     if (stringUtils.isBlank(query.getAccountName())) {
/*  60 */       query.setAccountName(null);
/*     */     }
/*  62 */     if (stringUtils.isBlank(query.getCategoryType())) {
/*  63 */       query.setCategoryType(null);
/*     */     }
/*  65 */     if (stringUtils.isBlank(query.getPayType())) {
/*  66 */       query.setPayType(null);
/*     */     }
/*  68 */     if (stringUtils.isBlank(query.getState())) {
/*  69 */       query.setState(null);
/*     */     }
/*  71 */     List speeds = this.portalspeedService
/*  72 */       .getPortalspeedList(new PortalspeedQuery());
/*  73 */     model.addAttribute("speeds", speeds);
/*  74 */     Pagination pagination = this.portalcardService
/*  75 */       .getPortalcardListWithPage(query);
/*  76 */     model.addAttribute("pagination", pagination);
/*  77 */     model.addAttribute("query", query);
/*  78 */     return "portalcard/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcard/add.action"})
/*     */   public String add(Long cardCategoryId, ModelMap model)
/*     */   {
/*  84 */     List cardCategoryList = this.portalcardcategoryService
/*  85 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  86 */     if ((cardCategoryId != null) && (cardCategoryId.longValue() != 0L)) {
/*  87 */       model.addAttribute("cardCategoryId", cardCategoryId);
/*  88 */       Double money = this.portalcardcategoryService
/*  89 */         .getPortalcardcategoryByKey(cardCategoryId).getMoney();
/*  90 */       model.addAttribute("money", money);
/*     */     }
/*  92 */     model.addAttribute("cardCategoryList", cardCategoryList);
/*  93 */     return "portalcard/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalcard/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalcard e, String cardCategoryId, String cardCount, ModelMap model)
/*     */   {
/* 100 */     if ((stringUtils.isBlank(e.getName())) || 
/* 101 */       (stringUtils.isBlank(cardCategoryId)) || 
/* 102 */       (stringUtils.isBlank(cardCount))) {
/* 103 */       model.addAttribute("msg", "充值卡分类必须选择，名称和创建数量必须填写！！！");
/* 104 */       model.addAttribute("entity", e);
/* 105 */       model.addAttribute("money", e.getMoney());
/* 106 */       if (stringUtils.isNotBlank(cardCategoryId)) {
/* 107 */         model.addAttribute("cardCategoryId", 
/* 108 */           Long.valueOf(cardCategoryId));
/*     */       }
/* 110 */       if (stringUtils.isNotBlank(cardCount)) {
/* 111 */         model.addAttribute("cardCount", Integer.valueOf(cardCount));
/*     */       }
/* 113 */       List cardCategoryList = this.portalcardcategoryService
/* 114 */         .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 115 */       model.addAttribute("cardCategoryList", cardCategoryList);
/* 116 */       return "portalcard/save";
/*     */     }
/* 118 */     Long ccId = Long.valueOf(cardCategoryId);
/* 119 */     int count = Integer.valueOf(cardCount).intValue();
/* 120 */     Portalcardcategory cc = this.portalcardcategoryService
/* 121 */       .getPortalcardcategoryByKey(ccId);
/* 122 */     String categoryType = cc.getState();
/*     */     String payType;
/* 124 */     if (categoryType.equals("0")) {
/* 125 */       payType = "2";
/*     */     }
/*     */     else
/*     */     {
/* 126 */       if (categoryType.equals("4"))
/* 127 */         payType = "4";
/*     */       else {
/* 129 */         payType = "3";
/*     */       }
/*     */     }
/* 132 */     if (e.getMoney() == null) {
/* 133 */       e.setMoney(cc.getMoney());
/*     */     }
/* 135 */     if (e.getMoney() == null) {
/* 136 */       e.setMoney(Double.valueOf(0.0D));
/*     */     }
/*     */ 
/* 139 */     Long time = cc.getTime();
/* 140 */     Long payTime = Long.valueOf(0L);
/* 141 */     if (categoryType.equals("0"))
/* 142 */       payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
/* 143 */     else if (categoryType.equals("1"))
/* 144 */       payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
/* 145 */     else if (categoryType.equals("2"))
/* 146 */       payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
/* 147 */     else if (categoryType.equals("3"))
/* 148 */       payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
/* 149 */     else if (categoryType.equals("4")) {
/* 150 */       payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
/*     */     }
/*     */ 
/* 153 */     for (int i = 1; i <= count; i++) {
/* 154 */       Portalcard card = new Portalcard();
/* 155 */       card.setName(e.getName());
/* 156 */       card.setDescription(e.getDescription());
/* 157 */       card.setCategoryType(categoryType);
/* 158 */       card.setPayType(payType);
/* 159 */       card.setState("0");
/* 160 */       card.setPayTime(payTime);
/* 161 */       card.setAccountDel(Integer.valueOf(0));
/* 162 */       card.setUserDel(Integer.valueOf(0));
/* 163 */       card.setCdKey(UUID.randomUUID().toString());
/* 164 */       card.setMoney(e.getMoney());
/*     */ 
/* 166 */       card.setMaclimit(cc.getMaclimit());
/* 167 */       card.setMaclimitcount(cc.getMaclimitcount());
/* 168 */       card.setAutologin(cc.getAutologin());
/* 169 */       card.setSpeed(cc.getSpeed());
/*     */ 
/* 171 */       this.portalcardService.addPortalcard(card);
/*     */     }
/*     */ 
/* 174 */     return "redirect:/portalcard/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcard/edit.action"})
/*     */   public String edit(@RequestParam Long id)
/*     */   {
/* 180 */     Portalcard card = this.portalcardService.getPortalcardByKey(id);
/* 181 */     int state = Integer.valueOf(card.getState()).intValue();
/* 182 */     state++;
/* 183 */     if (state > 2) {
/* 184 */       state = 0;
/*     */     }
/* 186 */     card.setState(String.valueOf(state));
/* 187 */     this.portalcardService.updatePortalcardByKey(card);
/* 188 */     return "redirect:/portalcard/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcard/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 194 */     Portalcard card = new Portalcard();
/* 195 */     card.setId(id);
/* 196 */     card.setUserDel(Integer.valueOf(1));
/* 197 */     this.portalcardService.updatePortalcardByKey(card);
/* 198 */     return "redirect:/portalcard/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalcard/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 205 */     List<Long> list = Arrays.asList(ids);
/* 206 */     for (Long id : list) {
/* 207 */       Portalcard card = new Portalcard();
/* 208 */       card.setId(id);
/* 209 */       card.setUserDel(Integer.valueOf(1));
/* 210 */       this.portalcardService.updatePortalcardByKey(card);
/*     */     }
/*     */ 
/* 213 */     return "redirect:/portalcard/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcard/out.action"})
/*     */   public String out(PortalcardQuery query, ModelMap model)
/*     */   {
/* 219 */     query.setNameLike(true);
/* 220 */     query.setAccountNameLike(true);
/* 221 */     if (stringUtils.isBlank(query.getName())) {
/* 222 */       query.setName(null);
/*     */     }
/* 224 */     if (stringUtils.isBlank(query.getAccountName())) {
/* 225 */       query.setAccountName(null);
/*     */     }
/* 227 */     if (stringUtils.isBlank(query.getCategoryType())) {
/* 228 */       query.setCategoryType(null);
/*     */     }
/* 230 */     if (stringUtils.isBlank(query.getPayType())) {
/* 231 */       query.setPayType(null);
/*     */     }
/* 233 */     if (stringUtils.isBlank(query.getState())) {
/* 234 */       query.setState(null);
/*     */     }
/* 236 */     List speeds = this.portalspeedService
/* 237 */       .getPortalspeedList(new PortalspeedQuery());
/* 238 */     model.addAttribute("speeds", speeds);
/* 239 */     Pagination pagination = this.portalcardService
/* 240 */       .getPortalcardListWithPage(query);
/* 241 */     model.addAttribute("pagination", pagination);
/* 242 */     model.addAttribute("query", query);
/* 243 */     return "portalcard/out";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalcard/outV.action"})
/*     */   public String outV(PortalcardQuery query, ModelMap model, HttpServletRequest request)
/*     */   {
/* 249 */     query.setNameLike(true);
/* 250 */     query.setAccountNameLike(true);
/* 251 */     if (stringUtils.isBlank(query.getName())) {
/* 252 */       query.setName(null);
/*     */     }
/* 254 */     if (stringUtils.isBlank(query.getAccountName())) {
/* 255 */       query.setAccountName(null);
/*     */     }
/* 257 */     if (stringUtils.isBlank(query.getCategoryType())) {
/* 258 */       query.setCategoryType(null);
/*     */     }
/* 260 */     if (stringUtils.isBlank(query.getPayType())) {
/* 261 */       query.setPayType(null);
/*     */     }
/* 263 */     if (stringUtils.isBlank(query.getState())) {
/* 264 */       query.setState(null);
/*     */     }
/* 266 */     List cards = this.portalcardService.getPortalcardList(query);
/* 267 */     String cfgPath = request.getServletContext().getRealPath("/");
/* 268 */     Date now = new Date();
/* 269 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/* 270 */     String nowString = format.format(now);
/* 271 */     File dir = new File(cfgPath + "ExcelOut/");
/* 272 */     if (!dir.exists()) {
/* 273 */       dir.mkdirs();
/*     */     }
/* 275 */     String fileName = cfgPath + "ExcelOut/card_" + nowString + ".xls";
/*     */     try {
/* 277 */       ExcelUtils.writeCardsToExcel(fileName, cards);
/*     */     } catch (Exception e) {
/* 279 */       e.printStackTrace();
/* 280 */       model.addAttribute("msg", "文件创建失败！");
/* 281 */       model.addAttribute("downUrl", null);
/* 282 */       model.addAttribute("err", Integer.valueOf(1));
/* 283 */       return "portalaccount/outResult";
/*     */     }
/* 285 */     model.addAttribute("msg", "文件创建成功！");
/* 286 */     model.addAttribute("downUrl", "card_" + nowString + ".xls");
/* 287 */     model.addAttribute("creatDate", nowString);
/* 288 */     model.addAttribute("err", Integer.valueOf(0));
/* 289 */     return "portalcard/outResult";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcardController
 * JD-Core Version:    0.6.2
 */