// ************个性化设置************
var prefix = "/iot-manage/iot/device";
var localParams = {};
var localColumns = [
    {
        checkbox: true
    },
    {
        field: 'devSerial', // 列字段名
        title: '序列号' // 列标题
    },
    {
        field: 'name', // 列字段名
        title: '设备名称' // 列标题
    },
    {
        field: 'devType', // 列字段名
        title: '产品型号' // 列标题
    },
    {
        field: 'serverId', // 列字段名
        title: 'IoT创建者' // 列标题
    },
    {
        field: 'createTime', // 列字段名
        title: '创建时间' // 列标题
    },
    {
        title: '操作',
        field: 'id',
        align: 'center',
        formatter: function (value, row, index) {
            var e = '<a class="btn btn-success btn-sm ' + s_edit_h + '" href="#" title="编辑设备"  mce_href="#" onclick="edit(\''
                + row.id
                + '\')"><i class="fa fa-edit"></i></a> ';
            var f = '<a class="btn btn-info btn-sm ' + s_view_h + '" href="#" title="查看设备"  mce_href="#" onclick="view(\''
                + row.id
                + '\')"><i class="fa fa-camera-retro"></i></a> ';
            var g = '<a class="btn btn-danger btn-sm ' + s_remove_h + '" href="#" title="删除设备"  mce_href="#" onclick="remove(\''
                + row.id
                + '\')"><i class="fa fa-remove"></i></a> ';
            var h = '<a class="btn btn-warning btn-sm ' + s_syncDevice_h + '" href="#" title="同步设备"  mce_href="#" onclick="syncDevice(\''
                + row.id
                + '\')"><i class="fa fa-refresh"></i></a> ';
            return e + f + h + g;
        }
    }];
var localPageName = "设备";
// ************个性化设置************

$(function () {
    load();
});

// 加载
function load() {
    $('#exampleTable').bootstrapTable({
        method: 'post', // 服务器数据的请求方式 get or post
        url: prefix + "/list", // 服务器数据的加载地址
        // showRefresh : true,
        // showToggle : true,
        // showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        // search: true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        queryParams: function (params) {
            localParams["limit"] = params.limit;
            localParams["offset"] = params.offset;
            localParams["devSerial"] = $('#devSerial').val();
            localParams["name"] = $('#name').val();
            return localParams;
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: localColumns,
        onLoadSuccess: function (data) {
            if (data.code && data.code !== '0000') {
                layer.alert(data.msg, {
                    title: '提示',
                    icon: 2
                });
            }
        }
    });
}

// 刷新列表
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

// 同步IoT devices
function syncDevices() {
    layer.confirm("确定要同步" + localPageName + "吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = [];
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.ajax({
            type: 'POST',
            url: prefix + '/syncDevices',
            success: function (data) {
                if (data.code === '0000') {
                    layer.msg("同步" + localPageName + "成功");
                    reLoad();
                } else {
                    layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    }, function () {
    });
}

// 添加
function add() {
    // iframe层
    layer.open({
        type: 2,
        title: '增加' + localPageName,
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '350px'],
        content: prefix + '/add'
    });
}

// 修改
function edit(id) {
    // iframe层
    layer.open({
        type: 2,
        title: '编辑' + localPageName,
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '350px'],
        content: prefix + '/edit?id=' + id
    });
}

// 查看
function view(id) {
    // iframe层
    layer.open({
        type: 2,
        title: localPageName + "信息",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '600px'],
        content: prefix + '/view?id=' + id
    });
}

// 删除
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (data) {
                if (data.code === '0000') {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    })
}

// 同步
function syncDevice(id) {
    layer.confirm('确定要同步选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/syncDevice",
            type: "post",
            data: {
                'id': id
            },
            success: function (data) {
                if (data.code === '0000') {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.alert(data.msg, {
                        title: '提示',
                        icon: 2
                    });
                }
            }
        });
    })
}

