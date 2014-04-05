package com.QuantumFinance.net.base;

public class RegBase {
	private User user;

	public RegBase(String email, String password, String password_confirmation) {
		this.user = new User(email, password, password_confirmation);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	class User {
		private String password_confirmation;
		private String password;
		private String email;

		public User(String email, String password, String password_confirmation) {
			this.email = email;
			this.password = password;
			this.password_confirmation = password_confirmation;
		}

		public String getPassword_confirmation() {
			return password_confirmation;
		}

		public void setPassword_confirmation(String password_confirmation) {
			this.password_confirmation = password_confirmation;
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
