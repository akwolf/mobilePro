package com.jnzy.mdm.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.comm.exception.AppRuntimeException;
import com.jnzy.mdm.comm.web.http.PacketType;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Locale;


public class ServiceProxyResponse implements ServletResponse,Serializable {
	private String code;
	private String message;
	private Object data;

	private ServletResponse response;

	public ServiceProxyResponse() {
	}

	public ServiceProxyResponse(ServletResponse response) {
		this.response = response;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@JsonIgnore
	public ServletResponse getResponse() {
		if (response == null) {
			throw new AppRuntimeException("Something happened which is ServletResponse is null !!! ");
		}
		return response;
	}

	public void setResponse(ServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 参数错误
	 * @return
	 */
	public static ServiceProxyResponse argsError(){
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(BusinessDataCode.ARGS_ERROR.getCode());
		response.setMessage(BusinessDataCode.ARGS_ERROR.getDesc());
		return response;
	}
	
	/**
	 * 服务器异常
	 * @return
	 */
	public static ServiceProxyResponse serviceException(){
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(BusinessDataCode.SERVICE_EXCEPTION.getCode());
		response.setMessage(BusinessDataCode.SERVICE_EXCEPTION.getDesc());
		return response;
	}
	

	/**
	 * 错误返回 只带code
	 * 
	 * @param businessDataCode
	 * @return
	 */
	public static ServiceProxyResponse error(BusinessDataCode businessDataCode) {
		return error(businessDataCode, null);
	}

	/**
	 * 错误返回 带code data
	 * 
	 * @param businessDataCode
	 * @param data
	 * @return
	 */
	public static ServiceProxyResponse error(BusinessDataCode businessDataCode,
			Object data) {
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(businessDataCode.getCode());
		response.setMessage(businessDataCode.getDesc());
		response.setData(data);

		return response;
	}

	public static String exceptionStack(Throwable throwable) {
		return new StringBuilder(throwable.toString()).append(" at line ")
				.append(throwable.getStackTrace()[0].getLineNumber())
				.append(" of ")
				.append(throwable.getStackTrace()[0].getFileName()).toString();
	}

	/**
	 * 返回 带返回数据 code message
	 * 
	 * @param data
	 * @return
	 */
	public static ServiceProxyResponse backSuccess(
			BusinessDataCode businessDataCode, String message, Object data) {
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(businessDataCode.getCode());
		response.setData(data);
		response.setMessage(message);
		return response;
	}

	/**
	 * 成功返回 带返回数据
	 * 
	 * @param data
	 * @return
	 */
	public static ServiceProxyResponse success(Object data) {
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(BusinessDataCode.SUCCESS.getCode());
		response.setData(data);
		response.setMessage(BusinessDataCode.SUCCESS.getDesc());
		return response;
	}

	/**
	 * 成功返回 带返回数据
	 * 
	 * @param data
	 * @return
	 */
	public static ServiceProxyResponse success(Object data, String message) {
		ServiceProxyResponse response = new ServiceProxyResponse();
		response.setCode(BusinessDataCode.SUCCESS.getCode());
		response.setData(data);
		response.setMessage(message);
		return response;
	}

	/**
	 * 只返回成功状态码
	 * 
	 * @return
	 */
	public static ServiceProxyResponse success() {
		return success(null);
	}

	public static class PageServiceProxyResponse extends ServiceProxyResponse {
		private long totalCount;

		public long getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(long totalCount) {
			this.totalCount = totalCount;
		}

		public static PageServiceProxyResponse error(
				BusinessDataCode businessDataCode, Object data, long totalCount) {
			PageServiceProxyResponse response = new PageServiceProxyResponse();
			response.setCode(businessDataCode.getCode());
			response.setData(data);
			response.setMessage(null);
			response.setTotalCount(totalCount);
			return response;
		}

		/**
		 * 
		 * @param data
		 * @param totalCount
		 * @return
		 */
		public static PageServiceProxyResponse success(Object data,
				long totalCount) {
			PageServiceProxyResponse response = new PageServiceProxyResponse();
			response.setCode(BusinessDataCode.SUCCESS.getCode());
			response.setData(data);
			response.setTotalCount(totalCount);
			response.setMessage(null);
			return response;
		}
	}

	@JsonIgnore
	public String getCharacterEncoding() {
		return getResponse().getCharacterEncoding();
	}

	@JsonIgnore
	public String getContentType() {
		return getResponse().getContentType();
	}

	@JsonIgnore
	public ServletOutputStream getOutputStream() throws IOException {
		return getResponse().getOutputStream();
	}

	@JsonIgnore
	public PrintWriter getWriter() throws IOException {
		return getResponse().getWriter();
	}

	public void setCharacterEncoding(String charset) {
		getResponse().setCharacterEncoding(charset);
	}

	public void setContentLength(int len) {
		getResponse().setContentLength(len);
	}

	public void setContentType(String type) {
		getResponse().setContentType(type);
	}

	public void setBufferSize(int size) {
		getResponse().setBufferSize(size);
	}

	@JsonIgnore
	public int getBufferSize() {
		return getResponse().getBufferSize();
	}

	public void flushBuffer() throws IOException {
		getResponse().flushBuffer();
	}

	public void resetBuffer() {
		getResponse().resetBuffer();
	}

	@JsonIgnore
	public boolean isCommitted() {
		return getResponse().isCommitted();
	}

	public void reset() {
		getResponse().reset();
	}

	public void setLocale(Locale loc) {
		getResponse().setLocale(loc);
	}

	@JsonIgnore
	public Locale getLocale() {
		return getResponse().getLocale();
	}

	/**
	 * 以流的形式写出数据体
	 * 
	 * @throws IOException
	 */
	public void writeData() throws IOException {
		if (response != null) {
			OutputStream output = response.getOutputStream();
			ServiceProxyResponseWriter writer = new ServiceProxyResponseWriter(
					output);
			writer.write((byte[]) data, PacketType.BINARY);
			writer.flush();
		}
	}

	@Override
	public String toString() {
		StringBuilder toStr = new StringBuilder("code:").append(this.code)
				.append(",message:").append(this.message).append(",data:")
				.append(this.data);
		return toStr.toString();
	}
}
