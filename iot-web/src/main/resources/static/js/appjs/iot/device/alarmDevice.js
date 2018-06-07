// ************个性化设置************
var prefix = content_path + "iot/device";
var localParams = {};
var localColumns = [
    // {
    //     checkbox: true
    // },
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
        field: 'areaCode', // 列字段名
        title: '设备地址编号' // 列标题
    },
    {
        field: 'alarmStatus', // 列字段名
        title: '预警状态', // 列标题,
        align: "center",
        formatter: function (value, row, index) {
            if (value && value === 1) {
                return '<span class="label label-danger">预警中</span>';
            } else {
                return '<span class="label label-primary">正常</span>';
            }
        }
    },
    {
        field: 'lastReportTime', // 列字段名
        title: '最新上报时间' // 列标题
    },
    {
        field: 'alarmDesc', // 列字段名
        title: '告警描述' // 列标题
    },
    {
        title: '操作',
        field: 'id',
        align: 'center',
        formatter: function (value, row, index) {
            var a = '<a class="btn btn-info btn-sm ' + s_view_h + '" href="#" title="查看设备"  mce_href="#" onclick="info(\''
                + row.id
                + '\')">查看</a> ';
            var b = '<a class="btn btn-info btn-sm ' + s_alarmConfig_h + '" href="#" title="预警配置"  mce_href="#" onclick="alarmConfig(\''
                + row.id
                + '\')">配置</a> ';
            var c = '<a class="btn btn-info btn-sm ' + s_report_h + '" href="#" title="设备消息报表"  mce_href="#" onclick="report(\''
                + row.id
                + '\')">报表</a> ';
            var d = '<a class="btn btn-success btn-sm ' + s_syncDevice_h + '" href="#" title="同步设备"  mce_href="#" onclick="syncDevice(\''
                + row.id
                + '\')">同步</a> ';
            var e = '<a class="btn btn-success btn-sm ' + s_command_h + '" href="#" title="下发指令"  mce_href="#" onclick="command(\''
                + row.id
                + '\')">指令</a> ';

            return a + b + c + d + e;
        }
    }];
var localPageName = "设备";
// ************个性化设置************

$(function () {
    load();
    setTimeout('window.location.reload()', 300000);
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
            localParams["alarmStatus"] = $('#alarmStatus').val();
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

// 查看
function info(id) {
    // iframe层
    layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        title: localPageName + "信息",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['75%', '75%'],
        content: prefix + '/info?id=' + id
    });
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
                    layer.msg("同步成功");
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

// 下发指令
function command(id) {
    // iframe层
    layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        title: localPageName + "指令信息",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['75%', '75%'],
        content: prefix + '/command?id=' + id
    });
}

// 预警配置
function alarmConfig(id) {
    // iframe层
    layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        title: localPageName + "预警配置",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['75%', '75%'],
        content: prefix + '/alarmConfig/' + id
    });
}

function report(id) {
    // iframe层
    layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        title: localPageName + "报表",
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['75%', '75%'],
        content: prefix + '/report?id=' + id
    });
}