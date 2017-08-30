<%@page import="com.entites.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>工作人员界面</title>
</head>
<%
	User user = (User) request.getSession().getAttribute("currentUser");
	if (user == null || user.getAuth() != 2) {
		response.sendRedirect("logout");
	}
%>
<body>
	<jsp:include page="/normalTop.jsp"></jsp:include>
	<div class="container">
		<div class="alert alert-success" role="alert">工作人员辛苦啦!您需要处理的任务是:${requestScope.task}</div>

		<div class="panel panel-default">
			<div class="panel-body">任务密码输入区:</div>
			<div class="panel-footer">
				<div class="row">
					<div class="col-lg-6">
						<form id="completeForm" action="taskComplete" method="post">
							<div class="input-group">
								<input type="text" class="form-control" name="password"
									placeholder="请输入用户的任务密码..."> <span
									class="input-group-btn">
									<button class="btn btn-default" id="completeBtn" type="submit">任务完成!</button>
								</span>
							</div>
						</form>
						<!-- /input-group -->
					</div>
					<!-- /.col-lg-6 -->
				</div>
				<!-- /.row -->
				<c:if test="${!empty requestScope.error}">
					<div class="alert alert-warning" role="alert">${requestScope.error}</div>
				</c:if>
			</div>
		</div>
	</div>
	<jsp:include page="/foot.jsp" />
</body>
</html>
