<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Aggiungi Fattura</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<a class="btn btn-light" href="http://localhost:8086/FatturaPassiva/Cerca" role="button">Annulla</a>
<body>
	<div class="container">
	<spring:url value="/FatturaPassiva/AddDettagli/" var="saveURL" />
		<form:form modelAttribute="oggettoFatturaTemporanea" method="post" action="${saveURL}" cssClass="form">
		
		<h2>Add Fattura</h2>
			<form:hidden path="idfatturapassiva" />
			<form:hidden path="dettagli" />
			<div class="form-group">
				<label>Data</label>
				<form:input type ="date" path="data" cssClass="form-control" id="data" />
				<form:errors path="data" cssClass="error" />
			</div>
			<div class="form-group">
				<label>Numero</label>
				<form:input path="numero" cssClass="form-control" id="numero" />
				<form:errors path="numero" cssClass="error" />
			</div>
			<div class="form-group">
				<label>Descrizione</label>
				<form:input path="descrizione" cssClass="form-control" id="descrizione" />
				<form:errors path="descrizione" cssClass="error" />
			</div>
			<div class="form-group">
				<label>Fornitore</label>
				<form:select path="ofornitore" cssClass="form-control" id="ragionesociale">
					<form:option value="0">Seleziona un fornitore</form:option>
					<form:options items="${elencoFornitori}" itemValue="idfornitore" itemLabel="ragionesociale" />
				</form:select>
							<form:errors path="ofornitore" cssClass="error"/>
			</div>
			<button type="submit" class="btn btn-primary">Aggiungi Dettagli</button>
		</form:form>
	</div>
</body>
</html>