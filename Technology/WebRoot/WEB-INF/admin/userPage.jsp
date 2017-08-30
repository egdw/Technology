<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>完成详细内容</title>
</head>

<body>
	<jsp:include page="/normalTop.jsp"></jsp:include>
	<div class="container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">详细完成项目</div>
			<div class="panel-body">
				<p>下面是该用户完成的详细的内容</p>
			</div>
			<!-- Table -->
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<td>编号</td>
						<td>名称</td>
						<td>密码</td>
						<td>完成</td>
						<td>完成时间</td>
					</tr>
				</thead>
				<c:forEach items="${requestScope.tasks}" var="index" varStatus="x">
					<tr>
						<td>${x.index+1}</td>
						<td>${index.tName}</td>
						<td>${index.completePassword }</td>
						<td>${index.iscomplete}</td>
						<c:if test="${empty index.completeTime}">
							<td>还未完成</td>
						</c:if>
						<c:if test="${!empty index.completeTime}">
							<td><fmt:formatDate value="${index.completeTime}" pattern="HH:mm:ss"/></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

	<div class="alert alert-success" role="alert"
		style="text-align: center">已经完成${requestScope.completeTimes}个项目.总计${requestScope.tasksCount}个项目.</div>
	<jsp:include page="/foot.jsp"/>
</body>
</html>
