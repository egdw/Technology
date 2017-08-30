<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>登录</title>
</head>
<body style="background: #EEEEEE">
	<jsp:include page="/normalTop.jsp"></jsp:include>

	<div class="jumbotron">
		<center><h1>欢迎参加科技文化节</h1></center>
		<center><p>同学们可根据了解的默认密码登陆系统</p></center>
		<center><p>建议登陆成功后立即修改密码</p></center>
		<div class="container">
			<div class="panel panel-success">
		<div class="panel-footer">
			<form action="login" method="post">
				<input type="text" class="form-control" name="username"
					placeholder="请输入您的用户名" aria-describedby="sizing-addon1"> <input
					type="password" class="form-control" name="password"
					placeholder="请输入您的密码" aria-describedby="sizing-addon1"> <input
					type="submit" value="登录" class="form-control"
					aria-describedby="sizing-addon1">
			</form>
		</div>
		<c:if test="${!empty requestScope.error}">
			<div class="alert alert-warning" role="alert">${requestScope.error}</div>
		</c:if>
		</div>
		
	</div>
	</div>


	
	<jsp:include page="/foot.jsp" />
</body>
</html>