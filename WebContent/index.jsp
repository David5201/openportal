<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	//response.sendRedirect(request.getContextPath() + "/index_choose");
    String webpath = request.getContextPath();
	request.getRequestDispatcher("/index_choose").forward(request,
		response);
	return;
%>