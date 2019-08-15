package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Sala")
public class Sala {
	@TableGenerator(name = "idSala", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idSala")
	@Column(name = "SALA_ID")
	private int salaId;

	private String nazivSale;

	@OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
	private Collection<Rezervacija> rezervacija;

	@ManyToOne
	@JoinColumn(name = "ZGRADA_ID")
	private Zgrada zgrada;

	public String getNazivZgrade() {
		return this.zgrada.getNazivZg();
	}

	public String getNazivSale() {
		return nazivSale;
	}

	public void setNazivSale(String nazivSale) {
		this.nazivSale = nazivSale;
	}

	public void setZgrada(Zgrada z) {
		this.zgrada = z;
	}

	public int getSalaId() {
		return salaId;
	}

	public String toString() {
		return this.nazivSale;
	}

}