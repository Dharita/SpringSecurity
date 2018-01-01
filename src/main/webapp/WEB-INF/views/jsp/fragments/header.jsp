<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>User Management</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/users/list" var="urlHome" />
<spring:url value="/users/add" var="urlAddUser" />
<spring:url value="/logout" var="urlLogout" />

<nav class="navbar navbar-inverse ">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">User Management</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlAddUser}">Add User</a></li>
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<li class="active"><a href="javascript:document.getElementById('logout').submit()">Logout</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>

<!-- csrt for log out-->
<form action="${urlLogout}" method="post" id="logout">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

