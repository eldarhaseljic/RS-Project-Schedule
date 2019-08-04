package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Sala")
public class Sala {
	@Id
	@GeneratedValue
	@Column(name = "SALA_ID")
	private int salaId;

	private String nazivSale;
	
	@OneToMany(mappedBy = "sala",cascade = CascadeType.ALL)
	private Collection<Rezervacija> rezervacija;
	
	@ManyToOne 
	@JoinColumn(name = "ZGRADA_ID")
	private Zgrada zgrada;
	
	public String getNazivSale() {
		return nazivSale;
	}

	public void setNazivSale(String nazivSale) {
		this.nazivSale = nazivSale;
	}
	
}
