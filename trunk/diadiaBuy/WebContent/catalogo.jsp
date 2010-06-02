<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List, model.Prodotto"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Catalogo</title>
</head>
<body>

<% List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
	//FIXME Unchecked cast %>

<% for (Prodotto p : prodotti) { %>
	<h1><%= p.getNome() %></h1>
	<div><%= p.getPrezzo() %> euro<br />Disponibilità: <%= p.getDisponibilita() %></div>
<% } %>

</body>
</html>