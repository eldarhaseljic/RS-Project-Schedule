package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "GrupaStudenata")
public class Grupa {

	@TableGenerator(name = "idGr", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGr")
	@Column(name = "GRUPA_ID")
	private int idGrupe;

	private String tipgrupe;

	@OneToMany(mappedBy = "grupa", cascade = CascadeType.ALL)
	private Collection<Rezervacija> rezervacije;

	@ManyToMany
	@JoinTable(name = "lista_studenata_grupa", joinColumns = @JoinColumn(name = "GRUPA_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private Collection<Student> studenti;

	// S obzirom na to da je u slijedecem moguce doci do redundancije, potrebno je
	// svaki put provjeriti da li trazeni
	// nastavnik/predmet predaje predmet/nastavnik.

	@ManyToOne
	@JoinColumn(name = "PREDMET_ID")
	private Predmet predmet;

	@ManyToOne
	@JoinColumn(name = "NASTAVNIK_ID")
	private Nastavnik nastavnik;

	@OneToMany(mappedBy = "grupa", cascade = CascadeType.ALL)
	private Collection<Cas> casovi;

	public String getTipgrupe() {
		return tipgrupe;
	}

	public void setTipgrupe(String tipgrupe) {
		this.tipgrupe = tipgrupe;
	}

	public void setStudente(Collection<Student> s) {
		this.studenti = s;
	}

	public void setNastavnika(Nastavnik n) {
		this.nastavnik = n;
	}

	public void setPredmet(Predmet p) {
		this.predmet = p;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}

	public Collection<Student> getStudente() {
		return studenti;
	}

	public String getImePredmeta() {
		return this.getPredmet().toString();
	}

	public String getImeNastavnika() {
		return this.getNastavnik().toString();
	}

	public int getId() {
		return idGrupe;
	}

	public String getImenaStudenata() {
		Collection<Student> s = this.getStudente();
		String temp = "";
		for (Student stud : s)
			temp += stud.getImeStud() + " " + stud.getPrezStud() + "\n";
		return temp;
	}

	public String toString() {
		return this.getImePredmeta() + " GR" + this.getId();
	}
}
