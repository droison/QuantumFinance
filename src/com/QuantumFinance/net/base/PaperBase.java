package com.QuantumFinance.net.base;

import java.io.Serializable;
import java.util.Date;

public class PaperBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517976277758455252L;
	private int id;// 3,
	private String title;// "test",
	private String lable;// "1",
	private String synopsis;// "1",
	private String content;// "11反反复复",
	private int view_count;// 4,
	private int credit_number;// 4,
	private int comments;// 4,
	private Date created_at;// "2014-03-10T15:33:17Z",
	private Date updated_at;// "2014-03-14T07:55:51Z",
	private String logo;// "/system/comments/logos/000/000/003/centre/20140103_174223.jpg?1394681068"

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public int getCredit_number() {
		return credit_number;
	}

	public void setCredit_number(int credit_number) {
		this.credit_number = credit_number;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
