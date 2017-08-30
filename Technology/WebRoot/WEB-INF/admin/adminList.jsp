<%@page import="com.entites.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>数据统计</title>
</head>
<%
	User user = (User) request.getSession().getAttribute("currentUser");
	if (user == null || user.getAuth() != 1) {
		response.sendRedirect("logout");
	}
%>
<body>

	<jsp:include page="/adminTop.jsp"></jsp:include>
	<!--用户管理-->
	<div class="container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				数据统计&nbsp; <a href="taskDownload" target="_blank"><button>导出为xls</button></a>
				&nbsp;
				<button onclick="return false;" data-toggle="modal"
					data-target="#myModal">清除所有记录!</button>
			</div>
			<div class="panel-body">
				<p>清除数据的时候请再三留意!一旦删除,不能恢复!</p>
				<p>此界面显示相应的完成情况</p>
			</div>
			<!-- Table -->
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<td>编号</td>
						<td>用户</td>
						<td>完成数量</td>
						<td>管理</td>
					</tr>
				</thead>
				<c:forEach items="${requestScope.users}" var="index">
					<tr>
						<td>${index.uId}</td>
						<td>${index.uName }</td>
						<td>${index.completeNums}/${requestScope.taskCount}</td>
						<td>
							<a href="userDetail?id=${index.uId}" target="_blank"><button>详细</button></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<br>
	<br>
	<br>
	<jsp:include page="/foot.jsp"/>
	
	 <!-- 删除确认 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="user_del_info">
                        确定要删除吗?
                        <input type="hidden" value="" id="user_del_input_temp">
                    </h4>
                </div>
                <div class="modal-body">
                    一旦删除将不能撤回
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <form action="clearTask" method="post" style="float: right">
						<button type="submit" class="btn btn-primary" id="user_del_btn2">
							确定删除</button>
					</form>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
</body>
</html>