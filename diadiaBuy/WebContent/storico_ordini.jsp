<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="model.Utente"%>
<%@page import="java.util.List"%>
<%@page import="model.Ordine"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - I tuoi ordini</title>

<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher(response.encodeURL("/diadiaBuy/login.jsp"));
		rd.forward(request, response);
		return;
	}
	
	List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
	DateFormat dateFormat = new SimpleDateFormat();
	
	String encodedOrderURL = response.encodeURL("/diadiaBuy/dettagli_ordine.do");
%>

</head>
<body>

<% for (Ordine ordine : ordini) { %>
	<div>
		<span><%= dateFormat.format(ordine.toDate()) %></span>
		<span><%= ordine.getTotale() %></span>
		<span><%= ordine.getStato() %></span>
		<form action="<%= encodedOrderURL %>" method="post">
			<input type="hidden" value="<%= ordine.getId() %>" name="ordine_id" />
			<input type="submit" value="Dettagli" />
		</form>
	</div><br />
<% } %>

</body>
</html>