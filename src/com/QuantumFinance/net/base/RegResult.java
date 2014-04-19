package com.QuantumFinance.net.base;

import java.util.Date;

public class RegResult {
	private String authentication_token;
	private String avatar_content_type;
	private String avatar_file_name;
	private String avatar_file_size;
	private Date avatar_updated_at;
	private Date created_at;
	private String email;
	private int id;
	private boolean is_admin;
	private String name;
	private String sex;
	private Date updated_at;

	public String getAuthentication_token() {
		return authentication_token;
	}

	public void setAuthentication_token(String authentication_token) {
		this.authentication_token = authentication_token;
	}

	public String getAvatar_content_type() {
		return avatar_content_type;
	}

	public void setAvatar_content_type(String avatar_content_type) {
		this.avatar_content_type = avatar_content_type;
	}

	public String getAvatar_file_name() {
		return avatar_file_name;
	}

	public void setAvatar_file_name(String avatar_file_name) {
		this.avatar_file_name = avatar_file_name;
	}

	public String getAvatar_file_size() {
		return avatar_file_size;
	}

	public void setAvatar_file_size(String avatar_file_size) {
		this.avatar_file_size = avatar_file_size;
	}

	public Date getAvatar_updated_at() {
		return avatar_updated_at;
	}

	public void setAvatar_updated_at(Date avatar_updated_at) {
		this.avatar_updated_at = avatar_updated_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
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

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	private RegError error;

	public RegError getError() {
		return error;
	}

	public void setError(RegError error) {
		this.error = error;
	}

	class RegError {
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}
}
