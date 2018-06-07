// ************个性化设置************
var prefix = content_path + "iot/device";
// ************个性化设置************

$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code === '0000') {
                parent.layer.msg("编辑成功");
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
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入设备名称"
            }
        }
    })
}

function serverChange() {
    var serverId = $("#connectPointId").val();
    if (!serverId) {
        $("#serviceMode").html('<option value="">--选择产品型号--</option>');
        return;
    }

    $.ajax({
        type: "POST",
        url: prefix + "/serverChange",
        data: {"serverId": serverId},// 你的formid
        async: false,
        error: function (request) {
            layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code === '0000') {
                var serviceModes = data.data;
                var html = '<option value="">--选择产品型号--</option>';
                $.each(serviceModes, function (i, v) {
                    html += '<option value="'+ v.serviceMode +'">'+ v.serviceMode +'</option>';
                });
                $("#serviceMode").html(html);
            } else {
                layer.alert(data.msg, {
                    title: '提示',
                    icon: 2
                });
            }
        }
    });
}