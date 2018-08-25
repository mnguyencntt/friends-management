<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>Home</title>
</head>

<body>
	<div align="center">
		<h1>Welcome my app!</h1>
		<h2>
			Hello "<b><c:out value="${pageContext.request.remoteUser}"></c:out></b>"
		</h2>
	</div>
</body>
</html>
