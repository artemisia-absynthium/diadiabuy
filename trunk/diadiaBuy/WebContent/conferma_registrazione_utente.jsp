<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Conferma la registrazione</title>
</head>
<%	
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String indirizzo = request.getParameter("indirizzo");
%>
<body>

<h1 align="center">Conferma la tua registrazione</h1>
	
	<h2>I tuoi dati sono:</h2>
		
	<form name="input" action="conferma_registrazione.do" method="post">
		<div>
			<span>Nome: <input type="text" readonly="readonly" name="username" value="<%= username %>" size="20" /></span><br />		
			<input type="hidden" name="password" value="<%= password %>" />
		</div><br /><br />
		  
		<div>
			<span><input type="submit" name="Invia" value="Invia" /></span>
			<span><input type="submit" name="Correggi" value="Correggi" /></span>
		</div>

	</form>

</body>
</html>