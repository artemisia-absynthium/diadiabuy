<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="model.Utente"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="web.util.StringUtils"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%><html xmlns="http://www.w3.org/1999/xhtml">

<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null || !utente.isAdmin()) {
		out.clear();
		RequestDispatcher rd = application.getRequestDispatcher("/diadiaBuy/notAuthorized.jsp");
		rd.forward(request, response);
		return;
	}
	
	final String messaggio = "Riempire la form correttamente";
	String nome = request.getParameter("nome");
	String descrizione = request.getParameter("descrizione");
	String prezzo = request.getParameter("prezzo");
	String disponibilita = request.getParameter("disponibilita");
	Map<String,String> errori = (Map<String,String>) request.getAttribute("errori");
	if (errori == null) {
		errori = new HashMap<String,String>();
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>DiaDiaBuy - Aggiungi un prodotto</title>
</head>
<body>

<h1>Aggiungi un prodotto</h1>

<form name="input" action="inserisci_prodotto.do" method="post">
	<div>
		<% if (!errori.isEmpty()) { %>
			<span class="errore"><%= messaggio %></span>
		<% } %><br /><br />
		<span>Nome: <input type="text" name="nome" value="<%= StringUtils.normalizeNull(nome) %>" size="20" /></span>
		<% if (errori.get("nome")!=null) { %>
			<span class="errore"><%= errori.get("nome") %></span>
		<% } %>
		<br />
		<input type="hidden" name="codice" value="<%= DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis())) %>" size="20" />
		<span>Descrizione: <input type="text" name="descrizione" value="<%= StringUtils.normalizeNull(descrizione) %>" size="50" /></span>
		<% if (errori.get("descrizione")!=null) { %>
			<span class="errore"><%= errori.get("descrizione") %></span>
		<% } %>
		<br />
		<span>Prezzo: <input type="text" name="prezzo" value="<%= StringUtils.normalizeNull(prezzo) %>" size="20" /></span>
		<% if (errori.get("prezzo")!=null) { %>
			<span class="errore"><%= errori.get("prezzo") %></span>
		<% } %>
		<br />
		<span>Disponibilità: <input type="text" name="disponibilita" value="<%= StringUtils.normalizeNull(disponibilita) %>" size="20" /></span>
		<% if (errori.get("disponibilita")!=null) { %>
			<span class="errore"><%= errori.get("disponibilita") %></span>
		<% } %>
	</div>
	<br />
	<br />
	<div>
		<span><input type="submit" value="Invia" /></span>
		<span><input type="reset" value="Annulla" /></span>
	</div>
</form>

</body>
</html>