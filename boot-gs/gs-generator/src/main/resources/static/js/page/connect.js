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
                console.log(data);
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
                console.log(data);
            },
            error: function (err) {
                console.log(err);
            }
        });
    });
});