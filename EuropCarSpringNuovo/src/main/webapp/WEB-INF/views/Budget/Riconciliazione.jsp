<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Riconciliazione</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<a class="btn btn-light" href="http://localhost:8086/menu/List"
	role="button">Home</a>
<a class="btn btn-light"
	href="http://localhost:8086/Budget/Lista" role="button">Indietro</a>
<body>
	<div class="container">
		<spring:url value="/Budget/SaveRiconciliazione/" var="saveURL" />
		<h3>${messaggio}</h3>
		<form:form modelAttribute="oggettoForm" method="post"
			action="${saveURL}" cssClass="form">
			<div class="form-group">
				<label>Data Inizio</label>
				<form:input type="date" path="datainizio" cssClass="form-control"
					id="datainizio" />
				<form:errors path="datainizio" cssClass="error" />
			</div>
			<div class="form-group">
				<label>Data Fine</label>
				<form:input type="date" path="datafine" cssClass="form-control"
					id="datafine" />
				<form:errors path="datafine" cssClass="error" />
			</div>

			<button type="submit" class="btn btn-primary">Riconcilia</button>
		</form:form>
	</div>
	<br>
	<h3>${messaggio}</h3>

</body>
</html>