package com.smartThings.haflete.remoteServices;

import java.util.List;

import javax.ejb.Remote;

import com.smartThings.haflete.entity.City;

@Remote
public interface CityRemote {
	List<City> allCities();
}
