<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="fragments/header.jsp" />

<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.message {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #43a943;
	background-color: #dfe0de;
	border-color: #ccebcc;
}
</style>

<div class="container">

	<h1>Login</h1>
	<br> <br>
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>

	<c:if test="${not empty msg}">
		<div class="message">${msg}</div>
	</c:if>

	<spring:url value="/login" var="userActionUrl" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="userForm" action="${userActionUrl}">

		<spring:bind path="username">
			<div class="form-group">
				<label class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<form:input path="username" class="form-control" id="username"
						placeholder="Username" required="true" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="password">
			<div class="form-group">
				<label class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<form:password path="password" class="form-control" id="password"
						placeholder="Password" required="true" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn-lg btn-primary pull-right">Login</button>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="fragments/footer.jsp" />

</body>
</html>