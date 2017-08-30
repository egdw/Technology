<%@page import="com.entites.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>设置</title>
</head>
<body>
	<c:if test="${sessionScope.currentUser.auth == 1}">
		<jsp:include page="/adminTop.jsp"></jsp:include>
	</c:if>
	<c:if test="${sessionScope.currentUser.auth != 1}">
		<jsp:include page="/normalTop.jsp"></jsp:include>
	</c:if>
	<div class="container">
		<div class="panel panel-success">
			<div class="panel-body">密码修改</div>
			<div class="panel-footer">
				<form action="changePassword" method="post">
					<input type="hidden" name="_method" value="PUT"> <input
						type="password" name="oldPassword" class="form-control"
						placeholder="旧密码" aria-describedby="sizing-addon1"> <input
						name="newPassword" type="password" class="form-control"
						placeholder="新密码" aria-describedby="sizing-addon1"> <input
						name="newPassword2" type="password" class="form-control"
						placeholder="新密码" aria-describedby="sizing-addon1"> <input
						type="submit" value="确认修改" class="form-control"
						aria-describedby="sizing-addon1">
				</form>
			</div>
		</div>
		<c:if test="${!empty requestScope.error}">
			<div class="alert alert-warning" role="alert">${requestScope.error}</div>
		</c:if>
	</div>
	<jsp:include page="/foot.jsp"/>
</body>
</html>