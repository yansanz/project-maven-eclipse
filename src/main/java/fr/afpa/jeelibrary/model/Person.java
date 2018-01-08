package fr.afpa.jeelibrary.model;

public class Person {
	
	private String lastName;
	private String fisrtName;
	private int id;
	
	public Person() {}
	
	public Person(int id, String lastName, String firstName) {
		this.lastName = lastName;
		this.fisrtName = firstName;
		this.id =id;
	}
	
	@Override
	public String toString() {
		return "Person [ " + lastName + " " + fisrtName + "]";
	}

	public Person(String lastName, String firstName) {
		setLastName(lastName);
		setFisrtName(firstName);
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFisrtName() {
		return this.fisrtName;
	}

	public void setFisrtName(String firstName) {
		this.fisrtName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
