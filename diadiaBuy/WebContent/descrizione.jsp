<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="model.Prodotto" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% Prodotto prodotto = (Prodotto) request.getAttribute("prodotto"); %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - <%= prodotto.getNome() %></title>
</head>
<body>

<h1><%= prodotto.getNome() %></h1>
<div>Prezzo: <%= prodotto.getPrezzo() %> euro.<br />Disponibilità: <%= prodotto.getDisponibilita() %></div>
<div><%= request.getAttribute("descrizione") %></div>

</body>
</html>