var prefix = content_path + "sys/manager";

$().ready(function () {
    initEndUserNameTree();
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
        save();
    }
});

function getAllSelectNodes() {
    var ref = $('#endUserNameTree').jstree(true); // 获得整个树
    var nodes = ref.get_selected(true);
    $("#endUserNameTree").find(".jstree-undetermined").each(function (i, element) {
        var node = ref.get_node($(element));
        node && node['id'] && nodes.push(node);
    });

    var rs = {};
    $.each(nodes, function(i, e) {
        // account
        var node = nodes[i];
        if ('#' === node.parent) {
            rs[node.id] || (rs[node.id] = []);
        }
        // endUserName
        else {
            rs[node.parent] || (rs[node.parent] = []);
            rs[node.parent].push(node.id);
        }
    });
    $('#accountEndUserNames').val(JSON.stringify(rs));
}

function initEndUserNameTree() {
    $.ajax({
        type: "GET",
        url: prefix + "/endUserNameTree",
        success: function (endUserNameTree) {
            loadEndUserNameTree(endUserNameTree);
        }
    });
}

function loadEndUserNameTree(endUserNameTree) {
    $('#endUserNameTree').jstree({
        'core': {
            'data': endUserNameTree
        },
        "checkbox": {
            "three_state": true,
        },
        "plugins": ["wholerow", "checkbox"]
    });
}

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
            name: {
                required: true
            },
            username: {
                required: true,
                minlength: 2,
                remote: {
                    url: prefix + "/validUsername", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        username: function () {
                            return $("#username").val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 6
            },
            status: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入姓名"
            },
            username: {
                required: icon + "请输入您的用户名",
                minlength: icon + "用户名必须两个字符以上",
                remote: icon + "用户名已经存在"
            },
            password: {
                required: icon + "请输入您的密码",
                minlength: icon + "密码必须6个字符以上"
            },
            status: {
                required: icon + "请选择状态"
            }
        }
    })
}