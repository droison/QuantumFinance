package com.QuantumFinance.db;

import java.io.Serializable;

public class DbAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7131039161880696253L;
	private int id;
	private String username;
	private String token;
	private String face;
	private String phone;
	private String email;
	private boolean bind_weibo;
	private boolean bind_qq;
	private String sex;
	private int userid;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isBind_weibo() {
		return bind_weibo;
	}

	public void setBind_weibo(boolean bind_weibo) {
		this.bind_weibo = bind_weibo;
	}

	public boolean isBind_qq() {
		return bind_qq;
	}

	public void setBind_qq(boolean bind_qq) {
		this.bind_qq = bind_qq;
	}

}
