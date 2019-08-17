package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Zgrada")
public class Zgrada {
	@TableGenerator(name = "idZgrada", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idZgrada")
	@Column(name = "ZGRADA_ID")
	private int zgradaId;

	private String nazivZg;
	private String adresaZg;

	@OneToMany(mappedBy = "zgrada", cascade = CascadeType.ALL)
	private Collection<Sala> reference;

	public String getNazivZg() {
		return nazivZg;
	}

	public void setNazivZg(String nazivZg) {
		this.nazivZg = nazivZg;
	}

	public String getAdresaZg() {
		return adresaZg;
	}

	public void setAdresaZg(String adresaZg) {
		this.adresaZg = adresaZg;
	}

	public int getZgradaId() {
		return zgradaId;
	}

	public Collection<Sala> getSale() {
		return reference;
	}

}