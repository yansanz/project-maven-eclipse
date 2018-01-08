<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id ="pdp">
	<label >Copyright © 2017</label>
		<a href="<c:url value ="?contact=ok"/>"><input  id="btnContact"  type="button"  value="Contact"></a>
	<label id="contact">${contact}</label>
	<a href="<c:url value ="?infLeg=ok"/>"><input  id="btnInfLegal"  type="button"  value="Info legale"></a>
	<label id="contact">${infoLegal}</label>
	<a href="<c:url value ="?condUtil=ok"/>"><input  id="btnCondUtil"  type="button"  value="Condition Utilisation"></a>
	<label id="contact">${condUtil}</label>
	
	</div>
</body>
