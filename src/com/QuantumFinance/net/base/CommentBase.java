package com.QuantumFinance.net.base;

import java.io.Serializable;
import java.util.Date;

public class CommentBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1264117172925342941L;
	private int id;// 5,
	private int user_id;// 1,
	private int comment_id;// 3,
	private int to_user_id;// null,
	private String content;// "sawevsdawedsaew",
	private Date created_at; // "2014-03-14T07:55:51Z",
	private Date updated_at; // "2014-03-14T07:55:51Z",
	private String user_avatar;// : "/avatars/thumb/missing.png"
	private String user_name;// "孙汕锟",
	private String to_user_name;// "",
	private String to_user_avatar;
	private String comment_name;// "test"

	public String getTo_user_avatar() {
		return to_user_avatar;
	}

	public void setTo_user_avatar(String to_user_avatar) {
		this.to_user_avatar = to_user_avatar;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getTo_user_name() {
		return to_user_name;
	}

	public void setTo_user_name(String to_user_name) {
		this.to_user_name = to_user_name;
	}

	public String getComment_name() {
		return comment_name;
	}

	public void setComment_name(String comment_name) {
		this.comment_name = comment_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(int to_user_id) {
		this.to_user_id = to_user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getUser_avatar() {
		return user_avatar;
	}

	public void setUser_avatar(String user_avatar) {
		this.user_avatar = user_avatar;
	}

}
