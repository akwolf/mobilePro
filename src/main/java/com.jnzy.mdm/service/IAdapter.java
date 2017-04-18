package com.jnzy.mdm.service;

/**
 * 数据适配器。
 *
 */
public interface IAdapter {

	/**
	 * 根据签名，做相应的适配
	 * @param sign 签名
	 * @param s 被适配对象
	 * @return
	 */
	public Object adapt(String sign,Object s)throws Exception;
	
}
