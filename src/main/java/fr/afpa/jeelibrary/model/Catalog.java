package fr.afpa.jeelibrary.model;


import java.util.ArrayList;
import java.util.List;

public class Catalog {

	private String catalogName = null;
	private List<Book> books = new ArrayList<Book>();

	public Catalog() {
	}

	public Catalog(String name) {
		setCatalogName(name);
	}

	/**
	 * @param books
	 */
	public Catalog(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		// TODO Should implement the method 'equals' in Book to use the
		// 'contains' method efficiently.
		if (!books.contains(book)) {
			books.add(book);
		}
	}

	public boolean removeBook(Book book) {
		// TODO Should implement the method 'equals' in Book to use the 'remove'
		// method efficiently.
		return books.remove(book);
	}

	public String toString() {
		String info = "Catalog \"" + getCatalogName() + "\" contains the following books:\r\n";

		for (Book b : books) {
			info += "- \"" + b.getTitle() + "\" written by " + b.getAuthor().getFisrtName() + " "
					+ b.getAuthor().getLastName() + " (";
			info += b.isAvailable() ? "available" : "NOT available";
			info += ")\r\n";
		}
		// info += "\r\n";

		return info;
	}

	/**
	 * @return the catalogName
	 */
	public String getCatalogName() {
		return catalogName;
	}

	/**
	 * @param catalogName
	 *            the catalogName to set
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * @param books
	 *            the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
