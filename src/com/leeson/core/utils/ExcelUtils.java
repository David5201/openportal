/*      */ package com.leeson.core.utils;
/*      */ 
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalcard;
/*      */ import com.leeson.core.bean.Portallinkrecordall;
/*      */ import com.leeson.core.bean.Portalorder;
/*      */ import com.leeson.core.bean.Portalspeed;
/*      */ import com.leeson.core.bean.Radiuslinkrecordall;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalspeedQuery;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalspeedService;
/*      */ import com.leeson.portal.core.utils.SpringContextHelper;
/*      */ import java.io.File;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import jxl.Cell;
/*      */ import jxl.Sheet;
/*      */ import jxl.Workbook;
/*      */ import jxl.format.Colour;
/*      */ import jxl.format.UnderlineStyle;
/*      */ import jxl.write.DateFormat;
/*      */ import jxl.write.DateTime;
/*      */ import jxl.write.Label;
/*      */ import jxl.write.Number;
/*      */ import jxl.write.NumberFormat;
/*      */ import jxl.write.WritableCellFormat;
/*      */ import jxl.write.WritableFont;
/*      */ import jxl.write.WritableSheet;
/*      */ import jxl.write.WritableWorkbook;
/*      */ 
/*      */ public class ExcelUtils
/*      */ {
/*   45 */   private static PortalaccountService accountService = (PortalaccountService)
/*   46 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*      */ 
/*   47 */   private static PortalspeedService speedService = (PortalspeedService)
/*   48 */     SpringContextHelper.getBean("portalspeedServiceImpl");
/*      */ 
/*   50 */   private static SimpleDateFormat format = new SimpleDateFormat(
/*   51 */     "yyyy-MM-dd hh:mm:ss");
/*      */ 
/*   52 */   private static DecimalFormat textf = new DecimalFormat(".##");
/*      */ 
/*      */   public static void writeAccountToExcel(String fileName, List<Portalaccount> accounts)
/*      */     throws Exception
/*      */   {
/*   63 */     Number n = null;
/*   64 */     DateTime d = null;
/*      */ 
/*   66 */     File tempFile = new File(fileName);
/*   67 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/*   68 */     WritableSheet sheet = workbook.createSheet("接入账户列表", 0);
/*      */ 
/*   72 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/*   73 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*   74 */       Colour.GREEN);
/*   75 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/*   78 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/*   79 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*   80 */       Colour.RED);
/*   81 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/*   84 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/*   85 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*   86 */       Colour.BLACK);
/*   87 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/*   90 */     NumberFormat nf = new NumberFormat("0");
/*   91 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/*   94 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/*   95 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/*   98 */     Label l = new Label(0, 0, "接入账户列表", headerFormat);
/*   99 */     sheet.addCell(l);
/*      */ 
/*  102 */     int column = 0;
/*  103 */     l = new Label(column++, 1, "序号", titleFormat);
/*  104 */     sheet.addCell(l);
/*  105 */     l = new Label(column++, 1, "登录名", titleFormat);
/*  106 */     sheet.addCell(l);
/*  107 */     l = new Label(column++, 1, "姓名", titleFormat);
/*  108 */     sheet.addCell(l);
/*  109 */     l = new Label(column++, 1, "性别", titleFormat);
/*  110 */     sheet.addCell(l);
/*  111 */     l = new Label(column++, 1, "电话号码", titleFormat);
/*  112 */     sheet.addCell(l);
/*  113 */     l = new Label(column++, 1, "电子邮件", titleFormat);
/*  114 */     sheet.addCell(l);
/*  115 */     l = new Label(column++, 1, "说明", titleFormat);
/*  116 */     sheet.addCell(l);
/*  117 */     l = new Label(column++, 1, "类别", titleFormat);
/*  118 */     sheet.addCell(l);
/*  119 */     l = new Label(column++, 1, "买断到期", titleFormat);
/*  120 */     sheet.addCell(l);
/*  121 */     l = new Label(column++, 1, "剩余时长(分钟)", titleFormat);
/*  122 */     sheet.addCell(l);
/*  123 */     l = new Label(column++, 1, "剩余流量(M)", titleFormat);
/*  124 */     sheet.addCell(l);
/*      */ 
/*  126 */     l = new Label(column++, 1, "密码", titleFormat);
/*  127 */     sheet.addCell(l);
/*  128 */     l = new Label(column++, 1, "MAC限制", titleFormat);
/*  129 */     sheet.addCell(l);
/*  130 */     l = new Label(column++, 1, "自动绑定MAC个数（MAC共享数）", titleFormat);
/*  131 */     sheet.addCell(l);
/*  132 */     l = new Label(column++, 1, "无感知", titleFormat);
/*  133 */     sheet.addCell(l);
/*  134 */     l = new Label(column++, 1, "限速", titleFormat);
/*  135 */     sheet.addCell(l);
/*      */ 
/*  137 */     int y = accounts.size();
/*  138 */     for (int i = 0; i < y; i++) {
/*  139 */       column = 0;
/*  140 */       l = new Label(column++, i + 2, String.valueOf(i + 1), detFormat);
/*  141 */       sheet.addCell(l);
/*  142 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getLoginName(), 
/*  143 */         detFormat);
/*  144 */       sheet.addCell(l);
/*  145 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getName(), detFormat);
/*  146 */       sheet.addCell(l);
/*      */       String sex;
/*  149 */       if ("0".equals(((Portalaccount)accounts.get(i)).getGender())) {
/*  150 */         sex = "女";
/*      */       }
/*      */       else
/*      */       {
/*  151 */         if ("1".equals(((Portalaccount)accounts.get(i)).getGender()))
/*  152 */           sex = "男";
/*      */         else
/*  154 */           sex = "错误";
/*      */       }
/*  156 */       l = new Label(column++, i + 2, sex, detFormat);
/*  157 */       sheet.addCell(l);
/*      */ 
/*  159 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getPhoneNumber(), 
/*  160 */         detFormat);
/*  161 */       sheet.addCell(l);
/*  162 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getEmail(), 
/*  163 */         detFormat);
/*  164 */       sheet.addCell(l);
/*  165 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getDescription(), 
/*  166 */         detFormat);
/*  167 */       sheet.addCell(l);
/*      */       String state;
/*  170 */       if (((Portalaccount)accounts.get(i)).getState().equals(String.valueOf(1))) {
/*  171 */         state = "免费";
/*      */       }
/*      */       else
/*      */       {
/*  172 */         if (((Portalaccount)accounts.get(i)).getState().equals(String.valueOf(2))) {
/*  173 */           state = "计时";
/*      */         }
/*      */         else
/*      */         {
/*  174 */           if (((Portalaccount)accounts.get(i)).getState().equals(String.valueOf(3))) {
/*  175 */             state = "买断";
/*      */           }
/*      */           else
/*      */           {
/*  176 */             if (((Portalaccount)accounts.get(i)).getState().equals(String.valueOf(4)))
/*  177 */               state = "流量";
/*      */             else
/*  179 */               state = "停用"; 
/*      */           }
/*      */         }
/*      */       }
/*  181 */       l = new Label(column++, i + 2, state, detFormat);
/*  182 */       sheet.addCell(l);
/*      */ 
/*  184 */       d = new DateTime(column++, i + 2, ((Portalaccount)accounts.get(i)).getDate(), 
/*  185 */         dateFormat);
/*  186 */       sheet.addCell(d);
/*      */ 
/*  188 */       n = new Number(column++, i + 2, 
/*  189 */         ((Portalaccount)accounts.get(i)).getTime().longValue() / 1000L / 60L, priceFormat);
/*  190 */       sheet.addCell(n);
/*      */ 
/*  192 */       Long octets = ((Portalaccount)accounts.get(i)).getOctets();
/*  193 */       if (octets == null)
/*  194 */         octets = Long.valueOf(0L);
/*      */       else {
/*  196 */         octets = Long.valueOf(octets.longValue() / 1024L / 1024L);
/*      */       }
/*  198 */       n = new Number(column++, i + 2, octets.longValue(), priceFormat);
/*  199 */       sheet.addCell(n);
/*      */ 
/*  201 */       l = new Label(column++, i + 2, ((Portalaccount)accounts.get(i)).getPassword(), 
/*  202 */         detFormat);
/*  203 */       sheet.addCell(l);
/*      */ 
/*  205 */       Integer maclimitState = ((Portalaccount)accounts.get(i)).getMaclimit();
/*  206 */       Integer macCount = ((Portalaccount)accounts.get(i)).getMaclimitcount();
/*  207 */       if (maclimitState != null) {
/*  208 */         if (maclimitState.intValue() == 1)
/*  209 */           l = new Label(column++, i + 2, "启用", detFormat);
/*  210 */         else if (maclimitState.intValue() == 0)
/*  211 */           l = new Label(column++, i + 2, "关闭", detFormat);
/*      */         else
/*  213 */           l = new Label(column++, i + 2, "错误", detFormat);
/*      */       }
/*      */       else {
/*  216 */         l = new Label(column++, i + 2, "未知", detFormat);
/*      */       }
/*  218 */       sheet.addCell(l);
/*      */ 
/*  220 */       if (macCount != null) {
/*  221 */         if (macCount.intValue() >= 0)
/*  222 */           n = new Number(column++, i + 2, macCount.intValue(), 
/*  223 */             priceFormat);
/*      */         else
/*  225 */           n = new Number(column++, i + 2, 1.0D, priceFormat);
/*      */       }
/*      */       else {
/*  228 */         n = new Number(column++, i + 2, 1.0D, priceFormat);
/*      */       }
/*  230 */       sheet.addCell(n);
/*      */ 
/*  232 */       Integer macAuto = ((Portalaccount)accounts.get(i)).getAutologin();
/*  233 */       if (macAuto != null) {
/*  234 */         if (macAuto.intValue() == 1)
/*  235 */           l = new Label(column++, i + 2, "启用", detFormat);
/*  236 */         else if (macAuto.intValue() == 0)
/*  237 */           l = new Label(column++, i + 2, "关闭", detFormat);
/*      */         else
/*  239 */           l = new Label(column++, i + 2, "错误", detFormat);
/*      */       }
/*      */       else {
/*  242 */         l = new Label(column++, i + 2, "未知", detFormat);
/*      */       }
/*  244 */       sheet.addCell(l);
/*      */ 
/*  246 */       String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       try {
/*  248 */         speed = speedService.getPortalspeedByKey(((Portalaccount)accounts.get(i)).getSpeed()).getName();
/*      */       } catch (Exception e) {
/*  250 */         speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       }
/*  252 */       l = new Label(column++, i + 2, speed, detFormat);
/*  253 */       sheet.addCell(l);
/*      */     }
/*      */ 
/*  257 */     column = 0;
/*  258 */     sheet.setColumnView(column++, 10);
/*  259 */     sheet.setColumnView(column++, 20);
/*  260 */     sheet.setColumnView(column++, 20);
/*  261 */     sheet.setColumnView(column++, 10);
/*  262 */     sheet.setColumnView(column++, 20);
/*  263 */     sheet.setColumnView(column++, 20);
/*  264 */     sheet.setColumnView(column++, 20);
/*  265 */     sheet.setColumnView(column++, 10);
/*  266 */     sheet.setColumnView(column++, 20);
/*  267 */     sheet.setColumnView(column++, 15);
/*      */ 
/*  269 */     workbook.write();
/*  270 */     workbook.close();
/*      */   }
/*      */ 
/*      */   public static List<Portalaccount> readExcelAccount(String fileName)
/*      */     throws Exception
/*      */   {
/*  281 */     List unInAccounts = new ArrayList();
/*  282 */     Workbook book = Workbook.getWorkbook(new File(fileName));
/*      */ 
/*  284 */     Sheet sheet = book.getSheet(0);
/*      */ 
/*  286 */     int columNum = sheet.getColumns();
/*  287 */     int rowNum = sheet.getRows();
/*      */ 
/*  291 */     for (int i = 2; i < rowNum; i++) {
/*  292 */       Portalaccount account = new Portalaccount();
/*  293 */       int macLimit = 0;
/*  294 */       int count = 1;
/*  295 */       int isAuto = 0;
/*  296 */       String password = "1234";
/*  297 */       long sid = 1L;
/*  298 */       for (int j = 1; j < columNum; j++) {
/*  299 */         Cell cell = sheet.getCell(j, i);
/*  300 */         String result = cell.getContents();
/*      */ 
/*  303 */         if ((j == 1) && 
/*  304 */           (stringUtils.isNotBlank(result))) {
/*  305 */           account.setLoginName(result);
/*      */         }
/*      */ 
/*  308 */         if ((j == 2) && 
/*  309 */           (stringUtils.isNotBlank(result))) {
/*  310 */           account.setName(result);
/*      */         }
/*      */ 
/*  313 */         if (j == 3) {
/*  314 */           if ("男".equals(result))
/*  315 */             account.setGender("1");
/*  316 */           else if ("女".equals(result)) {
/*  317 */             account.setGender("0");
/*      */           }
/*      */         }
/*  320 */         if ((j == 4) && 
/*  321 */           (stringUtils.isNotBlank(result))) {
/*  322 */           account.setPhoneNumber(result);
/*      */         }
/*      */ 
/*  325 */         if ((j == 5) && 
/*  326 */           (stringUtils.isNotBlank(result))) {
/*  327 */           account.setEmail(result);
/*      */         }
/*      */ 
/*  330 */         if ((j == 6) && 
/*  331 */           (stringUtils.isNotBlank(result))) {
/*  332 */           account.setDescription(result);
/*      */         }
/*      */ 
/*  335 */         if (j == 7) {
/*  336 */           if ("停用".equals(result))
/*  337 */             account.setState("0");
/*  338 */           else if ("免费".equals(result))
/*  339 */             account.setState("1");
/*  340 */           else if ("计时".equals(result))
/*  341 */             account.setState("2");
/*  342 */           else if ("买断".equals(result))
/*  343 */             account.setState("3");
/*  344 */           else if ("流量".equals(result))
/*  345 */             account.setState("4");
/*      */           else {
/*  347 */             account.setState("0");
/*      */           }
/*      */         }
/*  350 */         if (j == 8) {
/*      */           try {
/*  352 */             Date accDate = format.parse(result);
/*  353 */             account.setDate(accDate);
/*      */           } catch (Exception e) {
/*  355 */             account.setDate(new Date());
/*      */           }
/*      */         }
/*  358 */         if (j == 9) {
/*      */           try {
/*  360 */             long accTime = Long.parseLong(result);
/*  361 */             account.setTime(Long.valueOf(accTime * 60L * 1000L));
/*      */           } catch (Exception e) {
/*  363 */             account.setTime(Long.valueOf(0L));
/*      */           }
/*      */         }
/*  366 */         if (j == 10) {
/*      */           try {
/*  368 */             long accTime = Long.parseLong(result);
/*  369 */             account.setOctets(Long.valueOf(accTime * 1024L * 1024L));
/*      */           } catch (Exception e) {
/*  371 */             account.setOctets(Long.valueOf(0L));
/*      */           }
/*      */         }
/*  374 */         if (j == 11) {
/*  375 */           if ((result == null) || (result.equals("")))
/*  376 */             password = "1234";
/*      */           else {
/*  378 */             password = result;
/*      */           }
/*  380 */           account.setPassword(password);
/*      */         }
/*  382 */         if ((j == 12) && 
/*  383 */           ("启用".equals(result))) {
/*  384 */           macLimit = 1;
/*      */         }
/*      */ 
/*  387 */         if (j == 13)
/*      */           try {
/*  389 */             int state = Integer.valueOf(result).intValue();
/*  390 */             if (state >= 0)
/*  391 */               count = state;
/*      */           }
/*      */           catch (Exception localException1)
/*      */           {
/*      */           }
/*  396 */         if ((j == 14) && 
/*  397 */           ("启用".equals(result))) {
/*  398 */           isAuto = 1;
/*      */         }
/*      */ 
/*  401 */         if (j == 15) {
/*      */           try {
/*  403 */             PortalspeedQuery sq = new PortalspeedQuery();
/*  404 */             sq.setName(result);
/*  405 */             sq.setNameLike(false);
/*  406 */             sid = ((Portalspeed)speedService.getPortalspeedList(sq).get(0))
/*  407 */               .getId().longValue();
/*      */           } catch (Exception e) {
/*  409 */             sid = 1L;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  414 */       PortalaccountQuery aq = new PortalaccountQuery();
/*  415 */       aq.setLoginName(account.getLoginName());
/*  416 */       aq.setLoginNameLike(false);
/*      */ 
/*  418 */       Integer haveAcc = accountService.getPortalaccountCount(new PortalaccountQuery());
/*  419 */       if (haveAcc != null) {
/*  420 */         if (haveAcc.intValue() >= Integer.valueOf(((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
/*  421 */           unInAccounts.add(account);
/*      */         }
/*  423 */         else if (accountService.getPortalaccountList(aq).size() > 0)
/*  424 */           unInAccounts.add(account);
/*      */         else
/*      */           try {
/*  427 */             account.setMaclimit(Integer.valueOf(macLimit));
/*  428 */             account.setMaclimitcount(Integer.valueOf(count));
/*  429 */             account.setAutologin(Integer.valueOf(isAuto));
/*  430 */             account.setSpeed(Long.valueOf(sid));
/*  431 */             accountService.addPortalaccount(account);
/*      */           } catch (Exception e) {
/*  433 */             unInAccounts.add(account);
/*      */           }
/*      */       }
/*      */       else {
/*      */         try
/*      */         {
/*  439 */           account.setMaclimit(Integer.valueOf(macLimit));
/*  440 */           account.setMaclimitcount(Integer.valueOf(count));
/*  441 */           account.setAutologin(Integer.valueOf(isAuto));
/*  442 */           account.setSpeed(Long.valueOf(sid));
/*  443 */           accountService.addPortalaccount(account);
/*      */         } catch (Exception e) {
/*  445 */           unInAccounts.add(account);
/*      */         }
/*      */       }
/*      */     }
/*  449 */     book.close();
/*  450 */     return unInAccounts;
/*      */   }
/*      */ 
/*      */   public static void writeCardsToExcel(String fileName, List<Portalcard> cards)
/*      */     throws Exception
/*      */   {
/*  462 */     Number n = null;
/*  463 */     DateTime d = null;
/*      */ 
/*  465 */     File tempFile = new File(fileName);
/*  466 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/*  467 */     WritableSheet sheet = workbook.createSheet("充值卡列表", 0);
/*      */ 
/*  471 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/*  472 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  473 */       Colour.GREEN);
/*  474 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/*  477 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  478 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  479 */       Colour.RED);
/*  480 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/*  483 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  484 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  485 */       Colour.BLACK);
/*  486 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/*  489 */     NumberFormat nf = new NumberFormat("0.00");
/*  490 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/*  493 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/*  494 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/*  497 */     Label l = new Label(0, 0, "充值卡列表", headerFormat);
/*  498 */     sheet.addCell(l);
/*      */ 
/*  514 */     int column = 0;
/*  515 */     l = new Label(column++, 1, "序号", titleFormat);
/*  516 */     sheet.addCell(l);
/*  517 */     l = new Label(column++, 1, "名称", titleFormat);
/*  518 */     sheet.addCell(l);
/*  519 */     l = new Label(column++, 1, "详细信息", titleFormat);
/*  520 */     sheet.addCell(l);
/*  521 */     l = new Label(column++, 1, "CD-KEY", titleFormat);
/*  522 */     sheet.addCell(l);
/*  523 */     l = new Label(column++, 1, "充值类型", titleFormat);
/*  524 */     sheet.addCell(l);
/*  525 */     l = new Label(column++, 1, "分类", titleFormat);
/*  526 */     sheet.addCell(l);
/*  527 */     l = new Label(column++, 1, "计数", titleFormat);
/*  528 */     sheet.addCell(l);
/*  529 */     l = new Label(column++, 1, "MAC限制", titleFormat);
/*  530 */     sheet.addCell(l);
/*  531 */     l = new Label(column++, 1, "自动绑定MAC个数（MAC共享数）", titleFormat);
/*  532 */     sheet.addCell(l);
/*  533 */     l = new Label(column++, 1, "无感知", titleFormat);
/*  534 */     sheet.addCell(l);
/*  535 */     l = new Label(column++, 1, "限速", titleFormat);
/*  536 */     sheet.addCell(l);
/*  537 */     l = new Label(column++, 1, "售价（元）", titleFormat);
/*  538 */     sheet.addCell(l);
/*  539 */     l = new Label(column++, 1, "状态", titleFormat);
/*  540 */     sheet.addCell(l);
/*  541 */     l = new Label(column++, 1, "售出日期", titleFormat);
/*  542 */     sheet.addCell(l);
/*  543 */     l = new Label(column++, 1, "充值用户", titleFormat);
/*  544 */     sheet.addCell(l);
/*  545 */     l = new Label(column++, 1, "充值日期", titleFormat);
/*  546 */     sheet.addCell(l);
/*      */ 
/*  548 */     int y = cards.size();
/*  549 */     for (int i = 0; i < y; i++) {
/*  550 */       column = 0;
/*  551 */       l = new Label(column++, i + 2, String.valueOf(i + 1), detFormat);
/*  552 */       sheet.addCell(l);
/*  553 */       l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getName(), detFormat);
/*  554 */       sheet.addCell(l);
/*  555 */       l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getDescription(), 
/*  556 */         detFormat);
/*  557 */       sheet.addCell(l);
/*  558 */       l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getCdKey(), detFormat);
/*  559 */       sheet.addCell(l);
/*      */       String payType;
/*  562 */       if ("2".equals(((Portalcard)cards.get(i)).getPayType())) {
/*  563 */         payType = "计时";
/*      */       }
/*      */       else
/*      */       {
/*  564 */         if ("3".equals(((Portalcard)cards.get(i)).getPayType())) {
/*  565 */           payType = "买断";
/*      */         }
/*      */         else
/*      */         {
/*  566 */           if ("4".equals(((Portalcard)cards.get(i)).getPayType()))
/*  567 */             payType = "流量";
/*      */           else
/*  569 */             payType = "错误"; 
/*      */         }
/*      */       }
/*  571 */       l = new Label(column++, i + 2, payType, detFormat);
/*  572 */       sheet.addCell(l);
/*      */       String categoryType;
/*  575 */       if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  576 */         categoryType = "包时卡";
/*      */       }
/*      */       else
/*      */       {
/*  577 */         if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  578 */           categoryType = "日卡";
/*      */         }
/*      */         else
/*      */         {
/*  579 */           if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  580 */             categoryType = "月卡";
/*      */           }
/*      */           else
/*      */           {
/*  581 */             if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  582 */               categoryType = "年卡";
/*      */             }
/*      */             else
/*      */             {
/*  583 */               if ("4".equals(((Portalcard)cards.get(i)).getCategoryType()))
/*  584 */                 categoryType = "流量卡";
/*      */               else
/*  586 */                 categoryType = "错误"; 
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  588 */       l = new Label(column++, i + 2, categoryType, detFormat);
/*  589 */       sheet.addCell(l);
/*      */ 
/*  591 */       Long payTime = Long.valueOf(0L);
/*  592 */       String payTimeString = "";
/*  593 */       if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  594 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
/*  595 */         payTimeString = String.valueOf(payTime) + "小时";
/*  596 */       } else if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  597 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
/*  598 */         payTimeString = String.valueOf(payTime) + "天";
/*  599 */       } else if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  600 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
/*  601 */         payTimeString = String.valueOf(payTime) + "月";
/*  602 */       } else if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  603 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L / 
/*  604 */           12L);
/*  605 */         payTimeString = String.valueOf(payTime) + "年";
/*  606 */       } else if ("4".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/*  607 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
/*  608 */         payTimeString = String.valueOf(payTime) + "M";
/*      */       } else {
/*  610 */         payTimeString = "错误";
/*      */       }
/*  612 */       l = new Label(column++, i + 2, payTimeString, detFormat);
/*  613 */       sheet.addCell(l);
/*      */ 
/*  615 */       Integer maclimitState = ((Portalcard)cards.get(i)).getMaclimit();
/*  616 */       Integer macCount = ((Portalcard)cards.get(i)).getMaclimitcount();
/*  617 */       if (maclimitState != null) {
/*  618 */         if (maclimitState.intValue() == 1)
/*  619 */           l = new Label(column++, i + 2, "启用", detFormat);
/*  620 */         else if (maclimitState.intValue() == 0)
/*  621 */           l = new Label(column++, i + 2, "关闭", detFormat);
/*      */         else
/*  623 */           l = new Label(column++, i + 2, "错误", detFormat);
/*      */       }
/*      */       else {
/*  626 */         l = new Label(column++, i + 2, "未知", detFormat);
/*      */       }
/*  628 */       sheet.addCell(l);
/*      */ 
/*  630 */       if (macCount != null) {
/*  631 */         if (macCount.intValue() >= 0)
/*  632 */           n = new Number(column++, i + 2, macCount.intValue(), 
/*  633 */             priceFormat);
/*      */         else
/*  635 */           n = new Number(column++, i + 2, 1.0D, priceFormat);
/*      */       }
/*      */       else {
/*  638 */         n = new Number(column++, i + 2, 1.0D, priceFormat);
/*      */       }
/*  640 */       sheet.addCell(n);
/*      */ 
/*  642 */       Integer macAuto = ((Portalcard)cards.get(i)).getAutologin();
/*  643 */       if (macAuto != null) {
/*  644 */         if (macAuto.intValue() == 1)
/*  645 */           l = new Label(column++, i + 2, "启用", detFormat);
/*  646 */         else if (macAuto.intValue() == 0)
/*  647 */           l = new Label(column++, i + 2, "关闭", detFormat);
/*      */         else
/*  649 */           l = new Label(column++, i + 2, "错误", detFormat);
/*      */       }
/*      */       else {
/*  652 */         l = new Label(column++, i + 2, "未知", detFormat);
/*      */       }
/*  654 */       sheet.addCell(l);
/*      */ 
/*  656 */       String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       try {
/*  658 */         speed = speedService.getPortalspeedByKey(((Portalcard)cards.get(i)).getSpeed()).getName();
/*      */       } catch (Exception e) {
/*  660 */         speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       }
/*  662 */       l = new Label(column++, i + 2, speed, detFormat);
/*  663 */       sheet.addCell(l);
/*      */ 
/*  665 */       l = new Label(column++, i + 2, String.valueOf(((Portalcard)cards.get(i))
/*  666 */         .getMoney()), priceFormat);
/*  667 */       sheet.addCell(l);
/*      */       String state;
/*  670 */       if ("0".equals(((Portalcard)cards.get(i)).getState())) {
/*  671 */         state = "新卡";
/*      */       }
/*      */       else
/*      */       {
/*  672 */         if ("1".equals(((Portalcard)cards.get(i)).getState())) {
/*  673 */           state = "已售出";
/*      */         }
/*      */         else
/*      */         {
/*  674 */           if ("2".equals(((Portalcard)cards.get(i)).getState())) {
/*  675 */             state = "已激活";
/*      */           }
/*      */           else
/*      */           {
/*  676 */             if ("-1".equals(((Portalcard)cards.get(i)).getState()))
/*  677 */               state = "未支付";
/*      */             else
/*  679 */               state = "错误"; 
/*      */           }
/*      */         }
/*      */       }
/*  681 */       l = new Label(column++, i + 2, state, detFormat);
/*  682 */       sheet.addCell(l);
/*      */ 
/*  684 */       if (((Portalcard)cards.get(i)).getBuyDate() != null) {
/*  685 */         d = new DateTime(column++, i + 2, ((Portalcard)cards.get(i)).getBuyDate(), 
/*  686 */           dateFormat);
/*  687 */         sheet.addCell(d);
/*      */       } else {
/*  689 */         l = new Label(column++, i + 2, "", detFormat);
/*  690 */         sheet.addCell(l);
/*      */       }
/*      */ 
/*  693 */       l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getAccountName(), 
/*  694 */         detFormat);
/*  695 */       sheet.addCell(l);
/*      */ 
/*  697 */       if (((Portalcard)cards.get(i)).getPayDate() != null) {
/*  698 */         d = new DateTime(column++, i + 2, ((Portalcard)cards.get(i)).getPayDate(), 
/*  699 */           dateFormat);
/*  700 */         sheet.addCell(d);
/*      */       } else {
/*  702 */         l = new Label(column++, i + 2, "", detFormat);
/*  703 */         sheet.addCell(l);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  709 */     column = 0;
/*  710 */     sheet.setColumnView(column++, 10);
/*  711 */     sheet.setColumnView(column++, 20);
/*  712 */     sheet.setColumnView(column++, 30);
/*  713 */     sheet.setColumnView(column++, 40);
/*  714 */     sheet.setColumnView(column++, 10);
/*  715 */     sheet.setColumnView(column++, 10);
/*  716 */     sheet.setColumnView(column++, 10);
/*  717 */     sheet.setColumnView(column++, 10);
/*  718 */     sheet.setColumnView(column++, 10);
/*  719 */     sheet.setColumnView(column++, 20);
/*  720 */     sheet.setColumnView(column++, 10);
/*  721 */     sheet.setColumnView(column++, 20);
/*      */ 
/*  723 */     workbook.write();
/*  724 */     workbook.close();
/*      */   }
/*      */ 
/*      */   public static void writeOrdersToExcel(String fileName, List<Portalorder> cards, String money)
/*      */     throws Exception
/*      */   {
/*  737 */     DateTime d = null;
/*      */ 
/*  739 */     File tempFile = new File(fileName);
/*  740 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/*  741 */     WritableSheet sheet = workbook.createSheet("订单报表", 0);
/*      */ 
/*  745 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/*  746 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  747 */       Colour.GREEN);
/*  748 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/*  751 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  752 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  753 */       Colour.RED);
/*  754 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/*  757 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  758 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  759 */       Colour.BLACK);
/*  760 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/*  763 */     NumberFormat nf = new NumberFormat("0.00");
/*  764 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/*  767 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/*  768 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/*  771 */     Label l = new Label(0, 0, "订单报表", headerFormat);
/*  772 */     sheet.addCell(l);
/*      */ 
/*  774 */     l = new Label(0, 1, "订单报表内容，收入共计： " + money + " 元", titleFormat);
/*  775 */     sheet.addCell(l);
/*      */ 
/*  791 */     int column = 0;
/*  792 */     l = new Label(column++, 2, "序号", titleFormat);
/*  793 */     sheet.addCell(l);
/*  794 */     l = new Label(column++, 2, "名称", titleFormat);
/*  795 */     sheet.addCell(l);
/*  796 */     l = new Label(column++, 2, "详细信息", titleFormat);
/*  797 */     sheet.addCell(l);
/*  798 */     l = new Label(column++, 2, "CD-KEY", titleFormat);
/*  799 */     sheet.addCell(l);
/*  800 */     l = new Label(column++, 2, "充值类型", titleFormat);
/*  801 */     sheet.addCell(l);
/*  802 */     l = new Label(column++, 2, "分类", titleFormat);
/*  803 */     sheet.addCell(l);
/*  804 */     l = new Label(column++, 2, "计数", titleFormat);
/*  805 */     sheet.addCell(l);
/*  806 */     l = new Label(column++, 2, "状态", titleFormat);
/*  807 */     sheet.addCell(l);
/*  808 */     l = new Label(column++, 2, "创建日期", titleFormat);
/*  809 */     sheet.addCell(l);
/*  810 */     l = new Label(column++, 2, "充值用户", titleFormat);
/*  811 */     sheet.addCell(l);
/*  812 */     l = new Label(column++, 2, "支付日期", titleFormat);
/*  813 */     sheet.addCell(l);
/*  814 */     l = new Label(column++, 2, "支付渠道", titleFormat);
/*  815 */     sheet.addCell(l);
/*  816 */     l = new Label(column++, 2, "交易流水号", titleFormat);
/*  817 */     sheet.addCell(l);
/*  818 */     l = new Label(column++, 2, "买家帐号", titleFormat);
/*  819 */     sheet.addCell(l);
/*  820 */     l = new Label(column++, 2, "卖家帐号", titleFormat);
/*  821 */     sheet.addCell(l);
/*  822 */     l = new Label(column++, 2, "售价（元）", titleFormat);
/*  823 */     sheet.addCell(l);
/*      */ 
/*  825 */     int y = cards.size();
/*  826 */     for (int i = 0; i < y; i++) {
/*  827 */       column = 0;
/*  828 */       l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
/*  829 */       sheet.addCell(l);
/*  830 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getName(), detFormat);
/*  831 */       sheet.addCell(l);
/*  832 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getDescription(), 
/*  833 */         detFormat);
/*  834 */       sheet.addCell(l);
/*  835 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getCdKey(), detFormat);
/*  836 */       sheet.addCell(l);
/*      */       String payType;
/*  839 */       if ("2".equals(((Portalorder)cards.get(i)).getPayType())) {
/*  840 */         payType = "计时";
/*      */       }
/*      */       else
/*      */       {
/*  841 */         if ("3".equals(((Portalorder)cards.get(i)).getPayType())) {
/*  842 */           payType = "买断";
/*      */         }
/*      */         else
/*      */         {
/*  843 */           if ("4".equals(((Portalorder)cards.get(i)).getPayType()))
/*  844 */             payType = "流量";
/*      */           else
/*  846 */             payType = "错误"; 
/*      */         }
/*      */       }
/*  848 */       l = new Label(column++, i + 3, payType, detFormat);
/*  849 */       sheet.addCell(l);
/*      */       String categoryType;
/*  852 */       if ("0".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  853 */         categoryType = "包时卡";
/*      */       }
/*      */       else
/*      */       {
/*  854 */         if ("1".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  855 */           categoryType = "日卡";
/*      */         }
/*      */         else
/*      */         {
/*  856 */           if ("2".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  857 */             categoryType = "月卡";
/*      */           }
/*      */           else
/*      */           {
/*  858 */             if ("3".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  859 */               categoryType = "年卡";
/*      */             }
/*      */             else
/*      */             {
/*  860 */               if ("4".equals(((Portalorder)cards.get(i)).getCategoryType()))
/*  861 */                 categoryType = "流量卡";
/*      */               else
/*  863 */                 categoryType = "错误"; 
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  865 */       l = new Label(column++, i + 3, categoryType, detFormat);
/*  866 */       sheet.addCell(l);
/*      */ 
/*  868 */       Long payTime = Long.valueOf(0L);
/*  869 */       String payTimeString = "";
/*  870 */       if ("0".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  871 */         payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
/*  872 */         payTimeString = String.valueOf(payTime) + "小时";
/*  873 */       } else if ("1".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  874 */         payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
/*  875 */         payTimeString = String.valueOf(payTime) + "天";
/*  876 */       } else if ("2".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  877 */         payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
/*  878 */         payTimeString = String.valueOf(payTime) + "月";
/*  879 */       } else if ("3".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  880 */         payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L / 
/*  881 */           12L);
/*  882 */         payTimeString = String.valueOf(payTime) + "年";
/*  883 */       } else if ("4".equals(((Portalorder)cards.get(i)).getCategoryType())) {
/*  884 */         payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
/*  885 */         payTimeString = String.valueOf(payTime) + "M";
/*      */       } else {
/*  887 */         payTimeString = "错误";
/*      */       }
/*  889 */       l = new Label(column++, i + 3, payTimeString, detFormat);
/*  890 */       sheet.addCell(l);
/*      */       String state;
/*  893 */       if ("0".equals(((Portalorder)cards.get(i)).getState())) {
/*  894 */         state = "未支付";
/*      */       }
/*      */       else
/*      */       {
/*  895 */         if ("1".equals(((Portalorder)cards.get(i)).getState()))
/*  896 */           state = "已支付";
/*      */         else
/*  898 */           state = "错误";
/*      */       }
/*  900 */       l = new Label(column++, i + 3, state, detFormat);
/*  901 */       sheet.addCell(l);
/*      */ 
/*  903 */       if (((Portalorder)cards.get(i)).getBuyDate() != null) {
/*  904 */         d = new DateTime(column++, i + 3, ((Portalorder)cards.get(i)).getBuyDate(), 
/*  905 */           dateFormat);
/*  906 */         sheet.addCell(d);
/*      */       } else {
/*  908 */         l = new Label(column++, i + 3, "", detFormat);
/*  909 */         sheet.addCell(l);
/*      */       }
/*      */ 
/*  912 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getAccountName(), 
/*  913 */         detFormat);
/*  914 */       sheet.addCell(l);
/*      */ 
/*  916 */       if (((Portalorder)cards.get(i)).getPayDate() != null) {
/*  917 */         d = new DateTime(column++, i + 3, ((Portalorder)cards.get(i)).getPayDate(), 
/*  918 */           dateFormat);
/*  919 */         sheet.addCell(d);
/*      */       } else {
/*  921 */         l = new Label(column++, i + 3, "", detFormat);
/*  922 */         sheet.addCell(l);
/*      */       }
/*      */       String payby;
/*  926 */       if (((Portalorder)cards.get(i)).getPayby().intValue() == 0) {
/*  927 */         payby = "系统";
/*      */       }
/*      */       else
/*      */       {
/*  928 */         if (1 == ((Portalorder)cards.get(i)).getPayby().intValue()) {
/*  929 */           payby = "支付宝";
/*      */         }
/*      */         else
/*      */         {
/*  930 */           if (2 == ((Portalorder)cards.get(i)).getPayby().intValue())
/*  931 */             payby = "微信";
/*      */           else
/*  933 */             payby = "错误"; 
/*      */         }
/*      */       }
/*  935 */       l = new Label(column++, i + 3, payby, detFormat);
/*  936 */       sheet.addCell(l);
/*  937 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getTradeno(), detFormat);
/*  938 */       sheet.addCell(l);
/*  939 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getBuyer(), detFormat);
/*  940 */       sheet.addCell(l);
/*  941 */       l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getSeller(), detFormat);
/*  942 */       sheet.addCell(l);
/*      */ 
/*  944 */       l = new Label(column++, i + 3, String.valueOf(((Portalorder)cards.get(i))
/*  945 */         .getMoney()), priceFormat);
/*  946 */       sheet.addCell(l);
/*      */     }
/*      */ 
/*  951 */     column = 0;
/*  952 */     sheet.setColumnView(column++, 10);
/*  953 */     sheet.setColumnView(column++, 20);
/*  954 */     sheet.setColumnView(column++, 30);
/*  955 */     sheet.setColumnView(column++, 40);
/*  956 */     sheet.setColumnView(column++, 10);
/*  957 */     sheet.setColumnView(column++, 10);
/*  958 */     sheet.setColumnView(column++, 10);
/*  959 */     sheet.setColumnView(column++, 10);
/*  960 */     sheet.setColumnView(column++, 20);
/*  961 */     sheet.setColumnView(column++, 10);
/*  962 */     sheet.setColumnView(column++, 20);
/*  963 */     sheet.setColumnView(column++, 10);
/*      */ 
/*  965 */     workbook.write();
/*  966 */     workbook.close();
/*      */   }
/*      */ 
/*      */   public static void writeCardsToExcel(String fileName, List<Portalcard> cards, String money)
/*      */     throws Exception
/*      */   {
/*  979 */     Number n = null;
/*  980 */     DateTime d = null;
/*      */ 
/*  982 */     File tempFile = new File(fileName);
/*  983 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/*  984 */     WritableSheet sheet = workbook.createSheet("充值卡报表", 0);
/*      */ 
/*  988 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/*  989 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  990 */       Colour.GREEN);
/*  991 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/*  994 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  995 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/*  996 */       Colour.RED);
/*  997 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/* 1000 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/* 1001 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1002 */       Colour.BLACK);
/* 1003 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/* 1006 */     NumberFormat nf = new NumberFormat("0.00");
/* 1007 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/* 1010 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/* 1011 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/* 1014 */     Label l = new Label(0, 0, "充值卡报表", headerFormat);
/* 1015 */     sheet.addCell(l);
/*      */ 
/* 1017 */     l = new Label(0, 1, "出售充值卡，收入共计： " + money + " 元", titleFormat);
/* 1018 */     sheet.addCell(l);
/*      */ 
/* 1034 */     int column = 0;
/* 1035 */     l = new Label(column++, 2, "序号", titleFormat);
/* 1036 */     sheet.addCell(l);
/* 1037 */     l = new Label(column++, 2, "名称", titleFormat);
/* 1038 */     sheet.addCell(l);
/* 1039 */     l = new Label(column++, 2, "详细信息", titleFormat);
/* 1040 */     sheet.addCell(l);
/* 1041 */     l = new Label(column++, 2, "CD-KEY", titleFormat);
/* 1042 */     sheet.addCell(l);
/* 1043 */     l = new Label(column++, 2, "充值类型", titleFormat);
/* 1044 */     sheet.addCell(l);
/* 1045 */     l = new Label(column++, 2, "分类", titleFormat);
/* 1046 */     sheet.addCell(l);
/* 1047 */     l = new Label(column++, 2, "计数", titleFormat);
/* 1048 */     sheet.addCell(l);
/* 1049 */     l = new Label(column++, 2, "MAC限制", titleFormat);
/* 1050 */     sheet.addCell(l);
/* 1051 */     l = new Label(column++, 2, "自动绑定MAC个数（MAC共享数）", titleFormat);
/* 1052 */     sheet.addCell(l);
/* 1053 */     l = new Label(column++, 2, "无感知", titleFormat);
/* 1054 */     sheet.addCell(l);
/* 1055 */     l = new Label(column++, 2, "限速", titleFormat);
/* 1056 */     sheet.addCell(l);
/* 1057 */     l = new Label(column++, 2, "状态", titleFormat);
/* 1058 */     sheet.addCell(l);
/* 1059 */     l = new Label(column++, 2, "售出日期", titleFormat);
/* 1060 */     sheet.addCell(l);
/* 1061 */     l = new Label(column++, 2, "充值用户", titleFormat);
/* 1062 */     sheet.addCell(l);
/* 1063 */     l = new Label(column++, 2, "充值日期", titleFormat);
/* 1064 */     sheet.addCell(l);
/* 1065 */     l = new Label(column++, 2, "售价（元）", titleFormat);
/* 1066 */     sheet.addCell(l);
/*      */ 
/* 1068 */     int y = cards.size();
/* 1069 */     for (int i = 0; i < y; i++) {
/* 1070 */       column = 0;
/* 1071 */       l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
/* 1072 */       sheet.addCell(l);
/* 1073 */       l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getName(), detFormat);
/* 1074 */       sheet.addCell(l);
/* 1075 */       l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getDescription(), 
/* 1076 */         detFormat);
/* 1077 */       sheet.addCell(l);
/* 1078 */       l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getCdKey(), detFormat);
/* 1079 */       sheet.addCell(l);
/*      */       String payType;
/* 1082 */       if ("2".equals(((Portalcard)cards.get(i)).getPayType())) {
/* 1083 */         payType = "计时";
/*      */       }
/*      */       else
/*      */       {
/* 1084 */         if ("3".equals(((Portalcard)cards.get(i)).getPayType())) {
/* 1085 */           payType = "买断";
/*      */         }
/*      */         else
/*      */         {
/* 1086 */           if ("4".equals(((Portalcard)cards.get(i)).getPayType()))
/* 1087 */             payType = "流量";
/*      */           else
/* 1089 */             payType = "错误"; 
/*      */         }
/*      */       }
/* 1091 */       l = new Label(column++, i + 3, payType, detFormat);
/* 1092 */       sheet.addCell(l);
/*      */       String categoryType;
/* 1095 */       if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1096 */         categoryType = "包时卡";
/*      */       }
/*      */       else
/*      */       {
/* 1097 */         if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1098 */           categoryType = "日卡";
/*      */         }
/*      */         else
/*      */         {
/* 1099 */           if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1100 */             categoryType = "月卡";
/*      */           }
/*      */           else
/*      */           {
/* 1101 */             if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1102 */               categoryType = "年卡";
/*      */             }
/*      */             else
/*      */             {
/* 1103 */               if ("4".equals(((Portalcard)cards.get(i)).getCategoryType()))
/* 1104 */                 categoryType = "流量卡";
/*      */               else
/* 1106 */                 categoryType = "错误"; 
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1108 */       l = new Label(column++, i + 3, categoryType, detFormat);
/* 1109 */       sheet.addCell(l);
/*      */ 
/* 1111 */       Long payTime = Long.valueOf(0L);
/* 1112 */       String payTimeString = "";
/* 1113 */       if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1114 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
/* 1115 */         payTimeString = String.valueOf(payTime) + "小时";
/* 1116 */       } else if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1117 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
/* 1118 */         payTimeString = String.valueOf(payTime) + "天";
/* 1119 */       } else if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1120 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
/* 1121 */         payTimeString = String.valueOf(payTime) + "月";
/* 1122 */       } else if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1123 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L / 
/* 1124 */           12L);
/* 1125 */         payTimeString = String.valueOf(payTime) + "年";
/* 1126 */       } else if ("4".equals(((Portalcard)cards.get(i)).getCategoryType())) {
/* 1127 */         payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
/* 1128 */         payTimeString = String.valueOf(payTime) + "M";
/*      */       } else {
/* 1130 */         payTimeString = "错误";
/*      */       }
/* 1132 */       l = new Label(column++, i + 3, payTimeString, detFormat);
/* 1133 */       sheet.addCell(l);
/*      */ 
/* 1135 */       Integer maclimitState = ((Portalcard)cards.get(i)).getMaclimit();
/* 1136 */       Integer macCount = ((Portalcard)cards.get(i)).getMaclimitcount();
/* 1137 */       if (maclimitState != null) {
/* 1138 */         if (maclimitState.intValue() == 1)
/* 1139 */           l = new Label(column++, i + 3, "启用", detFormat);
/* 1140 */         else if (maclimitState.intValue() == 0)
/* 1141 */           l = new Label(column++, i + 3, "关闭", detFormat);
/*      */         else
/* 1143 */           l = new Label(column++, i + 3, "错误", detFormat);
/*      */       }
/*      */       else {
/* 1146 */         l = new Label(column++, i + 3, "未知", detFormat);
/*      */       }
/* 1148 */       sheet.addCell(l);
/*      */ 
/* 1150 */       if (macCount != null) {
/* 1151 */         if (macCount.intValue() >= 0)
/* 1152 */           n = new Number(column++, i + 3, macCount.intValue(), 
/* 1153 */             priceFormat);
/*      */         else
/* 1155 */           n = new Number(column++, i + 3, 1.0D, priceFormat);
/*      */       }
/*      */       else {
/* 1158 */         n = new Number(column++, i + 3, 1.0D, priceFormat);
/*      */       }
/* 1160 */       sheet.addCell(n);
/*      */ 
/* 1162 */       Integer macAuto = ((Portalcard)cards.get(i)).getAutologin();
/* 1163 */       if (macAuto != null) {
/* 1164 */         if (macAuto.intValue() == 1)
/* 1165 */           l = new Label(column++, i + 3, "启用", detFormat);
/* 1166 */         else if (macAuto.intValue() == 0)
/* 1167 */           l = new Label(column++, i + 3, "关闭", detFormat);
/*      */         else
/* 1169 */           l = new Label(column++, i + 3, "错误", detFormat);
/*      */       }
/*      */       else {
/* 1172 */         l = new Label(column++, i + 3, "未知", detFormat);
/*      */       }
/* 1174 */       sheet.addCell(l);
/*      */ 
/* 1176 */       String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       try {
/* 1178 */         speed = speedService.getPortalspeedByKey(((Portalcard)cards.get(i)).getSpeed()).getName();
/*      */       } catch (Exception e) {
/* 1180 */         speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
/*      */       }
/* 1182 */       l = new Label(column++, i + 3, speed, detFormat);
/* 1183 */       sheet.addCell(l);
/*      */       String state;
/* 1186 */       if ("0".equals(((Portalcard)cards.get(i)).getState())) {
/* 1187 */         state = "新卡";
/*      */       }
/*      */       else
/*      */       {
/* 1188 */         if ("1".equals(((Portalcard)cards.get(i)).getState())) {
/* 1189 */           state = "已售出";
/*      */         }
/*      */         else
/*      */         {
/* 1190 */           if ("2".equals(((Portalcard)cards.get(i)).getState()))
/* 1191 */             state = "已激活";
/*      */           else
/* 1193 */             state = "错误"; 
/*      */         }
/*      */       }
/* 1195 */       l = new Label(column++, i + 3, state, detFormat);
/* 1196 */       sheet.addCell(l);
/*      */ 
/* 1198 */       if (((Portalcard)cards.get(i)).getBuyDate() != null) {
/* 1199 */         d = new DateTime(column++, i + 3, ((Portalcard)cards.get(i)).getBuyDate(), 
/* 1200 */           dateFormat);
/* 1201 */         sheet.addCell(d);
/*      */       } else {
/* 1203 */         l = new Label(column++, i + 3, "", detFormat);
/* 1204 */         sheet.addCell(l);
/*      */       }
/*      */ 
/* 1207 */       l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getAccountName(), 
/* 1208 */         detFormat);
/* 1209 */       sheet.addCell(l);
/*      */ 
/* 1211 */       if (((Portalcard)cards.get(i)).getPayDate() != null) {
/* 1212 */         d = new DateTime(column++, i + 3, ((Portalcard)cards.get(i)).getPayDate(), 
/* 1213 */           dateFormat);
/* 1214 */         sheet.addCell(d);
/*      */       } else {
/* 1216 */         l = new Label(column++, i + 3, "", detFormat);
/* 1217 */         sheet.addCell(l);
/*      */       }
/*      */ 
/* 1220 */       l = new Label(column++, i + 3, String.valueOf(((Portalcard)cards.get(i))
/* 1221 */         .getMoney()), priceFormat);
/* 1222 */       sheet.addCell(l);
/*      */     }
/*      */ 
/* 1227 */     column = 0;
/* 1228 */     sheet.setColumnView(column++, 10);
/* 1229 */     sheet.setColumnView(column++, 20);
/* 1230 */     sheet.setColumnView(column++, 30);
/* 1231 */     sheet.setColumnView(column++, 40);
/* 1232 */     sheet.setColumnView(column++, 10);
/* 1233 */     sheet.setColumnView(column++, 10);
/* 1234 */     sheet.setColumnView(column++, 10);
/* 1235 */     sheet.setColumnView(column++, 10);
/* 1236 */     sheet.setColumnView(column++, 20);
/* 1237 */     sheet.setColumnView(column++, 10);
/* 1238 */     sheet.setColumnView(column++, 20);
/* 1239 */     sheet.setColumnView(column++, 10);
/*      */ 
/* 1241 */     workbook.write();
/* 1242 */     workbook.close();
/*      */   }
/*      */ 
/*      */   public static void writeLinksToExcel(String fileName, List<Portallinkrecordall> linkAll, String onlineTime)
/*      */     throws Exception
/*      */   {
/* 1256 */     DateTime d = null;
/*      */ 
/* 1258 */     File tempFile = new File(fileName);
/* 1259 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/* 1260 */     WritableSheet sheet = workbook.createSheet("连接记录报表", 0);
/*      */ 
/* 1264 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/* 1265 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1266 */       Colour.GREEN);
/* 1267 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/* 1270 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/* 1271 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1272 */       Colour.RED);
/* 1273 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/* 1276 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/* 1277 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1278 */       Colour.BLACK);
/* 1279 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/* 1282 */     NumberFormat nf = new NumberFormat("0.00");
/* 1283 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/* 1286 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/* 1287 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/* 1290 */     Label l = new Label(0, 0, "连接记录报表", headerFormat);
/* 1291 */     sheet.addCell(l);
/*      */ 
/* 1293 */     l = new Label(0, 1, "连接记录报表内容，在线时长共计： " + onlineTime + " 分钟", 
/* 1294 */       titleFormat);
/* 1295 */     sheet.addCell(l);
/*      */ 
/* 1298 */     int column = 0;
/* 1299 */     l = new Label(column++, 2, "序号", titleFormat);
/* 1300 */     sheet.addCell(l);
/* 1301 */     l = new Label(column++, 2, "登录名", titleFormat);
/* 1302 */     sheet.addCell(l);
/* 1303 */     l = new Label(column++, 2, "MAC地址", titleFormat);
/* 1304 */     sheet.addCell(l);
/* 1305 */     l = new Label(column++, 2, "IP地址", titleFormat);
/* 1306 */     sheet.addCell(l);
/* 1307 */     l = new Label(column++, 2, "BAS地址", titleFormat);
/* 1308 */     sheet.addCell(l);
/* 1309 */     l = new Label(column++, 2, "上线时间", titleFormat);
/* 1310 */     sheet.addCell(l);
/* 1311 */     l = new Label(column++, 2, "下线时间", titleFormat);
/* 1312 */     sheet.addCell(l);
/* 1313 */     l = new Label(column++, 2, "在线时长(分钟)", titleFormat);
/* 1314 */     sheet.addCell(l);
/* 1315 */     l = new Label(column++, 2, "计费类型", titleFormat);
/* 1316 */     sheet.addCell(l);
/* 1317 */     l = new Label(column++, 2, "下行流量", titleFormat);
/* 1318 */     sheet.addCell(l);
/* 1319 */     l = new Label(column++, 2, "上行流量", titleFormat);
/* 1320 */     sheet.addCell(l);
/* 1321 */     l = new Label(column++, 2, "使用流量", titleFormat);
/* 1322 */     sheet.addCell(l);
/* 1323 */     l = new Label(column++, 2, "BASNAME", titleFormat);
/* 1324 */     sheet.addCell(l);
/* 1325 */     l = new Label(column++, 2, "SSID", titleFormat);
/* 1326 */     sheet.addCell(l);
/* 1327 */     l = new Label(column++, 2, "APMac", titleFormat);
/* 1328 */     sheet.addCell(l);
/* 1329 */     l = new Label(column++, 2, "认证方式", titleFormat);
/* 1330 */     sheet.addCell(l);
/* 1331 */     l = new Label(column++, 2, "无感知", titleFormat);
/* 1332 */     sheet.addCell(l);
/* 1333 */     l = new Label(column++, 2, "客户端", titleFormat);
/* 1334 */     sheet.addCell(l);
/* 1335 */     l = new Label(column++, 2, "原因", titleFormat);
/* 1336 */     sheet.addCell(l);
/*      */ 
/* 1338 */     int y = linkAll.size();
/* 1339 */     for (int i = 0; i < y; i++) {
/* 1340 */       column = 0;
/* 1341 */       l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
/* 1342 */       sheet.addCell(l);
/* 1343 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getLoginName(), 
/* 1344 */         detFormat);
/* 1345 */       sheet.addCell(l);
/* 1346 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getMac(), detFormat);
/* 1347 */       sheet.addCell(l);
/* 1348 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getIp(), detFormat);
/* 1349 */       sheet.addCell(l);
/* 1350 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getBasip(), detFormat);
/* 1351 */       sheet.addCell(l);
/* 1352 */       d = new DateTime(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getStartDate(), 
/* 1353 */         dateFormat);
/* 1354 */       sheet.addCell(d);
/* 1355 */       d = new DateTime(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getEndDate(), 
/* 1356 */         dateFormat);
/* 1357 */       sheet.addCell(d);
/*      */ 
/* 1360 */       double time = ((Portallinkrecordall)linkAll.get(i)).getTime().longValue() / 60000L;
/* 1361 */       l = new Label(column++, i + 3, String.valueOf(time), priceFormat);
/* 1362 */       sheet.addCell(l);
/*      */       String state;
/* 1365 */       if ("0".equals(((Portallinkrecordall)linkAll.get(i)).getState())) {
/* 1366 */         state = "停用";
/*      */       }
/*      */       else
/*      */       {
/* 1367 */         if ("1".equals(((Portallinkrecordall)linkAll.get(i)).getState())) {
/* 1368 */           state = "免费";
/*      */         }
/*      */         else
/*      */         {
/* 1369 */           if ("2".equals(((Portallinkrecordall)linkAll.get(i)).getState())) {
/* 1370 */             state = "计时";
/*      */           }
/*      */           else
/*      */           {
/* 1371 */             if ("3".equals(((Portallinkrecordall)linkAll.get(i)).getState())) {
/* 1372 */               state = "买断";
/*      */             }
/*      */             else
/*      */             {
/* 1373 */               if ("4".equals(((Portallinkrecordall)linkAll.get(i)).getState()))
/* 1374 */                 state = "流量";
/*      */               else
/* 1376 */                 state = "外部用户"; 
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1378 */       l = new Label(column++, i + 3, state, detFormat);
/* 1379 */       sheet.addCell(l);
/*      */ 
/* 1381 */       String inS = "0 M";
/* 1382 */       String outS = "0 M";
/* 1383 */       String octetsS = "0 M";
/*      */       try {
/* 1385 */         double in = Double.valueOf(((Portallinkrecordall)linkAll.get(i)).getIns().longValue()).doubleValue();
/* 1386 */         double out = Double.valueOf(((Portallinkrecordall)linkAll.get(i)).getOuts().longValue()).doubleValue();
/* 1387 */         double octets = in + out;
/* 1388 */         in /= 1048576.0D;
/* 1389 */         out /= 1048576.0D;
/* 1390 */         octets /= 1048576.0D;
/* 1391 */         inS = textf.format(in);
/* 1392 */         outS = textf.format(out);
/* 1393 */         octetsS = textf.format(octets);
/* 1394 */         if (inS.startsWith(".")) {
/* 1395 */           inS = "0" + inS;
/*      */         }
/* 1397 */         if (outS.startsWith(".")) {
/* 1398 */           outS = "0" + outS;
/*      */         }
/* 1400 */         if (octetsS.startsWith(".")) {
/* 1401 */           octetsS = "0" + octetsS;
/*      */         }
/* 1403 */         inS = inS + " M";
/* 1404 */         outS = outS + " M";
/* 1405 */         octetsS = octetsS + " M";
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/* 1409 */       l = new Label(column++, i + 3, outS, detFormat);
/* 1410 */       sheet.addCell(l);
/* 1411 */       l = new Label(column++, i + 3, inS, detFormat);
/* 1412 */       sheet.addCell(l);
/* 1413 */       l = new Label(column++, i + 3, octetsS, detFormat);
/* 1414 */       sheet.addCell(l);
/*      */ 
/* 1416 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getBasname(), 
/* 1417 */         detFormat);
/* 1418 */       sheet.addCell(l);
/* 1419 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getSsid(), detFormat);
/* 1420 */       sheet.addCell(l);
/* 1421 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getApmac(), detFormat);
/* 1422 */       sheet.addCell(l);
/* 1423 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getMethodtype(), 
/* 1424 */         detFormat);
/* 1425 */       sheet.addCell(l);
/* 1426 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getAuto(), detFormat);
/* 1427 */       sheet.addCell(l);
/* 1428 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getAgent(), detFormat);
/* 1429 */       sheet.addCell(l);
/* 1430 */       l = new Label(column++, i + 3, ((Portallinkrecordall)linkAll.get(i)).getEx1(), detFormat);
/* 1431 */       sheet.addCell(l);
/*      */     }
/*      */ 
/* 1435 */     column = 0;
/* 1436 */     sheet.setColumnView(column++, 5);
/* 1437 */     sheet.setColumnView(column++, 20);
/* 1438 */     sheet.setColumnView(column++, 20);
/* 1439 */     sheet.setColumnView(column++, 20);
/* 1440 */     sheet.setColumnView(column++, 20);
/* 1441 */     sheet.setColumnView(column++, 20);
/* 1442 */     sheet.setColumnView(column++, 20);
/* 1443 */     sheet.setColumnView(column++, 20);
/* 1444 */     sheet.setColumnView(column++, 20);
/* 1445 */     sheet.setColumnView(column++, 20);
/* 1446 */     sheet.setColumnView(column++, 20);
/* 1447 */     sheet.setColumnView(column++, 20);
/* 1448 */     sheet.setColumnView(column++, 20);
/* 1449 */     sheet.setColumnView(column++, 20);
/* 1450 */     sheet.setColumnView(column++, 20);
/* 1451 */     sheet.setColumnView(column++, 20);
/* 1452 */     sheet.setColumnView(column++, 20);
/*      */ 
/* 1454 */     workbook.write();
/* 1455 */     workbook.close();
/*      */   }
/*      */ 
/*      */   public static void writeRadiusLinksToExcel(String fileName, List<Radiuslinkrecordall> linkAll, String onlineTime, String allOctets)
/*      */     throws Exception
/*      */   {
/* 1469 */     DateTime d = null;
/*      */ 
/* 1471 */     File tempFile = new File(fileName);
/* 1472 */     WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
/* 1473 */     WritableSheet sheet = workbook.createSheet("Radius连接记录报表", 0);
/*      */ 
/* 1477 */     WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
/* 1478 */       WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1479 */       Colour.GREEN);
/* 1480 */     WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
/*      */ 
/* 1483 */     WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
/* 1484 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1485 */       Colour.RED);
/* 1486 */     WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
/*      */ 
/* 1489 */     WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
/* 1490 */       WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
/* 1491 */       Colour.BLACK);
/* 1492 */     WritableCellFormat detFormat = new WritableCellFormat(detFont);
/*      */ 
/* 1495 */     NumberFormat nf = new NumberFormat("0.00");
/* 1496 */     WritableCellFormat priceFormat = new WritableCellFormat(nf);
/*      */ 
/* 1499 */     DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
/* 1500 */     WritableCellFormat dateFormat = new WritableCellFormat(df);
/*      */ 
/* 1503 */     Label l = new Label(0, 0, "Radius连接记录报表", headerFormat);
/* 1504 */     sheet.addCell(l);
/*      */ 
/* 1506 */     l = new Label(0, 1, "Radius连接记录报表内容，在线时长共计： " + onlineTime + 
/* 1507 */       " 分钟，流量共计： " + allOctets + " M", titleFormat);
/* 1508 */     sheet.addCell(l);
/*      */ 
/* 1511 */     int column = 0;
/* 1512 */     l = new Label(column++, 2, "序号", titleFormat);
/* 1513 */     sheet.addCell(l);
/* 1514 */     l = new Label(column++, 2, "标识ID", titleFormat);
/* 1515 */     sheet.addCell(l);
/* 1516 */     l = new Label(column++, 2, "nas名称", titleFormat);
/* 1517 */     sheet.addCell(l);
/* 1518 */     l = new Label(column++, 2, "nasIP", titleFormat);
/* 1519 */     sheet.addCell(l);
/* 1520 */     l = new Label(column++, 2, "sourceIP", titleFormat);
/* 1521 */     sheet.addCell(l);
/* 1522 */     l = new Label(column++, 2, "用户IP", titleFormat);
/* 1523 */     sheet.addCell(l);
/* 1524 */     l = new Label(column++, 2, "MAC地址", titleFormat);
/* 1525 */     sheet.addCell(l);
/* 1526 */     l = new Label(column++, 2, "登录名", titleFormat);
/* 1527 */     sheet.addCell(l);
/* 1528 */     l = new Label(column++, 2, "下行流量", titleFormat);
/* 1529 */     sheet.addCell(l);
/* 1530 */     l = new Label(column++, 2, "上行流量", titleFormat);
/* 1531 */     sheet.addCell(l);
/* 1532 */     l = new Label(column++, 2, "使用流量", titleFormat);
/* 1533 */     sheet.addCell(l);
/* 1534 */     l = new Label(column++, 2, "上线时间", titleFormat);
/* 1535 */     sheet.addCell(l);
/* 1536 */     l = new Label(column++, 2, "下线时间", titleFormat);
/* 1537 */     sheet.addCell(l);
/* 1538 */     l = new Label(column++, 2, "在线时长(分钟)", titleFormat);
/* 1539 */     sheet.addCell(l);
/* 1540 */     l = new Label(column++, 2, "计费类型", titleFormat);
/* 1541 */     sheet.addCell(l);
/* 1542 */     l = new Label(column++, 2, "NAS类型", titleFormat);
/* 1543 */     sheet.addCell(l);
/* 1544 */     l = new Label(column++, 2, "原因", titleFormat);
/* 1545 */     sheet.addCell(l);
/*      */ 
/* 1547 */     int y = linkAll.size();
/* 1548 */     for (int i = 0; i < y; i++) {
/* 1549 */       column = 0;
/* 1550 */       l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
/* 1551 */       sheet.addCell(l);
/* 1552 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getAcctsessionid(), 
/* 1553 */         detFormat);
/* 1554 */       sheet.addCell(l);
/* 1555 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getEx3(), detFormat);
/* 1556 */       sheet.addCell(l);
/* 1557 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getNasip(), detFormat);
/* 1558 */       sheet.addCell(l);
/* 1559 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getSourceip(), 
/* 1560 */         detFormat);
/* 1561 */       sheet.addCell(l);
/* 1562 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getUserip(), 
/* 1563 */         detFormat);
/* 1564 */       sheet.addCell(l);
/* 1565 */       l = new Label(column++, i + 3, 
/* 1566 */         ((Radiuslinkrecordall)linkAll.get(i)).getCallingstationid(), detFormat);
/* 1567 */       sheet.addCell(l);
/* 1568 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getName(), detFormat);
/* 1569 */       sheet.addCell(l);
/*      */ 
/* 1571 */       double out = ((Radiuslinkrecordall)linkAll.get(i)).getOuts().longValue() / 1048576L;
/* 1572 */       l = new Label(column++, i + 3, String.valueOf(out) + " M", priceFormat);
/* 1573 */       sheet.addCell(l);
/*      */ 
/* 1575 */       double in = ((Radiuslinkrecordall)linkAll.get(i)).getIns().longValue() / 1048576L;
/* 1576 */       l = new Label(column++, i + 3, String.valueOf(in) + " M", priceFormat);
/* 1577 */       sheet.addCell(l);
/*      */ 
/* 1579 */       double octets = (((Radiuslinkrecordall)linkAll.get(i)).getOuts().longValue() + ((Radiuslinkrecordall)linkAll.get(i)).getIns().longValue()) / 1048576L;
/* 1580 */       l = new Label(column++, i + 3, String.valueOf(octets) + " M", priceFormat);
/* 1581 */       sheet.addCell(l);
/* 1582 */       d = new DateTime(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getStartDate(), 
/* 1583 */         dateFormat);
/* 1584 */       sheet.addCell(d);
/* 1585 */       d = new DateTime(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getEndDate(), 
/* 1586 */         dateFormat);
/* 1587 */       sheet.addCell(d);
/*      */ 
/* 1590 */       double time = ((Radiuslinkrecordall)linkAll.get(i)).getTime().longValue() / 60000L;
/* 1591 */       l = new Label(column++, i + 3, String.valueOf(time), priceFormat);
/* 1592 */       sheet.addCell(l);
/*      */       String state;
/* 1595 */       if ("0".equals(((Radiuslinkrecordall)linkAll.get(i)).getState())) {
/* 1596 */         state = "停用";
/*      */       }
/*      */       else
/*      */       {
/* 1597 */         if ("1".equals(((Radiuslinkrecordall)linkAll.get(i)).getState())) {
/* 1598 */           state = "免费";
/*      */         }
/*      */         else
/*      */         {
/* 1599 */           if ("2".equals(((Radiuslinkrecordall)linkAll.get(i)).getState())) {
/* 1600 */             state = "计时";
/*      */           }
/*      */           else
/*      */           {
/* 1601 */             if ("3".equals(((Radiuslinkrecordall)linkAll.get(i)).getState())) {
/* 1602 */               state = "买断";
/*      */             }
/*      */             else
/*      */             {
/* 1603 */               if ("4".equals(((Radiuslinkrecordall)linkAll.get(i)).getState()))
/* 1604 */                 state = "流量";
/*      */               else
/* 1606 */                 state = "外部用户"; 
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1608 */       l = new Label(column++, i + 3, state, detFormat);
/* 1609 */       sheet.addCell(l);
/*      */       String clientType;
/* 1612 */       if ("0".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1613 */         clientType = "标准";
/*      */       }
/*      */       else
/*      */       {
/* 1614 */         if ("1".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1615 */           clientType = "华为";
/*      */         }
/*      */         else
/*      */         {
/* 1616 */           if ("2".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1617 */             clientType = "H3C";
/*      */           }
/*      */           else
/*      */           {
/* 1618 */             if ("3".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1619 */               clientType = "Cisco";
/*      */             }
/*      */             else
/*      */             {
/* 1620 */               if ("4".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1621 */                 clientType = "ROS";
/*      */               }
/*      */               else
/*      */               {
/* 1622 */                 if ("5".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1623 */                   clientType = "爱快";
/*      */                 }
/*      */                 else
/*      */                 {
/* 1624 */                   if ("6".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1())) {
/* 1625 */                     clientType = "中兴";
/*      */                   }
/*      */                   else
/*      */                   {
/* 1626 */                     if ("7".equals(((Radiuslinkrecordall)linkAll.get(i)).getEx1()))
/* 1627 */                       clientType = "卓迈";
/*      */                     else
/* 1629 */                       clientType = "错误"; 
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1631 */       l = new Label(column++, i + 3, clientType, detFormat);
/* 1632 */       sheet.addCell(l);
/* 1633 */       l = new Label(column++, i + 3, ((Radiuslinkrecordall)linkAll.get(i)).getEx2(), detFormat);
/* 1634 */       sheet.addCell(l);
/*      */     }
/*      */ 
/* 1638 */     column = 0;
/* 1639 */     sheet.setColumnView(column++, 5);
/* 1640 */     sheet.setColumnView(column++, 20);
/* 1641 */     sheet.setColumnView(column++, 20);
/* 1642 */     sheet.setColumnView(column++, 20);
/* 1643 */     sheet.setColumnView(column++, 20);
/* 1644 */     sheet.setColumnView(column++, 20);
/* 1645 */     sheet.setColumnView(column++, 20);
/* 1646 */     sheet.setColumnView(column++, 20);
/* 1647 */     sheet.setColumnView(column++, 20);
/* 1648 */     sheet.setColumnView(column++, 20);
/* 1649 */     sheet.setColumnView(column++, 20);
/* 1650 */     sheet.setColumnView(column++, 20);
/* 1651 */     sheet.setColumnView(column++, 10);
/* 1652 */     sheet.setColumnView(column++, 10);
/*      */ 
/* 1654 */     workbook.write();
/* 1655 */     workbook.close();
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.ExcelUtils
 * JD-Core Version:    0.6.2
 */