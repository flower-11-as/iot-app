var prefix = content_path + "sys/role";
var menuIds;
$(function () {
    getMenuTreeData();
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
        update();
    }
});

function loadMenuTree(menuTree) {
    console.log(JSON.stringify(menuTree));
    $('#menuTree').jstree({
        "plugins": ["wholerow", "checkbox"],
        'core': {
            'data': menuTree
        },
        "checkbox": {
            //"keep_selected_style" : false,
            //"undetermined" : true
            //"three_state" : false,
            //"cascade" : ' up'
        }
    });
    $('#menuTree').jstree('open_all');
}

function getAllSelectNodes() {
    var ref = $('#menuTree').jstree(true); // 获得整个树
    menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
    $("#menuTree").find(".jstree-undetermined").each(function (i, element) {
        var menuId = $(element).closest('.jstree-node').attr("id");
        menuId > 0 && menuIds.push(menuId);
    });
}

function getMenuTreeData() {
    var id = $('#id').val();
    $.ajax({
        type: "GET",
        url: content_path + "sys/menu/tree/" + id,
        success: function (data) {
            loadMenuTree(data);
        }
    });
}

function update() {
    $('#menuIds').val(menuIds);
    var role = $('#signupForm').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/update",
        data: role, // 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
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
            roleName: {
                required: true
            }
        },
        messages: {
            roleName: {
                required: icon + "请输入角色名"
            }
        }
    });
}