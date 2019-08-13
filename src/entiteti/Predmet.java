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
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name="Predmet")
public class Predmet {
	@TableGenerator(
			name = "idPred",
			allocationSize = 1,
			initialValue = 1)
	@Id 
	@GeneratedValue(
			strategy=GenerationType.TABLE, 
			generator="idPred")
	@Column(name = "PREDMET_ID")
	private int IdPredmeta;
	
	private String imePred;
	
	@OneToMany(mappedBy = "predmet",cascade = CascadeType.ALL)
	private Collection<Grupa> grupe;

	@ManyToMany
	@JoinTable(name = "lista_predmeta_nastavnika", 
				joinColumns = @JoinColumn(name="PREDMET_ID"),
				inverseJoinColumns=@JoinColumn(name = "NASTAVNIK_ID"))
	private Collection<Nastavnik> nastavnici;
	
	@ManyToMany
	@JoinTable(name = "lista_predmeta_usmjerenja", 
				joinColumns = @JoinColumn(name="PREDMET_ID"),
				inverseJoinColumns=@JoinColumn(name = "USMJERENJE_ID"))
	private Collection<Usmjerenje> usmjerenja;
	
	@ManyToMany
	@JoinTable(name = "lista_predmeta_semestara", 
	            joinColumns = @JoinColumn(name = "PREDMET_ID"), 
	            inverseJoinColumns = @JoinColumn(name = "SEMESTAR_ID"))
	private Collection<Semestar> semestri;
	
	public Predmet(String ime, Collection<Nastavnik> n, Collection<Usmjerenje> u, Collection<Semestar> s){
		this.imePred = ime;
		this.nastavnici = n;
		this.semestri = s;
		this.usmjerenja =u;
	}
	public Predmet() {}

	public void setNastavnici(Collection<Nastavnik> nastavnici) {
		this.nastavnici = nastavnici;
	}
	public void setUsmjerenja(Collection<Usmjerenje> usmjerenja) {
		this.usmjerenja = usmjerenja;
	}
	public String getImePred() {
		return imePred;
	}

	public void setImePred(String imePred) {
		this.imePred = imePred;
	}
	
	public int getId() { 
		return IdPredmeta;
	}
	
	public String toString() { 
		return imePred;
	}

	public Collection<Grupa> getGrupe() {
		return grupe;
	}
	
	public Collection<Usmjerenje> getUsmjerenja() {
		return usmjerenja;
	}
	
	public String getImeUsmjerenja() {
		Collection<Usmjerenje> u = this.getUsmjerenja();
		String temp = "";
		
		for(Usmjerenje usm : u)
		{
			temp += usm.getImeUsmjerenja() + "\n";
		}

		return temp;
 	}
	
	public Collection<Nastavnik> getNastavnike() {
		return nastavnici;
	}
	
	public String getImenaProfesora() {
		Collection<Nastavnik> n = this.getNastavnike();
		String temp = "";
		
		for(Nastavnik nast : n)
		{
			temp += nast.toString() + "\n";
		}
		
		return temp;
	}
}