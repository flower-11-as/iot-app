var prefix = "/iot-manage/sys/param";
$(function() {
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        update();
    }
});

function update() {
    $.ajax({
        cache : true,
        type : "POST",
        url : prefix + "/update",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            layer.alert("Connection error");
        },
        success : function(data) {
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
function validate() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            name : {
                required : true
            },
            type : {
                required : true
            }
        },
        messages : {
            name : {
                required : icon + "请输入菜单名"
            },
            type : {
                required : icon + "请选择菜单类型"
            }
        }
    })
}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            paramKey: {
                required: true
            },
            paramValue: {
                required: true
            },
            group: {
                required: true
            }
        },
        messages: {
            paramKey: {
                required: icon + "请输入参数"
            },
            paramValue: {
                required: icon + "请输入参数值"
            },
            group: {
                required: icon + "请输入分组"
            }
        }
    })
}