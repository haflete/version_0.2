package com.smartThings.haflete.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.smartThings.haflete.dao.util.HibernateUtil;
import com.smartThings.haflete.dao.util.SuperDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.searchBeans.ItemSearchCriteria;
import com.smartThings.haflete.entity.searchBeans.SearchCriteria;

public class ItemDAO extends SuperDAO<Item> {

	public List<Item> findItems(SearchCriteria searchCriteria, Long id) {
//		Criteria criteria = prepareCraiteria(Item.class);
//		criteria.setFirstResult(searchCriteria.getRowNumber());
//		criteria.setMaxResults(searchCriteria.getPageSize());
//		criteria.createAlias("store", "store");
//		
//		criteria.add(Restrictions.eq("store.id", id));
//		criteria.addOrder(Order.desc("id"));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		
		Query query = HibernateUtil.getSession().createQuery(
				"select distinct item from Item item where item.store.id = :storeId order by item.id desc")
				.setParameter("storeId", id);
		
		query.setFirstResult(searchCriteria.getRowNumber());
		query.setMaxResults(searchCriteria.getPageSize());
		
		return query.list();
	}

	public List<Item> findItems(ItemSearchCriteria searchCriteria) {
		Query query = null;
		if(searchCriteria.getType() != null) {
			query = HibernateUtil.getSession().createQuery("select distinct item from Item item where"
					+ " item.store.type = :type order by item.id desc");
			query.setParameter("type", searchCriteria.getType());
		} else {
			query = HibernateUtil.getSession().
					createQuery("select distinct item from Item item order by item.id desc");
		}
		
		query.setFirstResult(searchCriteria.getRowNumber());
		query.setMaxResults(searchCriteria.getPageSize());
		
		return query.list();
	}

}
