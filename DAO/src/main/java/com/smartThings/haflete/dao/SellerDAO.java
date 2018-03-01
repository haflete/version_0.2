package com.smartThings.haflete.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.smartThings.haflete.dao.util.SuperDAO;
import com.smartThings.haflete.entity.Seller;

public class SellerDAO extends SuperDAO<Seller> {

	public Seller login(Seller loginSeller) {
		Criteria criteria = prepareCraiteria(loginSeller.getClass());
		criteria.add(Restrictions.eq("email", loginSeller.getEmail()));
		criteria.add(Restrictions.eq("password", loginSeller.getPassword()));
		
		return criteria.uniqueResult() != null ? (Seller) criteria.uniqueResult() : null;
	}

	
}
