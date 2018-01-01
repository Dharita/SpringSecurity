<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${userForm['new']}">
			<h1>Add User</h1>
		</c:when>
		<c:otherwise>
			<h1>Update User</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/users" var="userActionUrl" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="userForm" action="${userActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<form:input path="name" type="text" class="form-control " id="name"
						placeholder="Name" pattern="^[A-Za-z ]{3,25}$" required="true"
						maxlength="25"
						title="Must be 3 to 25 characters long and can contain only alphabets and space" />
					<form:errors path="name" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<form:input path="email" class="form-control" id="email"
						placeholder="Email"
						required="true" maxlength="50"
						title="Must be 8 to 50 characters long and a valid email id" />
					<form:errors path="email" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="username">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<form:input path="username" class="form-control" id="username"
						placeholder="Username" pattern="^[A-Za-z0-9_]{3,25}$"
						required="true" maxlength="25"
						title="Must be a valid 3 to 25 characters long and can contain only alphanumeric characters and underscore" />
					<form:errors path="username" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="password">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<form:password path="password" class="form-control" id="password"
						placeholder="Password" pattern="^[A-Za-z0-9_]{3,25}$"
						required="true" maxlength="25"
						title="Must be 3 to 25 characters long and can contain only alphanumeric charachers and underscore" />
					<form:errors path="password" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="confirmPassword">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Confirm Password</label>
				<div class="col-sm-10">
					<form:password path="confirmPassword" class="form-control"
						id="password" placeholder="Confirm Password"
						pattern="^[A-Za-z0-9_]{3,25}$" required="true" maxlength="25"
						title="Must be 3 to 25 characters long and can contain only alphanumeric charachers and underscore" />
					<form:errors path="confirmPassword" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="gender">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton
							path="gender" value="M" /> Male
					</label> <label class="radio-inline"> <form:radiobutton
							path="gender" value="F" /> Female
					</label> <br />
					<form:errors path="gender" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="contactNumber">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Contact Number</label>
				<div class="col-sm-10">
					<form:input path="contactNumber" type="text" class="form-control "
						id="contactNumber" placeholder="Contact Number"
						pattern="^[0-9]{12}$" required="true" maxlength="12"
						title="Must be a valid 12 digit characters number" />
					<form:errors path="contactNumber" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="address">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Address</label>
				<div class="col-sm-10">
					<form:textarea path="address" rows="5" class="form-control"
						id="address" placeholder="address" maxlength="150" />
					<form:errors path="address" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="country">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Country</label>
				<div class="col-sm-5">
					<form:select path="country" class="form-control">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${countryList}" />
					</form:select>
					<form:errors path="country" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="newsletter">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Newsletter</label>
				<div class="col-sm-10">
					<div class="checkbox">
						<label> <form:checkbox path="newsletter" id="newsletter" />
						</label>
						<form:errors path="newsletter" class="control-label" />
					</div>
				</div>
			</div>
		</spring:bind>

		<spring:bind path="framework">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Web Frameworks</label>
				<div class="col-sm-10">
					<form:checkboxes path="framework" items="${frameworkList}"
						element="label class='checkbox-inline'" />
					<br />
					<form:errors path="framework" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="skill">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Java Skills</label>
				<div class="col-sm-5">
					<form:select path="skill" items="${javaSkillList}" multiple="true"
						size="5" class="form-control" />
					<form:errors path="skill" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${userForm['new']}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>