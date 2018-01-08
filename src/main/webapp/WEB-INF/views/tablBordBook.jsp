<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table id="bookTable">
 
</table>


<table border="1" id="TableBook">

	<c:forEach var="myBook" items="${livre}">
		<tr>

			<td><c:out value="${myBook.title}" /></td>
			<td><c:out value="${myBook.subtitle}" /></td>
			<td><c:out value="${myBook.author}" /></td>
			<td><c:out value="${myBook.genre}" /></td>
			<td class="tdButton2"><input onchange="getIsbn(${myBook.isbn});highLight2(this);" type="radio"
				id="iChoix" name="choixBook" value="${myBook.isbn}">
		</tr>
	</c:forEach>
</table>