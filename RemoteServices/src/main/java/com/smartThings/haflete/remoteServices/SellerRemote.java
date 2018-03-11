package com.smartThings.haflete.remoteServices;

import javax.ejb.Remote;

import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;

@Remote
public interface SellerRemote {

	void save(Seller seller) throws BusinessException;

	Seller login(Seller loginSeller) throws BusinessException;

	Seller loginByUuid(String value);

	void checkIfExists(String value, String type) throws BusinessException;

}
