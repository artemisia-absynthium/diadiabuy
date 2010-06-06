<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher("/diadiaBuy/login.jsp");
		rd.forward(request, response);
		return;
	}
	
	Ordine carrello = (Ordine) request.getAttribute("carrello");
	List<RigaOrdine> righe = carrello.getRigheOrdine();
%>


<%@page import="model.Utente"%>
<%@page import="model.Ordine"%>
<%@page import="model.RigaOrdine"%>
<%@page import="java.util.List"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Il tuo carrello</title>
</head>
<body>

<% if (righe.isEmpty()) { /* Se il carrello è vuoto */ %>
	<div>Il tuo carrello è ancora vuoto.</div>
<% } else { %>
	<% for (RigaOrdine riga : righe) { %>
		<div>
			<span><%= riga.getNomeProdotto() %></span>
			<span><%= riga.getQuantita() %></span>
			<span><%= riga.getTotale() %></span>
		</div><br />
	<% } %>
<% } %>

<a href="/diadiaBuy/consulta_prodotti.do">Torna al catalogo</a>

</body>
</html>