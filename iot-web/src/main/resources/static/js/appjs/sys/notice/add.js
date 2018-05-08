// ************个性化设置************
var prefix = "/iot-manage/sys/notice";
// ************个性化设置************

$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function save() {
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/save",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
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

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            type: {
                required: true
            },
            title: {
                required: true
            },
            content: {
                required: true
            }
        },
        messages: {
            type: {
                required: icon + "请选择通知类型"
            },
            title: {
                required: icon + "请输入通知标题"
            },
            content: {
                required: icon + "请输入通知内容"
            }
        }
    })
}