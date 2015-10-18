package org.rsd.todo.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.rsd.todo.metamodel.Item_;
import org.rsd.todo.model.Item;

@SuppressWarnings("serial")
@Stateless
@LocalBean
public class ItemDao extends AbstractDao<Item> {
	public ItemDao() {
		super(Item.class);
	}
	
	public List<Item> findDoneItems() {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Item> criteriaQuery = builder.createQuery(Item.class);
		Root<Item> item = criteriaQuery.from(Item.class);
		
		criteriaQuery.select(item)
			.where(builder.isTrue(item.get(Item_.done)));
		return em.createQuery(criteriaQuery).getResultList();
	}
}
