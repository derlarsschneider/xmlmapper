package de.consutor.xmlmapper.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * 
 * @author Capgemini, Lars Schneider $
 */
@Entity
public class Person {
	/**
	 * Technical id.
	 */
	private long id;

	/**
	 * Versioncounter for optimistic locking.
	 */
	private int version;

	private String firstName;
	private String lastName;

	/**
	 * Standard constructor.
	 */
	public Person() {
		super();
	}

	@Id
	@Column(nullable = false)
	@GeneratedValue(generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_PERSON")
	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
	}

	@Column(nullable = false, length = 16384)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		this.lastName = value;
	}

	@Column(nullable = false, length = 16384)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		this.firstName = value;
	}

	@Column(nullable = false)
	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int value) {
		this.version = value;
	}
	@Override
	public String toString() {
	
		return id+", "+firstName+", "+lastName+" ("+version+")";
	}
}
