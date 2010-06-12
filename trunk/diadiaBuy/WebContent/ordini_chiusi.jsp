<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="model.Ordine"%>
<%@page import="web.util.StringUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="checklogged_admin.jspf" %>
<%
	List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
	String messaggio = (String) request.getAttribute("messaggio");
	DateFormat dateFormat = new SimpleDateFormat();
%>
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Ordini da evadere</title>
</head>
<body>
<%= StringUtils.normalizeNull(messaggio) %><br /><br />
<% if (ordini.isEmpty()) { /* Se non ci sono ordini da evadere */ %>
	<h1>Non ci sono ordini da evadere.</h1>
<% } %>

<% for (Ordine o : ordini) { %>
	<div>
		<span class="name"><%= dateFormat.format(o.toDate()) %></span>
		<span><%= o.getCodice() %></span>
		<span><%= o.getTotale() %></span>
		<form action="evadi.do" method="post">
			<input type="hidden" value="<%= o.getId() %>" name="product_id" />
			<input type="submit" value="Evadi" />
		</form>
	</div>
<% } %>

</body>
</html>