package fr.afpa.jeelibrary.model;

import java.util.ArrayList;
import java.util.List;

// TODO Complete Subscriber class!
public class Subscriber extends Person {

	private int id;
	private List<Copy> copies = new ArrayList<Copy>();
	private  int nbrEmprunt;

	public Subscriber() {
		super();
	}

	/**
	 * @param subscriptionNumber
	 */
	public Subscriber(String lastName, String firstName, int id) {
		super(lastName, firstName);
		this.setId(id);
	}
	public Subscriber(int id){
		this.setId(id);
	}
	public Subscriber(String lastName, String firstName, int id, int nbrEmprunt) {
		super(lastName, firstName);
		this.setId(id);
		this.setNbrEmprunt(nbrEmprunt);
	}

	public void borrowCopy(Copy copy) {
		if (copies.size() == 5)
			System.out.println("Sorry! Maximum number of borrowing reached.");
		// Remark 1 : ArrayList accepts null values. Null values are counted in
		// the size of
		// the list just like non null elements.
		// Adding null values to a list is discouraged (unless specific needs).
		// Remark 2: a subscriber cannot borrow the same copy twice.
		else if (copy != null && !copies.contains(copy)) {
			copies.add(copy);
			copy.setSubscriber(this);
			copy.setAvailable(false);
		}
	}

	public boolean returnCopy(Copy copy) {

		if (copy.getSubscriber() != null)
			copy.setSubscriber(null);
		// TODO Should implement the method 'equals' in Copy to use the 'remove'
		// method efficiently.
		return copies.remove(copy);
	}

	public String toString() {
		String info = null;

		info =  getFisrtName() + " " + getLastName()  ;

		// The subscriber has borrowed at least one copy
		
		return info;
	}

	public List<Copy> getCopies() {

		return this.copies;
	}

	public void setCopies(List<Copy> value) {

		this.copies = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbrEmprunt() {
		return nbrEmprunt;
	}

	public void setNbrEmprunt(int nbrEmprunt) {
		this.nbrEmprunt = nbrEmprunt;
	}


}
