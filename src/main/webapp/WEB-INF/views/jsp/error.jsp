<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="fragments/header.jsp" />

<body>

	<div class="container">
		<h1>${headerText}</h1>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h3>Hello ${pageContext.request.userPrincipal.name}, you do not
				have permission to access this page.</h3>
		</c:if>
	</div>

	<jsp:include page="fragments/footer.jsp" />

</body>
</html>