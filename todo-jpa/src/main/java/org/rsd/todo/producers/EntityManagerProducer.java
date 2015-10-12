package org.rsd.todo.producers;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class EntityManagerProducer {
	@Produces
	@PersistenceContext(unitName = "todo")
	private	EntityManager em;
	
}
