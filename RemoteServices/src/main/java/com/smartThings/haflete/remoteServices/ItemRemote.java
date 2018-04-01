package com.smartThings.haflete.remoteServices;

import javax.ejb.Remote;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;

@Remote
public interface ItemRemote {

	Item createFrontImg(Seller loginSeller, Item item, byte[] data) throws BusinessException;

	Item bringById(Long id) throws BusinessException;

	Item createOrUpdate(Item item) throws BusinessException;
	
}
