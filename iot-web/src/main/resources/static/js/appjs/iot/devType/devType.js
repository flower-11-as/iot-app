// ************个性化设置************
var prefix = content_path + "iot/devType";
var localParams = {};
var localColumns = [
    // {
    //     checkbox: true
    // },
    {
        field: 'id', // 列字段名
        title: '序号' // 列标题
    },
    {
        field: 'serverId', // 列字段名
        title: '平台用户名' // 列标题
    },
    {
        field: 'devType', // 列字段名
        title: '产品型号' // 列标题
    },
    {
        field: 'delFlag', // 列字段名
        title: '是否已停用', // 列标题
        align: 'center',
        formatter: function (value, row, index) {
            if (value === 0) {
                return '<span class="label label-primary">未停用</span>';
            } else if (value === 1) {
                return '<span class="label label-danger">已停用</span>';
            }
        }
    },
    {
        title: '操作',
        field: 'id',
        align: 'center',
        formatter: function (value, row, index) {
            var d = '<a class="btn btn-primary btn-sm ' + s_info_h + '" href="#" title="产品信息"  mce_href="#" onclick="info(\''
                + row.id
                + '\')"><i class="fa fa-edit"></i></a> ';
            return d;
        }
    }];
var localPageName = "产品型号";
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
            localParams["devType"] = $('#devType').val();
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

// 同步IoT devTypes
function syncDevTypes() {
    layer.confirm("确定要同步" + localPageName + "吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = [];
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.ajax({
            type: 'POST',
            url: prefix + '/syncDevTypes',
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

// 产品信息
function info(id) {
    // iframe层
    layer.open({
        type: 2,
        title: localPageName + "信息",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '600px'],
        content: prefix + '/info?id=' + id
    });
}