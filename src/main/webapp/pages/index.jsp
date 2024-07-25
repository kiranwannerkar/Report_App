<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<!-- this is bootstrap css -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">
</head>
<body>
	<!-- div container we r using for responsive margin -->

	<div class="container">
		<h3 class="pb-3 pt-3">Report Application</h3>
		<!-- pb -> padding bottom pt-> Top -->


		<form:form action="search" modelAttribute="search" method="POST">

			<table>
				<tr>
					<td>Plan Name:</td>
					<td><form:select path="planName">
							<!-- we r binding the form data with java class -->
							<form:option value="">-Select-</form:option>
							<form:options items="${names}" />
						</form:select></td>

					<td>Plan Status:</td>
					<td><form:select path="planStatus">
							<!-- we r binding the form data with java class -->
							<form:option value="">-Select-</form:option>
							<form:options items="${statuses}" />
						</form:select></td>
					<td>Gender</td>
					<td><form:select path="gender">
							<!-- we r binding the form data with java class -->
							<form:option value="">-Select-</form:option>
							<form:option value="Male">Male</form:option>
							<form:option value="Fe-Male">Fe-Male</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td>Start Date:</td>
					<td><form:input path="planStartDate" type="date" /></td>

					<td>End Date:</td>
					<td><form:input type="date" path="planEndDate" /></td>
				</tr>
				<tr>
					<td><a href="/" class="btn btn-secondary">Reset</a></td>  
					<td><input type="submit" value="Search"
						class="btn btn-primary"></td>  <!-- this will give u search btn in color form -->
				</tr>
			</table>

		</form:form>

		<hr />
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>Holder Name</th>
					<th>Gender</th>
					<th>Plan Name</th>
					<th>Plan Status</th>
					<th>Start Date</th>
					<th>End Date</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${plans}" var="plan" varStatus="index">
					<tr>
						<td>${index.count }</td>  <!-- to get serial no of id  -->
						<td>${plan.citizenName }</td>
						<td>${plan.gender  }</td>
						<td>${plan.planName }</td>
						<td>${plan.planStatus }</td>
						<td>${plan.planStartDate }</td>
						<td>${plan.planEndDate }</td>
					</tr>
					<!-- if there is record then row getting created -->

				</c:forEach>
					<tr>
					<td colspan="7" text align="center">
					<c:if test="${empty plans}"> 
			           No Records Found
			        </c:if>
			        </td>
			        
			        </tr>
			        

			</tbody>
		</table>
		
		<hr />

		Export: <a href="excel">Excel</a> <a href="pdf">PDF</a>
		<!-- this is hyperlink -->
	</div>


	<!-- this is bootstrap javaScript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous">
		
	</script>
</body>
</html>