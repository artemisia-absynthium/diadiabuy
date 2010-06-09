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
	
	String messaggio = (String) request.getAttribute("messaggio");
	String code = request.getParameter("product_code");
	
	String encodedInserisciURL = response.encodeURL("/diadiaBuy/inserisci_fornitore.do");
%>

<%@page import="web.util.StringUtils"%>
<%@page import="model.Utente"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Gestione fornitori</title>

</head>
<body>

<h1>Inserisci fornitore</h1><br /><br />
<span><%= StringUtils.normalizeNull(messaggio) %></span><br /><br />
<form action="<%= encodedInserisciURL %>" method="post">
	<input type="hidden" value="<%= code %>" name="product_code" />
	<span>Nome: <input type="text" value="" name="nome_fornitore" /></span><br />
	<span>Indirizzo: <input type="text" value="" name="indirizzo_fornitore" /></span><br />
	<span>Telefono: <input type="text" value="" name="telefono_fornitore" /></span><br />
	<span><input type="submit" value="Invia" /></span>
	<span><input type="reset" value="Reset" /></span>
</form>

<a href="<%= response.encodeURL("/diadiaBuy/gestisci_fornitori.do") %>">Torna indietro.</a>

</body>
</html>