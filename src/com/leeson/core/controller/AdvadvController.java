package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Advbanner;
import com.leeson.core.bean.Advpic;
import com.leeson.core.bean.Advstores;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.AdvadvQuery;
import com.leeson.core.query.AdvbannerQuery;
import com.leeson.core.query.AdvpicQuery;
import com.leeson.core.query.AdvstoresQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.AdvbannerService;
import com.leeson.core.service.AdvpicService;
import com.leeson.core.service.AdvstoresService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.utils.ImageUtil;
import com.leeson.portal.core.controller.WISPr.utils.Tools;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class AdvadvController {
	private static Logger logger = Logger.getLogger(AdvadvController.class);

	@Autowired
	private AdvadvService advadvService;

	@Autowired
	private PortaluserService portaluserService;

	@Autowired
	private AdvstoresService advstoresService;

	@Autowired
	private AdvbannerService advbannerService;

	@Autowired
	private AdvpicService advpicService;

	@RequestMapping({ "/pic.action" })
	public void clickPic(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String url = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
				.get("core"))[0];
		try {
			Advpic pic = this.advpicService.getAdvpicByKey(id);
			url = pic.getUrl();
			pic.setClickCount(Long.valueOf(pic.getClickCount().longValue() + 1L));
			this.advpicService.updateAdvpicByKey(pic);

			Advadv adv = this.advadvService.getAdvadvByKey(pic.getAid());
			adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
			this.advadvService.updateAdvadvByKey(adv);
		} catch (Exception localException) {
		}
		response.sendRedirect(url);
	}

	@RequestMapping({ "/banner.action" })
	public void clickBanner(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String url = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
				.get("core"))[0];
		try {
			Advbanner banner = this.advbannerService.getAdvbannerByKey(id);
			url = banner.getUrl();
			banner.setClickCount(Long.valueOf(banner.getClickCount().longValue() + 1L));
			this.advbannerService.updateAdvbannerByKey(banner);

			Advadv adv = this.advadvService.getAdvadvByKey(banner.getAid());
			adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
			this.advadvService.updateAdvadvByKey(adv);
		} catch (Exception localException) {
		}
		response.sendRedirect(url);
	}

	@RequestMapping({ "/adv.action" })
	public void clickAdv(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String url = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
				.get("core"))[0];
		try {
			Advadv adv = this.advadvService.getAdvadvByKey(id);
			url = adv.getUrl();
			adv.setClickCount(Long.valueOf(adv.getClickCount().longValue() + 1L));
			this.advadvService.updateAdvadvByKey(adv);
		} catch (Exception localException) {
		}
		response.sendRedirect(url);
	}

	@RequestMapping({ "/portal.action" })
	public String portal(Long id, Integer auth, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		int result = 0;

		if (Tools.Do()) {
			try {
				Advadv adv = this.advadvService.getAdvadvByKey(id);
				if (adv != null) {
					adv.setShowCount(Long.valueOf(adv.getShowCount().longValue() + 1L));
					this.advadvService.updateAdvadvByKey(adv);
					int state = adv.getState().intValue();
					if (state == 1) {
						Date startDate = adv.getShowDate();
						Date endDate = adv.getEndDate();
						Date nowDate = new Date();
						if (((startDate == null) || (nowDate.getTime() >= startDate.getTime()))
								&& ((endDate == null) || (endDate.getTime() >= nowDate.getTime()))) {
							Advstores store = this.advstoresService.getAdvstoresByKey(adv.getSid());
							if (store != null) {
								Long advID = adv.getId();

								AdvbannerQuery bq = new AdvbannerQuery();
								bq.setAid(advID);
								bq.orderbyPos(true);
								List<Advbanner> banners = this.advbannerService.getAdvbannerList(bq);
								for (Advbanner banner : banners) {
									banner.setShowCount(Long.valueOf(banner.getShowCount().longValue() + 1L));
									this.advbannerService.updateAdvbannerByKey(banner);
								}

								AdvpicQuery pq = new AdvpicQuery();
								pq.setAid(advID);
								pq.orderbyPos(true);
								List<Advpic> pics = this.advpicService.getAdvpicList(pq);

								int onePic = 0;
								if ((pics != null) && (pics.size() > 0)) {
									int picsNum = pics.size();
									if (picsNum % 2 != 0) {
										onePic = 1;
									}
								}
								model.addAttribute("onePic", Integer.valueOf(onePic));
								for (Advpic pic : pics) {
									pic.setShowCount(Long.valueOf(pic.getShowCount().longValue() + 1L));
									this.advpicService.updateAdvpicByKey(pic);
								}

								model.addAttribute("store", store);
								model.addAttribute("adv", adv);
								model.addAttribute("banners", banners);
								model.addAttribute("pics", pics);
								result = 1;
							}
						}
					}
				}
			} catch (Exception localException) {
			}
		}
		model.addAttribute("ret", Integer.valueOf(result));
		if (auth == null) {
			return "adv/portal";
		}
		if (auth.intValue() == 0)
			return "adv/portalAuth";
		if (auth.intValue() == 1)
			return "adv/portalWispr";
		if (auth.intValue() == 2) {
			return "adv/portalWifidog";
		}
		return "adv/portal";
	}

	@ResponseBody
  @RequestMapping({"pload"})
  public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response)
  {
    Map map = new HashMap();
    try {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
      MultipartFile image = multipartRequest.getFile("advimg");

      String imageName = image.getOriginalFilename();
      String file_ext = imageName
        .substring(imageName.lastIndexOf(".") + 1);
      String tempImageName = UUID.randomUUID().toString() + "." + 
        file_ext;

      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
      String filename = "/" + df.format(new Date()) + "/";

      String filePath = request.getServletContext()
        .getRealPath("/advPic") + filename;
      File fileLocation = new File(filePath);
      if (!fileLocation.exists()) {
        fileLocation.mkdir();
      }
      filePath = filePath + tempImageName;
      File file = new File(filePath);
      byte[] advImageBytes = null;
      InputStream advImageStream = null;
      file.createNewFile();
      advImageStream = image.getInputStream();
      advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
      FileCopyUtils.copy(advImageBytes, file);
      advImageStream.close();
      int result = ImageUtil.checkImgHW(filePath, 600, 150);
      if (result == 0) {
    	String tempPath = "/advPic/" + filename + tempImageName;
        map.put("tempPath", tempPath);
        map.put("ret", "0");
        map.put("msg", "上传成功！！");
      } else if (result == 1) {
        map.put("ret", "1");
        map.put("msg", "图片尺寸错误！！");
      } else {
        map.put("ret", "1");
        map.put("msg", "图片格式错误！！");
      }
    } catch (Exception e) {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
      map.put("ret", "1");
      map.put("msg", "发生错误！！");
    }
    return map;
  }

	@RequestMapping({ "ist.action" })
	public String page(AdvadvQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List users = this.portaluserService.getPortaluserList(new PortaluserQuery());
		model.addAttribute("users", users);

		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		if (!"admin".equals(user.getLoginName())) {
			query.setUid(Long.valueOf(uid));
		}

		query.setNameLike(true);
		query.setDescriptionLike(true);
		if (stringUtils.isBlank(query.getName())) {
			query.setName(null);
		}
		if (stringUtils.isBlank(query.getDescription())) {
			query.setDescription(null);
		}
		Pagination pagination = this.advadvService.getAdvadvListWithPage(query);
		model.addAttribute("pagination", pagination);
		model.addAttribute("query", query);

		AdvstoresQuery squery = new AdvstoresQuery();
		if (!"admin".equals(user.getLoginName())) {
			squery.setUid(Long.valueOf(uid));
		}
		List stores = this.advstoresService.getAdvstoresList(squery);
		model.addAttribute("stores", stores);
		return "advadv/list";
	}

	@RequestMapping({ "dd.action" })
	public String add(Advadv e, ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		AdvstoresQuery query = new AdvstoresQuery();
		if (!"admin".equals(user.getLoginName())) {
			query.setUid(Long.valueOf(uid));
		}
		List stores = this.advstoresService.getAdvstoresList(query);
		model.addAttribute("stores", stores);
		model.addAttribute("entity", e);
		return "advadv/save";
	}

	@RequestMapping(value = { "dd.action" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String add(Advadv e, String showDateS, String endDateS, ModelMap model, HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();

		if ((showDateS != null) && (!"".equals(showDateS)))
			try {
				Date viewDate = format.parse(showDateS + " 00:00:00");
				e.setShowDate(viewDate);
			} catch (Exception ex) {
				model.addAttribute("entity", e);
				model.addAttribute("msg", "日期格式不正确！");
				AdvstoresQuery query = new AdvstoresQuery();
				if (!"admin".equals(user.getLoginName())) {
					query.setUid(Long.valueOf(uid));
				}
				List stores = this.advstoresService.getAdvstoresList(query);
				model.addAttribute("stores", stores);
				return "advadv/save";
			}
		else {
			e.setShowDate(new Date());
		}
		if ((endDateS != null) && (!"".equals(endDateS))) {
			try {
				Date viewDate = format.parse(endDateS + " 00:00:00");
				e.setEndDate(viewDate);
			} catch (Exception ex) {
				model.addAttribute("entity", e);
				model.addAttribute("msg", "日期格式不正确！");
				AdvstoresQuery query = new AdvstoresQuery();
				if (!"admin".equals(user.getLoginName())) {
					query.setUid(Long.valueOf(uid));
				}
				List stores = this.advstoresService.getAdvstoresList(query);
				model.addAttribute("stores", stores);
				return "advadv/save";
			}
		}

		String domain = e.getUrl().trim();
		if (stringUtils.isNotBlank(domain)) {
			if ((!domain.startsWith("http:"))) {
				domain = "http://" + domain;
			}
			while (domain.endsWith("/"))
				domain = domain.substring(0, domain.length() - 1);
		} else {
			domain = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
					.get("core"))[0];
		}
		e.setUrl(domain);

		e.setUid(Long.valueOf(uid));
		e.setCreatDate(new Date());
		this.advadvService.addAdvadv(e);

		return "redirect:ist.action";
	}

	@RequestMapping({ "dit.action" })
	public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request) {
		Advadv e = this.advadvService.getAdvadvByKey(id);
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		if (!"admin".equals(user.getLoginName())) {
			long suid = e.getUid().longValue();
			if (suid != uid) {
				return "redirect:ist.action";
			}
		}
		AdvstoresQuery query = new AdvstoresQuery();
		if (!"admin".equals(user.getLoginName())) {
			query.setUid(Long.valueOf(uid));
		}
		List stores = this.advstoresService.getAdvstoresList(query);
		model.addAttribute("stores", stores);
		model.addAttribute("entity", e);
		return "advadv/save";
	}

	@RequestMapping(value = { "dit.action" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String edit(Advadv e, String showDateS, String endDateS, ModelMap model, HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Advadv s = this.advadvService.getAdvadvByKey(e.getId());
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		if (!"admin".equals(user.getLoginName())) {
			long suid = s.getUid().longValue();
			if (suid != uid) {
				return "redirect:ist.action";
			}
		}

		if ((showDateS != null) && (!"".equals(showDateS)))
			try {
				Date viewDate = format.parse(showDateS + " 00:00:00");
				e.setShowDate(viewDate);
			} catch (Exception ex) {
				model.addAttribute("entity", e);
				model.addAttribute("msg", "日期格式不正确！");
				AdvstoresQuery query = new AdvstoresQuery();
				if (!"admin".equals(user.getLoginName())) {
					query.setUid(Long.valueOf(uid));
				}
				List stores = this.advstoresService.getAdvstoresList(query);
				model.addAttribute("stores", stores);
				return "advadv/save";
			}
		else {
			e.setShowDate(new Date());
		}
		if ((endDateS != null) && (!"".equals(endDateS))) {
			try {
				Date viewDate = format.parse(endDateS + " 00:00:00");
				e.setEndDate(viewDate);
			} catch (Exception ex) {
				model.addAttribute("entity", e);
				model.addAttribute("msg", "日期格式不正确！");
				AdvstoresQuery query = new AdvstoresQuery();
				if (!"admin".equals(user.getLoginName())) {
					query.setUid(Long.valueOf(uid));
				}
				List stores = this.advstoresService.getAdvstoresList(query);
				model.addAttribute("stores", stores);
				return "advadv/save";
			}
		}

		String domain = e.getUrl().trim();
		if (stringUtils.isNotBlank(domain)) {
			if ((!domain.startsWith("http:"))) {
				domain = "http://" + domain;
			}
			while (domain.endsWith("/"))
				domain = domain.substring(0, domain.length() - 1);
		} else {
			domain = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
					.get("core"))[0];
		}
		e.setUrl(domain);

		this.advadvService.updateAdvadvByKey(e);
		return "redirect:ist.action";
	}

	@RequestMapping({ "elete.action" })
	public String delete(@RequestParam Long id, HttpServletRequest request) {
		Advadv s = this.advadvService.getAdvadvByKey(id);
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		if (!"admin".equals(user.getLoginName())) {
			long suid = s.getUid().longValue();
			if (suid != uid) {
				return "redirect:ist.action";
			}
		}
		this.advadvService.deleteByKey(id);
		return "redirect:ist.action";
	}

	@RequestMapping(value = { "eletes.action" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String deletes(@RequestParam Long[] ids, HttpServletRequest request) {
		List<Long> list = Arrays.asList(ids);
		for (Long id : list) {
			Advadv s = this.advadvService.getAdvadvByKey(id);
			HttpSession session = request.getSession();
			Portaluser user = (Portaluser) session.getAttribute("user");
			if ((user == null) || (user.getId() == null)) {
				return "homeAction/index";
			}
			long uid = user.getId().longValue();
			if (!"admin".equals(user.getLoginName())) {
				long suid = s.getUid().longValue();
				if (suid == uid)
					this.advadvService.deleteByKey(id);
			} else {
				this.advadvService.deleteByKey(id);
			}
		}

		return "redirect:ist.action";
	}

	@RequestMapping(value = { "eletes.action" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String deletes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Portaluser user = (Portaluser) session.getAttribute("user");
		if ((user == null) || (user.getId() == null)) {
			return "homeAction/index";
		}
		long uid = user.getId().longValue();
		AdvadvQuery query = new AdvadvQuery();
		if (!"admin".equals(user.getLoginName())) {
			query.setUid(Long.valueOf(uid));
		}
		List<Advadv> advs = this.advadvService.getAdvadvList(query);
		for (Advadv adv : advs) {
			this.advadvService.deleteByKey(adv.getId());
		}
		return "redirect:ist.action";
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.controller.AdvadvController JD-Core Version:
 * 0.6.2
 */