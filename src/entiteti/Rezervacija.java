package entiteti;

import java.time.LocalDate;
import java.time.LocalTime;

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

	private LocalDate datumOdrzavanja;
	private LocalTime vrijemePocetka;
	private LocalTime vrijemeZavrsetka;

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

	public LocalTime getVrijemeZavrsetka() {
		return vrijemeZavrsetka;
	}

	public void setVrijemeZavrsetka(LocalTime vrijemeZavrsetka) {
		this.vrijemeZavrsetka = vrijemeZavrsetka;
	}

}