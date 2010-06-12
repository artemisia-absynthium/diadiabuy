<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="checklogged.jspf" %>
<%	
	Ordine carrello = (Ordine) request.getAttribute("carrello");
	List<RigaOrdine> righe = carrello.getRigheOrdine();
%>


<%@page import="model.Utente"%>
<%@page import="model.Ordine"%>
<%@page import="java.util.List"%>
<%@page import="model.RigaOrdine"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Conferma il tuo acquisto</title>
</head>
<body>

<% if (righe.isEmpty()) { /* Se il carrello è vuoto */ %>
	<div>Non hai comprato niente, <a href="<%= response.encodeURL("/diadiaBuy/consulta_prodotti.do") %>">torna al catalogo.</a></div>
<% } else { %>
	<% for (RigaOrdine riga : righe) { %>
		<div>
			<span><%= riga.getNomeProdotto() %></span>
			<span><%= riga.getQuantita() %></span>
			<span><%= riga.getTotale() %></span>
		</div><br />
	<% } %><br /><br />
	<div>Totale: <%= carrello.getTotale() %></div><br /><br />
	<a href="<%= response.encodeURL("/diadiaBuy/conferma_acquisto.do") %>">Conferma acquisto</a>
<% } %>

</body>
</html>