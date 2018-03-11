package com.smartThings.haflete.portal.backBean;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

import com.smartThings.haflete.entity.AddressInfo;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.Store;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.CookieHelper;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.SellerRemote;

@Named
@SessionScoped
public class LoginBackBean extends SuperBackBean {
	
	public static final String KEEP_LOGIN_COOKIE_NAME = "counter";
	public static final int KEEP_LOGIN_COOKIE_AGE = 360;
	
	SellerRemote sellerEJB;
	
	@Inject
	ServiceLocater serviceLocater;
	
	@Inject
	CookieHelper cookieHelper;
	
	private Seller loginSeller;
	private Seller seller;
	private String confirmPass;
	private String mapCenterCoords;
	private boolean isLoggedIn;
	
	@PostConstruct
	public void init() {
		Cookie uuidCookie = cookieHelper.getCookie(KEEP_LOGIN_COOKIE_NAME);
		sellerEJB = serviceLocater.getSellerRemote();
		if(uuidCookie != null && uuidCookie.getValue() != null) {
			loginSeller = sellerEJB.loginByUuid(uuidCookie.getValue());
		}
		
		if(loginSeller != null) {
			redirect("/pages/sellerHome.xhtml");
		}
		
		loginSeller = new Seller();
		seller = new Seller();
		Store store = new Store();
		AddressInfo addressInfo = new AddressInfo();
		store.setAddressInfo(addressInfo);
		seller.setStore(store);
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		cookieHelper.removeCookie(KEEP_LOGIN_COOKIE_NAME);
		isLoggedIn = false;
		return "/pages/home?faces-redirect=true";
	}
	
	public void login() {
		try {
			String uuid = UUID.randomUUID().toString();
			loginSeller.setKeepMeLoginCookie(uuid);
			cookieHelper.setCookie(KEEP_LOGIN_COOKIE_NAME, uuid, KEEP_LOGIN_COOKIE_AGE);
			loginSeller = sellerEJB.login(loginSeller);
			isLoggedIn = true;
			
			redirect("/pages/sellerHome.xhtml");
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
	
	public void validateUserName() {
		checkIfExisits(seller.getUsername(), "username");
	}
	
	private void checkIfExisits(String value, String type) {
		try {
			if(value != null && !value.isEmpty()) {
				sellerEJB.checkIfExists(value, type);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			addErrorMessage(e.getBundleName());
		}
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

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
