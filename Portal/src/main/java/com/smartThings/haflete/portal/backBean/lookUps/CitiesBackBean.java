package com.smartThings.haflete.portal.backBean.lookUps;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.smartThings.haflete.entity.City;
import com.smartThings.haflete.portal.backBean.util.SuperBackBean;
import com.smartThings.haflete.portal.util.ServiceLocater;
import com.smartThings.haflete.remoteServices.CityRemote;

@Named
@ApplicationScoped
public class CitiesBackBean extends SuperBackBean {
	
	private static final long serialVersionUID = -1766117512304714274L;

	private List<City> cities;
	
	@Inject
	ServiceLocater serviceLocater;
	
	private CityRemote cityEJB;
	
	@PostConstruct
	public void init() {
		
		cityEJB = serviceLocater.getCityRemote();
		
		if(cities == null || cities.isEmpty()) {
			cities = cityEJB.allCities();
		}
	}
	
	public List<City> getCities() {
		
		if(cities == null || cities.isEmpty()) {
			cities = cityEJB.allCities();
		}
		
		return cities;
	}
}
