<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null || !utente.isAdmin()) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher(response.encodeURL("/diadiaBuy/notAuthorized.jsp"));
		rd.forward(request, response);
		return;
	}
	
	List<Fornitore> fornitori = (List<Fornitore>) request.getAttribute("fornitori");
%>

<%@page import="model.Utente"%>
<%@page import="java.util.List"%>
<%@page import="model.Fornitore"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Elenco fornitori</title>
</head>
<body>

<% for (Fornitore fornitore : fornitori) { %>
	<div>
		<span><%= fornitore.getNome() %></span>
		<span><%= fornitore.getIndirizzo() %></span>
		<span><%= fornitore.getTelefono() %></span>
	</div><br />
<% } %>

<a href="<%= response.encodeURL("/diadiaBuy/gestisci_fornitori.do") %>">Torna indietro.</a>

</body>
</html>