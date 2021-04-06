package com.bs.pipe.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 所有的方法都可用此返回类型，统一返回类型
 *
 */
public class ResObject {
	private Integer status; // 返回状态
	
	private String msg; // 原因
	
	private Object data; // 返回值

	public ResObject() {
	}

	public ResObject(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static ResObject bind(Integer status, String msg) {
		return new ResObject(status, msg, null);
	}
	
	public static ResObject bind(Integer status, String msg, Object data) {
		return new ResObject(status, msg, data);
	}

	public static ResObject ok() {
		return new ResObject(200, "ok", null);
	}
	
	public static ResObject ok(Object obj){
		return new ResObject(200,"ok",obj);
	}
	
	public static ResObject ok(String msg, Object obj2){
		return new ResObject(200,msg,obj2);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResObject other = (ResObject) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResObject [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

	public static ResObject bind(ResObject res, String msg, Object data) {
		res.setStatus(400);
		if(StringUtils.isNotBlank(msg)){
			if(StringUtils.isNotBlank(res.getMsg())){
				res.setMsg(res.getMsg() + "," + msg);
			}else{
				res.setMsg(msg);
			}
		}
		if(data != null){
			if(res.getData() != null){
				res.setData(res.getData() + "," + data);
			}else{
				res.setData(data);
			}
		}
		return res;
	}
	
}
