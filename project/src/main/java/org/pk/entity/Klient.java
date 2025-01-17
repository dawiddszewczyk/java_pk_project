package org.pk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Klasa służąca mapowaniu encji bazodanowej klient na obiekt javy
 * jest połączona relacją 1:n z wypożyczeniami
 */
@SuppressWarnings("serial")
@Table(name="klient")
@Entity
public class Klient implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idklienta")
	private int id;
	@Column(name="imie")
	private String imie;
	@Column(name="nazwisko")
	private String nazwisko;
	@Column(name="email")
	private String email;
	@Column(name="haslo")
	private String haslo;
	@Column(name="zadluzenie")
	private double zadluzenie;
	
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "klient",
		cascade = {CascadeType.MERGE, CascadeType.REMOVE}
	)
	private List<Wypozyczenie> wypozyczenia;
	
	// Pusty konstruktor dla Hibernate
	public Klient() {
		
	}
	
	public Klient(String imie, String nazwisko, String email) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
	}

	public Klient(int id, String imie, String nazwisko, String email) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
	}
	
	public Klient(int id, String imie, String nazwisko, String email, String haslo) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
		this.haslo = haslo;
	}
	
	public Klient(String imie, String nazwisko, String email, String haslo, double zadluzenie) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
		this.haslo = haslo;
		this.zadluzenie=zadluzenie;
	}
	
	/**
	 * Metoda służąca do stworzenia połączenia (referencje) między wypożyczeniem, pojazdem i klientem
	 * @param wypozyczenie przyjmuje stworzone wypożyczenie, z przypisanym mu klientem i pojazdem
	 */
	public void dodajWypozyczenie2S(Wypozyczenie wypozyczenie) {
		if(wypozyczenie==null) wypozyczenia = new ArrayList<>();
		wypozyczenia.add(wypozyczenie);
		wypozyczenie.getPojazd().dodajWypozyczenie1S(wypozyczenie);
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Wypozyczenie> getWypozyczenia() {
		return wypozyczenia;
	}
	public void setWypozyczenia(List<Wypozyczenie> wypozyczenia) {
		this.wypozyczenia = wypozyczenia;
	}
	public String getHaslo() {
		return haslo;
	}
	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	public double getZadluzenie() {
		return zadluzenie;
	}
	public void setZadluzenie(double zadluzenie) {
		this.zadluzenie = zadluzenie;
	}

	@Override
	public String toString() {
		return "Klient [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", email=" + email + ", wypozyczenia="
				+ wypozyczenia + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Klient other = (Klient) obj;
		return id == other.id;
	}
}
