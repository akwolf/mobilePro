package com.jnzy.mdm.bean;
/**
 * 这个类2.0用到的.
 *
 */
public enum BusinessDataCode {
	SUCCESS("C10000","操作成功"),
	ARGS_ERROR("C10001","非法请求"),
	NO_DATA("C10002","暂无数据"),
	VERSION_CANNOT_USE("C10003","对不起，请升级客户端..."),
	SERVICE_EXCEPTION("C10004","服务器异常，请稍后重试"),//接口报错
	FAIL("C10005","请求失败，请稍后重试"),//第三方接口返回为空
	ERROR_DATA("C10006","对不起，数据有误"),//数据库中数据有误

	PUSH_ERROR("P10001","推送失败"),
	PUSH_ERROR_ERROR("P10002","数据有误"),
	PUSH_NO_NEW_STRATEGY("P10003","该设备号没有新的策略"),

	OTHER_IMSI_ERROR("O10001","IMSI变更"),

	USER_DISABLE("U10001","该用户被禁用"),
	USER_DELETE("U10002","该用户被删除"),
	BIG_DATA("U10003","数据量过大"),
	SCAN_FAIL("U10004","病毒扫描失败"),
	USER_NO_DATE("U10005","该设备信息未申报或尚未导入平台，请联系管理员”"),
	;
	///////////////////////////////////////////////////////////////////ƒƒ
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private BusinessDataCode(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
}
