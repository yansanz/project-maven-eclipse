package fr.afpa.jeelibrary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.afpa.jeelibrary.model.Author;
import fr.afpa.jeelibrary.model.Book;
import fr.afpa.jeelibrary.model.Catalog;
import fr.afpa.jeelibrary.model.Copy;
import fr.afpa.jeelibrary.model.Subscriber;

public class DaoImpl implements IDao {

	String url = "jdbc:mysql://localhost:3306/libraryeval";
	String login = "root";
	String password = "anice/2005";
	Connection connection = null;
	Statement statement = null;
	ResultSet result = null;
	private Statement statement2 = null;

	public DaoImpl() {
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		try {
			// chargement du driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getIdPersonne() {
		int id = 0;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select  max(id_personne) as nbre  from personne";

			result = statement.executeQuery(init);
			while (result.next()) {
				String idTemp = result.getString("nbre");
				id = Integer.valueOf(idTemp).intValue();
			}
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void modifierLivre(Book b) {
		int idClient = 0;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql
			String init = "select _ecrit.id_personne from _ecrit where _ecrit.isbn ='" + b.getIsbn() + "';";
			result = statement.executeQuery(init);
			while (result.next()) {
				idClient = result.getInt("id_personne");
			}
			System.out.println("test requete " + idClient);
			String query1 = "update personne set nom = '" + b.getAuthor().getFisrtName() + "', prenom = '"
					+ b.getAuthor().getLastName() + "' where personne.id_personne = '" + idClient + "';";
			String query2 = "update  _auteur set annee_naissance = '" + b.getAuthor().getDateOfBirth()
					+ "'where _auteur.id_personne = '" + idClient + "';";
			String query3 = "update  _livre set isbn ='" + b.getIsbn() + "', titre_livre='" + b.getTitle() + "',"
					+ " sous_titre='" + b.getSubtitle() + "', nbre_exemplaire = nbre_exemplaire +'" + b.getNbrCopy()
					+ "', nom_collection ='" + b.getGenre() + "' where _livre.isbn = '" + b.getIsbn() + "';";
			statement.executeUpdate(query1);
			statement.executeUpdate(query2);
			statement.executeUpdate(query3);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void ajouterLivre(Book b) {
		int id = 0;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			id = getIdPersonne() + 1;
			String query2 = "insert into personne values ('" + id + "','" + b.getAuthor().getFisrtName() + "','"
					+ b.getAuthor().getLastName() + "')";
			String query3 = "insert into _auteur values('" + b.getAuthor().getDateOfBirth() + "','" + id + "')";
			String query4 = "insert into _livre values('" + b.getIsbn() + "','" + b.getTitle() + "','" + b.getSubtitle()
					+ "','" + b.getNbrCopy() + "','" + 1 + "','" + b.getGenre() + "')";
			String query5 = "insert into _ecrit values('" + b.getIsbn() + "','" + id + "')";

			statement.executeUpdate(query2);
			// statement.executeUpdate(query);
			statement.executeUpdate(query3);
			statement.executeUpdate(query4);
			statement.executeUpdate(query5);
			if (b.getNbrCopy() != 0) {
				int idCopy = getIdcopy() + 1;
				for (int i = 0; i < b.getNbrCopy(); i++) {
					idCopy++;
					String query6 = "insert into _exemplaire values('" + idCopy + "','" + 1 + "','" + "en stock" + "','"
							+ b.getIsbn() + "')";
					statement.executeUpdate(query6);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Subscriber getSubExemplaire(int numeroCopy) {
		Subscriber s = null;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select  _emprunte.id_personne,personne.nom, personne.prenom, _emprunte.id_exemplaire"
					+ " from _emprunte,personne where _emprunte.id_personne = personne.id_personne and _emprunte.id_exemplaire ='"
					+ numeroCopy + "';";

			result = statement.executeQuery(init);
			while (result.next()) {
				int id = result.getInt("id_personne");
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");

				s = new Subscriber(nom, prenom, id);
				System.out.println(s.toString());

			}

			return s;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;

	}

	public Author existOrNo(String name, String surname) {
		Author a = null;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select personne.id_personne, personne.nom , personne.prenom, _auteur.annee_naissance "
					+ " from personne,_auteur where personne.id_personne = _auteur.id_personne and personne.nom = '"
					+ name + "' and personne.prenom = '" + surname + "';";

			result = statement.executeQuery(init);
			while (result.next()) {
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				int idClient = result.getInt("id_personne");
				int anneeNaissance = result.getInt("annee_naissance");
				a = new Author(nom, prenom, anneeNaissance, idClient);
			}
			return a;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void ajouterLivreAuteurExistant(Book b) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql
			System.out.println(b.getAuthor().getFisrtName() + " " + b.getAuthor().getIdAuteur());
			String query4 = "insert into _livre values('" + b.getIsbn() + "','" + b.getTitle() + "','" + b.getSubtitle()
					+ "','" + b.getNbrCopy() + "','" + 1 + "','" + b.getGenre() + "')";
			String query5 = "insert into _ecrit values('" + b.getIsbn() + "','" + b.getAuthor().getIdAuteur() + "')";

			// statement.executeUpdate(query);
			statement.executeUpdate(query4);
			statement.executeUpdate(query5);
			if (b.getNbrCopy() != 0) {
				int idCopy = getIdcopy() + 1;
				for (int i = 0; i < b.getNbrCopy(); i++) {
					idCopy++;
					String query6 = "insert into _exemplaire values('" + idCopy + "','" + 1 + "','" + "en stock" + "','"
							+ b.getIsbn() + "')";
					statement.executeUpdate(query6);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Book> RechTitreExact(String name) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre, _livre.Sous_titre,_livre.Nbre_exemplaire, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN and _livre.Titre_livre like '%" + name + "%';";
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Book> RechTitre() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre, _livre.Sous_titre,_livre.Nbre_exemplaire, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN order by _livre.Titre_livre" ;
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	public ArrayList<Book> RechTitreA() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre, _livre.Sous_titre,_livre.Nbre_exemplaire, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN order by personne.nom" ;
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	public ArrayList<Book> RechTitreG() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre, _livre.Sous_titre,_livre.Nbre_exemplaire, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN order by _livre.nom_collection" ;
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Boolean dispoLivre(String ISBN) {

		try {
			boolean dispo = true;
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select  count(disponible) as dispo from _exemplaire where disponible = 1 and ISBN = '"
					+ ISBN + "';";
			result = statement.executeQuery(query);
			while (result.next()) {
				if (result.getInt("dispo") >= 1) {
					dispo = true;
				} else {
					dispo = false;
				}

			}
			statement.close();
			connection.close();
			return dispo;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String getIsbn(int idCopy) {

		try {
			String Isbn = null;
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select  ISBN from _exemplaire where Id_exemplaire = '" + idCopy + "';";
			result = statement.executeQuery(query);
			while (result.next()) {
				Isbn = result.getString("ISBN");

			}
			statement.close();
			connection.close();
			return Isbn;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Book RechIsbn(String ISBN) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre,_livre.Nbre_exemplaire, _livre.Sous_titre,_livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN and _livre.ISBN ='" + ISBN + "';";
			result = statement.executeQuery(query);
			Book book = null;
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				book = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);

			}
			System.out.println(book);
			statement.close();
			connection.close();
			return book;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Book> RechAuthor(String auteur) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre,_livre.Nbre_exemplaire, _livre.Sous_titre, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN and  personne.nom = '" + auteur + "'order by _livre.Titre_livre;";
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			System.out.println(livre);
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				String sousTitre = result.getString("Sous_titre");
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre, nbrCopy);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Book> RechGenre(String genre) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN , _livre.Titre_livre,_livre.Nbre_exemplaire, _livre.Sous_titre, _livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre, _ecrit,personne where _auteur.id_personne = personne.id_personne and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN and  _livre.nom_collection = '" + genre + "'order by _livre.Titre_livre;";
			result = statement.executeQuery(query);
			ArrayList<Book> livre = new ArrayList<Book>();
			ArrayList<Book> books = new ArrayList<Book>();
			Catalog c = new Catalog(books);
			System.out.println(livre);
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int nbrCopy = Integer.valueOf((String) result.getString("Nbre_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				String genre1 = result.getString("_livre.nom_collection");
				Book b = new Book(Isbn, Titre, sousTitre, Auteur, genre1, nbrCopy);
				c.setCatalogName(result.getString("nom_collection"));
				c.addBook(b);
				livre.add(b);

			}
			statement.close();
			connection.close();
			return (ArrayList<Book>) c.getBooks();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<Copy> getCopy(String ISBN) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN ,_exemplaire.id_exemplaire,_exemplaire.disponible, _exemplaire.info_exemplaire, _livre.Titre_livre,_livre.Nbre_exemplaire, _livre.Sous_titre,"
					+ "_livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre,_exemplaire, _ecrit,personne where _auteur.id_personne = personne.id_personne and _exemplaire.ISBN = _livre.ISBN and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN and _livre.ISBN ='" + ISBN + "';";
			result = statement.executeQuery(query);
			ArrayList<Copy> exemplaire = new ArrayList<Copy>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int dispo = result.getInt("disponible");
				int NumCopy = Integer.valueOf((String) result.getString("id_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				Copy c = new Copy(Isbn, Titre, sousTitre, Auteur, NumCopy, dispo);
				exemplaire.add(c);

			}
			statement.close();
			connection.close();
			return exemplaire;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Copy> getAllCopy() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select _livre.ISBN ,_exemplaire.id_exemplaire,_exemplaire.disponible, _exemplaire.info_exemplaire, _livre.Titre_livre,_livre.Nbre_exemplaire, _livre.Sous_titre,"
					+ "_livre.nom_collection,personne.nom, personne.prenom, _auteur.Annee_naissance "
					+ "from _auteur,_livre,_exemplaire, _ecrit,personne where _auteur.id_personne = personne.id_personne and _exemplaire.ISBN = _livre.ISBN and _ecrit.id_personne = personne.id_personne\r\n"
					+ " and _livre.ISBN= _ecrit.ISBN;";
			result = statement.executeQuery(query);
			ArrayList<Copy> exemplaire = new ArrayList<Copy>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String Titre = result.getString("Titre_livre");
				String sousTitre = result.getString("Sous_titre");
				int dispo = result.getInt("disponible");
				int NumCopy = Integer.valueOf((String) result.getString("id_exemplaire")).intValue();
				Author Auteur = new Author(result.getString("nom"), result.getString("prenom"),
						Integer.parseInt(result.getString("Annee_naissance")));
				Copy c = new Copy(Isbn, Titre, sousTitre, Auteur, NumCopy, dispo);
				exemplaire.add(c);

			}
			statement.close();
			connection.close();
			return exemplaire;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void modifCopy(int numeroCopy) {
		String ISBN = null;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement2 = connection.createStatement();

			// correspond à la requete sql
			if (dispoCopy(numeroCopy) == true) {
				String init = "select ISBN from _exemplaire where id_exemplaire ='" + numeroCopy + "';";
				result = statement2.executeQuery(init);
				while (result.next()) {
					ISBN = result.getString("ISBN");
				}
				String query2 = "delete from _exemplaire where id_exemplaire = '" + numeroCopy + "';";
				String query = "update _livre  set  _livre.Nbre_exemplaire = Nbre_exemplaire -1 where _livre.ISBN = '"
						+ ISBN + "';";
				statement2.executeUpdate(query2);
				statement2.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement2.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean dispoCopy(int idCopy) {

		try {
			boolean dispo = true;
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select Disponible from  _exemplaire where Id_exemplaire = " + idCopy + ";";
			result = statement.executeQuery(query);
			while (result.next()) {
				if (result.getInt("Disponible") == 1) {
					dispo = true;
				} else {
					dispo = false;
				}

			}
			System.out.println("traitement ok");
			statement.close();
			connection.close();
			return dispo;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public int getIdcopy() {
		int id = 0;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select  max(id_exemplaire) as nbre from _exemplaire";

			result = statement.executeQuery(init);
			while (result.next()) {
				String idTemp = result.getString("nbre");

				System.out.println(idTemp);
				id = Integer.valueOf(idTemp).intValue();
			}

			return id;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;

	}

	public ArrayList<Author> ListAuthor() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select personne.nom ,_auteur.annee_naissance,personne.id_personne, personne.prenom from _auteur, personne where personne.id_personne = _auteur.id_personne";

			result = statement.executeQuery(init);
			ArrayList<Author> list = new ArrayList<Author>();
			while (result.next()) {
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				int year = result.getInt("annee_naissance");
				int id = result.getInt("id_personne");
				Author a = new Author(nom, prenom, year, id);
				list.add(a);

			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Catalog> getCatalog() {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select  nom_collection from _collection";
			ArrayList<Catalog> catalog = new ArrayList<Catalog>();
			result = statement.executeQuery(init);
			while (result.next()) {
				String nomCollection = result.getString("nom_collection");
				Catalog c = new Catalog(nomCollection);
				catalog.add(c);
			}
			System.out.println(catalog);
			return catalog;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void addCopy(String ISBN) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			int numCopy = getIdcopy() + 1;
			System.out.println("test dao" + numCopy + ISBN);
			// correspond à la requete sql

			String query2 = "insert into _exemplaire values ('" + numCopy + "','" + 1 + "','" + "en stock" + "','"
					+ ISBN + "')";
			String query = "update _livre set  Nbre_exemplaire = Nbre_exemplaire +1 where _livre.ISBN = '" + ISBN
					+ "';";
			statement.executeUpdate(query2);
			statement.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Subscriber> getAllSub() {
		// TODO Auto-generated method stub
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select personne.nom, personne.prenom , personne.id_personne, _emprunteur.nbre_emprunt_en_cours"
					+ " from _emprunteur, personne where personne.id_personne = _emprunteur.id_personne order by personne.nom";
			result = statement.executeQuery(query);
			ArrayList<Subscriber> emprunteur = new ArrayList<Subscriber>();
			while (result.next()) {
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				int id_personne = Integer.parseInt(result.getString("id_personne"));
				int nbrEmprunt = result.getInt("nbre_emprunt_en_cours");

				Subscriber s = new Subscriber(nom, prenom, id_personne, nbrEmprunt);
				emprunteur.add(s);
			}
			statement.close();
			connection.close();
			return emprunteur;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Copy> getCopyByBorrower(int idClient) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String init = "select  _emprunte.id_personne, _emprunte.id_exemplaire, _exemplaire.isbn,_livre.titre_livre from _emprunte , _exemplaire , _livre where _emprunte.id_exemplaire = _exemplaire.id_exemplaire "
					+ "and _exemplaire.isbn = _livre.isbn and _emprunte.id_personne ='" + idClient + "';";

			result = statement.executeQuery(init);
			ArrayList<Copy> livre = new ArrayList<Copy>();
			while (result.next()) {
				String Isbn = result.getString("ISBN");
				String title = result.getString("Titre_livre");
				int idCopy = result.getInt("id_exemplaire");
				Copy c = new Copy(Isbn, title, idCopy);
				livre.add(c);
			}
			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<Subscriber> recherchNom(String name) {
		// TODO Auto-generated method stub
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select personne.nom, personne.prenom , personne.id_personne, _emprunteur.nbre_emprunt_en_cours  "
					+ "from _emprunteur, personne where personne.id_personne = _emprunteur.id_personne and personne.nom like  '%"
					+ name + "%';";
			result = statement.executeQuery(query);
			ArrayList<Subscriber> emprunteur = new ArrayList<Subscriber>();
			System.out.println(emprunteur);
			while (result.next()) {
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				int id_personne = Integer.parseInt(result.getString("id_personne"));
				int nbrEmprunt = result.getInt("nbre_emprunt_en_cours");

				Subscriber s = new Subscriber(nom, prenom, id_personne, nbrEmprunt);
				emprunteur.add(s);
			}
			statement.close();
			connection.close();
			return emprunteur;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean getNbreEmprunt(int idClient) {
		boolean statut = true;

		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select Nbre_emprunt_en_cours from _emprunteur where Id_personne = " + idClient + ";";
			result = statement.executeQuery(query);
			System.out.println(result);
			while (result.next()) {
				if (result.getInt("Nbre_emprunt_en_cours") >= 5) {
					statut = false;
				} else {
					statut = true;
				}

			}
			System.out.println("traitement ok");
			statement.close();
			connection.close();
			return statut;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void supprimEmprunteur(int id) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement2 = connection.createStatement();
			System.out.println(id);
			// correspond à la requete sql
			if (verifEmpruntAvanSuppression(id)) {
				String query = "delete from _emprunteur  where id_personne='" + id + "';";
				String query2 = "delete from personne  where id_personne='" + id + "';";
				statement2.executeUpdate(query);
				statement2.executeUpdate(query2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement2.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public int getNbreExactEmpruntparSub(int id) {
		 int nbExactEmprunt =0;
		try {
			
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			System.out.println(id);
			// correspond à la requete sql
			String query = "select nbre_emprunt_en_cours, id_personne from _emprunteur "
					+ "  where id_personne ='" + id + "';";
			result = statement.executeQuery(query);
			while (result.next()) {
				  nbExactEmprunt = result.getInt("nbre_emprunt_en_cours");
				
				return nbExactEmprunt;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nbExactEmprunt;
	}

	public Subscriber getOneEmprunteur(int id) {
		Subscriber s = null;
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			System.out.println(id);
			// correspond à la requete sql
			String query = "select personne.id_personne, personne.nom, personne.prenom from _emprunteur,personne "
					+ "  where personne.id_personne =_emprunteur.id_personne and personne.id_personne='" + id + "';";
			result = statement.executeQuery(query);
			while (result.next()) {
				id = result.getInt("id_personne");
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				s = new Subscriber(nom, prenom, id);
				return s;
			}
			return s;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public void Emprunte(String numeroIsbn, int numeroCopy, int idClient) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement2 = connection.createStatement();
			// correspond à la requete sql
			if (dispoCopy(numeroCopy) == true && getNbreEmprunt(idClient) == true) {
				String query = "insert into _emprunte values (" + idClient + "," + numeroCopy + ",'" + numeroIsbn
						+ "')";
				String query2 = "update _exemplaire set Disponible = 0 where id_exemplaire = " + numeroCopy + ";";
				String query3 = "update _emprunteur set nbre_emprunt_en_cours = nbre_emprunt_en_cours + 1 where id_personne = "
						+ idClient + ";";
				statement2.executeUpdate(query);
				statement2.executeUpdate(query2);
				statement2.executeUpdate(query3);
			} // liberer les reesources.. memoire
			statement2.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Restitue(int numeroCopy, int idClient) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement2 = connection.createStatement();
			// correspond à la requete sql
			String query = "delete from _emprunte where id_personne=  '" + idClient + "' and id_exemplaire ='"
					+ numeroCopy + "';";
			String query2 = "update _exemplaire set Disponible = Disponible +1 where id_exemplaire = " + numeroCopy
					+ ";";
			String query3 = "update _emprunteur set nbre_emprunt_en_cours = nbre_emprunt_en_cours - 1 where id_personne = "
					+ idClient + ";";
			statement2.executeUpdate(query);
			statement2.executeUpdate(query2);
			statement2.executeUpdate(query3);
			statement2.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modifEmprunteur(Subscriber s) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query2 = "update personne set  nom = '" + s.getFisrtName() + "', prenom= '" + s.getLastName()
					+ "' where id_personne='" + s.getId() + "';";
			statement.executeUpdate(query2);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean verifEmpruntAvanSuppression(int idClient) {
		boolean statut = true;

		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql

			String query = "select Nbre_emprunt_en_cours from _emprunteur where Id_personne = " + idClient + ";";
			result = statement.executeQuery(query);
			System.out.println(result);
			while (result.next()) {
				if (result.getInt("Nbre_emprunt_en_cours") >= 1) {
					statut = false;
				} else {
					statut = true;
				}

			}
			System.out.println("traitement ok");
			statement.close();
			connection.close();
			return statut;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void ajouterEmprunteur(Subscriber s) {
		try {
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();

			// correspond à la requete sql
			int id = getIdPersonne() + 1;
			String query = "insert into _emprunteur values ('" + 1 + "','" + 0 + "','" + id + "')";
			String query2 = "insert into personne values ('" + id + "','" + s.getLastName() + "','" + s.getFisrtName()
					+ "')";
			statement.executeUpdate(query2);
			statement.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// liberer les reesources.. memoire
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
