package com.QuantumFinance.net.base;

import java.util.Date;

public class LoginOrRegResult {
	public static class Login {
		private int id;
		private String name;
		private String avatar;
		private String email;
		private String authentication_token;
		private Date last_login_time;
		private boolean weibo;
		private boolean qq;
		private String sex;
		private String message;

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isWeibo() {
			return weibo;
		}

		public void setWeibo(boolean weibo) {
			this.weibo = weibo;
		}

		public boolean isQq() {
			return qq;
		}

		public void setQq(boolean qq) {
			this.qq = qq;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAuthentication_token() {
			return authentication_token;
		}

		public void setAuthentication_token(String authentication_token) {
			this.authentication_token = authentication_token;
		}

		public Date getLast_login_time() {
			return last_login_time;
		}

		public void setLast_login_time(Date last_login_time) {
			this.last_login_time = last_login_time;
		}

	}

	public static class Reg {
		private boolean result;
		private String private_token;
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public String getPrivate_token() {
			return private_token;
		}

		public void setPrivate_token(String private_token) {
			this.private_token = private_token;
		}

	}
}
