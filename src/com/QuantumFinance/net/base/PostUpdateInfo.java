package com.QuantumFinance.net.base;

import java.io.File;

import android.graphics.Bitmap;

public class PostUpdateInfo {
	private String token;
	private Bitmap avatar_file;
	private String name;
	private String mobile_phone;
	private String email;

	@Override
	public String toString() {
		return "token=" + token + ";name=" + name + ";mobile_phone=" + mobile_phone + ";email=" + email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Bitmap getAvatar_file() {
		return avatar_file;
	}

	public void setAvatar_file(Bitmap avatar_file) {
		this.avatar_file = avatar_file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
