<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<style>
#users {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 50%;
	font-size: 20;
}

#users td, #users th {
	border: 1px solid #ddd;
	padding: 8px;
}

#users tr:nth-child(even) {
	background-color: #f2f2f2;
}

#users tr:hover {
	background-color: #ddd;
}

#users th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	background-color: #e2faff;
	color: white;
}
</style>

<div class="container">

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

	<h1>User Detail</h1>
	<br />

	<table id="users">
		<tr>
			<td style="font-weight: bold">Name</td>
			<td>${user.name}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Email</td>
			<td>${user.email}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Gender</td>
			<td>${user.gender}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Contact Number</td>
			<td>${user.contactNumber}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Address</td>
			<td>${user.address}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Country</td>
			<td>${user.country}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Web Frameworks</td>
			<td>${user.framework}</td>
		</tr>

		<tr>
			<td style="font-weight: bold">Skill</td>
			<td>${user.skill}</td>
		</tr>

	</table>
</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>