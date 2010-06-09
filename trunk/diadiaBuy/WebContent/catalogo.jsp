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
	
<span><%= StringUtils.normalizeNull(messaggio) %></span>

<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
	<a href="<%= response.encodeURL("/diadiaBuy/carrello.do") %>">Consulta il tuo carrello</a>
<% } %>

<% for (Prodotto p : prodotti) { %>
	<h1><%= p.getNome() %></h1>
	<div><%= p.getPrezzo() %> euro<br />
	DisponibilitÓ: <%= p.getDisponibilita() %><br />
		<form action="<%= encodedDettagliURL %>" method="post">
			<input type="hidden" value="<%= p.getId() %>" name="product_id" />
			<input type="submit" value="Dettagli" />
		</form>
		<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
			<div>
				<form action="<%= encodedCarrelloURL %>" method="post">
					<input type="hidden" value="<%= p.getId() %>" name="product_id" />
					<input type="text" value="1" name="quantita" /> &nbsp;
					<input type="submit" value="Aggiungi al carrello" />
				</form>
			</div><br /><br />
		<% } %>
	</div>
<% } %>

</body>
</html>