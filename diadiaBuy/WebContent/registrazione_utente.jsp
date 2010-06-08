<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.Map, java.util.HashMap"%>

<%@page import="web.util.StringUtils"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Registrazione</title>
</head>
<%	
	final String messaggio = "Riempire la form correttamente";
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	Map<String,String> errori = (Map<String,String>) request.getAttribute("errori");
	if (errori == null) {
		errori = new HashMap<String,String>();
	}
%>
<body>

<h1>Registrazione Utente</h1>

<form name="input" action="registrazione.do" method="post">
	<div>
		<% if (!errori.isEmpty()) { %>
			<span class="errore"><%= messaggio %></span>
		<% } %><br /><br />
		<span>Username: <input type="text" name="username" value="<%= StringUtils.normalizeNull(username) %>" size="20" /></span>
		<% if (errori.get("username")!=null) { %>
			<span class="errore"><%= errori.get("username") %></span>
		<% } %>
		<br />
		<span>Password: <input type="password" name="password" value="<%= StringUtils.normalizeNull(password) %>" size="20" /></span>
		<% if (errori.get("password")!=null) { %>
			<span class="errore"><%= errori.get("password") %></span>
		<% } %>
	</div>
	<br />
	<br />
	<div>
		<span><input type="submit" value="Invia" /></span>
		<span><input type="reset" value="Annulla" /></span>
	</div>
</form>

</body>
</html>