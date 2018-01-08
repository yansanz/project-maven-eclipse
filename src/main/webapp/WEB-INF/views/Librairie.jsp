<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tablBord.css">
<title>Librairie</title>

</head>
<body>
	<header>
		<h1>Tableau de bord</h1>
	</header>
	<div id="tablBord">
		<fieldset id="tbLib">
			<div id="btnMode">
			<a href="<c:url value ="/Acceuil"/>"><input class="btn"
						id="btnRetour" type="button" value="Acceuil"></a> 
				<fieldset id="zoneInfo">
					<legend id="legend">INFO</legend>
					${infoBul}
				</fieldset>
			</div>

			<div id="zonRechSub">
				<h3>Emprunteur</h3>
				<input type="search" placeholder="recherche" id="donnees" name="donees" size="30" onkeyup="rechercheDonnee();">
			</div>
			<div id="zonCiblSub">

				<jsp:include page="tablBordSub.jsp"></jsp:include>

			</div>
			<br>
			<div id="zonRechBook">
				<h3>Livre</h3>
				<a href="<c:url value ="/Acceuil/Librairie"/>"><input class="btn2"
					 type="button" value="Titre"></a>
					<a href="<c:url value ="/Acceuil/LibrairieA"/>"><input class="btn2"
				 type="button" value="Auteur"></a>
				 <a href="<c:url value ="/Acceuil/LibrairieG"/>"><input class="btn2"
					 type="button" value="Genre"></a>
					
			</div>
			<div id="zonCiblBook">

				<jsp:include page="tablBordBook.jsp"></jsp:include>

				<jsp:include page="TablBordCopy.jsp"></jsp:include>

			</div>

			<div id="commande">
				<c:set var="mode" value="${modeLib}" />
				<c:if test="${modeLib== null}">
					<c:set var="mode" value="${modeEmpr}" />
					<c:if test="${modeEmpr== null}">

						<input id="btnInf" class="btn" type="button" value="EMPRUNTER"
							onclick="document.emprunt.submit()">
						<br>
						<input id="myBtn" class="btn" type="button" value="RESTITUER"
							onclick="document.restituer.submit()">

						<br>
					</c:if>
				</c:if>
				<a href="<c:url value ="/Acceuil"/>"><input class="btn"
					id="btnReset" type="button" value="reset"></a> <br>
				<c:set var="mode" value="${modeEmpr}" />
				<c:if test="${modeEmpr!= null}">
					<input id="myBtn" class="btn" type="button" value="OK"
						onclick="document.empruntValid.submit()">
				</c:if>
				<c:set var="mode" value="${modeLib}" />
				<c:if test="${modeLib!= null}">
					<input id="myBtn" class="btn" type="button" value="OK"
						onclick="document.restituerValid.submit()">
				</c:if>
			</div>

			<form name="emprunt" action="<c:url value='/Acceuil/Emprunt'/>"
				method="get">
				<input id="txtCibl1" placeholder="cible1" name="txtCibl1" value=""
					type="hidden" size="20"> <input id="txtCibl2"
					placeholder="cible2" name="txtCibl2" value="" type="hidden"
					size="20">
			</form>
			<form name="restituer" action="<c:url value='/Acceuil/Retour'/>"
				method="get">
				<input id="txtCibl3" placeholder="cible1" name="txtCibl3" value=""
					type="hidden" size="20"> <input id="txtCibl4"
					placeholder="cible2" name="txtCibl4" value="" type="hidden"
					size="20">
			</form>
			<form name="restituerValid"
				action="<c:url value='/Acceuil/RetourValid'/>" method="get">
				<input id="txtCibl5" placeholder="cible1" name="txtCibl5" value=""
					type="hidden" size="20"> <input id="txtCibl6"
					placeholder="cible2" name="txtCibl6" value="${id}" type="hidden"
					size="20">
			</form>
			<form name="empruntValid"
				action="<c:url value='/Acceuil/EmpruntValid'/>" method="get">
				<input id="txtCibl7" placeholder="cible1" name="txtCibl7" value=""
					type="hidden" size="20"> <input id="txtCibl8"
					placeholder="cible2" name="txtCibl8" value="${id}" type="hidden"
					size="20">
			</form>
		</fieldset>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tablBord.js"></script>
	<footer>
		<jsp:include page="/Footer.jsp"></jsp:include>
	</footer>
</body>
</html>