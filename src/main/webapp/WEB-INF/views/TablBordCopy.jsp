<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table border="1" id="iTablCopy">
	<c:forEach var="myBook" items="${exemplaire}">
		<tr>
			<td><c:out value="${myBook.isbn}" /></td>
			<td><c:out value="${myBook.title}" /></td>
			<td><c:out value="${myBook.numCopy}" /></td>
			<td><c:set var="dispo" /> <c:if test="${myBook.dispo == 1}">
					<c:out value="dispo" />
				</c:if> <c:if test="${myBook.dispo == 0}">
					<c:out value="emprunt en cours" />
				</c:if></td>
			<td class="tdButton"><input onchange="getIdCopy(${myBook.numCopy});highLight(this);" type="radio"
				id="iChoixCopy" name="choix" value="${myBook.numCopy}">
		</tr>
	</c:forEach>
</table>
