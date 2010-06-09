<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="model.Prodotto" %>

<%@page import="model.Utente"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>

<% 	
	Prodotto prodotto = (Prodotto) request.getAttribute("prodotto"); 
	Utente utente = (Utente) session.getAttribute("utente");
	String descrizione = (String) request.getAttribute("descrizione"); 
	
	String encodedCarrelloURL = response.encodeURL("/diadiaBuy/aggiungi_al_carrello.do");
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - <%= prodotto.getNome() %></title>
</head>
<body>

<h1><%= prodotto.getNome() %></h1>
<div>Prezzo: <%= prodotto.getPrezzo() %> euro.<br />Disponibilità: <%= prodotto.getDisponibilita() %></div>
<div><%= descrizione %></div><br /><br />
<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
	<div>
		<form action="<%= encodedCarrelloURL %>" method="post">
			<input type="hidden" value="<%= prodotto.getId() %>" name="product_id" />
			<input type="text" value="1" name="quantita" /> &nbsp;
			<input type="submit" value="Aggiungi al carrello" />
		</form>
	</div><br /><br />
<% } %>
<a href="<%= response.encodeURL("/diadiaBuy/consulta_prodotti.do") %>">Torna all'elenco prodotti</a>

</body>
</html>