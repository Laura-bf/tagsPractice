<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglibs/datos.tld" prefix="datos"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<datos:conexion cadena="jdbc:mysql://localhost:3306/ejemplo"
			driver="com.mysql.cj.jdbc.Driver" usuario="curso"
			clave="Cursocurso1;">

			<tr>
				<td>NOMBRE</td>
				<td>APELLIDO</td>
				<td>EDAD</td>
			</tr>
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