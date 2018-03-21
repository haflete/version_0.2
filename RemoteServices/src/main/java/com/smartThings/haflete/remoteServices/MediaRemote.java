package com.smartThings.haflete.remoteServices;

import javax.ejb.Remote;

import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;

@Remote
public interface MediaRemote {

	ItemMedia createNewImage(ItemMedia media, Seller loginSeller) throws BusinessException;
}
