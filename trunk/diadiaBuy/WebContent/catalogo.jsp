<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="model.Utente"%>
<%@page import="web.util.StringUtils"%>
<%@page import="model.Prodotto"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="diadiabuy.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Catalogo</title>
</head>
<% Utente utente = (Utente) session.getAttribute("utente"); %>
<body>

<% 	
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
	String messaggio = (String) request.getAttribute("messaggio");
	String encodedDettagliURL = response.encodeURL("/diadiaBuy/dettagli.do");
	String encodedCarrelloURL = response.encodeURL("/diadiaBuy/aggiungi_al_carrello.do");
%>
	
<span><%= StringUtils.normalizeNull(messaggio) %></span><br /> 

<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
	<a href="<%= response.encodeURL("/diadiaBuy/carrello.do") %>">Consulta il tuo carrello</a>
	<br /><br />
<% } %>

<% for (Prodotto p : prodotti) { %>
	<div><span class="name"><%= p.getNome() %></span>
	<span class="1"><%= p.getPrezzo() %> euro</span>
	<span class="2">Disponibilità: <%= p.getDisponibilita() %></span>
		<form action="<%= encodedDettagliURL %>" method="post">
			<input type="hidden" value="<%= p.getId() %>" name="product_id" />
			<span class="3"><input type="submit" value="Dettagli" /></span>
		</form>
		<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
				<form action="<%= encodedCarrelloURL %>" method="post">
					<input type="hidden" value="<%= p.getId() %>" name="product_id" />
					<span class="4"><input type="text" value="1" name="quantita" size="3" /> &nbsp;
					<input type="submit" value="Aggiungi al carrello" /></span>
				</form>
		<% } %>
		</div>
		<br /><br />
<% } %>

</body>
</html>