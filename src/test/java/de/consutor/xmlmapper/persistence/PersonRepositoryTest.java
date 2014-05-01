package de.consutor.xmlmapper.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.consutor.xmlmapper.persistence.entity.Person;
import de.consutor.xmlmapper.persistence.repository.PersonRepository;

@ContextConfiguration(locations = { "/spring/application.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonRepositoryTest {
	// extends AbstractTransactionalJUnit4SpringContextTests {

	private PersonRepository repository;

	@Before
	public void clearRepository() {
		this.repository.deleteAll();
	}

	@Test
	public void testCrudPerson() {
		// Create and persist new person
		Person p = new Person();
		p.setFirstName("Lars");
		p.setLastName("Schneider");
		System.out.println(p);

		p = repository.save(p);
		System.out.println(p);
		// long id = p.getId();

	}

	@Autowired
	public void setRepository(PersonRepository value) {
		this.repository = value;
	}
}
