package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name="Nastavnik")
public class Nastavnik {
	@Id
	@GeneratedValue
	@Column(name = "NASTAVNIK_ID")
	private int IdNastavnika;
	
	private String imeNast;
	private String prezNast;
	
	@OneToMany(mappedBy = "nastavnik",cascade = CascadeType.ALL)
	private Collection<Grupa> grupe;

	@ManyToMany(mappedBy = "predmeti")
	private Collection<Predmet> predmeti;
	
	@OneToMany(mappedBy = "nastavnik",cascade = CascadeType.ALL)
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
	
}
