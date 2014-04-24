package com.QuantumFinance.net.base;

public class PostLoginBase {
	
		private String password;
		private String email;

		public PostLoginBase(String email, String password) {
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
