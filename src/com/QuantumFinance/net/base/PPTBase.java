package com.QuantumFinance.net.base;

import java.io.Serializable;
import java.util.Date;

public class PPTBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9136416366901053925L;
	private int id;
	private String name;// : "PPT2",
	private int sort;// : 1,
	private int comment_or_product_id;// : 21,
	private String ppt_type;// : "product",
	private Date created_at;// : "2014-03-17T14:03:31Z",
	private Date updated_at;// : "2014-03-17T14:03:31Z",
	private String ppt_logo;// /system/ppts/ppt_logos/000/000/002/centre/%E7%88%B1%E6%8A%95%E8%B5%84.jpg?1395065011",
	private String product_or_comment_name;// : "借款进货"

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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getComment_or_product_id() {
		return comment_or_product_id;
	}

	public void setComment_or_product_id(int comment_or_product_id) {
		this.comment_or_product_id = comment_or_product_id;
	}

	public String getPpt_type() {
		return ppt_type;
	}

	public void setPpt_type(String ppt_type) {
		this.ppt_type = ppt_type;
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

	public String getPpt_logo() {
		return ppt_logo;
	}

	public void setPpt_logo(String ppt_logo) {
		this.ppt_logo = ppt_logo;
	}

	public String getProduct_or_comment_name() {
		return product_or_comment_name;
	}

	public void setProduct_or_comment_name(String product_or_comment_name) {
		this.product_or_comment_name = product_or_comment_name;
	}

}
