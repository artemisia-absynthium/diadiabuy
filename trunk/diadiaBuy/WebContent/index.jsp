<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="model.Utente" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy</title>
</head>
<% Utente utente = (Utente) session.getAttribute("utente"); %>
<body>

<h1>Benvenuto su DiaDiaBuy <% if (utente != null) { out.print(utente.getUsername()); } %></h1>

<% if (utente == null) { /* Collegamenti visibili solo agli utenti NON registrati/loggati */ %>
	<a href="/diadiaBuy/registrazione_utente.jsp">Registrati</a><br />
	<a href="/diadiaBuy/login.jsp">Entra</a><br />
<% } %>
<a href="/diadiaBuy/consulta_prodotti.do">Consulta il catalogo</a><br />

<% if (utente != null) { /* Collegamenti visibili solo agli utenti registrati */ %>
	<a href="/diadiaBuy/carrello.do">Consulta il tuo carrello</a>
<% } %>

</body>
</html>