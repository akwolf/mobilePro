package com.jnzy.mdm.comm.web.http;

/**
 * 数据流中支持的包定义
 * 
 * @author wangkuan
 *
 */
public enum PacketType {
	
	/**
	 * 普通文本
	 */
	STRING,

	/**
	 * JSON 文本
	 */
	JSON,

	/**
	 * 二进制数据流
	 */
	BINARY;
}
