<%@page import="com.entites.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<link href="https://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet">
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<title>管理首页</title>
<%
	User user = (User) request.getSession().getAttribute("currentUser");
	if (user == null || user.getAuth() != 1) {
		response.sendRedirect("logout");
	}
%>
</head>
<body>
	<jsp:include page="/adminTop.jsp"></jsp:include>
	<div class="container">
		<!--任务管理-->
		<div class="container">
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="panel-heading">
					任务管理&nbsp;
					<button class="btn btn-default btn-sm" id="project_add">新增项目</button>
				</div>
				<div class="panel-body">
					<p>在这里添加不同的项目的内容~</p>
					<p>这里完成之后在添加修改相应的工作人员哦~</p>
				</div>
				<!-- Table -->
				<table class="table table-striped table-hover" id="project_table">
					<thead>
						<tr>
							<td>编号</td>
							<td>任务</td>
							<td>管理</td>
						</tr>
					</thead>
					<tbody id="project_list">
						<c:forEach items="${requestScope.tasks}" var="index" varStatus="x">
							<tr>
								<td>${index.tId}</td>
								<td>${index.tName}</td>
								<td>
									<button class="btn btn-warning btn-sm del_btn">删除</button>
									<button class="btn btn-primary btn-sm update_btn">更改</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!--用户管理-->
		<div class="container">
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="panel-heading">
					用户管理&nbsp;
					<button class="btn btn-default btn-sm" id="user_add">新增普通用户</button>
					<a href="downLoadUserList" target="_blank">
						<button
							class="btn btn-default btn-sm">导出excle</button>
					</a>
					<button
							class="btn btn-default" data-toggle="modal" data-target="#userAddList">批量导入</button>
				</div>
				<div class="panel-body">
					<p>在这里添加需要参与的普通用户~</p>
				</div>
				<!-- Table -->
				<table class="table table-striped table-hover" id="user_table">
					<thead>
						<tr>
							<td>编号</td>
							<td>名称</td>
							<td>密码</td>
							<td>管理</td>
						</tr>
					</thead>
					<tbody id="user_list">
						<c:forEach items="${requestScope.users}" var="index" varStatus="x">
							<tr>
								<td>${index.uId}</td>
								<td>${index.uName}</td>
								<td>${index.uPassword}</td>
								<td>
									<button class="btn btn-warning btn-sm del_btn">删除</button>
									<button class="btn btn-primary btn-sm update_btn">更改</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!--工作人员管理-->
		<div class="container">
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="panel-heading">
					工作人员管理&nbsp;
					<button class="btn btn-default btn-sm" id="stuff_add">新增工作人员</button>
					<a href="workerManagerDownload" target="_blank"><button
							class="btn btn-default btn-sm">导出excle</button></a>
				</div>
				<div class="panel-body">
					<p>完成项目添加之后在这里添加工作人员哦~</p>
				</div>
				<!-- Table -->
				<table class="table table-striped table-hover" id="stuff_table">
					<thead>
						<tr>
							<td>编号</td>
							<td>名称</td>
							<td>密码</td>
							<td>负责任务</td>
							<td>管理</td>
						</tr>
					</thead>
					<tbody id="stuff_list">
						<c:forEach items="${requestScope.workers}" var="index"
							varStatus="x">
							<tr>
								<td>${index.uId}</td>
								<td>${index.uName }</td>
								<td>${index.uPassword}</td>
								<td data-id="2">${index.task}</td>
								<td>
									<button class="btn btn-warning btn-sm del_btn">删除</button>
									<button class="btn btn-primary btn-sm update_btn">更改</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 项目更改 模态框（Modal） -->
		<div class="modal fade" id="projectUpdateModel" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="user_update_info">项目更改</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="project_update_task">任务:</label> <input type="text"
								class="form-control" id="project_update_task">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary"
							id="project_update_btn" data-id="0">提交</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<!-- 项目添加 模态框（Modal） -->
		<div class="modal fade" id="projectAddModel" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">项目添加</h4>
					</div>
					<div class="modal-body">
						<form class="">
							<div class="input-group">
								<span class="input-group-addon">任务</span>
								<div class="form-group">
									<input id="project_add_name" class="form-control" type="text"
										placeholder="" name="username" value=""
										aria-describedby="basic-addon1">
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="project_add_btn">
							添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>


		<!-- 用户添加 模态框（Modal） -->
		<div class="modal fade" id="userAddModel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">用户添加</h4>
					</div>
					<div class="modal-body">
						<form class="">
							<div class="input-group">
								<span class="input-group-addon">用户名</span>
								<div class="form-group">
									<input id="user_add_name" class="form-control" type="text"
										placeholder="" name="username" value=""
										aria-describedby="basic-addon1">
								</div>
							</div>
							<div class="input-group">
								<span class="input-group-addon">密码</span>
								<div class="form-group">
									<input id="user_add_pwd" class="form-control" type="text"
										placeholder="密码为空则生成随机密码" name="username" value=""
										aria-describedby="basic-addon1">
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="user_add_btn">
							添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<!-- 用户更改 模态框（Modal） -->
		<div class="modal fade" id="userUpdateModel" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">用户更改</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="project_update_task">用户名:</label> <input type="text"
								class="form-control" id="user_update_name">
						</div>
						<div class="form-group">
							<label for="project_update_task">密码:</label> <input type="text"
								class="form-control" id="user_update_pwd">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="user_update_btn"
							data-id="0">提交</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>

		<!-- 工作人员添加 模态框（Modal） -->
		<div class="modal fade" id="stuffAddModel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">工作人员添加</h4>
					</div>
					<div class="modal-body">
						<form class="">
							<div class="form-group">
								<label for="project_update_task">用户名</label> <input
									id="stuff_add_name" class="form-control" type="text"
									placeholder="" name="username" value=""
									aria-describedby="basic-addon1">
							</div>
							<div class="form-group">
								<label for="project_update_task">密码:</label> <input
									id="stuff_add_pwd" class="form-control" type="text"
									placeholder="密码为空则生成随机密码" name="username" value=""
									aria-describedby="basic-addon1">
							</div>
							<div class="form-group">
								<label for="stuff_task">负责任务</label>
								<div class="form-group">
									<select class="form-control" id="stuff_task">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="stuff_add_btn">
							添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<!-- 工作人员更改 模态框（Modal） -->
		<div class="modal fade" id="stuffUpdateModel" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">工作人员更改</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="project_update_task">用户名</label> <input
								id="stuff_update_name" class="form-control" type="text"
								placeholder="" name="username" value=""
								aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="project_update_task">密码:</label> <input
								id="stuff_update_pwd" class="form-control" type="text"
								placeholder="" name="username" value=""
								aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<label for="stuff_task">负责任务</label>
							<div class="form-group">
								<select class="form-control" id="stuff_update_task">
									
								</select>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary"
							id="stuff_update_btn" data-id="0">提交</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	
	
	<!-- 用户批量导入 模态框（Modal） -->
		<div class="modal fade" id="userAddList" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">用户批量上传</h4>
					</div>
					<form action="uploadUserList" method="post" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label for="project_update_task">注意:上传用户名称即可(密码自动生成).见下图.</label>
							<br>
							<img alt="" src="<%=request.getContextPath()%>/img/demo.png" width="100">
						</div>
						<div class="form-group">
							<label for="project_update_task">文件上传(只接受xls格式文件)</label> <input name="file" type="file"
								class="form-control" id="file" accept=".xls">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary" id="user_update_btn"
							data-id="0">上传</button>
					</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	<br>
	<br>
	<br>
	<jsp:include page="/foot.jsp"/>
	<script src="<%=request.getContextPath()%>/js/admin.js"></script>
</body>

</html>