<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List, model.Prodotto"%>
<%@page import="model.Utente"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Catalogo</title>
</head>
<% Utente utente = (Utente) session.getAttribute("utente"); %>
<body>

<% List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti"); %>

<% for (Prodotto p : prodotti) { %>
	<h1><%= p.getNome() %></h1>
	<div><%= p.getPrezzo() %> euro<br />
	Disponibilità: <%= p.getDisponibilita() %><br />
		<form action="/diadiaBuy/dettagli.do" method="post">
			<input type="hidden" value="<%= p.getId() %>" name="product_id" />
			<input type="submit" value="Dettagli" />
		</form>
		<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
			<div>
				<form action="/diadiaBuy/aggiungi_al_carrello.do" method="post">
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