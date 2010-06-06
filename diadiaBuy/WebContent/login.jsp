<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="web.util.StringUtils"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>

<% String messaggio = (String) request.getAttribute("messaggio"); %>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Login</title>
</head>
<body>

<form action="/diadiaBuy/login.do" method="post" name="login">
	<span class="errore"><%= StringUtils.normalizeNull(messaggio) %></span><br /><br />
	<div>
		<span>Username: <input type="text" name="username" /></span><br />
		<span>Password: <input type="password" name="password" /></span>
	</div>
	<br />
	<br />
	<div>
		<span><input type="submit" value="Entra" name="login" /></span>
		<span><input type="reset" value="Reset" /></span>
	</div>
</form>

</body>
</html>