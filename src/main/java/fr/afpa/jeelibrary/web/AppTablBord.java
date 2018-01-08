package fr.afpa.jeelibrary.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.afpa.jeelibrary.dao.DaoImpl;
import fr.afpa.jeelibrary.model.Author;
import fr.afpa.jeelibrary.model.Book;
import fr.afpa.jeelibrary.model.Catalog;
import fr.afpa.jeelibrary.model.Copy;
import fr.afpa.jeelibrary.model.Subscriber;
import fr.afpa.jeelibrary.service.IService;
import fr.afpa.jeelibrary.service.Service;

public class AppTablBord extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6374645171296130824L;
	/**
	 * 
	 */
	IService service;

	public void init() throws ServletException {
		DaoImpl dao = new DaoImpl();
		service = new Service(dao);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String action = request.getPathInfo();
		if (action == null || action.equals("/")) {
			TablBord(request, response);
		} else if (action.equals("/rechercheBook")) {
			rechercheBook(request, response);
		} else if (action.equals("/rechercheSub")) {
			rechercheSub(request, response);
		} else if (action.equals("/info")) {
			info(request, response);
		} else if (action.equals("/modif")) {
			modif(request, response);
		} else if (action.equals("/modifSub")) {
			modifSub(request, response);
		} else if (action.equals("/supr")) {
			supression(request, response);
		} else if (action.equals("/modifBook")) {
			modifBook(request, response);
		} else if (action.equals("/add")) {
			add(request, response);
		} else if (action.equals("/addBook")) {
			addBook(request, response);
		} else if (action.equals("/addSub")) {
			addSub(request, response);
		} else if (action.equals("/Librairie")) {
			librairie(request, response);
		} else if (action.equals("/Emprunt")) {
			emprunt(request, response);
		} else if (action.equals("/Retour")) {
			retour(request, response);
		} else if (action.equals("/RetourValid")) {
			retourValid(request, response);
		} else if (action.equals("/EmpruntValid")) {
			empruntValid(request, response);
		} else if (action.equals("/addCopy")) {
			addCopy(request, response);
		} else if (action.equals("/suprCopy")) {
			suprCopy(request, response);
		} else if (action.equals("/infoValid")) {
			infoValid(request, response);
		} else if (action.equals("/ReturnInfoCopy")) {
			ReturnInfoCopy(request, response);
		} else if (action.equals("/LibrairieA")) {
			LibrairieA(request, response);
		} else if (action.equals("/LibrairieG")) {
			LibrairieG(request, response);
		} else if (action.equals("/search")) {
			librairieS(request, response);
		} else if (action.equals("/searchTitle")) {
			TableTitle(request, response);
		}
	}

	/**
	 * Looks up for books by title
	 * 
	 * @param request
	 *            the request object
	 * @param response
	 *            the response object
	 * @throws IOException
	 * @throws ServletException
	 */
	private void TableTitle(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String rechercheTitre = request.getParameter("valeur");
		ArrayList<Book> bk = service.RechTitreExact(rechercheTitre);
		ObjectMapper oMapper = new ObjectMapper();
		oMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String retourJSon = oMapper.writeValueAsString(bk);
		System.out.println(retourJSon);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(retourJSon);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void librairieS(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String rechercheNom = request.getParameter("valeur");
		ArrayList<Subscriber> sub = service.recherchNom(rechercheNom);
		ObjectMapper objectMapper = new ObjectMapper();
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		// 1. Convert List of Person objects to JSON
		String arrayJson = objectMapper.writeValueAsString(sub);
		// System.out.println("1. Convert List of Book objects to JSON :");
		System.out.println(arrayJson);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(arrayJson);

	}

	private void LibrairieG(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ArrayList<Subscriber> sub = new ArrayList<Subscriber>();
		ArrayList<Book> livre = new ArrayList<Book>();
		sub = service.getAllSub();
		livre = service.RechTitreG();
		request.setAttribute("emprunteur", sub);
		request.setAttribute("livre", livre);
		request.setAttribute("infoBul", "selectionner 1 emprunteur et 1 exemplaire pour l'emprunt ");
		getServletContext().getRequestDispatcher("/WEB-INF/views/Librairie.jsp").forward(request, response);
	}

	private void LibrairieA(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ArrayList<Subscriber> sub = new ArrayList<Subscriber>();
		ArrayList<Book> livre = new ArrayList<Book>();
		sub = service.getAllSub();
		livre = service.RechTitreA();
		request.setAttribute("emprunteur", sub);
		request.setAttribute("livre", livre);
		request.setAttribute("infoBul", "selectionner 1 emprunteur et 1 exemplaire pour l'emprunt ");
		getServletContext().getRequestDispatcher("/WEB-INF/views/Librairie.jsp").forward(request, response);
	}

	/**
	 * Allows returning of a copy.
	 * @param request
	 *            the request object
	 * @param response
	 *            the response object
	 * @throws IOException
	 * @throws ServletException
	 */
	private void ReturnInfoCopy(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getParameter("txtCibl13").equals("")) {
			request.setAttribute("infoBul", "operation impossible");

		} else {
			int idCopy = Integer.valueOf(request.getParameter("txtCibl13")).intValue();
			Subscriber s = service.getSubExemplaire(idCopy);
			if (s == null) {
				request.setAttribute("infoBul", "Les infos abonné n'ont pu être récupérées.");
			} else {
				int idClient = s.getId();
				service.Restitue(idCopy, idClient);
				// TODO code written by DMA
				//service.returnCopy(idCopy);
			}
		}
		TablBord(request, response);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void infoValid(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getParameter("txtCibl10").equals("") && request.getParameter("txtCibl11").equals("")) {
			request.setAttribute("infoBul", "operation impossible");
		} else {
			int idCopy = Integer.valueOf(request.getParameter("txtCibl10")).intValue();
			String isbn = request.getParameter("txtCibl11");

			ArrayList<Copy> exemplaire = new ArrayList<Copy>();
			exemplaire = service.getCopy(isbn);
			Subscriber s = service.getSubExemplaire(idCopy);
			request.setAttribute("isbnInfo", isbn);
			request.setAttribute("modeInf", isbn);
			if (s != null) {
				request.setAttribute("idSub", s.getId());
				request.setAttribute("infoBul",
						s.getFisrtName() + " " + s.getLastName() + " a emprunté l'exemplaire N°" + idCopy);
				request.setAttribute("exemplaire", exemplaire);
			} else {
				request.setAttribute("infoBul", "R.A.S");
				request.setAttribute("exemplaire", exemplaire);
			}
		}
		TablBord(request, response);

	}

	private void suprCopy(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getParameter("txtCibl7").equals("")) {
			request.setAttribute("infoBul", "operation impossible");
		} else {
			int idCopy = Integer.valueOf(request.getParameter("txtCibl7")).intValue();
			if (service.dispoCopy(idCopy)) {
				service.modifCopy(idCopy);
			} else {
				request.setAttribute("infoBul", "exemplaire a restituer avant suppression");
			}
		}
		TablBord(request, response);

	}

	private void addCopy(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (request.getParameter("txtCibl9").equals("")) {
			request.setAttribute("infoBul", "operation impossible");
		} else {
			String isbn = request.getParameter("txtCibl9");
			service.addCopy(isbn);
		}
		TablBord(request, response);
	}

	private void empruntValid(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int idCopy = Integer.valueOf(request.getParameter("txtCibl7")).intValue();
		int idClient = Integer.valueOf(request.getParameter("txtCibl8")).intValue();
		String isbn = service.getIsbn(idCopy);
		if (service.getNbreEmprunt(idClient)) {
			service.Emprunte(isbn, idCopy, idClient);
			Subscriber s = service.getOneEmprunteur(idClient);
			request.setAttribute("infoBul", "Emprunt Ok : " + s.getLastName());
		} else {
			request.setAttribute("infoBul", "nbre maxi emprunt deja atteint");
		}
		TablBord(request, response);
	}

	private void retourValid(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int idCopy = Integer.valueOf(request.getParameter("txtCibl5")).intValue();
		int idClient = Integer.valueOf(request.getParameter("txtCibl6")).intValue();
		service.Restitue(idCopy, idClient);
		Subscriber s = service.getOneEmprunteur(idClient);
		request.setAttribute("infoBul", "Retour Ok : " + s.getLastName());
		TablBord(request, response);
	}

	private void retour(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.valueOf(request.getParameter("txtCibl4")).intValue();
		ArrayList<Copy> exemplaire = new ArrayList<Copy>();
		Subscriber s = service.getOneEmprunteur(id);
		String lastName = s.getLastName();
		String fisrtName = s.getFisrtName();
		request.setAttribute("modeLib", id);
		request.setAttribute("id", id);
		request.setAttribute("infoBul", fisrtName + " " + lastName);
		exemplaire = service.getCopyByBorrower(id);

		request.setAttribute("exemplaire", exemplaire);
		getServletContext().getRequestDispatcher("/WEB-INF/views/Librairie.jsp").forward(request, response);
	}

	private void emprunt(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getParameter("txtCibl2").equals("") || request.getParameter("txtCibl1").equals("")) {
			librairie(request, response);
		} else {
			int id = Integer.valueOf(request.getParameter("txtCibl2")).intValue();
			String isbn = request.getParameter("txtCibl1");

			ArrayList<Copy> exemplaire = service.getCopy(isbn);

			Subscriber s = service.getOneEmprunteur(id);
			System.out.println(s.getFisrtName() + "test app");
			String lastName = s.getLastName();
			String fisrtName = s.getFisrtName();
			request.setAttribute("modeEmpr", id);
			request.setAttribute("id", id);
			request.setAttribute("infoBul", fisrtName + " " + lastName + " a deja: "
					+ service.getNbreExactEmpruntparSub(id) + " emprunts en cours");
			request.setAttribute("exemplaire", exemplaire);

			getServletContext().getRequestDispatcher("/WEB-INF/views/Librairie.jsp").forward(request, response);
		}
	}

	private void librairie(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ArrayList<Subscriber> sub = new ArrayList<Subscriber>();
		ArrayList<Book> exemplaire = new ArrayList<Book>();
		ArrayList<Book> livre = new ArrayList<Book>();
		sub = service.getAllSub();
		exemplaire = service.RechTitre();
		for (int i = 0; i < exemplaire.size(); i++) {
			if (service.dispoLivre(exemplaire.get(i).getIsbn())) {
				Book b = service.RechIsbn(exemplaire.get(i).getIsbn());
				livre.add(b);
			}
		}
		request.setAttribute("emprunteur", sub);
		request.setAttribute("livre", livre);
		request.setAttribute("infoBul", "selectionner 1 emprunteur et 1 exemplaire pour l'emprunt ");
		getServletContext().getRequestDispatcher("/WEB-INF/views/Librairie.jsp").forward(request, response);

	}

	private void addSub(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String nomProvisoire = request.getParameter("txtNom");
		String nom = nomProvisoire.replaceFirst(".", (nomProvisoire.charAt(0) + "").toUpperCase());
		String prenomProvisoire = request.getParameter("txtPrenom");
		String prenom = prenomProvisoire.replaceFirst(".", (prenomProvisoire.charAt(0) + "").toUpperCase());
		int id = 0;
		Subscriber s = new Subscriber(nom, prenom, id);
		service.ajouterEmprunteur(s);
		TablBord(request, response);
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Author a = null;
		Book b = null;
		String isbn = request.getParameter("txtIsbn");
		String titre = request.getParameter("txtTitre");
		String ssTitre = request.getParameter("txtSsTitre");
		int nbCopy = Integer.valueOf(request.getParameter("NbrCopy")).intValue();
		String genre = request.getParameter("genre");
		if (request.getParameter("auteur") != null) {
			if (request.getParameter("auteur").equals("") || request.getParameter("auteur").equals("Auteur")) {
				String nom = request.getParameter("namAut");
				String prenom = request.getParameter("prenAut");
				int age = Integer.valueOf(request.getParameter("agAut")).intValue();
				a = new Author(nom, prenom, age);
				b = new Book(isbn, titre, ssTitre, a, genre, nbCopy);
				service.ajouterLivre(b);
			}
		} else {
			String chaine = request.getParameter("auteur");
			String[] sousChaine = chaine.split(" ");
			String nomAuteur = sousChaine[1];
			String prenomAuteur = sousChaine[0];
			a = service.existOrNo(nomAuteur, prenomAuteur);
			b = new Book(isbn, titre, ssTitre, a, genre, nbCopy);
			service.ajouterLivreAuteurExistant(b);
		}
		TablBord(request, response);

	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String isbn = request.getParameter("txtCibl7");
		if (isbn.equals("book")) {
			ArrayList<Catalog> catalog = service.getCatalog();
			ArrayList<Author> author = service.ListAuthor();
			request.setAttribute("catalog", catalog);
			request.setAttribute("auteur", author);
			request.setAttribute("nbCopy", isbn);
		} else {
			request.setAttribute("modeSub", isbn);
		}
		getServletContext().getRequestDispatcher("/WEB-INF/views/tablBordUpgrade.jsp").forward(request, response);
	}

	private void modifBook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Author a = null;
		String isbn = request.getParameter("txtIsbn");
		String titre = request.getParameter("txtTitre");
		String ssTitre = request.getParameter("txtSsTitre");
		int nbCopy = Integer.valueOf(request.getParameter("txtNbrCopy")).intValue();
		String genre = request.getParameter("genre");
		if (request.getParameter("auteur") != null) {
			if (request.getParameter("auteur").equals("") || request.getParameter("auteur").equals("Auteur")) {
				String nom = request.getParameter("namAut");
				String prenom = request.getParameter("prenAut");
				int age = Integer.valueOf(request.getParameter("agAut")).intValue();
				a = new Author(nom, prenom, age);
			}
		} else {
			String chaine = request.getParameter("auteur");
			String[] sousChaine = chaine.split(" ");
			String nomAuteur = sousChaine[1];
			String prenomAuteur = sousChaine[0];
			a = service.existOrNo(nomAuteur, prenomAuteur);
		}
		Book b = new Book(isbn, titre, ssTitre, a, genre, nbCopy);
		service.modifierLivre(b);
		TablBord(request, response);

	}

	private void supression(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getParameter("txtCibl5").equals("") && request.getParameter("txtCibl6").equals("")) {
			request.setAttribute("infoBul", "selectionner un livre ou un emprunteur");
		} else {
			if (request.getParameter("txtCibl5").equals("")) {
				int id = Integer.valueOf(request.getParameter("txtCibl6")).intValue();
				if (service.verifEmpruntAvanSuppression(id)) {
					service.supprimEmprunteur(id);
				} else {
					request.setAttribute("infoBul", "emprunt a restutuer avant");
				}
				TablBord(request, response);
			} else {
				String isbn = request.getParameter("txtCibl5");
				ArrayList<Copy> livre = new ArrayList<Copy>();
				request.setAttribute("infoBul", "choisir exemplaire a supprimer ");
				livre = service.getCopy(isbn);
				request.setAttribute("exemplaire", livre);
				request.setAttribute("btnSuprCopy", "supprCopy");
			}
		}
		TablBord(request, response);

	}

	private void modifSub(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		int id = Integer.valueOf(request.getParameter("txtId")).intValue();
		String nomProvisoire = request.getParameter("txtNom");
		String nom = nomProvisoire.replaceFirst(".", (nomProvisoire.charAt(0) + "").toUpperCase());
		String prenomProvisoire = request.getParameter("txtPrenom");
		String prenom = prenomProvisoire.replaceFirst(".", (prenomProvisoire.charAt(0) + "").toUpperCase());
		Subscriber s = new Subscriber(prenom, nom, id);
		service.modifEmprunteur(s);
		TablBord(request, response);
	}

	private void modif(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (request.getParameter("txtCibl4").equals("") && request.getParameter("txtCibl3").equals("")) {
			request.setAttribute("infoBul", "selectionner un livre ou un emprunteur");
		} else {
			if (!request.getParameter("txtCibl4").equals("")) {
				int id = Integer.valueOf(request.getParameter("txtCibl4")).intValue();
				Subscriber s = service.getOneEmprunteur(id);
				System.out.println(s);
				String lastName = s.getLastName();
				String fisrtName = s.getFisrtName();
				request.setAttribute("id", id);
				request.setAttribute("lastName", lastName);
				request.setAttribute("fisrtName", fisrtName);
			} else {
				String isbn = request.getParameter("txtCibl3");
				Book b = service.RechIsbn(isbn);
				String titre = b.getTitle();
				String subTitre = b.getSubtitle();
				int nbrCopy = b.getNbrCopy();
				ArrayList<Catalog> catalog = service.getCatalog();
				ArrayList<Author> author = service.ListAuthor();
				Author auteur = b.getAuthor();
				String genre = b.getGenre();
				String namAut = auteur.getLastName();
				String prenAut = auteur.getFisrtName();
				int agAut = auteur.getDateOfBirth();
				request.setAttribute("isbn", isbn);
				request.setAttribute("nbrCopy", nbrCopy);
				request.setAttribute("titre", titre);
				request.setAttribute("subTitre", subTitre);
				request.setAttribute("namAut", namAut);
				request.setAttribute("prenAut", prenAut);
				request.setAttribute("agAut", agAut);
				request.setAttribute("catalog", catalog);
				request.setAttribute("auteur", author);
				request.setAttribute("genre", genre);
			}

			getServletContext().getRequestDispatcher("/WEB-INF/views/tablBordUpgrade.jsp").forward(request, response);
		}
		TablBord(request, response);
	}

	private void info(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String isbn = request.getParameter("txtCibl1");
		ArrayList<Copy> livre = new ArrayList<Copy>();
		if (isbn.equals("") && request.getParameter("txtCibl2").equals("")) {
			request.setAttribute("infoBul", "selectionner un livre ou un emprunteur");
		} else {
			if (!isbn.equals("")) {
				request.setAttribute("modeInf", isbn);
				request.setAttribute("isbn", isbn);
				livre = service.getCopy(isbn);
				request.setAttribute("isbnInfo", isbn);
			} else {
				int id = Integer.valueOf(request.getParameter("txtCibl2")).intValue();
				System.out.println("test sub2 " + id);
				livre = service.getCopyByBorrower(id);
				Subscriber s = service.getOneEmprunteur(id);
				request.setAttribute("modeInf", id);
				String lastName = s.getLastName();
				String fisrtName = s.getFisrtName();
				request.setAttribute("idSub", s.getId());
				request.setAttribute("infoBul", fisrtName + " " + lastName + " a "
						+ service.getNbreExactEmpruntparSub(id) + " emprunts en cours");

			}

			request.setAttribute("exemplaire", livre);
		}
		TablBord(request, response);

	}

	private void rechercheSub(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ArrayList<Subscriber> emprunteur = new ArrayList<Subscriber>();
		String name = request.getParameter("txtNom");
		if (name == null) {
			emprunteur = service.getAllSub();
		} else {
			emprunteur = service.recherchNom(name);
		}
		request.setAttribute("emprunteur", emprunteur);

		TablBord(request, response);

	}

	private void rechercheBook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ArrayList<Book> livre = new ArrayList<Book>();
		String name = request.getParameter("txtTitre");
		String genre = request.getParameter("genre");

		if (name != null) {
			if (!name.equals("")) {
				livre = service.RechTitreExact(name);
				request.setAttribute("livre", livre);
			} else if (request.getParameter("auteur") != null) {
				String chaine = request.getParameter("auteur");
				String[] sousChaine = chaine.split(" ");
				String nomAuteur = sousChaine[1];
				livre = service.RechAuthor(nomAuteur);
				request.setAttribute("livre", livre);
			} else if (!genre.equals("")) {
				livre = service.RechGenre(genre);
				request.setAttribute("livre", livre);
			} else {
				livre = service.RechTitre();
				request.setAttribute("livre", livre);
			}
		}

		TablBord(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// on passe la main au get
		doGet(request, response);
	}

	private void TablBord(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		ArrayList<Author> auteur = new ArrayList<Author>();
		ArrayList<Catalog> catalog = new ArrayList<Catalog>();
		String contact = request.getParameter("contact");
		String infLeg = request.getParameter("infLeg");
		String condUtil = request.getParameter("condUtil");
		if (contact != null) {
			request.setAttribute("contact", "yansanz90@gmail.com");
		}
		if (infLeg != null) {
			request.setAttribute("infoLegal", "loi n° 2004-575 du 21 juin 2004 , Article 6");
		}
		if (condUtil != null) {
			request.setAttribute("condUtil", "Document à but pedagogique - AFPA");
		}
		auteur = service.ListAuthor();
		catalog = service.getCatalog();
		request.setAttribute("auteur", auteur);
		request.setAttribute("catalog", catalog);
		getServletContext().getRequestDispatcher("/WEB-INF/views/TablBord.jsp").forward(request, response);

	}
}
