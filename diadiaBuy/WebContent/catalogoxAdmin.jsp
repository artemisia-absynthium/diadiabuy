<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="model.Utente"%>
<%@page import="java.util.List"%>
<%@page import="model.Prodotto"%><html xmlns="http://www.w3.org/1999/xhtml">

<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null || !utente.isAdmin()) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher(response.encodeURL("/diadiaBuy/notAuthorized.jsp"));
		rd.forward(request, response);
		return;
	}
	
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
	String encodedElencoURL = response.encodeURL("/diadiaBuy/elenco_fornitori.do");
	String encodedInserisciURL = response.encodeURL("/diadiaBuy/inserisci_fornitore.jsp");
%>

<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Gestione fornitori</title>
</head>
<body>

<% for (Prodotto p : prodotti) { %>
	<h2><%= p.getNome() %></h2>
	<form action="<%= encodedElencoURL %>" method="post">
		<input type="hidden" value="<%= p.getCodice() %>" name="product_code" />
		<input type="submit" value="Fornitori" />
	</form>
	<form action="<%= encodedInserisciURL %>" method="post">
		<input type="hidden" value="<%= p.getCodice() %>" name="product_code" />
		<span><input type="submit" value="Inserisci fornitore" /></span>
	</form>
<% } %>

</body>
</html>