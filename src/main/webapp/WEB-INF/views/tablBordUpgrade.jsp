<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<a
					href="<c:url value ="/Acceuil"/>"><input id="btnSub" class="btn" type="button" value="Acceuil"
					></a>
					 <a
					href="<c:url value ="/Acceuil/Librairie"/>"><input id="btnLib"
					class="btn" type="button" value="Mode Librairie"></a>
				
				<fieldset id="zoneInfo">
					<legend id="legend">INFO</legend>
					${infoBul}
				</fieldset>
			</div>

			<div id="zonCibl2">


				<form name="modifSub" action="<c:url value='/Acceuil/modifSub'/>"
					method="get">
					<c:set var="mode" value="${id}" />
					<c:if test="${id!= null}">
						<div id="champRech">
							<br> <input name="txtId" type="hidden" value="${id}"></input>
							<label>Nom</label> <br> <br> <input id="txtNom"
								name="txtNom" value="${lastName}" type="text" size="20">
							<br> <br> <br> <label>Prenom</label> <br> <br>
							<input id="txtPrenom" name="txtPrenom" value="${fisrtName}"
								type="text" size="20"> <br> <br>
						</div>
					</c:if>

				</form>
				<form name="addSub" action="<c:url value='/Acceuil/addSub'/>"
					method="get">
					<c:set var="mode" value="${modeSub}" />
					<c:if test="${modeSub != null}">
						<div id="champRech">
							<br> <input name="txtId" type="hidden" value="${id}"></input>
							<label>Nom</label> <br> <br> <input id="txtNom"
								name="txtNom" value="${lastName}" type="text" size="20">
							<br> <br> <br> <label>Prenom</label> <br> <br>
							<input id="txtPrenom" name="txtPrenom" value="${fisrtName}"
								type="text" size="20"> <br> <br>
						</div>
					</c:if>

				</form>

				<div id="tablCopy"></div>
				<form name="modifBook" action="<c:url value='/Acceuil/modifBook'/>"
					method="get">
					<c:set var="mode" value="${isbn}" />
					<c:if test="${isbn!= null}">
						<div id="tablBook">
							<p>ISBN</p>
							<input name="txtIsbn" id="addIsbn" value="${isbn}" type="text"
								size="20">
							<p>Titre</p>
							<input name="txtTitre" id="addTitre" value="${titre}" type="text"
								size="20">
							<p>Sous Titre</p>
							<input name="txtSsTitre" id="addSsTitre" value="${subTitre}"
								type="text" size="20"> <br> <br> <input
								name="txtNbrCopy" id="txtNbrCopy" value="${nbrCopy}"
								type="hidden"> <br> <br> <label>Auteur</label>
							<div class="popup">
								<input id="btnPop2" type="button" value="?"
									onclick="popUpInfo2()"> <span class="popuptext"
									id="myPopup2">selectionner l auteur dans la liste
									ci-dessous ou apres verification le creer dans les champs
									prevus</span>
							</div>
							<br> <select id="boxA" name="auteur"
								onchange="rechAuteurUp()">
								<option>Auteur</option>
								<c:forEach var="myAuteur" items="${auteur}">
									<option><c:out value="${myAuteur.fisrtName}" />
										<c:out value="${myAuteur.lastName}" />
										<c:out value="${myAuteur.dateOfBirth}" /></option>
								</c:forEach>
							</select> <input name="prenAut" id="prenAut" value="${prenAut}"
								type="text" onchange="rechAuteurTexUp()"> <input
								name="namAut" id="namAut" value="${namAut}" type="text"
								onchange="rechAuteurTexUp()"> <input name="agAut"
								id="agAut" value="${agAut}" type="number"
								onchange="rechAuteurTexUp()">
							<p>Collection</p>
							<select id="boxG" name="genre">
								<option>${genre}</option>
								<c:forEach var="myGenre" items="${catalog}">
									<option><c:out value="${myGenre.catalogName}" /></option>
								</c:forEach>
							</select>
						</div>
					</c:if>
				</form>

				<form name="addBook" action="<c:url value='/Acceuil/addBook'/>"
					method="get">
					<c:set var="mode" value="${nbCopy}" />
					<c:if test="${nbCopy!= null}">
						<div id="tablBook">
							<p>ISBN</p>
							<input name="txtIsbn" id="addIsbn" value="${isbn}" type="text"
								size="20">
							<p>Titre</p>
							<input name="txtTitre" id="addTitre" value="${titre}" type="text"
								size="20">
							<p>Sous Titre</p>
							<input name="txtSsTitre" id="addSsTitre" value="${subTitre}"
								type="text" size="20"> <br> <br> <label>Nbre
								exemplaire</label> <input name="NbrCopy" id="NbrCopy" value="${nbrCopy}"
								type="number" min="1" max="2"> <input name="txtNbrCopy"
								id="txtNbrCopy" value="${nbrCopy}" type="hidden"> <br>
							<br> <label>Auteur</label>
							<div class="popup">
								<input id="btnPop2" type="button" value="?"
									onclick="popUpInfo2()"> <span class="popuptext"
									id="myPopup2">selectionner l auteur dans la liste
									ci-dessous ou apres verification le creer dans les champs
									prevus</span>
							</div>
							<br> <select id="boxA" name="auteur"
								onchange="rechAuteurUp()">
								<option>Auteur</option>
								<c:forEach var="myAuteur" items="${auteur}">
									<option><c:out value="${myAuteur.fisrtName}" />
										<c:out value="${myAuteur.lastName}" />
										<c:out value="${myAuteur.dateOfBirth}" /></option>
								</c:forEach>
							</select> <input name="prenAut" placeholder="prenom" id="prenAut"
								value="${prenAut}" type="text" onchange="rechAuteurTexUp()">
							<input name="namAut" placeholder="nom" id="namAut"
								value="${namAut}" type="text" onchange="rechAuteurTexUp()">
							<input name="agAut" placeholder="Date Naissance" id="agAut" value="${agAut}"
								type="number" onchange="rechAuteurTexUp()">
							<p>Collection</p>
							<select id="boxG" name="genre">
								<option>${genre}</option>
								<c:forEach var="myGenre" items="${catalog}">
									<option><c:out value="${myGenre.catalogName}" /></option>
								</c:forEach>
							</select>
						</div>
					</c:if>
				</form>
				<br>
			</div>
			<c:set var="mode" value="${nbCopy}" />
			<c:if test="${nbCopy== null}">
				<div id="commande">
					<c:set var="mode" value="${modeSub}" />
					<c:if test="${modeSub != null}">
						<input id="btnValidAddSub" class="btn" type="button"
							value="Valider" onclick="document.addSub.submit()">
					</c:if>
					<br>
					<c:set var="mode" value="${id}" />
					<c:if test="${id!= null}">
						<input id="btnValid" class="btn" type="button" value="Valider"
							onclick="document.modifSub.submit()">
					</c:if>
					<br> <a href="<c:url value ="/Acceuil"/>"> <input
						class="btn" id="btnReset" type="button" value="reset"></a> <br>
					<c:set var="mode" value="${isbn}" />
					<c:if test="${isbn!= null}">
						<input id="btnValidBook" class="btn" type="button" value="Valider"
							onclick="document.modifBook.submit()">
						<a
							href="<c:url value ="/Acceuil/info?txtCibl1=${isbn}&txtCibl2="/>"><input
							class="btn" id="btnModifEx" type="button"
							value="Modif Nbre exemplaire"></a>
					</c:if>
				</div>
			</c:if>
			<form name="info" action="<c:url value='/Acceuil/info'/>"
				method="get">
				<input id="txtCibl1" placeholder="cible1" name="txtCibl1" value=""
					type="hidden" size="20"> <input id="txtCibl2"
					placeholder="cible2" name="txtCibl2" value="" type="hidden"
					size="20">
			</form>
			<form name="modif" action="<c:url value='/Acceuil/modif'/>"
				method="get">
				<input id="txtCibl3" placeholder="cible1" name="txtCibl1" value=""
					type="hidden" size="20"> <input id="txtCibl4"
					placeholder="cible2" name="txtCibl2" value="" type="hidden"
					size="20">
			</form>
			<c:set var="mode" value="${nbCopy}" />
			<c:if test="${nbCopy!= null}">


				<input id="btnValidAddBook" class="btn" type="button"
					value="Valider" onclick="document.addBook.submit()">
			</c:if>

		</fieldset>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tablBord.js"></script>
	<footer>
		<jsp:include page="/Footer.jsp"></jsp:include>
	</footer>
</body>
</html>