package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity(name = "Korisnik")
public class Korisnik {

	@TableGenerator(name = "idKorisnik", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idKorisnik")
	@Column(name = "KORISNIK_ID")
	private int id;

	private String ime;
	private String prezime;
	private boolean isNastavnik;
	private boolean isProdekan;
	private String username;
	private String password;
	private String email;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
