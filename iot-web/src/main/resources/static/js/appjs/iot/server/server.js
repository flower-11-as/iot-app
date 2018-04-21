// ************个性化设置************
var prefix = "/iot-manage/iot/server";
var localParams = {};
var localColumns = [
    {
        checkbox: true
    },
    {
        field: 'id', // 列字段名
        title: '序号' // 列标题
    },
    {
        field: 'serverId', // 列字段名
        title: '连接平台id' // 列标题
    },
    {
        field: 'description', // 列字段名
        title: '连接平台描述' // 列标题
    }];
var localPageName = "模板";
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
        search: true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        // sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        // "server"
        queryParams: function (params) {
            // localParams["limit"] = params.limit;
            // localParams["offset"] = params.offset;
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

// 同步IoT servers
function syncServer() {
    layer.confirm("确定要同步Iot连接平台吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = [];
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.ajax({
            type: 'POST',
            url: prefix + '/syncServers',
            success: function (data) {
                if (data.code === '0000') {
                    layer.msg("同步Iot连接平台成功");
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