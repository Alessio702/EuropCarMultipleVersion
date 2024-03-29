<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista Fatture Passive</title>
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
		<a class="btn btn-light" href="http://localhost:8086/FatturaPassiva/Ricerca"
	role="button">Indietro</a>
<body>
	<div class="container">
		<spring:url value="/FatturaPassiva/CercaPerSottoCategoria/" var="saveURL" />
		<br> <br>
		<h2>Scegli una SottoCategoria</h2>
		<form:form modelAttribute="oggettoSottoCategoria" method="post"
			action="${saveURL}" cssClass="form">

			<div class="form-group">
				<br> <br> <label>SottoCategorie</label>
				<form:select path="idsottocategoria" cssClass="form-control" id="sottocategoria">
					<br>
					<br>
					<form:option value="0">Seleziona una SottoCategoria:</form:option>
					<form:options items="${elencoSottoCategorie}" itemValue="idsottocategoria"
						itemLabel="sottocategoria" />
				</form:select>
				<br>
				<c:if test="${not empty message}">
					<c:out value="${message}" />
				</c:if>
			</div>
			<form:hidden path="codice" />
			<form:hidden path="sottocategoria" />
			<form:hidden path="budget" />
			<form:hidden path="budgetspeso" />
			<form:hidden path="oarea" />
			
			<button type="submit" class="btn btn-primary">Cerca	Fatture Passive</button>
		</form:form>

<br> <br>

		<c:if test="${oggettoSottoCategoria.getIdsottocategoria() != 0}">
			<div class="container">
			
			
				<h2>Lista Fatture Passive</h2>
				<table class="table table-striped">
					<thead>

						<th scope="row">Data</th>
						<th scope="row">Numero</th>
						<th scope="row">Descrizione</th>
						<th scope="row">SottoCategoria</th>
						
					</thead>
					<tbody>
						<c:forEach items="${elencoFatturePassive}" var="elenco">
							<tr>
								<td>${elenco.data.toString().substring(0, 10)}</td>
								<td>${elenco.numero}</td>
								<td>${elenco.descrizione}</td>
								<td>${elenco.ofornitore.ragionesociale}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</c:if>

	</div>
	<br><br>
</body>
</html>