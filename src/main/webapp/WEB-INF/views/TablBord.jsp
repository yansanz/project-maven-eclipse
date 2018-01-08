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
<title>Tableau_Bord</title>

</head>
<body>
	<header>
		<h1>Tableau de bord</h1>
	</header>
	<div id="tablBord">
		<fieldset id="tb">
			<div id="btnMode">
				<input id="btnSub" class="btn" type="button" value="Mode Emprunteur"
					onclick="modeSub()"> <a
					href="<c:url value ="/Acceuil/Librairie"/>"><input id="btnLib"
					class="btn" type="button" value="Mode Librairie"></a> <input
					id="btnBook" class="btn" type="button" value="Mode Book"
					onclick="modeBook()">
				<fieldset id="zoneInfo">
					<legend id="legend">INFO</legend>
					${infoBul}
				</fieldset>
			</div>
			<div id="zonRech">
				<form name="retour" action="<c:url value='/Acceuil/rechercheBook'/>"
					method="get">
					<input id="txtTitre" placeholder="titre" name="txtTitre" value=""
						type="text" size="20"
						onkeyup="rechTitre();"> <select
						id="boxG" name="genre"
						onchange="rechGenre();document.retour.submit()">
						<option>Genre</option>
						<c:forEach var="myGenre" items="${catalog}">
							<option><c:out value="${myGenre.catalogName}" /></option>
						</c:forEach>
					</select> <select id="boxA" name="auteur"
						onchange="rechAuteur();document.retour.submit()">
						<option>Auteur</option>
						<c:forEach var="myAuteur" items="${auteur}">
							<option><c:out value="${myAuteur.fisrtName}" />
								<c:out value="${myAuteur.lastName}" />
								<c:out value="${myAuteur.dateOfBirth}" /></option>
						</c:forEach>
					</select>
				</form>
			</div>
			<div id="zonRechSub">
				<form name="sub" action="<c:url value='/Acceuil/rechercheSub'/>"
					method="get">
					<input id="txtNom" placeholder="nom" name="txtNom" value=""
						type="search" size="20" onchange="document.sub.submit()">

					<a href="<c:url value ="/Acceuil/rechercheSub"/>"><input
						id="btnNom" type="button" value="recherche"></a>
				</form>
			</div>
			<div id="zonCibl">

				<jsp:include page="tablBordBook.jsp"></jsp:include>


				<jsp:include page="TablBordCopy.jsp"></jsp:include>


				<jsp:include page="tablBordSub.jsp"></jsp:include>

			</div>
			<div id="commande">
				<c:set var="mode" value="${btnSuprCopy}" />
				<c:if test="${btnSuprCopy!= null}">
					<input class="btn" id="btnSuprCopy" type="button"
						value="Supprimer copie" onclick="document.suprCopy.submit()">
				</c:if>
				<c:set var="mode" value="${btnSuprCopy}" />
				<c:if test="${btnSuprCopy== null}">
					<c:set var="mode" value="${modeInf}" />
					<c:if test="${modeInf!= null}">
					<input class="btn" id="btnRetour" type="button" value="Restituer"
							onclick="document.retourCopy.submit()">
							<br>
						<input class="btn" id="btnplus" type="button" value="+"
							onclick="document.addCopy.submit()">
						<input class="btn" id="btnMoins" type="button" value="-"
							onclick="document.suprCopy.submit()">
							<br>
						<input class="btn" class="btn" type="button" value="Info"
							onclick="document.infoValid.submit()">
					</c:if>
					<c:set var="mode" value="${modeInf}" />
					<c:if test="${modeInf== null}">
						<input id="btnInf" class="btn" type="button" value="Info"
							onclick="document.info.submit()">
						<!-- Trigger/Open The Modal -->
						<input id="myBtn" class="btn" type="button" value="Ajouter">

						<!-- The Modal -->
						<div id="myModal" class="modal">

							<!-- Modal content -->
							<div class="modal-content">
								<div class="modal-header">
									<span class="close">&times;</span>
									<h2>Ajout LIVRE ou Emprunteur</h2>
								</div>
								<div class="modal-body">
									<div class="container">
										<form name="add" action="<c:url value='/Acceuil/add'/>"
											method="get">
											<ul class="list">
												<li class="list__item"><input type="radio"
													class="radio-btn" name="choice" id="a-opt"
													onclick="choixAdd(1);document.add.submit()" /> <label
													for="a-opt" class="label">EMPRUNTEUR</label></li>
												<li class="list__item"><input type="radio"
													class="radio-btn" name="choice" id="b-opt"
													onclick="choixAdd(2);document.add.submit()" /> <label
													for="b-opt" class="label">LIVRE</label></li>

											</ul>
											<input id="txtCibl7" placeholder="cible7" name="txtCibl7"
												value="" type="hidden" size="20">
										</form>
									</div>
								</div>
							</div>

						</div>
						<input id="btnModif" class="btn" type="button" value="Modifier"
							onclick="document.modif.submit()">
						<input id="btnSup" class="btn" type="button" value="Supprimer"
							onclick="document.supr.submit()">
						<br>
						<input id="btnValid" class="btn" type="button" value="Valider">
					</c:if>
					<br>
					<a href="<c:url value ="/Acceuil"/>"><input class="btn"
						id="btnReset" type="button" value="reset"></a>
				</c:if>
			</div>
			<form name="info" action="<c:url value='/Acceuil/info'/>"
				method="get">
				<input id="txtCibl1" placeholder="cible1" name="txtCibl1" value=""
					type="hidden" size="20"> <input id="txtCibl2"
					placeholder="cible2" name="txtCibl2" value="" type="hidden"
					size="20">
			</form>
			<form name="modif" action="<c:url value='/Acceuil/modif'/>"
				method="get">
				<input id="txtCibl3" placeholder="cible1" name="txtCibl3" value=""
					type="hidden" size="20"> <input id="txtCibl4"
					placeholder="cible2" name="txtCibl4" value="" type="hidden"
					size="20">
			</form>
			<form name="supr" action="<c:url value='/Acceuil/supr'/>"
				method="get">
				<input id="txtCibl5" placeholder="cible1" name="txtCibl5" value=""
					type="hidden" size="20"> <input id="txtCibl6"
					placeholder="cible2" name="txtCibl6" value="${isbn}" type="hidden"
					size="20">
			</form>
			<form name="suprCopy" action="<c:url value='/Acceuil/suprCopy'/>"
				method="get">
				<input id="txtCibl7" placeholder="cible1" name="txtCibl7" value=""
					type="hidden" size="20"> <input id="txtCibl8"
					placeholder="cible2" name="txtCibl8" value="${isbn}" type="hidden"
					size="20">
			</form>
			<form name="addCopy" action="<c:url value='/Acceuil/addCopy'/>"
				method="get">
				<input id="txtCibl9" placeholder="cible1" name="txtCibl9"
					value="${isbn}" type="hidden" size="20">
			</form>
			<form name="retourCopy" action="<c:url value='/Acceuil/ReturnInfoCopy'/>"
				method="get">
				<input id="txtCibl12" placeholder="cible1" name="txtCibl12"
					value="${idSub}" type="hidden" size="20">
					<input id="txtCibl13" placeholder="cible1" name="txtCibl13"
					value="" type="hidden" size="20">
			</form>
			<form name="infoValid" action="<c:url value='/Acceuil/infoValid'/>"
				method="get">
				<input id="txtCibl10" name="txtCibl10" value="" type="hidden"
					size="20"> <input id="txtCibl11" name="txtCibl11"
					value="${isbnInfo}" type="hidden" size="20">
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