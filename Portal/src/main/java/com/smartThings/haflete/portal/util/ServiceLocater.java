package com.smartThings.haflete.portal.util;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.smartThings.haflete.remoteServices.CityRemote;
import com.smartThings.haflete.remoteServices.ItemRemote;
import com.smartThings.haflete.remoteServices.MediaRemote;
import com.smartThings.haflete.remoteServices.SellerRemote;

@Dependent
public class ServiceLocater implements Serializable{
	
	private static final long serialVersionUID = -1633984759378878888L;
	
	private SellerRemote sellerRemote;
	private CityRemote cityRemote;
	private MediaRemote mediaRemote;
	private ItemRemote itemRemote;
	private static final String PREFIX = "java:comp/env/ejb/";
	 
	public ItemRemote getItemRemote() {
		try {
			itemRemote = (ItemRemote) (new InitialContext()).lookup(PREFIX + "ItemEJB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemRemote;
	}
	
	public SellerRemote getSellerRemote() {
		try {
			sellerRemote = (SellerRemote) (new InitialContext()).lookup(PREFIX + "SellerEJB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sellerRemote;
	}
	
	public CityRemote getCityRemote() {
		try {
			cityRemote = (CityRemote) (new InitialContext()).lookup(PREFIX + "CityEJB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityRemote;
	}
	
	public MediaRemote getMediaRemote() {
		try {
			mediaRemote = (MediaRemote) (new InitialContext()).lookup(PREFIX + "MediaEJB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaRemote;
	}
}
