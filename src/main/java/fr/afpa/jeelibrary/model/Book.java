package fr.afpa.jeelibrary.model;



import java.util.ArrayList;
import java.util.List;



public class Book {

	private String isbn;
	private String genre;
	private String title;
	private String subtitle;
	private boolean available = false;
	private Author author;
	private int nbrCopy;
	private int dispo;
	private List<Copy> copies = new ArrayList<Copy>();
	

	public Book() {
	}

	/**
	 * @param isbn
	 * @param title
	 * @param subtitle
	 * @param author
	 */
	public Book(String isbn, String title, String subtitle, Author author) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
	}
	public Book(String isbn, String title, String subtitle,int dispo, int nbrCopy) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.setNbrCopy(nbrCopy);
		this.setDispo(dispo);
	}
	public Book(String isbn, String title, String subtitle, Author author, String genre,int nbrCopy) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.setGenre(genre);
		this.setNbrCopy(nbrCopy);
		
	}
	public Book(String ISBN) {
		this.isbn = ISBN;
	}

	public void addCopy(Copy copy) {
	
		// Adding null values to a list is discouraged (unless specific needs).
		if (copy != null && !copies.contains(copy)) {
			// Now copy knows its book
			copy.setBook(this);
			// Now book knows its copy
			copies.add(copy);
		}
	}
	
	public boolean removeCopy(Copy copy) {
		return copies.remove(copy);
	}

	
	public String getIsbn() {

		return this.isbn;
	}

	@Override
	public String toString() {
		return " [isbn=" + isbn + ", title=" + title + ", dispo " + available +  "]";
	}

	 public void setIsbn(String value) {

		this.isbn = value;
	}

	 public String getTitle() {

		return this.title;
	}

	 public void setTitle(String value) {

		this.title = value;
	}

	 public String getSubtitle() {

		return this.subtitle;
	}

	public void setSubtitle(String value) {

		this.subtitle = value;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return  this.author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * @return the copies
	 */
	public List<Copy> getCopies() {
		return copies;
	}

	/**
	 * @param copies
	 *            the copies to set
	 */
	public void setCopies(List<Copy> copies) {
	//	this.copies = service.ajoutCopy(this.getIsbn());
		System.out.println(copies);
	}

	public boolean isAvailable() {
		System.out.println(copies+ " test");
		boolean available = true;
		for (Copy c : copies) {
			if (c.getDispo()>=1) {
				available = true;
				break;
			}
		}

		return available;
	}

	public void setAvailable(boolean value) {

		this.available = value;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getNbrCopy() {
		return nbrCopy;
	}

	public void setNbrCopy(int nbrCopy) {
		this.nbrCopy = nbrCopy;
	}

	public int getDispo() {
		return dispo;
	}

	public void setDispo(int dispo) {
		this.dispo = dispo;
	}

}
