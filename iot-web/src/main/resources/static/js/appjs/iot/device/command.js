// ************个性化设置************
var prefix = content_path + "iot/device";
// ************个性化设置************

$(function () {
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
        url: prefix + "/sendCommand",
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
            // devSerial: {
            //     required: true
            // },
            // name: {
            //     required: true
            // },
            // devType: {
            //     required: true
            // },
            // connectPointId: {
            //     required: true
            // }
        },
        messages: {
            // devSerial: {
            //     required: icon + "请输入序列号"
            // },
            // name: {
            //     required: icon + "请输入设备名称"
            // },
            // devType: {
            //     required: icon + "请选择产品型号"
            // },
            // connectPointId: {
            //     required: icon + "请选择设备连接点"
            // }
        }
    })
}