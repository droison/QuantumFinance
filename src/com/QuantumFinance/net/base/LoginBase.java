package com.QuantumFinance.net.base;

public class LoginBase {
	private User user;

	public LoginBase(String email, String password) {
		this.user = new User(email, password);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	class User {
		private String password;
		private String email;

		public User(String email, String password) {
			this.email = email;
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}
}
