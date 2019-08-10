package entiteti;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Semestar")
public class Semestar {
	@TableGenerator(name = "idSem", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idSem")
	@Column(name = "SEMESTAR_ID")
	private int IdSemestra;

	private LocalDate datumPocetkaSemestra;
	private LocalDate datumZavrsetkaSemestra;
	private String oznakaSemestra;

	@OneToMany(mappedBy = "semestar", cascade = CascadeType.ALL)
	private Collection<Cas> casovi;

	@OneToMany(mappedBy = "semestar", cascade = CascadeType.ALL)
	private Collection<Student> studenti;

	public String getOznakaSemestra() {
		return oznakaSemestra;
	}

	public void setOznakaSemestra(String oznakaSemestra) {
		this.oznakaSemestra = oznakaSemestra;
	}

	public LocalDate getDatumPocetkaSemestra() {
		return datumPocetkaSemestra;
	}

	public void setDatumPocetkaSemestra(LocalDate datumPocetkaSemestra) {
		this.datumPocetkaSemestra = datumPocetkaSemestra;
	}

	public LocalDate getDatumZavrsetkaSemestra() {
		return datumZavrsetkaSemestra;
	}

	public void setDatumZavrsetkaSemestra(LocalDate datumZavrsetkaSemestra) {
		this.datumZavrsetkaSemestra = datumZavrsetkaSemestra;
	}

	public int getIDSemestra() {
		return IdSemestra;
	}
}
