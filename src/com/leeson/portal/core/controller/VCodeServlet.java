/*    */ package com.leeson.portal.core.controller;
/*    */ 
/*    */ import com.leeson.portal.core.controller.utils.VerifyCode;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class VCodeServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = -6731196330461009661L;
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 46 */     VerifyCode vc = new VerifyCode();
/* 47 */     BufferedImage image = vc.getImage();
/* 48 */     request.getSession().setAttribute("session_vcode", vc.getText());
/*    */ 
/* 50 */     VerifyCode.output(image, response.getOutputStream());
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.VCodeServlet
 * JD-Core Version:    0.6.2
 */