package fr.afpa.jeelibrary.model;



public class Author extends Person {
	private int idPerson;
	private int dateOfBirth;

	//private Calendar dateOfBirth;
	
	public Author() {}
	
	public Author(String lastName, String firstName,int dateOfBirth) {
		super(lastName, firstName);
		this.dateOfBirth = dateOfBirth;
	}
	public Author(String lastName, String firstName,int dateOfBirth,int idPerson) {
		super(lastName, firstName);
		this.dateOfBirth = dateOfBirth;
		this.setIdAuteur(idPerson);
	}
	
	@Override
	public String toString() {
		String info = this.getFisrtName() + " " +this.getLastName();
		return info;
	}

	public int getDateOfBirth() {
		return this.dateOfBirth;
	}

	 public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getIdAuteur() {
		return idPerson;
	}

	public void setIdAuteur(int idPerson) {
		this.idPerson = idPerson;
	}

}
