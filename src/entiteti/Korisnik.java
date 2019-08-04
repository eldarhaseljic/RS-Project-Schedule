package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="Korisnik")
public class Korisnik {
	
	@Id
	@GeneratedValue
	@Column(name = "KORISNIK_ID")
	private int id;
	
	private String ime;
	private String prezime;
	private boolean isNastavnik;
	private boolean isProdekan;
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public boolean isNastavnik() {
		return isNastavnik;
	}
	public void setNastavnik(boolean isNastavnik) {
		this.isNastavnik = isNastavnik;
	}
	public boolean isProdekan() {
		return isProdekan;
	}
	public void setProdekan(boolean isProdekan) {
		this.isProdekan = isProdekan;
	}
	

}
