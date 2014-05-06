package com.QuantumFinance.net.base;

public class PostEva {
	private String token;
	private Content fas;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Content getFas() {
		return fas;
	}

	public void setFas(Content fas) {
		this.fas = fas;
	}
	
	public PostEva(String token,String name,String professional, String annual_income, String fxph, String tzph, String zczk, String jtzk){
		this.fas = new Content(name, professional, annual_income, fxph, tzph, zczk, jtzk);
		this.token = token;
	}

	class Content {
		
		public Content(String name,String professional, String annual_income, String fxph, String tzph, String zczk, String jtzk){
			this.name = name;
			this.professional = professional;
			this.annual_income = annual_income;
			this.fxph = fxph;
			this.tzph = tzph;
			this.zczk = zczk;
			this.jtzk = jtzk;
		}
		
		private String name;
		private String professional;
		private String annual_income;
		private String fxph;
		private String tzph;
		private String zczk;
		private String jtzk;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProfessional() {
			return professional;
		}
		public void setProfessional(String professional) {
			this.professional = professional;
		}
		public String getAnnual_income() {
			return annual_income;
		}
		public void setAnnual_income(String annual_income) {
			this.annual_income = annual_income;
		}
		public String getFxph() {
			return fxph;
		}
		public void setFxph(String fxph) {
			this.fxph = fxph;
		}
		public String getTzph() {
			return tzph;
		}
		public void setTzph(String tzph) {
			this.tzph = tzph;
		}
		public String getZczk() {
			return zczk;
		}
		public void setZczk(String zczk) {
			this.zczk = zczk;
		}
		public String getJtzk() {
			return jtzk;
		}
		public void setJtzk(String jtzk) {
			this.jtzk = jtzk;
		}
		
	}
}
