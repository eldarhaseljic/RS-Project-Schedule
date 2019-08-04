package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Zgrada")
public class Zgrada {
	@Id
	@GeneratedValue
	@Column(name = "ZGRADA_ID")
	private int igradaId;
	
	private String nazivZg;
	private String adresaZg;
	
	@OneToMany(mappedBy="zgrada", cascade = CascadeType.ALL)
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
	
	
}
