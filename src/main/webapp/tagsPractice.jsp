<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/lib/datos.tld" prefix="datos"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- este link es lo del bootstrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
</head>
<body>

	<table class="table table-bordered table-striped">
		<datos:conexion cadena="jdbc:mysql://localhost:3306/ejemplo"
			clave="Cursocurso1;" usuario="curso"
			driver="com.mysql.cj.jdbc.Driver">
			<thead>
				<tr>
					<th>NOMBRE</th>
					<th>APELLIDO</th>
					<th>EDAD</th>
				</tr>
			</thead>
			<datos:resultado sql="SELECT nombre,apellido,edad FROM personas">
				<tr>
					<td><datos:valor campo="1" /></td>
					<td><datos:valor campo="2" /></td>
					<td><datos:valor campo="3" /></td>
				</tr>
			</datos:resultado>
		</datos:conexion>
	</table>
</body>
</html>