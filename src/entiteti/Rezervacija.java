package entiteti;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name = "Rezervacija")
public class Rezervacija {
	@TableGenerator(name = "idRezerv", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idRezerv")
	@Column(name = "REZERVACIJA_ID")
	private int IdRezervacije;

	private String tipRezervacije; // seminar,simpozij,nadoknada,diplomski

	@ManyToOne
	@JoinColumn(name = "NASTAVNIK_ID")
	private Nastavnik nastavnik;

	@ManyToOne
	@JoinColumn(name = "GRUPA_ID")
	private Grupa grupa;

	@ManyToOne
	@JoinColumn(name = "SALA_ID")
	private Sala sala;

	private String vrijemeTrajanja;
	private LocalDate datumOdrzavanja;
	private int vrijemePocetkaCasaSat;

@SuppressWarnings("unused")
	private int vrijemeZavrsetkaCasaSat;
	private int vrijemePocetkaCasaMinuta;
	private int vrijemeZavrsetkaCasaMinuta;

	public void setVrijemeZavrsetkaCasaSat(int vrijemeZavrsetkaCasa) {
		this.vrijemeZavrsetkaCasaSat = vrijemeZavrsetkaCasa;
	}

	public int getvrijemePocetkaCasaSat() {
		return vrijemePocetkaCasaSat;
	}

	public void setvrijemePocetkaCasaSat(int vrijemePocetkaCasa) {
		this.vrijemePocetkaCasaSat = vrijemePocetkaCasa;
	}

	public int getVrijemePocetkaCasaMinuta() {
		return vrijemePocetkaCasaMinuta;
	}

	public void setVrijemePocetkaCasaMinuta(int vrijemePocetkaCasaMinuta) {
		this.vrijemePocetkaCasaMinuta = vrijemePocetkaCasaMinuta;
	}

	public int getVrijemeZavrsetkaCasaMinuta() {
		return vrijemeZavrsetkaCasaMinuta;
	}

	public void setVrijemeZavrsetkaCasaMinuta(int vrijemeZavrsetkaCasaMinuta) {
		this.vrijemeZavrsetkaCasaMinuta = vrijemeZavrsetkaCasaMinuta;
	}

	public int getVrijemeZavrsetkaCasaSat(){
		return this.vrijemeZavrsetkaCasaSat;
	}
	
	public String getTipRezervacije() {
		return tipRezervacije;
	}

	public void setTipRezervacije(String tipRezervacije) {
		this.tipRezervacije = tipRezervacije;
	}

	public LocalDate getDatumOdrzavanja() {
		return datumOdrzavanja;
	}

	public void setDatumOdrzavanja(LocalDate datumOdrzavanja) {
		this.datumOdrzavanja = datumOdrzavanja;
	}

	public void setVrijemeTrajanja() {
		this.vrijemeTrajanja = vrijemePocetkaCasaSat+":"+vrijemePocetkaCasaMinuta+" - "+vrijemeZavrsetkaCasaSat+":"+vrijemeZavrsetkaCasaMinuta;
	}

	public String getVrijemeTrajanja() {return vrijemeTrajanja;}
	
	public String toString() { return this.datumOdrzavanja + " " + this.sala + " " + this.getVrijemeTrajanja();}
	
	
	public void setGrupa(Grupa g) { grupa = g;}
	public Grupa getGrupa(){ return grupa;}
	public Nastavnik getNastavnik() { return nastavnik;}
	
	public void setSala(Sala g) { sala = g;}
	
	public void setNastavnik(Nastavnik n) { nastavnik = n;}
	

}
