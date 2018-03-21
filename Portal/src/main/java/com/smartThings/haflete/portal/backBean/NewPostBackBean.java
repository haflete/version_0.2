package com.smartThings.haflete.portal.backBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.enums.MediaType;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.MediaRemote;

@ManagedBean
@ViewScoped
public class NewPostBackBean extends SuperBackBean {

	private static final long serialVersionUID = 2327748326914130126L;
	
	private Item item;
	private List<ItemMedia> medias;
	private MediaRemote mediaEJB;
	
	@Inject
	ServiceLocater serviceLocater;
	
	@Inject
	private LoginBackBean loginBackBean;
	
	@PostConstruct
	public void init() {
		item = new Item();
		medias = new ArrayList<>();
		mediaEJB = serviceLocater.getMediaRemote();
		item.setStore(loginBackBean.getLoginSeller().getStore());
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		ItemMedia media = new ItemMedia();
		UploadedFile file = event.getFile();
		media.setExt(file.getContentType());
		media.setItem(item);
		media.setName(file.getFileName());
		media.setType(MediaType.IMAGE);
		media.setContents(file.getContents());
		
		try {
			media = mediaEJB.createNewImage(media, loginBackBean.getLoginSeller());
		} catch (BusinessException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
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
}
