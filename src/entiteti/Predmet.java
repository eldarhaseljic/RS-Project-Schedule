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
	private Collection<Nastavnik> predmeti;
	
	@ManyToMany
	@JoinTable(name = "lista_predmeta_usmjerenja", 
				joinColumns = @JoinColumn(name="PREDMET_ID"),
				inverseJoinColumns=@JoinColumn(name = "USMJERENJE_ID"))
	private Collection<Usmjerenje> usmjerenja;

	public String getImePred() {
		return imePred;
	}

	public void setImePred(String imePred) {
		this.imePred = imePred;
	}
	
}
