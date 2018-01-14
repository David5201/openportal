package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {
	private static PortalaccountService accountService = (PortalaccountService) SpringContextHelper
			.getBean("portalaccountServiceImpl");

	private static PortalspeedService speedService = (PortalspeedService) SpringContextHelper
			.getBean("portalspeedServiceImpl");

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	private static DecimalFormat textf = new DecimalFormat(".##");

	public static void writeAccountToExcel(String fileName, List<Portalaccount> accounts) throws Exception {
		Number n = null;
		DateTime d = null;

		File tempFile = new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("接入账户列表", 0);

		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat detFormat = new WritableCellFormat(detFont);

		NumberFormat nf = new NumberFormat("0");
		WritableCellFormat priceFormat = new WritableCellFormat(nf);

		DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
		WritableCellFormat dateFormat = new WritableCellFormat(df);

		Label l = new Label(0, 0, "接入账户列表", headerFormat);
		sheet.addCell(l);

		int column = 0;
		l = new Label(column++, 1, "序号", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "登录名", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "姓名", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "性别", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "电话号码", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "电子邮件", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "说明", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "类别", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "买断到期", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "剩余时长(分钟)", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "剩余流量(M)", titleFormat);
		sheet.addCell(l);

		l = new Label(column++, 1, "密码", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "MAC限制", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "自动绑定MAC个数（MAC共享数）", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "无感知", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 1, "限速", titleFormat);
		sheet.addCell(l);

		int y = accounts.size();
		for (int i = 0; i < y; i++) {
			column = 0;
			l = new Label(column++, i + 2, String.valueOf(i + 1), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getLoginName(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getName(), detFormat);
			sheet.addCell(l);
			String sex;
			if ("0".equals(((Portalaccount) accounts.get(i)).getGender())) {
				sex = "女";
			} else {
				if ("1".equals(((Portalaccount) accounts.get(i)).getGender()))
					sex = "男";
				else
					sex = "错误";
			}
			l = new Label(column++, i + 2, sex, detFormat);
			sheet.addCell(l);

			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getPhoneNumber(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getEmail(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getDescription(), detFormat);
			sheet.addCell(l);
			String state;
			if (((Portalaccount) accounts.get(i)).getState().equals(String.valueOf(1))) {
				state = "免费";
			} else {
				if (((Portalaccount) accounts.get(i)).getState().equals(String.valueOf(2))) {
					state = "计时";
				} else {
					if (((Portalaccount) accounts.get(i)).getState().equals(String.valueOf(3))) {
						state = "买断";
					} else {
						if (((Portalaccount) accounts.get(i)).getState().equals(String.valueOf(4)))
							state = "流量";
						else
							state = "停用";
					}
				}
			}
			l = new Label(column++, i + 2, state, detFormat);
			sheet.addCell(l);

			d = new DateTime(column++, i + 2, ((Portalaccount) accounts.get(i)).getDate(), dateFormat);
			sheet.addCell(d);

			n = new Number(column++, i + 2, ((Portalaccount) accounts.get(i)).getTime().longValue() / 1000L / 60L,
					priceFormat);
			sheet.addCell(n);

			Long octets = ((Portalaccount) accounts.get(i)).getOctets();
			if (octets == null)
				octets = Long.valueOf(0L);
			else {
				octets = Long.valueOf(octets.longValue() / 1024L / 1024L);
			}
			n = new Number(column++, i + 2, octets.longValue(), priceFormat);
			sheet.addCell(n);

			l = new Label(column++, i + 2, ((Portalaccount) accounts.get(i)).getPassword(), detFormat);
			sheet.addCell(l);

			Integer maclimitState = ((Portalaccount) accounts.get(i)).getMaclimit();
			Integer macCount = ((Portalaccount) accounts.get(i)).getMaclimitcount();
			if (maclimitState != null) {
				if (maclimitState.intValue() == 1)
					l = new Label(column++, i + 2, "启用", detFormat);
				else if (maclimitState.intValue() == 0)
					l = new Label(column++, i + 2, "关闭", detFormat);
				else
					l = new Label(column++, i + 2, "错误", detFormat);
			} else {
				l = new Label(column++, i + 2, "未知", detFormat);
			}
			sheet.addCell(l);

			if (macCount != null) {
				if (macCount.intValue() >= 0)
					n = new Number(column++, i + 2, macCount.intValue(), priceFormat);
				else
					n = new Number(column++, i + 2, 1.0D, priceFormat);
			} else {
				n = new Number(column++, i + 2, 1.0D, priceFormat);
			}
			sheet.addCell(n);

			Integer macAuto = ((Portalaccount) accounts.get(i)).getAutologin();
			if (macAuto != null) {
				if (macAuto.intValue() == 1)
					l = new Label(column++, i + 2, "启用", detFormat);
				else if (macAuto.intValue() == 0)
					l = new Label(column++, i + 2, "关闭", detFormat);
				else
					l = new Label(column++, i + 2, "错误", detFormat);
			} else {
				l = new Label(column++, i + 2, "未知", detFormat);
			}
			sheet.addCell(l);

			String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
			try {
				speed = speedService.getPortalspeedByKey(((Portalaccount) accounts.get(i)).getSpeed()).getName();
			} catch (Exception e) {
				speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
			}
			l = new Label(column++, i + 2, speed, detFormat);
			sheet.addCell(l);
		}

		column = 0;
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 15);

		workbook.write();
		workbook.close();
	}

	public static List<Portalaccount> readExcelAccount(String fileName) throws Exception {
		List unInAccounts = new ArrayList();
		Workbook book = Workbook.getWorkbook(new File(fileName));

		Sheet sheet = book.getSheet(0);

		int columNum = sheet.getColumns();
		int rowNum = sheet.getRows();

		for (int i = 2; i < rowNum; i++) {
			Portalaccount account = new Portalaccount();
			int macLimit = 0;
			int count = 1;
			int isAuto = 0;
			String password = "1234";
			long sid = 1L;
			for (int j = 1; j < columNum; j++) {
				Cell cell = sheet.getCell(j, i);
				String result = cell.getContents();

				if ((j == 1) && (stringUtils.isNotBlank(result))) {
					account.setLoginName(result);
				}

				if ((j == 2) && (stringUtils.isNotBlank(result))) {
					account.setName(result);
				}

				if (j == 3) {
					if ("男".equals(result))
						account.setGender("1");
					else if ("女".equals(result)) {
						account.setGender("0");
					}
				}
				if ((j == 4) && (stringUtils.isNotBlank(result))) {
					account.setPhoneNumber(result);
				}

				if ((j == 5) && (stringUtils.isNotBlank(result))) {
					account.setEmail(result);
				}

				if ((j == 6) && (stringUtils.isNotBlank(result))) {
					account.setDescription(result);
				}

				if (j == 7) {
					if ("停用".equals(result))
						account.setState("0");
					else if ("免费".equals(result))
						account.setState("1");
					else if ("计时".equals(result))
						account.setState("2");
					else if ("买断".equals(result))
						account.setState("3");
					else if ("流量".equals(result))
						account.setState("4");
					else {
						account.setState("0");
					}
				}
				if (j == 8) {
					try {
						Date accDate = format.parse(result);
						account.setDate(accDate);
					} catch (Exception e) {
						account.setDate(new Date());
					}
				}
				if (j == 9) {
					try {
						long accTime = Long.parseLong(result);
						account.setTime(Long.valueOf(accTime * 60L * 1000L));
					} catch (Exception e) {
						account.setTime(Long.valueOf(0L));
					}
				}
				if (j == 10) {
					try {
						long accTime = Long.parseLong(result);
						account.setOctets(Long.valueOf(accTime * 1024L * 1024L));
					} catch (Exception e) {
						account.setOctets(Long.valueOf(0L));
					}
				}
				if (j == 11) {
					if ((result == null) || (result.equals("")))
						password = "1234";
					else {
						password = result;
					}
					account.setPassword(password);
				}
				if ((j == 12) && ("启用".equals(result))) {
					macLimit = 1;
				}

				if (j == 13)
					try {
						int state = Integer.valueOf(result).intValue();
						if (state >= 0)
							count = state;
					} catch (Exception localException1) {
					}
				if ((j == 14) && ("启用".equals(result))) {
					isAuto = 1;
				}

				if (j == 15) {
					try {
						PortalspeedQuery sq = new PortalspeedQuery();
						sq.setName(result);
						sq.setNameLike(false);
						sid = ((Portalspeed) speedService.getPortalspeedList(sq).get(0)).getId().longValue();
					} catch (Exception e) {
						sid = 1L;
					}
				}
			}

			PortalaccountQuery aq = new PortalaccountQuery();
			aq.setLoginName(account.getLoginName());
			aq.setLoginNameLike(false);

			Integer haveAcc = accountService.getPortalaccountCount(new PortalaccountQuery());
			if (haveAcc != null) {
				if (haveAcc.intValue() >= Integer.valueOf(((String[]) com.leeson.portal.core.model.CoreConfigMap
						.getInstance().getCoreConfigMap().get("core"))[1]).intValue()) {
					unInAccounts.add(account);
				} else if (accountService.getPortalaccountList(aq).size() > 0)
					unInAccounts.add(account);
				else
					try {
						account.setMaclimit(Integer.valueOf(macLimit));
						account.setMaclimitcount(Integer.valueOf(count));
						account.setAutologin(Integer.valueOf(isAuto));
						account.setSpeed(Long.valueOf(sid));
						accountService.addPortalaccount(account);
					} catch (Exception e) {
						unInAccounts.add(account);
					}
			} else {
				try {
					account.setMaclimit(Integer.valueOf(macLimit));
					account.setMaclimitcount(Integer.valueOf(count));
					account.setAutologin(Integer.valueOf(isAuto));
					account.setSpeed(Long.valueOf(sid));
					accountService.addPortalaccount(account);
				} catch (Exception e) {
					unInAccounts.add(account);
				}
			}
		}
		book.close();
		return unInAccounts;
	}

	public static void writeCardsToExcel(String fileName, List<Portalcard> cards)
    throws Exception
  {
    Number n = null;
    DateTime d = null;

    File tempFile = new File(fileName);
    WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
    WritableSheet sheet = workbook.createSheet("充值卡列表", 0);

    WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
      WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.GREEN);
    WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

    WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.RED);
    WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

    WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    WritableCellFormat detFormat = new WritableCellFormat(detFont);

    NumberFormat nf = new NumberFormat("0.00");
    WritableCellFormat priceFormat = new WritableCellFormat(nf);

    DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
    WritableCellFormat dateFormat = new WritableCellFormat(df);

    Label l = new Label(0, 0, "充值卡列表", headerFormat);
    sheet.addCell(l);

    int column = 0;
    l = new Label(column++, 1, "序号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "名称", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "详细信息", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "CD-KEY", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "充值类型", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "分类", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "计数", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "MAC限制", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "自动绑定MAC个数（MAC共享数）", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "无感知", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "限速", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "售价（元）", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "状态", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "售出日期", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "充值用户", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 1, "充值日期", titleFormat);
    sheet.addCell(l);

    int y = cards.size();
    for (int i = 0; i < y; i++) {
      column = 0;
      l = new Label(column++, i + 2, String.valueOf(i + 1), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getName(), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getDescription(), 
        detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getCdKey(), detFormat);
      sheet.addCell(l);
      String payType;
      if ("2".equals(((Portalcard)cards.get(i)).getPayType())) {
        payType = "计时";
      }
      else
      {
        if ("3".equals(((Portalcard)cards.get(i)).getPayType())) {
          payType = "买断";
        }
        else
        {
          if ("4".equals(((Portalcard)cards.get(i)).getPayType()))
            payType = "流量";
          else
            payType = "错误"; 
        }
      }
      l = new Label(column++, i + 2, payType, detFormat);
      sheet.addCell(l);
      String categoryType;
      if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
        categoryType = "包时卡";
      }
      else
      {
        if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
          categoryType = "日卡";
        }
        else
        {
          if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
            categoryType = "月卡";
          }
          else
          {
            if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
              categoryType = "年卡";
            }
            else
            {
              if ("4".equals(((Portalcard)cards.get(i)).getCategoryType()))
                categoryType = "流量卡";
              else
                categoryType = "错误"; 
            }
          }
        }
      }
      l = new Label(column++, i + 2, categoryType, detFormat);
      sheet.addCell(l);

      Long payTime = Long.valueOf(0L);
      String payTimeString = "";
      if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
        payTimeString = String.valueOf(payTime) + "小时";
      } else if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
        payTimeString = String.valueOf(payTime) + "天";
      } else if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
        payTimeString = String.valueOf(payTime) + "月";
      } else if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L /  
          12L);
        payTimeString = String.valueOf(payTime) + "年";
      } else if ("4".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
        payTimeString = String.valueOf(payTime) + "M";
      } else {
        payTimeString = "错误";
      }
      l = new Label(column++, i + 2, payTimeString, detFormat);
      sheet.addCell(l);

      Integer maclimitState = ((Portalcard)cards.get(i)).getMaclimit();
      Integer macCount = ((Portalcard)cards.get(i)).getMaclimitcount();
      if (maclimitState != null) {
        if (maclimitState.intValue() == 1)
          l = new Label(column++, i + 2, "启用", detFormat);
        else if (maclimitState.intValue() == 0)
          l = new Label(column++, i + 2, "关闭", detFormat);
        else
          l = new Label(column++, i + 2, "错误", detFormat);
      }
      else {
        l = new Label(column++, i + 2, "未知", detFormat);
      }
      sheet.addCell(l);

      if (macCount != null) {
        if (macCount.intValue() >= 0)
          n = new Number(column++, i + 2, macCount.intValue(), 
            priceFormat);
        else
          n = new Number(column++, i + 2, 1.0D, priceFormat);
      }
      else {
        n = new Number(column++, i + 2, 1.0D, priceFormat);
      }
      sheet.addCell(n);

      Integer macAuto = ((Portalcard)cards.get(i)).getAutologin();
      if (macAuto != null) {
        if (macAuto.intValue() == 1)
          l = new Label(column++, i + 2, "启用", detFormat);
        else if (macAuto.intValue() == 0)
          l = new Label(column++, i + 2, "关闭", detFormat);
        else
          l = new Label(column++, i + 2, "错误", detFormat);
      }
      else {
        l = new Label(column++, i + 2, "未知", detFormat);
      }
      sheet.addCell(l);

      String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
      try {
        speed = speedService.getPortalspeedByKey(((Portalcard)cards.get(i)).getSpeed()).getName();
      } catch (Exception e) {
        speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
      }
      l = new Label(column++, i + 2, speed, detFormat);
      sheet.addCell(l);

      l = new Label(column++, i + 2, String.valueOf(((Portalcard)cards.get(i))
        .getMoney()), priceFormat);
      sheet.addCell(l);
      String state;
      if ("0".equals(((Portalcard)cards.get(i)).getState())) {
        state = "新卡";
      }
      else
      {
        if ("1".equals(((Portalcard)cards.get(i)).getState())) {
          state = "已售出";
        }
        else
        {
          if ("2".equals(((Portalcard)cards.get(i)).getState())) {
            state = "已激活";
          }
          else
          {
            if ("-1".equals(((Portalcard)cards.get(i)).getState()))
              state = "未支付";
            else
              state = "错误"; 
          }
        }
      }
      l = new Label(column++, i + 2, state, detFormat);
      sheet.addCell(l);

      if (((Portalcard)cards.get(i)).getBuyDate() != null) {
        d = new DateTime(column++, i + 2, ((Portalcard)cards.get(i)).getBuyDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 2, "", detFormat);
        sheet.addCell(l);
      }

      l = new Label(column++, i + 2, ((Portalcard)cards.get(i)).getAccountName(), 
        detFormat);
      sheet.addCell(l);

      if (((Portalcard)cards.get(i)).getPayDate() != null) {
        d = new DateTime(column++, i + 2, ((Portalcard)cards.get(i)).getPayDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 2, "", detFormat);
        sheet.addCell(l);
      }

    }

    column = 0;
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 30);
    sheet.setColumnView(column++, 40);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);

    workbook.write();
    workbook.close();
  }

	public static void writeOrdersToExcel(String fileName, List<Portalorder> cards, String money)
    throws Exception
  {
    DateTime d = null;

    File tempFile = new File(fileName);
    WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
    WritableSheet sheet = workbook.createSheet("订单报表", 0);

    WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
      WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.GREEN);
    WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

    WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.RED);
    WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

    WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    WritableCellFormat detFormat = new WritableCellFormat(detFont);

    NumberFormat nf = new NumberFormat("0.00");
    WritableCellFormat priceFormat = new WritableCellFormat(nf);

    DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
    WritableCellFormat dateFormat = new WritableCellFormat(df);

    Label l = new Label(0, 0, "订单报表", headerFormat);
    sheet.addCell(l);

    l = new Label(0, 1, "订单报表内容，收入共计： " + money + " 元", titleFormat);
    sheet.addCell(l);

    int column = 0;
    l = new Label(column++, 2, "序号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "名称", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "详细信息", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "CD-KEY", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "充值类型", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "分类", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "计数", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "状态", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "创建日期", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "充值用户", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "支付日期", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "支付渠道", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "交易流水号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "买家帐号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "卖家帐号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "售价（元）", titleFormat);
    sheet.addCell(l);

    int y = cards.size();
    for (int i = 0; i < y; i++) {
      column = 0;
      l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getName(), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getDescription(), 
        detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getCdKey(), detFormat);
      sheet.addCell(l);
      String payType;
      if ("2".equals(((Portalorder)cards.get(i)).getPayType())) {
        payType = "计时";
      }
      else
      {
        if ("3".equals(((Portalorder)cards.get(i)).getPayType())) {
          payType = "买断";
        }
        else
        {
          if ("4".equals(((Portalorder)cards.get(i)).getPayType()))
            payType = "流量";
          else
            payType = "错误"; 
        }
      }
      l = new Label(column++, i + 3, payType, detFormat);
      sheet.addCell(l);
      String categoryType;
      if ("0".equals(((Portalorder)cards.get(i)).getCategoryType())) {
        categoryType = "包时卡";
      }
      else
      {
        if ("1".equals(((Portalorder)cards.get(i)).getCategoryType())) {
          categoryType = "日卡";
        }
        else
        {
          if ("2".equals(((Portalorder)cards.get(i)).getCategoryType())) {
            categoryType = "月卡";
          }
          else
          {
            if ("3".equals(((Portalorder)cards.get(i)).getCategoryType())) {
              categoryType = "年卡";
            }
            else
            {
              if ("4".equals(((Portalorder)cards.get(i)).getCategoryType()))
                categoryType = "流量卡";
              else
                categoryType = "错误"; 
            }
          }
        }
      }
      l = new Label(column++, i + 3, categoryType, detFormat);
      sheet.addCell(l);

      Long payTime = Long.valueOf(0L);
      String payTimeString = "";
      if ("0".equals(((Portalorder)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
        payTimeString = String.valueOf(payTime) + "小时";
      } else if ("1".equals(((Portalorder)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
        payTimeString = String.valueOf(payTime) + "天";
      } else if ("2".equals(((Portalorder)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
        payTimeString = String.valueOf(payTime) + "月";
      } else if ("3".equals(((Portalorder)cards.get(i)).getCategoryType())) {
    	payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L /  
          12L);
        payTimeString = String.valueOf(payTime) + "年";
      } else if ("4".equals(((Portalorder)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalorder)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
        payTimeString = String.valueOf(payTime) + "M";
      } else {
        payTimeString = "错误";
      }
      l = new Label(column++, i + 3, payTimeString, detFormat);
      sheet.addCell(l);
      String state;
      if ("0".equals(((Portalorder)cards.get(i)).getState())) {
        state = "未支付";
      }
      else
      {
        if ("1".equals(((Portalorder)cards.get(i)).getState()))
          state = "已支付";
        else
          state = "错误";
      }
      l = new Label(column++, i + 3, state, detFormat);
      sheet.addCell(l);

      if (((Portalorder)cards.get(i)).getBuyDate() != null) {
        d = new DateTime(column++, i + 3, ((Portalorder)cards.get(i)).getBuyDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 3, "", detFormat);
        sheet.addCell(l);
      }

      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getAccountName(), 
        detFormat);
      sheet.addCell(l);

      if (((Portalorder)cards.get(i)).getPayDate() != null) {
        d = new DateTime(column++, i + 3, ((Portalorder)cards.get(i)).getPayDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 3, "", detFormat);
        sheet.addCell(l);
      }
      String payby;
      if (((Portalorder)cards.get(i)).getPayby().intValue() == 0) {
        payby = "系统";
      }
      else
      {
        if (1 == ((Portalorder)cards.get(i)).getPayby().intValue()) {
          payby = "支付宝";
        }
        else
        {
          if (2 == ((Portalorder)cards.get(i)).getPayby().intValue())
            payby = "微信";
          else
            payby = "错误"; 
        }
      }
      l = new Label(column++, i + 3, payby, detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getTradeno(), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getBuyer(), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalorder)cards.get(i)).getSeller(), detFormat);
      sheet.addCell(l);

      l = new Label(column++, i + 3, String.valueOf(((Portalorder)cards.get(i))
        .getMoney()), priceFormat);
      sheet.addCell(l);
    }

    column = 0;
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 30);
    sheet.setColumnView(column++, 40);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 10);

    workbook.write();
    workbook.close();
  }

	public static void writeCardsToExcel(String fileName, List<Portalcard> cards, String money)
    throws Exception
  {
    Number n = null;
    DateTime d = null;

    File tempFile = new File(fileName);
    WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
    WritableSheet sheet = workbook.createSheet("充值卡报表", 0);

    WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, 
      WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.GREEN);
    WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

    WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.RED);
    WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

    WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    WritableCellFormat detFormat = new WritableCellFormat(detFont);

    NumberFormat nf = new NumberFormat("0.00");
    WritableCellFormat priceFormat = new WritableCellFormat(nf);

    DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
    WritableCellFormat dateFormat = new WritableCellFormat(df);

    Label l = new Label(0, 0, "充值卡报表", headerFormat);
    sheet.addCell(l);

    l = new Label(0, 1, "出售充值卡，收入共计： " + money + " 元", titleFormat);
    sheet.addCell(l);

    int column = 0;
    l = new Label(column++, 2, "序号", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "名称", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "详细信息", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "CD-KEY", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "充值类型", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "分类", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "计数", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "MAC限制", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "自动绑定MAC个数（MAC共享数）", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "无感知", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "限速", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "状态", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "售出日期", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "充值用户", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "充值日期", titleFormat);
    sheet.addCell(l);
    l = new Label(column++, 2, "售价（元）", titleFormat);
    sheet.addCell(l);

    int y = cards.size();
    for (int i = 0; i < y; i++) {
      column = 0;
      l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getName(), detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getDescription(), 
        detFormat);
      sheet.addCell(l);
      l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getCdKey(), detFormat);
      sheet.addCell(l);
      String payType;
      if ("2".equals(((Portalcard)cards.get(i)).getPayType())) {
        payType = "计时";
      }
      else
      {
        if ("3".equals(((Portalcard)cards.get(i)).getPayType())) {
          payType = "买断";
        }
        else
        {
          if ("4".equals(((Portalcard)cards.get(i)).getPayType()))
            payType = "流量";
          else
            payType = "错误"; 
        }
      }
      l = new Label(column++, i + 3, payType, detFormat);
      sheet.addCell(l);
      String categoryType;
      if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
        categoryType = "包时卡";
      }
      else
      {
        if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
          categoryType = "日卡";
        }
        else
        {
          if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
            categoryType = "月卡";
          }
          else
          {
            if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
              categoryType = "年卡";
            }
            else
            {
              if ("4".equals(((Portalcard)cards.get(i)).getCategoryType()))
                categoryType = "流量卡";
              else
                categoryType = "错误"; 
            }
          }
        }
      }
      l = new Label(column++, i + 3, categoryType, detFormat);
      sheet.addCell(l);

      Long payTime = Long.valueOf(0L);
      String payTimeString = "";
      if ("0".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L);
        payTimeString = String.valueOf(payTime) + "小时";
      } else if ("1".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L);
        payTimeString = String.valueOf(payTime) + "天";
      } else if ("2".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L);
        payTimeString = String.valueOf(payTime) + "月";
      } else if ("3".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1000L / 60L / 60L / 24L / 31L /  
          12L);
        payTimeString = String.valueOf(payTime) + "年";
      } else if ("4".equals(((Portalcard)cards.get(i)).getCategoryType())) {
    	  payTime = Long.valueOf(((Portalcard)cards.get(i)).getPayTime().longValue() / 1024L / 1024L);
        payTimeString = String.valueOf(payTime) + "M";
      } else {
        payTimeString = "错误";
      }
      l = new Label(column++, i + 3, payTimeString, detFormat);
      sheet.addCell(l);

      Integer maclimitState = ((Portalcard)cards.get(i)).getMaclimit();
      Integer macCount = ((Portalcard)cards.get(i)).getMaclimitcount();
      if (maclimitState != null) {
        if (maclimitState.intValue() == 1)
          l = new Label(column++, i + 3, "启用", detFormat);
        else if (maclimitState.intValue() == 0)
          l = new Label(column++, i + 3, "关闭", detFormat);
        else
          l = new Label(column++, i + 3, "错误", detFormat);
      }
      else {
        l = new Label(column++, i + 3, "未知", detFormat);
      }
      sheet.addCell(l);

      if (macCount != null) {
        if (macCount.intValue() >= 0)
          n = new Number(column++, i + 3, macCount.intValue(), 
            priceFormat);
        else
          n = new Number(column++, i + 3, 1.0D, priceFormat);
      }
      else {
        n = new Number(column++, i + 3, 1.0D, priceFormat);
      }
      sheet.addCell(n);

      Integer macAuto = ((Portalcard)cards.get(i)).getAutologin();
      if (macAuto != null) {
        if (macAuto.intValue() == 1)
          l = new Label(column++, i + 3, "启用", detFormat);
        else if (macAuto.intValue() == 0)
          l = new Label(column++, i + 3, "关闭", detFormat);
        else
          l = new Label(column++, i + 3, "错误", detFormat);
      }
      else {
        l = new Label(column++, i + 3, "未知", detFormat);
      }
      sheet.addCell(l);

      String speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
      try {
        speed = speedService.getPortalspeedByKey(((Portalcard)cards.get(i)).getSpeed()).getName();
      } catch (Exception e) {
        speed = speedService.getPortalspeedByKey(Long.valueOf(1L)).getName();
      }
      l = new Label(column++, i + 3, speed, detFormat);
      sheet.addCell(l);
      String state;
      if ("0".equals(((Portalcard)cards.get(i)).getState())) {
        state = "新卡";
      }
      else
      {
        if ("1".equals(((Portalcard)cards.get(i)).getState())) {
          state = "已售出";
        }
        else
        {
          if ("2".equals(((Portalcard)cards.get(i)).getState()))
            state = "已激活";
          else
            state = "错误"; 
        }
      }
      l = new Label(column++, i + 3, state, detFormat);
      sheet.addCell(l);

      if (((Portalcard)cards.get(i)).getBuyDate() != null) {
        d = new DateTime(column++, i + 3, ((Portalcard)cards.get(i)).getBuyDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 3, "", detFormat);
        sheet.addCell(l);
      }

      l = new Label(column++, i + 3, ((Portalcard)cards.get(i)).getAccountName(), 
        detFormat);
      sheet.addCell(l);

      if (((Portalcard)cards.get(i)).getPayDate() != null) {
        d = new DateTime(column++, i + 3, ((Portalcard)cards.get(i)).getPayDate(), 
          dateFormat);
        sheet.addCell(d);
      } else {
        l = new Label(column++, i + 3, "", detFormat);
        sheet.addCell(l);
      }

      l = new Label(column++, i + 3, String.valueOf(((Portalcard)cards.get(i))
        .getMoney()), priceFormat);
      sheet.addCell(l);
    }

    column = 0;
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 30);
    sheet.setColumnView(column++, 40);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 10);
    sheet.setColumnView(column++, 20);
    sheet.setColumnView(column++, 10);

    workbook.write();
    workbook.close();
  }

	public static void writeLinksToExcel(String fileName, List<Portallinkrecordall> linkAll, String onlineTime)
			throws Exception {
		DateTime d = null;

		File tempFile = new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("连接记录报表", 0);

		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat detFormat = new WritableCellFormat(detFont);

		NumberFormat nf = new NumberFormat("0.00");
		WritableCellFormat priceFormat = new WritableCellFormat(nf);

		DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
		WritableCellFormat dateFormat = new WritableCellFormat(df);

		Label l = new Label(0, 0, "连接记录报表", headerFormat);
		sheet.addCell(l);

		l = new Label(0, 1, "连接记录报表内容，在线时长共计： " + onlineTime + " 分钟", titleFormat);
		sheet.addCell(l);

		int column = 0;
		l = new Label(column++, 2, "序号", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "登录名", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "MAC地址", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "IP地址", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "BAS地址", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "上线时间", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "下线时间", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "在线时长(分钟)", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "计费类型", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "下行流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "上行流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "使用流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "BASNAME", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "SSID", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "APMac", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "认证方式", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "无感知", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "客户端", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "原因", titleFormat);
		sheet.addCell(l);

		int y = linkAll.size();
		for (int i = 0; i < y; i++) {
			column = 0;
			l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getLoginName(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getMac(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getIp(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getBasip(), detFormat);
			sheet.addCell(l);
			d = new DateTime(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getStartDate(), dateFormat);
			sheet.addCell(d);
			d = new DateTime(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getEndDate(), dateFormat);
			sheet.addCell(d);

			double time = ((Portallinkrecordall) linkAll.get(i)).getTime().longValue() / 60000L;
			l = new Label(column++, i + 3, String.valueOf(time), priceFormat);
			sheet.addCell(l);
			String state;
			if ("0".equals(((Portallinkrecordall) linkAll.get(i)).getState())) {
				state = "停用";
			} else {
				if ("1".equals(((Portallinkrecordall) linkAll.get(i)).getState())) {
					state = "免费";
				} else {
					if ("2".equals(((Portallinkrecordall) linkAll.get(i)).getState())) {
						state = "计时";
					} else {
						if ("3".equals(((Portallinkrecordall) linkAll.get(i)).getState())) {
							state = "买断";
						} else {
							if ("4".equals(((Portallinkrecordall) linkAll.get(i)).getState()))
								state = "流量";
							else
								state = "外部用户";
						}
					}
				}
			}
			l = new Label(column++, i + 3, state, detFormat);
			sheet.addCell(l);

			String inS = "0 M";
			String outS = "0 M";
			String octetsS = "0 M";
			try {
				double in = Double.valueOf(((Portallinkrecordall) linkAll.get(i)).getIns().longValue()).doubleValue();
				double out = Double.valueOf(((Portallinkrecordall) linkAll.get(i)).getOuts().longValue()).doubleValue();
				double octets = in + out;
				in /= 1048576.0D;
				out /= 1048576.0D;
				octets /= 1048576.0D;
				inS = textf.format(in);
				outS = textf.format(out);
				octetsS = textf.format(octets);
				if (inS.startsWith(".")) {
					inS = "0" + inS;
				}
				if (outS.startsWith(".")) {
					outS = "0" + outS;
				}
				if (octetsS.startsWith(".")) {
					octetsS = "0" + octetsS;
				}
				inS = inS + " M";
				outS = outS + " M";
				octetsS = octetsS + " M";
			} catch (Exception localException) {
			}
			l = new Label(column++, i + 3, outS, detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, inS, detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, octetsS, detFormat);
			sheet.addCell(l);

			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getBasname(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getSsid(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getApmac(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getMethodtype(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getAuto(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getAgent(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Portallinkrecordall) linkAll.get(i)).getEx1(), detFormat);
			sheet.addCell(l);
		}

		column = 0;
		sheet.setColumnView(column++, 5);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);

		workbook.write();
		workbook.close();
	}

	public static void writeRadiusLinksToExcel(String fileName, List<Radiuslinkrecordall> linkAll, String onlineTime,
			String allOctets) throws Exception {
		DateTime d = null;

		File tempFile = new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("Radius连接记录报表", 0);

		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.GREEN);
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat detFormat = new WritableCellFormat(detFont);

		NumberFormat nf = new NumberFormat("0.00");
		WritableCellFormat priceFormat = new WritableCellFormat(nf);

		DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
		WritableCellFormat dateFormat = new WritableCellFormat(df);

		Label l = new Label(0, 0, "Radius连接记录报表", headerFormat);
		sheet.addCell(l);

		l = new Label(0, 1, "Radius连接记录报表内容，在线时长共计： " + onlineTime + " 分钟，流量共计： " + allOctets + " M", titleFormat);
		sheet.addCell(l);

		int column = 0;
		l = new Label(column++, 2, "序号", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "标识ID", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "nas名称", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "nasIP", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "sourceIP", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "用户IP", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "MAC地址", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "登录名", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "下行流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "上行流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "使用流量", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "上线时间", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "下线时间", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "在线时长(分钟)", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "计费类型", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "NAS类型", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "原因", titleFormat);
		sheet.addCell(l);

		int y = linkAll.size();
		for (int i = 0; i < y; i++) {
			column = 0;
			l = new Label(column++, i + 3, String.valueOf(i + 1), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getAcctsessionid(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getEx3(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getNasip(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getSourceip(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getUserip(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getCallingstationid(), detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getName(), detFormat);
			sheet.addCell(l);

			double out = ((Radiuslinkrecordall) linkAll.get(i)).getOuts().longValue() / 1048576L;
			l = new Label(column++, i + 3, String.valueOf(out) + " M", priceFormat);
			sheet.addCell(l);

			double in = ((Radiuslinkrecordall) linkAll.get(i)).getIns().longValue() / 1048576L;
			l = new Label(column++, i + 3, String.valueOf(in) + " M", priceFormat);
			sheet.addCell(l);

			double octets = (((Radiuslinkrecordall) linkAll.get(i)).getOuts().longValue()
					+ ((Radiuslinkrecordall) linkAll.get(i)).getIns().longValue()) / 1048576L;
			l = new Label(column++, i + 3, String.valueOf(octets) + " M", priceFormat);
			sheet.addCell(l);
			d = new DateTime(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getStartDate(), dateFormat);
			sheet.addCell(d);
			d = new DateTime(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getEndDate(), dateFormat);
			sheet.addCell(d);

			double time = ((Radiuslinkrecordall) linkAll.get(i)).getTime().longValue() / 60000L;
			l = new Label(column++, i + 3, String.valueOf(time), priceFormat);
			sheet.addCell(l);
			String state;
			if ("0".equals(((Radiuslinkrecordall) linkAll.get(i)).getState())) {
				state = "停用";
			} else {
				if ("1".equals(((Radiuslinkrecordall) linkAll.get(i)).getState())) {
					state = "免费";
				} else {
					if ("2".equals(((Radiuslinkrecordall) linkAll.get(i)).getState())) {
						state = "计时";
					} else {
						if ("3".equals(((Radiuslinkrecordall) linkAll.get(i)).getState())) {
							state = "买断";
						} else {
							if ("4".equals(((Radiuslinkrecordall) linkAll.get(i)).getState()))
								state = "流量";
							else
								state = "外部用户";
						}
					}
				}
			}
			l = new Label(column++, i + 3, state, detFormat);
			sheet.addCell(l);
			String clientType;
			if ("0".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
				clientType = "标准";
			} else {
				if ("1".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
					clientType = "华为";
				} else {
					if ("2".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
						clientType = "H3C";
					} else {
						if ("3".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
							clientType = "Cisco";
						} else {
							if ("4".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
								clientType = "ROS";
							} else {
								if ("5".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
									clientType = "爱快";
								} else {
									if ("6".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1())) {
										clientType = "中兴";
									} else {
										if ("7".equals(((Radiuslinkrecordall) linkAll.get(i)).getEx1()))
											clientType = "卓迈";
										else
											clientType = "错误";
									}
								}
							}
						}
					}
				}
			}
			l = new Label(column++, i + 3, clientType, detFormat);
			sheet.addCell(l);
			l = new Label(column++, i + 3, ((Radiuslinkrecordall) linkAll.get(i)).getEx2(), detFormat);
			sheet.addCell(l);
		}

		column = 0;
		sheet.setColumnView(column++, 5);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 10);

		workbook.write();
		workbook.close();
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.utils.ExcelUtils JD-Core Version: 0.6.2
 */