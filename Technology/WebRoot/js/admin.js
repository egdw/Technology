
// 接口 /user
// 用户添加
$("#user_add").on("click", function () {
  $("#userAddModel").modal("show");
});
$("#user_add_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "user",
    data: {
      username: $("#user_add_name").val(),
      password: $("#user_add_pwd").val()
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      // 失败返回全部为空
      if (data !== "") {
        $("#userAddModel").modal('hide');
        swal("添加成功!", "该用户添加成功！", "success");
        var newProj = '<tr>' +
          '<td>'+ data.uId +'</td>' +
          '<td>'+ data.uName +'</td>' +
          '<td>'+ data.uPassword +'</td>' +
          '<td>' +
          '<button class="btn btn-warning btn-sm del_btn">删除</button>' +
          '<button class="btn btn-primary btn-sm update_btn">更改</button>' +
          '</td>' +
          '</tr>';
        $("#user_list").append(newProj);
      } else {
        $("#userAddModel").modal('hide');
        swal("添加失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#userAddModel").modal('hide');
      swal("添加失败！", "发生了一些小错误...", "error");
    }
  });
});
// 用户管理
$("#user_table").on("click", "button", function (e) {
  var $this = $(e.target);
  // 获取当前项的id
  var id = $this.parent().parent().find("td").eq(0).html();
  console.log(id);
  if ($this.hasClass("del_btn")) {
    swal({
        title: "删除？",
        text: "删除后将无法恢复",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        cancelButtonText: "取消",
        confirmButtonText: "确认删除",
        closeOnConfirm: false
      },
      function () {
        // 项目 删除
        $.ajax({
          type: "POST",
          url: "user",
          data: {
            id: id,
            _method: 'DELETE',
          },
          dataType: "json",
          cache: false,
          success: function (data) {
            if (data.code === "success") {
              swal("删除成功!", "该任务已经被删除！", "success");
              // 移除当前tr行
              $this.parent().parent().remove();
            } else {
              swal("删除失败！", "发生了一些小错误...", "error");
            }
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            swal("删除失败！", "发生了一些小错误...", "error");
          }
        });
      });
  } else if ($this.hasClass("update_btn")) {
    $('#user_update_name').val($this.parent().parent().find("td").eq(1).html());
    $('#user_update_pwd').val($this.parent().parent().find("td").eq(2).html());
    $('#user_update_btn').data("id", id);
    $("#userUpdateModel").modal({
      backdrop: 'static',
      keyboard: false
    });
  }
});
// 用户修改
$("#user_update_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "user",
    data: {
      id: $('#user_update_btn').data("id"),
      username: $('#user_update_name').val(),
      password: $('#user_update_pwd').val(),
      _method: 'PUT'
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      if (data.code === "success") {
        $("#userUpdateModel").modal('hide');
        swal("成功!", "修改成功！", "success");
       window.location.reload();
      } else {
        $("#userUpdateModel").modal('hide');
        swal("修改失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#userUpdateModel").modal('hide');
      swal("修改失败！", "发生了一些小错误...", "error");
    }
  });
});











// 接口 /task
// 绑定 任务管理 按钮事件
$("#project_table").on("click", "button", function (e) {
  var $this = $(e.target);
  // 获取当前项的id
  var id = $this.parent().parent().find("td").eq(0).html();
  console.log(id);
  if ($this.hasClass("del_btn")) {
    swal({
        title: "删除？",
        text: "删除后将无法恢复",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        cancelButtonText: "取消",
        confirmButtonText: "确认删除",
        closeOnConfirm: false
      },
      function () {
        // 项目 删除
        $.ajax({
          type: "POST",
          url: "task",
          data: {
            id: id,
            _method: 'DELETE'
          },
          dataType: "json",
          cache: false,
          success: function (data) {
            if (data.code === "success") {
              swal("删除成功!", "该任务已经被删除！", "success");
              // 移除当前tr行
              $this.parent().parent().remove();
            } else {
              swal("删除失败！", "发生了一些小错误...", "error");
            }
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            swal("删除失败！", "发生了一些小错误...", "error");
          }
        });
      });
  } else if ($this.hasClass("update_btn")) {
    $('#project_update_task').val($this.parent().parent().find("td").eq(1).html());
    $('#project_update_btn').data("id", id);
    $("#projectUpdateModel").modal({
      backdrop: 'static',
      keyboard: false
    });
  }
});
// 任务修改
$("#project_update_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "task",
    data: {
      tId: $('#project_update_btn').data("id"),
      tName: $('#project_update_task').val(),
      _method: 'PUT',
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      if (data.code === "success") {
        $("#projectUpdateModel").modal('hide');
        swal("成功!", "修改成功！", "success");
        window.location.reload();
      } else {
        $("#projectUpdateModel").modal('hide');
        swal("修改失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#projectUpdateModel").modal('hide');
      swal("修改失败！", "发生了一些小错误...", "error");
    }
  });
});
// 任务添加
$("#project_add").on("click", function () {
  $("#projectAddModel").modal("show");
});
$("#project_add_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "task",
    data: {
      username: $("#project_add_name").val()
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      // 失败返回全部为空
      if (data !== "") {
        $("#projectAddModel").modal('hide');
        swal("添加成功!", "该任务添加成功！", "success");
        var newProj = '<tr>' +
          '<td>'+ data.tId +'</td>' +
          '<td>'+ data.tName +'</td>' +
          '<td>' +
          '<button class="btn btn-warning btn-sm del_btn">删除</button>' +
          '<button class="btn btn-primary btn-sm update_btn">更改</button>' +
          '</td>' +
          '</tr>';
        $("#project_list").append(newProj);
      } else {
        $("#projectAddModel").modal('hide');
        swal("添加失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#projectAddModel").modal('hide');
      swal("添加失败！", "发生了一些小错误...", "error");
    }
  });
});





// 接口 /workerManager
// 绑定 工作人员管理 按钮事件
$("#stuff_table").on("click", "button", function (e) {
  var $this = $(e.target);
  // 获取当前项的id
  var id = $this.parent().parent().find("td").eq(0).html();
  console.log(id);
  if ($this.hasClass("del_btn")) {
    swal({
        title: "删除？",
        text: "删除后将无法恢复",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        cancelButtonText: "取消",
        confirmButtonText: "确认删除",
        closeOnConfirm: false
      },
      function () {
        // 项目 删除
        $.ajax({
          type: "POST",
          url: "workerManager",
          data: {
            id: id,
            _method: 'DELETE',
          },
          dataType: "json",
          cache: false,
          success: function (data) {
            if (data.code === "success") {
              swal("删除成功!", "该任务已经被删除！", "success");
              // 移除当前tr行
              $this.parent().parent().remove();
            } else {
              swal("删除失败！", "发生了一些小错误...", "error");
            }
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            swal("删除失败！", "发生了一些小错误...", "error");
          }
        });
      });
  } else if ($this.hasClass("update_btn")) {
    $('#stuff_update_name').val($this.parent().parent().find("td").eq(1).html());
    $('#stuff_update_pwd').val($this.parent().parent().find("td").eq(2).html());
    var options = $('#stuff_update_task option');
    var curId = $this.parent().parent().find("td").eq(3).data("id");
    console.log(options, curId);
//    for(var i=0; i<options.length; i++) {
//      console.log(options[i].value, options[i].value == curId);
//      if(options[i].value == curId) {
//        console.log(options[i].selected);
//        options[i].selected = true;
//      }
//    }
    
    //ajax 获取列表 循环 判断如果id值相等，则设置为 selected属性

    $.ajax({
      type: "GET",
      url: "getAllTask",
      dataType: "json",
      cache: false,
      success: function(data) {
        if (data !== "") {
          var options = "";
          $.each(data, function(index, item) {
            if (item.tId == curId) {
              options += '<option value="' + item.tId + '" selected>' + item.tName + '</option>';
            } else {
              options += '<option value="' + item.tId + '">' + item.tName + '</option>';
            }
          })
          $('#stuff_update_task').html(options);
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        console.log(textStatus);
      }
    });
    

    $('#stuff_update_btn').data("id", id);
    $("#stuffUpdateModel").modal({
      backdrop: 'static',
      keyboard: false
    });
  }
});
// 工作人员修改
$("#stuff_update_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "workerManager",
    data: {
      id: $('#stuff_update_btn').data("id"),
      username: $('#stuff_update_name').val(),
      password: $('#stuff_update_pwd').val(),
      task: $('#stuff_update_task').val(),
      _method: 'PUT'
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      if (data.code === "success") {
        $("#stuffAddModel").modal('hide');
        swal("成功!", "修改成功！", "success");
        window.location.reload();
      } else {
        $("#stuffAddModel").modal('hide');
        swal("修改失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#stuffAddModel").modal('hide');
      swal("修改失败！", "发生了一些小错误...", "error");
    }
  });
});
// 工作人员添加
$("#stuff_add").on("click", function () {
  $("#stuffAddModel").modal("show");
  $.ajax({
      type: "GET",
      url: "getAllTask",
      dataType: "json",
      cache: false,
      success: function(data) {
        if (data !== "") {
          var options = "";
          $.each(data, function(index, item) {
              options += '<option value="' + item.tId + '">' + item.tName + '</option>';
          })
          $('#stuff_task').html(options);
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        console.log(textStatus);
      }
    });

});
$("#stuff_add_btn").on("click", function () {
  $.ajax({
    type: "POST",
    url: "workerManager",
    data: {
      username: $("#stuff_add_name").val(),
      password: $("#stuff_add_pwd").val(),
      task: $("#stuff_task").val()
    },
    dataType: "json",
    cache: false,
    success: function (data) {
      // 失败返回全部为空
      if (data !== "") {
        $("#stuffAddModel").modal('hide');
        swal("添加成功!", "该任务添加成功！", "success");
        var newProj = '<tr>' +
          '<td>'+ data.uId +'</td>' +
          '<td>'+ data.uName +'</td>' +
          '<td>'+ data.uPassword +'</td>' +
          '<td data-id="'+ data.task +'">';
        // 获取任务的名称
        $.ajax({
          type: "POST",
          url: "getTaskName",
          data: {
            id: data.task,
          },
          dataType: "json",
          cache: false,
          success: function (source) {
            if (source !== "") {
              newProj +=
                source.tName +'</td>' +
                '<td>' +
                '<button class="btn btn-warning btn-sm del_btn">删除</button>' +
                '<button class="btn btn-primary btn-sm update_btn">更改</button>' +
                '</td>' +
                '</tr>';
              $("#stuff_list").append(newProj);
            }  else {
              $("#stuffAddModel").modal('hide');
              swal("添加失败！", "发生了一些小错误...", "error");
            }
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            $("#stuffAddModel").modal('hide');
            swal("添加失败！", "发生了一些小错误...", "error");
          }
        });

      } else {
        $("#stuffAddModel").modal('hide');
        swal("添加失败！", "发生了一些小错误...", "error");
      }
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(textStatus);
      $("#stuffAddModel").modal('hide');
      swal("添加失败！", "发生了一些小错误...", "error");
    }
  });
});
