package entiteti;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Rezervacija")
public class Rezervacija {
	@Id
	@GeneratedValue
	@Column(name = "REZERVACIJA_ID")
	private int IdRezervacije;
	
	private String tipRezervacije; //seminar,simpozij,nadoknada,diplomski
	
	@ManyToOne
	@JoinColumn(name = "NASTAVNIK_ID")
	private Nastavnik nastavnik;
	
	@ManyToOne
	@JoinColumn(name = "GRUPA_ID")
	private Grupa grupa;
	
	@ManyToOne
	@JoinColumn(name = "SALA_ID")
	private Sala sala;
	
	private LocalDate datumOdrzavanja;
	private LocalTime vrijemePocetka;
	private LocalDate vrijemeZavrsetka;
	
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
	public LocalTime getVrijemePocetka() {
		return vrijemePocetka;
	}
	public void setVrijemePocetka(LocalTime vrijemePocetka) {
		this.vrijemePocetka = vrijemePocetka;
	}
	public LocalDate getVrijemeZavrsetka() {
		return vrijemeZavrsetka;
	}
	public void setVrijemeZavrsetka(LocalDate vrijemeZavrsetka) {
		this.vrijemeZavrsetka = vrijemeZavrsetka;
	}
	
}
