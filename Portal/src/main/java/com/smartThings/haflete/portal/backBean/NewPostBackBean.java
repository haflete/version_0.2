package com.smartThings.haflete.portal.backBean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
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
	private ItemRemote itemEJB;
	private String croppedBase64;
	
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
		String encodedImg = croppedBase64.split(",")[1];
		byte[] data = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
		
        try {
			item = itemEJB.createFrontImg(loginBackBean.getLoginSeller(), item, data);
			addSuccMsg();
			RequestContext requestContext = RequestContext.getCurrentInstance();  
			requestContext.execute("$('.ui-button-icon-left').click()");
		} catch (BusinessException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
    }
	
	public void deleteImage(ItemMedia image) {
		try {
			item.removeItemMedia(image);
			item = itemEJB.createOrUpdate(item);
			addSuccMsg();
		} catch (BusinessException e) {
			addGeneralError();
			e.printStackTrace();
		}
	}
 
	public void handleFileUpload(FileUploadEvent event) {
		synchronized (this) {
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
				item = media.getItem();
				addSuccMsg();
			} catch (BusinessException e) {
				addErrorMessage(e.getMessage());
				e.printStackTrace();
			}
		}
    }
	
	public void handleVideoUpload(FileUploadEvent event) {
		synchronized (this) {
			ItemMedia media = new ItemMedia();
			UploadedFile file = event.getFile();
			media.setExt(file.getContentType());
			media.setItem(item);
			media.setName(file.getFileName());
			media.setType(MediaType.VIDEO);
			media.setContents(file.getContents());
			media.setSize(file.getContents().length);
			try {
				media = mediaEJB.createNewVideo(media, loginBackBean.getLoginSeller());
				item = media.getItem();
				addSuccMsg();
			} catch (BusinessException e) {
				addErrorMessage(e.getMessage());
				e.printStackTrace();
			}
		}
    }

	public String onFlowProcess(FlowEvent event) throws BusinessException {
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

	public String getCroppedBase64() {
		return croppedBase64;
	}

	public void setCroppedBase64(String croppedBase64) {
		this.croppedBase64 = croppedBase64;
	}
}
