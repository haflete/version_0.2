package com.smartThings.haflete.portal.backBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.smartThings.haflete.entity.AddressInfo;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.Store;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.SellerRemote;

@Named
@SessionScoped
public class LoginBackBean extends SuperBackBean {
	
	SellerRemote sellerEJB;
	
	@Inject
	ServiceLocater serviceLocater;
	
	private Seller loginSeller;
	private Seller seller;
	private String confirmPass;
	private String mapCenterCoords;
	
	@PostConstruct
	public void init() {
		loginSeller = new Seller();
		seller = new Seller();
		Store store = new Store();
		AddressInfo addressInfo = new AddressInfo();
		sellerEJB = serviceLocater.getSellerRemote();
		
		store.setAddressInfo(addressInfo);
		seller.setStore(store);
	}

	public void login() {
		try {
			loginSeller = sellerEJB.login(loginSeller);
		} catch (BusinessException e) {
			e.printStackTrace();
			addErrorMessage(e.getBundleName());
		}
	}
	
	public void save() {
		try {
			if(mapCenterCoords == null || mapCenterCoords.isEmpty())
				throw new BusinessException("gmapIsRequired");
			
			if(!seller.getPassword().equals(confirmPass))
				throw new BusinessException("confirmPass");
			
			sellerEJB.save(seller);
		} catch (BusinessException e) {
			e.printStackTrace();
			addErrorMessage(e.getBundleName());
		}
	}
	
	public String saveCoords() {
		mapCenterCoords = seller.getStore().getAddressInfo().getLatitude() + "," + seller.getStore().getAddressInfo().getLongitude();
		return null;
	}
	
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Seller getLoginSeller() {
		return loginSeller;
	}

	public void setLoginSeller(Seller loginSeller) {
		this.loginSeller = loginSeller;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getMapCenterCoords() {
		return mapCenterCoords;
	}

	public void setMapCenterCoords(String mapCenterCoords) {
		this.mapCenterCoords = mapCenterCoords;
	}
}
