// ************个性化设置************
var prefix = content_path + "sys/notice";
var managerIds = [];
// ************个性化设置************

$().ready(function () {
    getManagerTreeData();
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
        send();
    }
});

function getAllSelectNodes() {
    var ref = $('#managerTree').jstree(true); // 获得整个树

    managerIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组

    $("#managerTree").find(".jstree-undetermined").each(function (i, element) {
        managerIds.push($(element).closest('.jstree-node').attr("id"));
    });
}

function getManagerTreeData() {
    $.ajax({
        type: "GET",
        url: prefix + "/managerTree",
        success: function (managerTree) {
            loadManagerTree(managerTree);
        }
    });
}

function loadManagerTree(managerTree) {
    $('#managerTree').jstree({
        'core': {
            'data': managerTree
        },
        "checkbox": {
            "three_state": true,
        },
        "plugins": ["wholerow", "checkbox"]
    });
}

function send() {
    if (!managerIds || managerIds.length === 0) {
        layer.msg("请选择管理员", {'icon': 2});
        return;
    }

    $('#managerIds').val(managerIds);
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/sendNotice",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code === '0000') {
                parent.layer.msg("发送成功");
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
        },
        messages: {
        }
    })
}