// ************个性化设置************
var prefix = content_path + "iot/deviceBatch";
var localParams = {};
var localColumns = [
    {
        field: 'taskId', // 列字段名
        title: '任务id' // 列标题
    },
    {
        field: 'progress', // 列字段名
        title: '处理进度(%)' // 列标题
    },
    {
        field: 'status', // 列字段名
        title: '状态', // 列标题
        formatter: function (value, row, index) {
            if (value === 0) {
                return '<span class="label label-info">处理中</span>';
            } else if(value === 1) {
                return '<span class="label label-success">处理成功</span>';
            } else if(value === 2) {
                return '<span class="label label-danger">处理失败</span>';
            } else {
                return '-';
            }
        }
    },
    {
        field: 'createTime', // 列字段名
        title: '创建时间' // 列标题
    }];
var localPageName = "设备批量";
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

// 下载批量添加模板
function downDemo() {
    window.location.href = content_path + '导入设备模板.xlsx';
}

// 批量导入
function batchAdd() {
    $('#batchFile').click();
}

// 提交表单
function submitForm() {
    var formData = new FormData();
    var file = $('#batchFile')[0].files[0];
    formData.append("file", file);

    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/batchAdd",
        data: formData,// 你的formid
        // 告诉jQuery不要去处理发送的数据
        processData : false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        error: function (request) {
            layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code === '0000') {
                parent.layer.msg("提交成功");
                reLoad();
            } else {
                layer.alert(data.msg, {
                    title: '提示',
                    icon: 2
                });
            }
        }
    });
}