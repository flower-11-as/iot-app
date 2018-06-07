package com.scrawl.iot.paper.http.constans;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants.java
 * @author Jason.Zhang
 * @email mail2jason_zhang@163.com
 * @date 2018年2月5日 上午10:14:12
 * @version 1.0
 */
public class IotConstant {
	public static Map<String,String> resultCodeExplain;

	public static String SUCCESS_CODE = "0";

	// api
	public static String LOGIN_AUTH = "/server/login";
	public static String SERVER = "/service-config/get-iotservers";
	public static String SERVICE_MODE = "/service-config/get-iotservicemode";
	public static String DEV_TYPES = "/devices/list-devtypes";
	public static String DEV_TYPE = "/dev-manage/query-devType";
	public static String DEVICE_ALL = "/devices/list-devices";
    public static String DEVICE = "/devices/query-device-allinfo";
    public static String REG_DEVICE = "/devices/reg-device";
	public static String UPDATE_DEVICE = "/devices/update-device";
	public static String DEL_DEVICE = "/devices/del-device";
	public static String QUERY_SUBSCRIBE = "/query-subscribe-service-address";
	public static String UN_SUBSCRIBE = "/unsubscribe-service-address";
	public static String SUBSCRIBE = "/subscribe-service-address";
	public static String COMMAND = "/dev-control/urt-command";

	static{
		resultCodeExplain=new HashMap<>();
		resultCodeExplain.put("0", "操作成功");
		resultCodeExplain.put("1000", "数据库访问异常");
		resultCodeExplain.put("1001", "不合法的设备类型");
		resultCodeExplain.put("1002", "不合法的设备类别");
		resultCodeExplain.put("1003", "设备类型无绑定智能业务");
		resultCodeExplain.put("1004", "重复注册相同的设备");
		resultCodeExplain.put("1005", "未注册的设备");
		resultCodeExplain.put("1006", "未为非直连设备匹配到适合的设备连接点（例如未找到IOT平台）");
		resultCodeExplain.put("1007", "认证失败。错误原因可能是：1、用户名或密码错误；2、Token校验失败或已过期。");
		resultCodeExplain.put("1008", "业务未在智能业务表注册");
		resultCodeExplain.put("1009", "业务在服务地址表中没有记录");
		resultCodeExplain.put("1010", "不合法的用户ID");
		resultCodeExplain.put("1014", "不合法的接口传参");
		resultCodeExplain.put("1015", "注册设备中的设备类型不属于该用户");
		resultCodeExplain.put("1016", "用户无操作该设备的权限");
		resultCodeExplain.put("1200", "设备控制命令错误");
		resultCodeExplain.put("1201", "命令commandId繁忙");
		resultCodeExplain.put("1400", "数据引擎服务异常");
		resultCodeExplain.put("1502", "SessionId不合法");
		resultCodeExplain.put("1503", "不合法的请求体");
		resultCodeExplain.put("1504", "无法查询出与设备序列号相应的数据");
		resultCodeExplain.put("1505", "未找到可用的连接点Id");
		resultCodeExplain.put("1601", "请求头认证信息未正确填写");
		resultCodeExplain.put("1602", "CallbackUrl未配置");
		resultCodeExplain.put("40009", "编解码过程出现错误");
		resultCodeExplain.put("100000", "IOT平台返回错误（IOTGateway模块返回的错误都映射成此错误码）");
		resultCodeExplain.put("100001", "IOT平台http连接错误");
		resultCodeExplain.put("100101", "内部服务组件调用失败");
		resultCodeExplain.put("100102", "服务异常");
		resultCodeExplain.put("100103", "内部路由失败");
		resultCodeExplain.put("200000", "入口参数格式错误");
	}
}
