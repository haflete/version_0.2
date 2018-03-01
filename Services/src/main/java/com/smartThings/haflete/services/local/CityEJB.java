package com.smartThings.haflete.services.local;

import java.util.List;

import javax.ejb.Stateless;

import com.smartThings.haflete.dao.CityDAO;
import com.smartThings.haflete.dao.util.HibernateUtil;
import com.smartThings.haflete.entity.City;
import com.smartThings.haflete.remoteServices.CityRemote;

@Stateless(mappedName="ejb/CityEJB", name="CityEJB")
public class CityEJB implements CityRemote {
	
	CityDAO dao = new CityDAO();
	
	@Override
	public List<City> allCities() {
		HibernateUtil.getSession().createCriteria(City.class).list();
		return dao.listAll(City.class);
	}
}
