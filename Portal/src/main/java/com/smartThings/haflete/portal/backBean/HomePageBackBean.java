package com.smartThings.haflete.portal.backBean;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.enums.StoreType;
import com.smartThings.haflete.entity.searchBeans.ItemSearchCriteria;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.ItemRemote;

@ManagedBean
@RequestScoped
public class HomePageBackBean extends SuperBackBean {

	private static final long serialVersionUID = 2327748326914130422L;

	@Inject
	ServiceLocater serviceLocater;
	
	private ItemRemote itemEJB;
	
	private LazyDataModel<Item> items;
	
	private ItemSearchCriteria searchCriteria;
	
	@PostConstruct
	public void init() {
		itemEJB = serviceLocater.getItemRemote();
		searchCriteria = new ItemSearchCriteria();
		Map<String,String> dataMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String englishTypeName = dataMap.get("page");
		if(englishTypeName != null) {
			searchCriteria.setType(StoreType.findByEnName(englishTypeName));
		}
		
		this.items = new LazyDataModel<Item>() {
			@Override
			public List<Item> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				searchCriteria.setPageSize(pageSize);
				searchCriteria.setRowNumber(first);

				List<Item> result;
				try {
					result = itemEJB.findItems(searchCriteria);
					items.setRowCount(result.size());
					return result;
				} catch (BusinessException e) {
					addErrorMessage(e.getBundleName());
					e.printStackTrace();
				}

				return null;
			}
		};
	}

	public LazyDataModel<Item> getItems() {
		return items;
	}

	public void setItems(LazyDataModel<Item> items) {
		this.items = items;
	}
}
