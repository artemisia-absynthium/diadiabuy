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
		RequestDispatcher rd = application.getRequestDispatcher("/diadiaBuy/notAuthorized.jsp");
		rd.forward(request, response);
		return;
	}
	
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>

<% for (Prodotto p : prodotti) { %>
	<h1><%= p.getNome() %></h1>
	<form action="elenco_fornitori.do" method="post">
		<input type="hidden" value="<%= p.getCodice() %>" name="product_code" />
		<input type="submit" value="Fornitori" />
	</form>
	<form action="inserisci_fornitore.jsp" method="post">
		<input type="hidden" value="<%= p.getCodice() %>" name="product_code" />
		<span><input type="submit" value="Inserisci fornitore" /></span>
	</form>
<% } %>

</body>
</html>