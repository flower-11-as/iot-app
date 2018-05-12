// ************个性化设置************
var prefix = content_path + "sys/noticeRecord";
var localParams = {};
var localColumns = [
    {
        field: 'id', // 列字段名
        title: '序号' // 列标题
    },
    {
        field: 'title', // 列字段名
        title: '标题' // 列标题
    },
    {
        field: 'type', // 列字段名
        title: '通知类型', // 列标题
        align: 'center',
        formatter: function (value, row, index) {
            if (value === 1) {
                return '<span class="label label-info">系统通知</span>';
            } else if (value === 2) {
                return '<span class="label label-danger">系统预警</span>';
            }
        }
    },
    {
        field: 'createTime', // 列字段名
        title: '发送时间' // 列标题
    },
    {
        field: 'status', // 列字段名
        title: '是否已读', // 列标题
        align: 'center',
        formatter: function (value, row, index) {
            if (value === 0) {
                return '<span class="label label-info">未读</span>';
            } else if (value === 1) {
                return '<span class="label label-success">已读</span>';
            }
        }
    },
    {
        field: 'readTime', // 列字段名
        title: '查看时间' // 列标题
    },
    {
        title: '操作',
        field: 'id',
        align: 'center',
        formatter: function (value, row, index) {
            var a = '<a class="btn btn-success btn-sm ' + s_check_h + '" href="#" title="查看通知"  mce_href="#" onclick="check(\''
                + row.id
                + '\')"><i class="fa fa-comment-o">查看通知</i></a> ';
            return a;
        }
    }];
var localPageName = "通知";
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
        // search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        queryParams: function (params) {
            localParams["limit"] = params.limit;
            localParams["offset"] = params.offset;
            localParams["title"] = $("#title").val();
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
function check(id) {
    // iframe层
    layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        title: '查看' + localPageName,
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['75%', '75%'],
        content: prefix + '/check/' + id,
        success: function(elem) {
            reLoad();
        }
    });
}