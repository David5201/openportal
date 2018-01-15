package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.DiyUtils;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.query.AdvadvQuery;
import com.leeson.core.query.PortalwebQuery;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.PortalwebService;
import com.leeson.portal.core.listener.ReportService;
import com.leeson.portal.core.model.isDo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/portalwebController")
public class PortalwebController {

	@Autowired
	private PortalwebService portalwebService;

	@Autowired
	private AdvadvService advadvService;

	@RequestMapping({ "ist.action" })
	public String page(PortalwebQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		query.setNameLike(true);
		query.setDescriptionLike(true);
		if (stringUtils.isBlank(query.getName())) {
			query.setName(null);
		}
		if (stringUtils.isBlank(query.getDescription())) {
			query.setDescription(null);
		}
		Pagination pagination = this.portalwebService.getPortalwebListWithPage(query);
		model.addAttribute("pagination", pagination);
		model.addAttribute("query", query);

		List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
		model.addAttribute("advs", advs);
		return "portalweb/list";
	}

	@RequestMapping({ "dd.action" })
	public String add(ModelMap model) {
		List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
		model.addAttribute("advs", advs);
		return "portalweb/save";
	}

	@RequestMapping(value = { "dd.action" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String add(@RequestParam(required = false) MultipartFile file, Portalweb e, ModelMap model,
			HttpServletRequest request) {
		List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
		model.addAttribute("advs", advs);

		if (!Do()) {
			model.addAttribute("msg", "系统未授权或者已经过期！");
			model.addAttribute("entity", e);
			return "portalweb/save";
		}
		PortalwebQuery q = new PortalwebQuery();
		q.setName(e.getName());
		q.setNameLike(false);
		if (this.portalwebService.getPortalwebList(q).size() > 0) {
			model.addAttribute("msg", "该名称已经存在！");
			model.addAttribute("entity", e);
			return "portalweb/save";
		}

		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!ext.equals("zip")) {
			model.addAttribute("msg", "文件错误!");
			model.addAttribute("entity", e);
			return "portalweb/save";
		}

		this.portalwebService.addPortalweb(e);
		String id = String.valueOf(e.getId());

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String nowString = format.format(now);
		String sourceFile = "web-" + nowString + ".zip";
		String dir = null;
		try {
			InputStream in = file.getInputStream();
			dir = request.getServletContext().getRealPath("/version");
			File fileLocation = new File(dir);

			if (!fileLocation.exists()) {
				boolean isCreated = fileLocation.mkdir();
				if (!isCreated) {
					model.addAttribute("msg", "权限不足!");
					model.addAttribute("entity", e);
					return "portalweb/save";
				}
			}
			dir = request.getServletContext().getRealPath("/version");

			File uploadFile = new File(dir, sourceFile);

			FileUtils.copyInputStreamToFile(in, uploadFile);
			in.close();
		} catch (Exception ex) {
			model.addAttribute("msg", "上传出错!");
			File df = new File(dir, sourceFile);
			if (df.exists()) {
				ReportService.deleteAll(df);
			}
			model.addAttribute("entity", e);
			return "portalweb/save";
		}
		try {
			String descDir = request.getServletContext().getRealPath("/");
			descDir = descDir + "/" + id + "/";
			File idpath = new File(descDir);

			if (!idpath.exists()) {
				boolean isCreated = idpath.mkdir();
				if (!isCreated) {
					model.addAttribute("msg", "权限不足!");
					model.addAttribute("entity", e);
					return "portalweb/save";
				}
			}
			sourceFile = dir + "/" + sourceFile;
			DiyUtils.unZip(descDir, sourceFile);
		} catch (Exception ex) {
			model.addAttribute("msg", "解压出错!");
			File df = new File(dir);
			if (df.exists()) {
				ReportService.deleteAll(df);
			}
			model.addAttribute("entity", e);
			return "portalweb/save";
		}

		File df = new File(dir);
		if (df.exists()) {
			ReportService.deleteAll(df);
		}
		model.addAttribute("msg", "添加成功!");

		return "redirect:ist.action";
	}

	@RequestMapping({ "dit.action" })
	public String edit(@RequestParam Long id, ModelMap model) {
		Portalweb e = this.portalwebService.getPortalwebByKey(id);
		model.addAttribute("entity", e);
		List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
		model.addAttribute("advs", advs);
		return "portalweb/save";
	}

	@RequestMapping(value = { "dit.action" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String edit(@RequestParam(required = false) MultipartFile file, Portalweb e, ModelMap model,
			HttpServletRequest request) {
		List advs = this.advadvService.getAdvadvList(new AdvadvQuery());
		model.addAttribute("advs", advs);

		if (!Do()) {
			model.addAttribute("msg", "系统未授权或者已经过期！");
			model.addAttribute("entity", e);
			return "portalweb/save";
		}
		PortalwebQuery q = new PortalwebQuery();
		q.setName(e.getName());
		q.setNameLike(false);
		List webs = this.portalwebService.getPortalwebList(q);
		if ((webs != null) && (webs.size() > 0) && (((Portalweb) webs.get(0)).getId() != e.getId())) {
			model.addAttribute("msg", "该名称已经存在！");
			model.addAttribute("entity", e);
			return "portalweb/save";
		}

		this.portalwebService.updatePortalwebByKey(e);

		if (!file.isEmpty()) {
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!ext.equals("zip")) {
				model.addAttribute("msg", "文件错误!");
				model.addAttribute("entity", e);
				return "portalweb/save";
			}
			String id = String.valueOf(e.getId());
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String nowString = format.format(now);
			String sourceFile = "web-" + nowString + ".zip";
			String dir = null;
			try {
				InputStream in = file.getInputStream();
				dir = request.getServletContext().getRealPath("/version");
				File fileLocation = new File(dir);

				if (!fileLocation.exists()) {
					boolean isCreated = fileLocation.mkdir();
					if (!isCreated) {
						model.addAttribute("msg", "权限不足!");
						model.addAttribute("entity", e);
						return "portalweb/save";
					}
				}

				File uploadFile = new File(dir, sourceFile);

				FileUtils.copyInputStreamToFile(in, uploadFile);
				in.close();
			} catch (Exception ex) {
				model.addAttribute("msg", "上传出错!");
				File df = new File(dir, sourceFile);
				if (df.exists()) {
					ReportService.deleteAll(df);
				}
				model.addAttribute("entity", e);
				return "portalweb/save";
			}
			try {
				String descDir = request.getServletContext().getRealPath("/");
				descDir = descDir + "/" + id + "/";
				File idpath = new File(descDir);

				if (!idpath.exists()) {
					boolean isCreated = idpath.mkdir();
					if (!isCreated) {
						model.addAttribute("msg", "权限不足!");
						model.addAttribute("entity", e);
						return "portalweb/save";
					}
				}
				sourceFile = dir + "/" + sourceFile;
				DiyUtils.unZip(descDir, sourceFile);
			} catch (Exception ex) {
				model.addAttribute("msg", "解压出错!");
				File df = new File(dir);
				if (df.exists()) {
					ReportService.deleteAll(df);
				}
				model.addAttribute("entity", e);
				return "portalweb/save";
			}

			File df = new File(dir);
			if (df.exists()) {
				ReportService.deleteAll(df);
			}
		}

		model.addAttribute("msg", "更新成功!");

		return "redirect:ist.action";
	}

	@RequestMapping({ "elete.action" })
	public String delete(@RequestParam Long id, HttpServletRequest request) {
		this.portalwebService.deleteByKey(id);
		String dir = request.getServletContext().getRealPath("/" + id + "/");
		File df = new File(dir);
		if (df.exists()) {
			ReportService.deleteAll(df);
		}
		return "redirect:ist.action";
	}

	@RequestMapping(value = { "eletes.action" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public String deletes(@RequestParam Long[] ids, HttpServletRequest request) {
		List<Long> list = Arrays.asList(ids);
		this.portalwebService.deleteByKeys(list);
		for (Long id : list) {
			String dir = request.getServletContext().getRealPath("/" + id + "/");
			File df = new File(dir);
			if (df.exists()) {
				ReportService.deleteAll(df);
			}
		}

		return "redirect:ist.action";
	}

	@RequestMapping({ "own.action" })
	public String down(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String descDir = request.getServletContext().getRealPath("/");
			String idS = String.valueOf(id);
			String path = DiyUtils.Zip(descDir, idS);
			File file = new File(path);

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			response.addHeader("Content-Length", file.length() + "");
			response.setContentType("application/octet-stream;charset=UTF-8");

			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		} catch (IOException localIOException) {
		}
		return "redirect:ist.action";
	}

	public static boolean Do() {
		Long isThis = Long.valueOf(new Date().getTime());
		boolean Do = false;
		if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
			Do = true;
		}
		return Do;
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.controller.PortalwebController JD-Core
 * Version: 0.6.2
 */