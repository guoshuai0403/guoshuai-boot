/**
 * Created by guoshuai on 2018/8/28 0028.
 */
$(function () {
    // 自动查询第一个表的列信息
    var first_table_name = $(".table_name:first").text();
    getColumn(first_table_name);

    // 绑定点击事件
    $(".table_name").click(function (obj) {
        console.log(obj);
        alert(obj.text());
    });
});

function getColumn(table_name) {
    $.ajax({
        url: "/column/getByTableName",
        type: "get",
        data: {tableName:table_name},
        success: function (data) {
            console.log(JSON.stringify(data));
            var columns = "";
            $.each(data.obj, function (index, col) {
                columns =  columns +
                    "<tr>"+
                    "<td>"+ col.name +"</td>"+
                    "<td>"+ col.comment  +"</td>"+
                    "<td>"+ col.dataType  +"</td>"+
                    "<td>"+ col.maxLength  +"</td>"+
                    "<td>"+ col.indexType  +"</td>"+
                    "</tr>";
            });
            $("#column_tbody").html(columns);
        },
        error: function (err) {
            console.log("err");
            console.log(err.message);
            console.log(err);
        }
    });
}