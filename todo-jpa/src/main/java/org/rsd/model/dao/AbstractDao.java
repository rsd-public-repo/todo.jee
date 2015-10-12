package org.rsd.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

@SuppressWarnings("serial")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements
		Serializable {
	
	private final Class<T> entityClass;
	
	@Inject
	protected EntityManager em;

	public AbstractDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public T find(Object id) {
		return em.find(entityClass, id);
	}

	public void persist(final T entity) {
		em.persist(entity);
	}
	
	public void remove(final T entity) {
		em.remove(entity);
	}

	public List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder()
				.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return em.createQuery(criteriaQuery).getResultList();
	}

	public void deleteAll() {
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder()
				.createCriteriaDelete(entityClass);
		criteriaDelete.from(entityClass);
		em.createQuery(criteriaDelete).executeUpdate();
	}
}
