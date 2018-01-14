/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.bean.Portaluserrole;
/*     */ import com.leeson.core.query.PortaldepartmentQuery;
/*     */ import com.leeson.core.query.PortalroleQuery;
/*     */ import com.leeson.core.query.PortaluserQuery;
/*     */ import com.leeson.core.query.PortaluserroleQuery;
/*     */ import com.leeson.core.service.PortaldepartmentService;
/*     */ import com.leeson.core.service.PortalroleService;
/*     */ import com.leeson.core.service.PortaluserService;
/*     */ import com.leeson.core.service.PortaluserroleService;
/*     */ import com.leeson.core.utils.DepartmentUtils;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.shiro.SecurityUtils;
/*     */ import org.apache.shiro.authc.UsernamePasswordToken;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @Controller
/*     */ public class PortaluserController
/*     */ {
/*  51 */   private static Logger logger = Logger.getLogger(PortaluserController.class);
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserService portaluserService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaldepartmentService portaldepartmentService;
/*     */ 
/*     */   @Autowired
/*     */   private PortaluserroleService portaluserroleService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalroleService portalroleService;
/*     */ 
/*  66 */   @RequestMapping({"/portaluser/list.action"})
/*     */   public String page(PortaluserQuery query, Long roleId, ModelMap model) { query.setNameLike(true);
/*  67 */     query.setLoginNameLike(true);
/*     */ 
/*  69 */     if (stringUtils.isBlank(query.getName())) {
/*  70 */       query.setName(null);
/*     */     }
/*  72 */     if (stringUtils.isBlank(query.getLoginName())) {
/*  73 */       query.setLoginName(null);
/*     */     }
/*     */ 
/*  76 */     boolean dNull = false;
/*  77 */     if ((query.getDepartmentId() != null) && (query.getDepartmentId().longValue() == 0L)) {
/*  78 */       query.setDepartmentId(null);
/*  79 */       dNull = true;
/*     */     }
/*     */ 
/*  82 */     Pagination pagination = this.portaluserService
/*  83 */       .getPortaluserListWithPage(query);
/*     */     Long uid;
/*  85 */     if ((roleId != null) && (roleId.longValue() != 0L)) {
/*  86 */       PortaluserroleQuery urq = new PortaluserroleQuery();
/*  87 */       urq.setRoleId(roleId);
/*  88 */       List<Portaluserrole> urs = this.portaluserroleService.getPortaluserroleList(urq);
/*  89 */       List ut = new ArrayList();
/*  90 */       List us = pagination.getList();
/*  91 */       Collection uids = new HashSet();
/*  92 */       for (Portaluserrole ur : urs)
/*  93 */         uids.add(ur.getUserId());
/*     */       Iterator localIterator2;
/*  95 */       for (localIterator2 = uids.iterator(); localIterator2.hasNext(); 
/*  96 */         localIterator2.hasNext())
/*     */       {
/*  95 */         uid = (Long)localIterator2.next();
/*  96 */         localIterator2 = us.iterator(); 
				  Portaluser u = (Portaluser)localIterator2.next();
/*  97 */         if (u.getId() == uid) {
/*  98 */           ut.add(u);
/*     */         }
				  continue; 
/*     */       }
/*     */ 
/* 102 */       pagination.setList(ut);
/*     */     }
/*     */     Collection uids;
/* 105 */     if ((roleId != null) && (roleId.longValue() == 0L)) {
/* 106 */       List<Portaluserrole> urs = this.portaluserroleService.getPortaluserroleList(new PortaluserroleQuery());
/* 107 */       List ut = new ArrayList();
/* 108 */       List<Portaluser> us = (List<Portaluser>) pagination.getList();
/* 109 */       uids = new HashSet();
/* 110 */       for (Portaluserrole ur : urs) {
/* 111 */         uids.add(ur.getUserId());
/*     */       }
/* 113 */       for (Portaluser u : us) {
/* 114 */         if (!uids.contains(u.getId())) {
/* 115 */           ut.add(u);
/*     */         }
/*     */       }
/* 118 */       pagination.setList(ut);
/*     */     }
/*     */ 
/* 121 */     if (dNull) {
/* 122 */       List ut = new ArrayList();
/* 123 */       List<Portaluser> us = (List<Portaluser>) pagination.getList();
/* 124 */       for (Portaluser u : us) {
/* 125 */         if (u.getDepartmentId() == null) {
/* 126 */           ut.add(u);
/*     */         }
/*     */       }
/* 129 */       pagination.setList(ut);
/* 130 */       query.setDepartmentId(Long.valueOf(0L));
/*     */     }
/*     */ 
/* 135 */     List departmentList = this.portaldepartmentService
/* 136 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/*     */ 
/* 139 */     PortaluserroleQuery portaluserroleQuery = new PortaluserroleQuery();
/* 140 */     List userroles = this.portaluserroleService
/* 141 */       .getPortaluserroleList(portaluserroleQuery);
/* 142 */     model.addAttribute("userroles", userroles);
/*     */ 
/* 144 */     List roleList = this.portalroleService
/* 145 */       .getPortalroleList(new PortalroleQuery());
/* 146 */     model.addAttribute("roleList", roleList);
/*     */ 
/* 151 */     model.addAttribute("query", query);
/* 152 */     model.addAttribute("roleId", roleId);
/* 153 */     model.addAttribute("departmentList", departmentList);
/* 154 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/* 155 */     model.addAttribute("pagination", pagination);
/* 156 */     return "portaluser/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/addV"})
/*     */   public String addV(ModelMap model)
/*     */   {
/* 162 */     List departmentList = this.portaldepartmentService
/* 163 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/* 164 */     List roleList = this.portalroleService
/* 165 */       .getPortalroleList(new PortalroleQuery());
/* 166 */     model.addAttribute("roleList", roleList);
/* 167 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/* 168 */     return "portaluser/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portaluser e, Long roleId, ModelMap model)
/*     */   {
/* 175 */     if ((stringUtils.isBlank(e.getLoginName()) | 
/* 175 */       stringUtils.isBlank(e.getPassword()))) {
/* 176 */       model.addAttribute("msg", "用户名和密码不能为空！");
/* 177 */       model.addAttribute("entity", e);
/* 178 */       return "portaluser/save";
/*     */     }
/* 180 */     PortaluserQuery uq = new PortaluserQuery();
/* 181 */     uq.setLoginName(e.getLoginName());
/* 182 */     uq.setLoginNameLike(false);
/* 183 */     if (this.portaluserService.getPortaluserList(uq).size() > 0) {
/* 184 */       model.addAttribute("msg", "登录名已经存在！");
/* 185 */       model.addAttribute("entity", e);
/* 186 */       return "portaluser/save";
/*     */     }
/* 188 */     if (stringUtils.isBlank(e.getPassword()))
/* 189 */       e.setPassword(null);
/*     */     else {
/* 191 */       e.setPassword(DigestUtils.md5Hex(e.getPassword()));
/*     */     }
/* 193 */     this.portaluserService.addPortaluser(e);
/* 194 */     if (roleId != null) {
/* 195 */       Portaluserrole portaluserrole = new Portaluserrole();
/* 196 */       portaluserrole.setRoleId(roleId);
/* 197 */       portaluserrole.setUserId(((Portaluser)this.portaluserService.getPortaluserList(uq).get(0)).getId());
/* 198 */       this.portaluserroleService.addPortaluserrole(portaluserrole);
/*     */     }
/* 200 */     return "redirect:/portaluser/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/edit.action"})
/*     */   public String editPassword(@RequestParam Long id, ModelMap model)
/*     */   {
/* 206 */     Portaluser portaluser = new Portaluser();
/* 207 */     portaluser.setId(id);
/* 208 */     portaluser.setPassword(DigestUtils.md5Hex("1234"));
/* 209 */     this.portaluserService.updatePortaluserByKey(portaluser);
/* 210 */     return "redirect:/portaluser/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/editV.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 218 */     Portaluser e = this.portaluserService.getPortaluserByKey(id);
/* 219 */     List departmentList = this.portaldepartmentService
/* 220 */       .getPortaldepartmentList(new PortaldepartmentQuery());
/* 221 */     PortaluserroleQuery portaluserroleQuery = new PortaluserroleQuery();
/* 222 */     portaluserroleQuery.setUserId(id);
/* 223 */     List userroles = this.portaluserroleService
/* 224 */       .getPortaluserroleList(portaluserroleQuery);
/* 225 */     if (userroles.size() > 0) {
/* 226 */       Long roleId = ((Portaluserrole)userroles.get(0)).getRoleId();
/* 227 */       model.addAttribute("roleId", roleId);
/*     */     }
/*     */ 
/* 230 */     List roleList = this.portalroleService
/* 231 */       .getPortalroleList(new PortalroleQuery());
/* 232 */     model.addAttribute("roleList", roleList);
/*     */ 
/* 234 */     model.addAttribute("chooseList", DepartmentUtils.getAllDepartments(departmentList));
/* 235 */     model.addAttribute("entity", e);
/* 236 */     return "portaluser/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portaluser e, Long roleId)
/*     */   {
/* 242 */     Portaluser u = this.portaluserService.getPortaluserByKey(e.getId());
/* 243 */     if (stringUtils.isBlank(e.getPassword()))
/* 244 */       e.setPassword(u.getPassword());
/*     */     else {
/* 246 */       e.setPassword(DigestUtils.md5Hex(e.getPassword()));
/*     */     }
/* 248 */     e.setLoginName(u.getLoginName());
/*     */ 
/* 250 */     PortaluserroleQuery pu = new PortaluserroleQuery();
/* 251 */     pu.setUserId(e.getId());
/* 252 */     this.portaluserroleService.deleteByQuery(pu);
/*     */ 
/* 254 */     if (roleId != null) {
/* 255 */       Portaluserrole ar = new Portaluserrole();
/* 256 */       ar.setUserId(e.getId());
/* 257 */       ar.setRoleId(roleId);
/* 258 */       this.portaluserroleService.addPortaluserrole(ar);
/*     */     }
/* 260 */     this.portaluserService.updatePortaluserByKeyAll(e);
/* 261 */     return "redirect:/portaluser/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 267 */     PortaluserroleQuery pu = new PortaluserroleQuery();
/* 268 */     pu.setUserId(id);
/* 269 */     this.portaluserroleService.deleteByQuery(pu);
/* 270 */     this.portaluserService.deleteByKey(id);
/* 271 */     return "redirect:/portaluser/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 278 */     List<Long> list = Arrays.asList(ids);
/* 279 */     for (Long id : list) {
/* 280 */       PortaluserroleQuery pu = new PortaluserroleQuery();
/* 281 */       pu.setUserId(id);
/* 282 */       this.portaluserroleService.deleteByQuery(pu);
/*     */     }
/* 284 */     this.portaluserService.deleteByKeys(list);
/*     */ 
/* 286 */     return "redirect:/portaluser/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/login.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String loginUI(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 293 */     model.addAttribute("msg", "");
/* 294 */     return "portaluser/login";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/login.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String login(PortaluserQuery e, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 302 */     if ((stringUtils.isBlank(e.getLoginName()) | 
/* 302 */       stringUtils.isBlank(e.getPassword()))) {
/* 303 */       model.addAttribute("msg", "用户名和密码不能为空！");
/* 304 */       return "portaluser/login";
/*     */     }
/* 306 */     String inputPassword = e.getPassword();
/* 307 */     String md5PW = DigestUtils.md5Hex(e.getPassword());
/* 308 */     e.setPassword(md5PW);
/*     */ 
/* 310 */     String result = "portaluser/login";
/* 311 */     UsernamePasswordToken token = new UsernamePasswordToken(e.getLoginName(), e.getPassword());
/* 312 */     Subject currentUser = SecurityUtils.getSubject();
/*     */ 
/* 314 */     logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Try Login System !!!");
/*     */     try {
/* 316 */       currentUser.login(token);
/* 317 */       token.setRememberMe(true);
/* 318 */       Portaluser user = (Portaluser)this.portaluserService.getPortaluserList(e).get(0);
/* 319 */       request.getSession().setAttribute("user", user);
/* 320 */       result = "redirect:/homeAction/index.action";
/* 321 */       logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Use Username: [" + e.getLoginName() + "] Success to Login System !!!");
/*     */     }
/*     */     catch (Exception loginE)
/*     */     {
/* 325 */       model.addAttribute("msg", "用户名或密码错误！");
/* 326 */       result = "portaluser/login";
/* 327 */       logger.info("IP: " + GetNgnixRemotIP.getRemoteAddrIp(request) + " Use Username: [" + e.getLoginName() + "] Password: [" + inputPassword + "] False to Login System !!!");
/*     */     }
/* 329 */     return result;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/loginOut.action"})
/*     */   public String loginOut(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 346 */     Subject currentUser = SecurityUtils.getSubject();
/* 347 */     currentUser.logout();
/* 348 */     request.getSession().removeAttribute("user");
/* 349 */     return "portaluser/logout";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portaluser/editmyself.action"})
/*     */   public String editmyself(HttpServletRequest request, ModelMap model)
/*     */   {
/* 360 */     HttpSession session = request.getSession();
/* 361 */     Portaluser u = (Portaluser)session.getAttribute("user");
/* 362 */     Portaluser e = null;
/* 363 */     if (u != null) {
/* 364 */       e = this.portaluserService.getPortaluserByKey(u.getId());
/*     */     }
/* 366 */     if (e != null) {
/* 367 */       model.addAttribute("entity", e);
/* 368 */       return "portaluser/edit";
/*     */     }
/* 370 */     model.addAttribute("msg", "请重新登录！");
/* 371 */     return "portaluser/login";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portaluser/editmyself.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String editmyself(HttpServletRequest request, Portaluser e, ModelMap model)
/*     */   {
/* 379 */     HttpSession session = request.getSession();
/* 380 */     Portaluser u = (Portaluser)session.getAttribute("user");
/* 381 */     if (u == null) {
/* 382 */       model.addAttribute("msg", "请重新登录！");
/* 383 */       return "portaluser/login";
/*     */     }
/*     */ 
/* 386 */     if (stringUtils.isBlank(e.getPassword()))
/* 387 */       e.setPassword(u.getPassword());
/*     */     else {
/* 389 */       e.setPassword(DigestUtils.md5Hex(e.getPassword()));
/*     */     }
/* 391 */     e.setLoginName(u.getLoginName());
/* 392 */     e.setId(u.getId());
/* 393 */     this.portaluserService.updatePortaluserByKey(e);
/* 394 */     return "redirect:/homeAction/right.action";
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaluserController
 * JD-Core Version:    0.6.2
 */