package com.QuantumFinance.net.base;

import java.io.Serializable;

public class PostCommentBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6522315293923455159L;
	private Integer user_id;
	private Integer comment_id;
	private String content;
	private Integer to_user_id;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(Integer to_user_id) {
		this.to_user_id = to_user_id;
	}

}
