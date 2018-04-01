package com.smartThings.haflete.dao.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.entity.util.SuperEntity;

public abstract class SuperDAO <T extends SuperEntity> {
	
	public long save(T bean) throws BusinessException {
		try {
			HibernateUtil.getSession().save(bean);
			HibernateUtil.getSession().flush();
			return bean.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw handleException(e);
		}
	}
	public long save(List<T> bean) throws BusinessException {
		try {
			for (T t : bean) {
				HibernateUtil.getSession().save(t);	
			}
			HibernateUtil.getSession().flush();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw handleException(e);
		}
	}
	public long delete(T bean) throws BusinessException {
		try {
			HibernateUtil.getSession().delete(bean);
			HibernateUtil.getSession().flush();
			return bean.getId();
		} catch (Exception e) {

			throw handleException(e);
		}
	}
	public void delete(List<T> bean) throws BusinessException {
		try {
			for (T t : bean) {
				HibernateUtil.getSession().delete(bean);
			}
			HibernateUtil.getSession().flush();
		} catch (Exception e) {

			throw handleException(e);
		}
	}
	
	public void update(T bean) throws BusinessException {
		try {
			HibernateUtil.getSession().update(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw handleException(e);
		}
	}
	
	private void update(List<T> bean) throws BusinessException {
		try {
			for (T t : bean) {
				HibernateUtil.getSession().update(t);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw handleException(e);
		}
	}
	
	public T bringById(Class clazz, long id) throws BusinessException {
		return searchByPropertyReturnUniqueResult(clazz, "id", id);
	}
	
	public void flush() throws BusinessException {
		try {
			HibernateUtil.getSession().flush();
		} catch (Exception e) {
			throw this.handleException(e);
		}
	}
	
	public long saveOrUpdate(T bean) throws BusinessException {
		try {
			HibernateUtil.getSession().saveOrUpdate(bean);
			HibernateUtil.getSession().flush();
			return bean.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw handleException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> searchByPropertyReturnList(Class clazz, String property,
			Object value) {
		return ( List<T>) HibernateUtil.getSession().createCriteria(clazz)
				.add(Restrictions.eq(property, value)).list();
	}

	@SuppressWarnings("unchecked")
	public T searchByPropertyReturnUniqueResult(Class clazz, String property,
			Object value) {
		return (T) HibernateUtil.getSession().createCriteria(clazz)
				.add(Restrictions.eq(property, value)).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<T> listAll(Class clazz){
		return ( List<T>)HibernateUtil.getSession().createCriteria(clazz).list();
	}
	
	public void refreshEntity(T entiry){
		HibernateUtil.getSession().refresh(entiry);
	}
	
	public Number rowCount(Criteria criteria ){
		Number count =(Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		return count;
	}

	public BusinessException handleException(Exception e) throws BusinessException {
		throw new BusinessException("GENERAL_ERROR");
	}

	protected boolean notNullOrEmpty(String data) {
		return (data!=null && !data.isEmpty());
	}
	
	public Criteria prepareCraiteria(Class clazz) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(clazz);
		
		return criteria;
	}
}
