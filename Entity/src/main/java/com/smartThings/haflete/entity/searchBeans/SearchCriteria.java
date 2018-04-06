package com.smartThings.haflete.entity.searchBeans;

import java.io.Serializable;

public abstract class SearchCriteria implements Serializable {
	
	private static final long serialVersionUID = 7180586301408140810L;
	private int rowNumber=0;
	private int pageSize=10;
	
	public int getRowNumber() {  
		return rowNumber;
	} 
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
