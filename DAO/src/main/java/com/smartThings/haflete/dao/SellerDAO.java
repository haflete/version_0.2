package com.smartThings.haflete.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.smartThings.haflete.dao.util.HibernateUtil;
import com.smartThings.haflete.dao.util.SuperDAO;
import com.smartThings.haflete.entity.Seller;

public class SellerDAO extends SuperDAO<Seller> {

	public Seller login(Seller loginSeller) {
		Criteria criteria = prepareCraiteria(loginSeller.getClass());
		
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("email", loginSeller.getUsername()));
		or.add(Restrictions.eq("username", loginSeller.getUsername()));
		or.add(Restrictions.eq("mobileNum", loginSeller.getUsername()));
		criteria.add(or);
		
		criteria.add(Restrictions.eq("password", loginSeller.getPassword()));
		
		return criteria.uniqueResult() != null ? (Seller) criteria.uniqueResult() : null;
	}

	public Seller loginByUuid(String value) {
		Criteria criteria = prepareCraiteria(Seller.class);
		criteria.add(Restrictions.eq("keepMeLoginCookie", value));
		
		return criteria.uniqueResult() != null ? (Seller) criteria.uniqueResult() : null;
	}

	public void saveUUIDForSeller(Seller loginSeller) {
		
	}

	public boolean checkIfExists(String value, String type) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(Seller.class);
		criteria.add(Restrictions.eq(type, value));
		
		return criteria.uniqueResult() != null ? true : false;
	}
}
