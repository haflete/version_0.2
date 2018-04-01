package com.smartThings.haflete.services.local;

import javax.ejb.Stateless;

import com.smartThings.haflete.dao.SellerDAO;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.SellerRemote;

@Stateless(mappedName = "ejb/SellerEJB", name = "SellerEJB")
public class SellerEJB implements SellerRemote {
	SellerDAO dao = new SellerDAO(); 
	
	@Override
	public void save(Seller seller) throws BusinessException {
		dao.save(seller);
	} 

	@Override
	public Seller login(Seller loginSeller) throws BusinessException {
		
		String uuid = loginSeller.getKeepMeLoginCookie();
		Seller seller = dao.login(loginSeller);
		
		if(seller == null) {
			throw new BusinessException("WrongUsOrPass");
		}
		
		seller.setKeepMeLoginCookie(uuid);
		dao.update(seller);
		
		return seller;
	}

	@Override
	public Seller loginByUuid(String value) {
		return dao.loginByUuid(value);
	}

	@Override
	public void checkIfExists(String value, String type) throws BusinessException {
		if(dao.checkIfExists(value, type))
			throw new BusinessException("isExists");
	}
}
