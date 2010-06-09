<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="model.Utente"%>
<%@page import="model.Ordine"%>
<%@page import="java.util.List"%>
<%@page import="model.RigaOrdine"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher(response.encodeURL("/diadiaBuy/login.jsp"));
		rd.forward(request, response);
		return;
	}
	
	Ordine ordine = (Ordine) request.getAttribute("ordine");
	List<RigaOrdine> righe = ordine.getRigheOrdine();
	DateFormat dateFormat = new SimpleDateFormat();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - ordine <%= ordine.getCodice() %></title>
</head>
<body>

<h1>Dettagli ordine del <%= dateFormat.format(ordine.toDate()) %></h1>
<% for (RigaOrdine riga : righe) { %>
	<div>
		<span><%= riga.getNomeProdotto() %></span>
		<span><%= riga.getQuantita() %></span>
		<span><%= riga.getTotale() %></span>
	</div><br />
<% } %><br /><br />
Totale: <%= ordine.getTotale() %>

<a href="<%= response.encodeURL("/diadiaBuy/ordini.do") %>">Torna all'elenco degli ordini</a>

</body>
</html>