package fr.afpa.jeelibrary.model;

public class Copy {

	private boolean available = true;
	private Book book;
	private Subscriber subscriber;
	private String isbn;
	private String genre;
	private String title;
	private String subtitle;
	private Author author;
	private int dispo;
	private int NumCopy;
	
	public Copy(String isbn, String title,int NumCopy) {
		this.isbn = isbn;
		this.title = title;
		this.NumCopy = NumCopy;
	}
	
	public Copy(String isbn, String title, String subtitle, Author author,int NumCopy) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.NumCopy = NumCopy;
	}
	public Copy(String isbn, String title, String subtitle, Author author,int NumCopy,int dispo) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.setNumCopy(NumCopy);
		this.setDispo(dispo);
	}
	public Copy(String isbn, String title, String subtitle, Author author,int NumCopy,int dispo,Subscriber s) {
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.NumCopy = NumCopy;
		this.setDispo(dispo);
		this.setSubscriber(s);
	}
	public Copy() {}
	public Copy(int NumCopy){
		this.NumCopy= NumCopy;
	}

	public String toString() {
		return "NumCopy=" + NumCopy + ", available=" + available + ", book=" + book + ", isbn=" + isbn
				+ ", title=" + title + ", author=" + author + "]";
	}

	public Book getBook() {

		return this.book;
	}

	public void setBook(Book book) {

		if (this.book == null) {
			// Now copy knows its book
			this.book = book;
			// Now book knows its copy
			book.addCopy(this);
		}
	}

	public boolean isAvailable() {
		available = true;
		if (this.getDispo()==0) {
			available = false;
		}

		return this.available;
	}

	public void setAvailable(boolean value) {

		this.available = value;
	}

	public Subscriber getSubscriber() {

		return this.subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {

		if (subscriber != null) {
			this.subscriber = subscriber;
			this.subscriber.borrowCopy(this);
			this.available = false;
		} else {
			Subscriber s = this.subscriber;

			this.subscriber = null;
			this.available = true;
			s.returnCopy(this);
		}
	}

	public int getNumCopy() {
		return NumCopy;
	}

	public void setNumCopy(int NumCopy) {
		this.NumCopy = NumCopy;
	}
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getDispo() {
		return dispo;
	}
	public void setDispo(int dispo) {
		this.dispo = dispo;
	}
}
