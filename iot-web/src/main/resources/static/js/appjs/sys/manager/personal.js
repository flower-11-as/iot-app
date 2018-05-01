var prefix = "/iot-manage/sys/manager";
$(function () {
    validateRule();
});
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    if ($("#basicInfoForm").valid()) {
        $.ajax({
            cache: true,
            type: "POST",
            url: prefix + "/updatePersonal",
            data: $('#basicInfoForm').serialize(),
            async: false,
            error: function (request) {
                layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code === '0000') {
                    parent.layer.msg("更新成功");
                } else {
                    parent.layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    }
});

$("#pwd_save").click(function () {
    if ($("#modifyPwd").valid()) {
        $.ajax({
            cache: true,
            type: "POST",
            url: prefix + "/resetPersonalPwd",
            data: $('#modifyPwd').serialize(),
            async: false,
            error: function (request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code === '0000') {
                    parent.layer.msg("更新成功");
                } else {
                    parent.layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    }
});