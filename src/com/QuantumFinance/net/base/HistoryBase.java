package com.QuantumFinance.net.base;

import java.util.Date;

public class HistoryBase extends RecommendBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2515389225352710845L;
	private Date updated_at;

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
