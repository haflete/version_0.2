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
import com.smartThings.haflete.entity.enums.MediaType;
import com.smartThings.haflete.entity.enums.StoreType;
import com.smartThings.haflete.entity.searchBeans.ItemSearchCriteria;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.ItemRemote;

@ManagedBean
@RequestScoped
public class SellerHomeBackBean extends SuperBackBean {

	private static final long serialVersionUID = 2327748326914130122L;

	private LazyDataModel<Item> items;
	private ItemRemote itemEJB;
	private ItemSearchCriteria searchCriteria;
	
	@Inject
	ServiceLocater serviceLocater; 
	
	@Inject
	private LoginBackBean loginBackBean;
	
	@PostConstruct
	public void init() {
		searchCriteria = new ItemSearchCriteria();
		
		itemEJB = serviceLocater.getItemRemote();
		
		this.items = new LazyDataModel<Item>() {
			@Override
			public List<Item> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				searchCriteria.setPageSize(pageSize);
				searchCriteria.setRowNumber(first);

				List<Item> result;
				try {
					result = itemEJB.findItems(searchCriteria, loginBackBean.getLoginSeller());
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
	
	public LoginBackBean getLoginBackBean() {
		return loginBackBean;
	}

	public void setLoginBackBean(LoginBackBean loginBackBean) {
		this.loginBackBean = loginBackBean;
	}

	public LazyDataModel<Item> getItems() {
		return items;
	}

	public void setItems(LazyDataModel<Item> items) {
		this.items = items;
	}
}
