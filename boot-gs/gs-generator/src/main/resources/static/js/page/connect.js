/**
 * Created by guoshuai on 2018/8/28 0028.
 */
$(function () {
    // 设置连库参数
    $("#connect_form_submit").click(function () {
        $.ajax({
            url: "/connect/set",
            type: "post",
            data: $("#connect_form").serialize(),
            success: function (data) {
                if (data.code == 200) {
                    // 设置成功, 跳转页面
                    window.location.href = "/table";
                } else {
                    alert(data.msg);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    // 测试数据库连接是否正常
    $("#connect_test").click(function () {

        $.ajax({
            url: "/connect/test",
            type: "post",
            data: $("#connect_form").serialize(),
            success: function (data) {
                if (data.obj == true) {
                    alert("连接成功");
                } else {
                    alert("连接失败");
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });
});