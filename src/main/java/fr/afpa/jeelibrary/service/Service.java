package fr.afpa.jeelibrary.service;

import java.util.ArrayList;

import fr.afpa.jeelibrary.dao.IDao;
import fr.afpa.jeelibrary.model.Author;
import fr.afpa.jeelibrary.model.Book;
import fr.afpa.jeelibrary.model.Catalog;
import fr.afpa.jeelibrary.model.Copy;
import fr.afpa.jeelibrary.model.Subscriber;

public class Service implements IService {
private IDao dao;
	
	public Service(IDao dao) {
		this.dao = dao;
	}
	public ArrayList<Book> RechTitre(){
		return dao.RechTitre();
	}
	public void modifCopy(int numeroCopy) {
		dao.modifCopy(numeroCopy);
	}
	public  ArrayList<Copy> getCopy(String ISBN) {
		return dao.getCopy(ISBN);
	}
	public void addCopy(String ISBN) {
		dao.addCopy(ISBN);
	}
	public String getIsbn(int idCopy) {
		return dao.getIsbn(idCopy);
	}
	public int getNbreExactEmpruntparSub(int id) {
		return dao.getNbreExactEmpruntparSub(id);
	}
	public ArrayList<Book> RechTitreG(){
		return dao.RechTitreG();
	}
	public ArrayList<Book> RechTitreA(){
		return dao.RechTitreA();
	}
	public void modifierLivre(Book b) {
		dao.modifierLivre(b);
	}
	public ArrayList<Catalog> getCatalog(){
		return dao.getCatalog();
	}
	public ArrayList<Author> ListAuthor(){
		return dao.ListAuthor();
	}
	public void ajouterLivre(Book b) {
		dao.ajouterLivre(b);
	}
	public void ajouterLivreAuteurExistant(Book b) {
		dao.ajouterLivreAuteurExistant(b);
	}
	public Author existOrNo(String name,String surname) {
	return	dao.existOrNo(name, surname);
	}
	public ArrayList<Subscriber> getAllSub(){
		return dao.getAllSub();
	}
	public ArrayList<Copy> getCopyByBorrower(int idClient){
		return dao.getCopyByBorrower(idClient);
	}
	public void supprimEmprunteur(int id) {
		dao.supprimEmprunteur(id);
	}
	public Boolean getNbreEmprunt(int idClient) {
		 return dao.getNbreEmprunt(idClient);
	}
	public Boolean verifEmpruntAvanSuppression(int idClient) {
		 return dao.verifEmpruntAvanSuppression(idClient);
	}
	public void ajouterEmprunteur(Subscriber s) {
		dao.ajouterEmprunteur(s);
	}
	public Subscriber getOneEmprunteur(int id) {
		return dao.getOneEmprunteur(id);
	}
	public void modifEmprunteur(Subscriber s) {
		dao.modifEmprunteur(s);
	}
	public  void Emprunte( String numeroIsbn,int numeroCopy, int idClient) {
		dao.Emprunte(numeroIsbn, numeroCopy, idClient);
	}
	public void Restitue( int numeroCopy, int idClient) {
		dao.Restitue( numeroCopy, idClient);
	}
	public ArrayList<Book> RechTitreExact(String name) {
		return dao.RechTitreExact(name);
	}
	public  ArrayList<Book> RechGenre(String genre) {
		return dao.RechGenre(genre);
	}
	public ArrayList<Book> RechAuthor(String auteur){
		return dao.RechAuthor(auteur);
	}
	public ArrayList<Subscriber> recherchNom(String name) {
		return dao.recherchNom(name);
	}
	public  ArrayList<Copy> getAllCopy() {
		return dao.getAllCopy();
	}
	public Book RechIsbn(String ISBN) {
		return dao.RechIsbn(ISBN);
	}
	public Boolean dispoLivre(String ISBN) {
		return dao.dispoLivre(ISBN);
	}
	public Subscriber getSubExemplaire(int numeroCopy) {
		return dao.getSubExemplaire(numeroCopy);
	}
	public Boolean dispoCopy(int idCopy) {
		return dao.dispoCopy(idCopy);
	}
}

