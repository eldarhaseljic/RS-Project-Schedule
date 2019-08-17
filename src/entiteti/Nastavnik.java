package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Nastavnik")
public class Nastavnik {
	@TableGenerator(name = "idNast", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idNast")
	@Column(name = "NASTAVNIK_ID")
	private int IdNastavnika;

	private String imeNast;
	private String prezNast;
	private String titula;

	@OneToMany(mappedBy = "nastavnik", cascade = CascadeType.ALL)
	private Collection<Grupa> grupe;

	@ManyToMany(mappedBy = "nastavnici")
	private Collection<Predmet> predmeti;

	@OneToMany(mappedBy = "nastavnik", cascade = CascadeType.ALL)
	private Collection<Rezervacija> rezervacije;

	public String getImeNast() {
		return imeNast;
	}

	public void setImeNast(String imeNast) {
		this.imeNast = imeNast;
	}

	public String getPrezNast() {
		return prezNast;
	}

	public void setPrezNast(String prezNast) {
		this.prezNast = prezNast;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	public Collection<Grupa> getGrupe() {
		return grupe;
	}

	public String toString() {
		return this.imeNast + " " + this.prezNast;
	}

	public Collection<Predmet> getPredmeti() {
		return predmeti;
	}

	public String getIme() {
		return this.toString();
	}

	public int getId() {
		return IdNastavnika;
	}
}