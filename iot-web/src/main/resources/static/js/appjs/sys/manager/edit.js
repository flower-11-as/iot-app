// 以下为官方示例
$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: content_path + "sys/manager/update",
            data: $('#signupForm').serialize(),// 你的formid
            async: false,
            error: function () {
                layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code === '0000') {
                    parent.layer.msg("保存成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    }
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入姓名"
            }
        }
    })
}