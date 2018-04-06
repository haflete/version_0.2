package com.smartThings.haflete.entity.searchBeans;

import com.smartThings.haflete.entity.enums.MediaType;
import com.smartThings.haflete.entity.enums.StoreType;

public class ItemSearchCriteria extends SearchCriteria {
	
	private static final long serialVersionUID = 1L;
	
	private StoreType type;

	public StoreType getType() {
		return type;
	}

	public void setType(StoreType type) {
		this.type = type;
	}
}
