package com.QuantumFinance.net.base;

import java.io.Serializable;

public class PostSocialLoginBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6522315293923455159L;
	private String uid;
	private String name;
	private String sex;

	public PostSocialLoginBase(String uid, String name, String sex) {
		this.uid = uid;
		this.name = name;
		this.sex = sex;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
