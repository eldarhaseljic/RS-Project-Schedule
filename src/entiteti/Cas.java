package entiteti;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Cas")
public class Cas {
	
	@Id
	@GeneratedValue
	@Column(name = "CAS_ID")
	private int IdCasa;
	
	private LocalDate datumOdrzavanjaCasa;
	private LocalTime vrijemePocetkaCasa;
	private LocalDate vrijemeZavrsetkaCasa;
	
	@ManyToOne
	@JoinColumn(name = "GRUPA_ID")
	private Grupa grupa;
	
	@ManyToOne
	@JoinColumn(name = "SEMESTAR_ID")
	private Semestar semestar;

	public LocalDate getVrijemeZavrsetkaCasa() {
		return vrijemeZavrsetkaCasa;
	}

	public void setVrijemeZavrsetkaCasa(LocalDate vrijemeZavrsetkaCasa) {
		this.vrijemeZavrsetkaCasa = vrijemeZavrsetkaCasa;
	}

	public LocalTime getVrijemePocetkaCasa() {
		return vrijemePocetkaCasa;
	}

	public void setVrijemePocetkaCasa(LocalTime vrijemePocetkaCasa) {
		this.vrijemePocetkaCasa = vrijemePocetkaCasa;
	}

	public LocalDate getDatumOdrzavanjaCasa() {
		return datumOdrzavanjaCasa;
	}

	public void setDatumOdrzavanjaCasa(LocalDate datumOdrzavanjaCasa) {
		this.datumOdrzavanjaCasa = datumOdrzavanjaCasa;
	}


	
}
