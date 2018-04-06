package com.smartThings.haflete.portal.backBean;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.ItemRemote;

@ManagedBean
@ViewScoped
public class ViewItemBackBean extends SuperBackBean {

	private static final long serialVersionUID = 2327748326914130422L;

	@Inject
	ServiceLocater serviceLocater;
	
	private ItemRemote itemEJB;
	private Item item;
	private ItemMedia selectedMedia;
	private String mapCoords;
	private MapModel simpleModel;
	
	@PostConstruct
	public void init() {
		simpleModel = new DefaultMapModel();
         
		itemEJB = serviceLocater.getItemRemote();
		Map<String,String> dataMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String itemId = dataMap.get("itemId");
		try {
			if(itemId != null) {
				item = itemEJB.bringById(Long.valueOf(itemId));
				mapCoords = item.getStore().getAddressInfo().getLatitude() + "," + item.getStore().getAddressInfo().getLongitude();
				LatLng coord1 = new LatLng(Double.valueOf(item.getStore().getAddressInfo().getLatitude()),
						Double.valueOf(item.getStore().getAddressInfo().getLongitude()));
				
				simpleModel.addOverlay(new Marker(coord1, findBundleMsg("findDirc")));
			}
		} catch (NumberFormatException e) {
			addGeneralError();
			e.printStackTrace();
		} catch (BusinessException e) {
			addErrorMessage(e.getBundleName());
			e.printStackTrace();
		}
	}
	
	public void onMarkerSelect(OverlaySelectEvent event) {
		redirect("http://maps.google.com/maps?q=" + mapCoords);
    }
	
	public ItemMedia getSelectedMedia() {
		return selectedMedia;
	}

	public void setSelectedMedia(ItemMedia selectedMedia) {
		this.selectedMedia = selectedMedia;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getMapCoords() {
		return mapCoords;
	}

	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}
}
