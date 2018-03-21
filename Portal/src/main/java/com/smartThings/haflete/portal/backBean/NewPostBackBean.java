package com.smartThings.haflete.portal.backBean;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.enums.MediaType;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.ItemRemote;
import com.smartThings.haflete.remoteServices.MediaRemote;

@ManagedBean
@ViewScoped
public class NewPostBackBean extends SuperBackBean {

	private static final long serialVersionUID = 2327748326914130126L;
	
	private Item item;
	private MediaRemote mediaEJB;
	private ItemMedia choosenImage;
	private boolean choosedImage;
	private CroppedImage croppedImage;
	private ItemRemote itemEJB;
	
	@Inject
	ServiceLocater serviceLocater;
	
	@Inject
	private LoginBackBean loginBackBean;
	
	@PostConstruct
	public void init() {
		item = new Item();
		mediaEJB = serviceLocater.getMediaRemote();
		itemEJB = serviceLocater.getItemRemote();
		item.setStore(loginBackBean.getLoginSeller().getStore());
	}
	
	public void crop() {
        if(croppedImage == null) {
            return;
        }
        
        int endIndex = choosenImage.getUrl().lastIndexOf(File.separator);
	    String dirPath = choosenImage.getUrl().substring(0, endIndex);
	    
        String newImageUrl = dirPath + File.separator + "croped_" + choosenImage.getName();
        
        ItemMedia frontImage = new ItemMedia();
        frontImage.setName("croped_" + choosenImage.getName());
        frontImage.setType(MediaType.IMAGE);
        frontImage.setSize(croppedImage.getBytes().length);
        frontImage.setContents(croppedImage.getBytes());
        frontImage.setUrl(newImageUrl);
        item.setChoosenImage(frontImage);
        try {
			item = itemEJB.createFrontImg(item);
			addSuccMsg();
		} catch (BusinessException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
    }
	
	public void handleFileUpload(FileUploadEvent event) {
		ItemMedia media = new ItemMedia();
		UploadedFile file = event.getFile();
		media.setExt(file.getContentType());
		media.setItem(item);
		media.setName(file.getFileName());
		media.setType(MediaType.IMAGE);
		media.setContents(file.getContents());
		media.setSize(file.getContents().length);
		try {
			media = mediaEJB.createNewImage(media, loginBackBean.getLoginSeller());
			addSuccMsg();
		} catch (BusinessException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
    }
	 
	public boolean isChoosedImage() {
		return choosedImage;
	}

	public void setChoosedImage(boolean choosedImage) {
		this.choosedImage = choosedImage;
	}

	public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public LoginBackBean getLoginBackBean() {
		return loginBackBean;
	}

	public void setLoginBackBean(LoginBackBean loginBackBean) {
		this.loginBackBean = loginBackBean;
	}

	public ItemMedia getChoosenImage() {
		return choosenImage;
	}

	public void setChoosenImage(ItemMedia choosenImage) {
		this.choosenImage = choosenImage;
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}
}
