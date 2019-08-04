package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="GrupaStudenata")
public class Grupa {
	
	@Id
	@GeneratedValue
	@Column(name = "GRUPA_ID")
	private int idGrupe;
	
	private String tipgrupe;
	
	@OneToMany(mappedBy = "grupa",cascade = CascadeType.ALL)
	private Collection<Rezervacija> rezervacije;
	
		
	@ManyToMany
	@JoinTable(name = "lista_studenata_grupa", 
				joinColumns = @JoinColumn(name="GRUPA_ID"),
				inverseJoinColumns=@JoinColumn(name = "STUDENT_ID"))
	private Collection<Student> studenti;

	// S obzirom na to da je u slijedecem moguce doci do redundancije, potrebno je svaki put provjeriti da li trazeni 
	// nastavnik/predmet predaje predmet/nastavnik.
	
	@ManyToOne
	@JoinColumn(name = "PREDMET_ID")
	private Predmet predmet;
	
	@ManyToOne
	@JoinColumn(name = "NASTAVNIK_ID")
	private Nastavnik nastavnik;
	
	
	@OneToMany(mappedBy = "grupa",cascade = CascadeType.ALL)
	private Collection<Cas> casovi;


	public String getTipgrupe() {
		return tipgrupe;
	}


	public void setTipgrupe(String tipgrupe) {
		this.tipgrupe = tipgrupe;
	}





	
}
