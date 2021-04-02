package com.skilldistillery.boxes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoxTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Box box;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("BoxPU");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		box = em.find(Box.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		box = null;
	}

	@Test
	void test_Box_entity_mapping() {
		assertNotNull(box);
		assertEquals("test", box.getTitle());
	}

}
