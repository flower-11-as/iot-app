var prefix = content_path + "sys/manager";

// 以下为官方示例
$().ready(function () {
    initEndUserNameTree();
    validateRule();
});

function getAllSelectNodes() {
    var ref = $('#endUserNameTree').jstree(true); // 获得整个树
    var nodes = ref.get_selected(true);
    $("#endUserNameTree").find(".jstree-undetermined").each(function (i, element) {
        var node = ref.get_node($(element));
        node && node['id'] && nodes.push(node);
    });
    console.log(nodes);

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
    var managerId = $('#id').val();
    $.ajax({
        type: "GET",
        url: prefix + "/endUserNameTree/"+ managerId,
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

$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
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