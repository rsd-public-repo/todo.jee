package org.rsd.todo.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import javax.validation.ConstraintViolationException;
import org.rsd.todo.model.Item;

//@RunWith(Arquillian.class)
public class ItemTest {

	// ======================================
	// = Attributes =
	// ======================================

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("todo-test");;
	private EntityManager em;
	private EntityTransaction tx;
	private static ValidatorFactory vf;
	private static Validator validator;

	// ======================================
	// = Lifecycle Methods =
	// ======================================

	@BeforeClass
	public static void initEntityManager() throws Exception {
		emf = Persistence.createEntityManagerFactory("todo-test");
		vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}

	@AfterClass
	public static void close() {
		vf.close();
		emf.close();
	}

	@Before
	public void initTransaction() throws Exception {
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	@After
	public void closeEntityManager() throws Exception {
		if (em != null)
			em.close();
	}

	// ======================================
	// = Unit tests =
	// ======================================

	@Test
	public void executeBookTest() throws Exception {
		entityManagerNotNull();
		shouldFindItem();
		shouldCreateNewItem();
		shouldRaiseConstraintViolationCauseNullTitle();
	}

	public void entityManagerNotNull() throws Exception {
		System.out.println("Executing entityManagerNotNull");
		assertNotNull(em);
		System.out.println("entityManagerNotNull success");
	}

	public void shouldFindItem() throws Exception {
		System.out.println("Executing shouldFindItem");
		Item item = em.find(Item.class, 1001L);
		assertEquals("Item active", item.getDetails());
		System.out.println("shouldFindItem success");
	}

	public void shouldCreateNewItem() throws Exception {
		System.out.println("Executing shouldCreateNewItem");

		// Creates an instance of book
		Item item = new Item("New Item", false);

		Set<ConstraintViolation<Item>> violations = validator.validate(item);
		assertEquals(0, violations.size());

		System.out.println("shouldCreateNewItem validation succeeded");

		// Persists the book to the database
		tx.begin();
		em.persist(item);
		tx.commit();
		assertNotNull("ID should not be null", item.getId());

		System.out.println("shouldCreateNewItem persisted the book");

		// Retrieves all the books from the database
		List<Item> items = em.createNamedQuery("findNewItem", Item.class)
				.getResultList();
		assertEquals(1, items.size());
		item = em.createNamedQuery("findNewItem", Item.class)
				.getSingleResult();
		System.out.println("shouldCreateNewItem found a book");

		assertEquals("New Item",
				item.getDetails());

		System.out
				.println("shouldCreateNewItem found The New Item");
	}

	public void shouldRaiseConstraintViolationCauseNullTitle() {

		System.out
				.println("Executing shouldRaiseConstraintViolationCauseNullTitle");

		Item item = new Item(null, false);

		Set<ConstraintViolation<Item>> violations = validator.validate(item);

		assertEquals(1, violations.size());

		assertEquals("may not be null", violations.iterator().next()
				.getMessage());
		assertEquals(null, violations.iterator().next().getInvalidValue());
		assertEquals("{javax.validation.constraints.NotNull.message}",
				violations.iterator().next().getMessageTemplate());

		System.out
				.println("shouldRaiseConstraintViolationCauseNullTitle succeeded");
	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldRaiseConstraintViolationCauseNullTitle2() {

		System.out
				.println("Executing shouldRaiseConstraintViolationCauseNullTitle2");

		Item item = new Item(null, false);
		tx.begin();
		em.persist(item);
		tx.commit();
		// em.flush();
	}
}
