package com.smartThings.haflete.remoteServices;

import javax.ejb.Remote;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.util.BusinessException;

@Remote
public interface ItemRemote {

	Item createFrontImg(Item item) throws BusinessException;
	
}
