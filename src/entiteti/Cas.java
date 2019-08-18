package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity(name = "Cas")
public class Cas {

	@TableGenerator(name = "idCas", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idCas")
	@Column(name = "CAS_ID")
	private int IdCasa;

	private String datumOdrzavanjaCasa;
	private int vrijemePocetkaCasaSat;
	private int vrijemeZavrsetkaCasaSat;
	private int vrijemePocetkaCasaMinuta;
	private int vrijemeZavrsetkaCasaMinuta;

	@ManyToOne
	@JoinColumn(name = "GRUPA_ID")
	private Grupa grupa;

	@ManyToOne
	@JoinColumn(name = "SEMESTAR_ID")
	private Semestar semestar;

	@OneToOne
	@JoinColumn(name = "SALA_ID")
	private Sala sala;

	public int getVrijemeZavrsetkaCasaSat() {
		return vrijemeZavrsetkaCasaSat;
	}

	public void setVrijemeZavrsetkaCasaSat(int vrijemeZavrsetkaCasa) {
		this.vrijemeZavrsetkaCasaSat = vrijemeZavrsetkaCasa;
	}

	public int getvrijemePocetkaCasaSat() {
		return vrijemePocetkaCasaSat;
	}

	public void setvrijemePocetkaCasaSat(int vrijemePocetkaCasa) {
		this.vrijemePocetkaCasaSat = vrijemePocetkaCasa;
	}

	public String getDatumOdrzavanjaCasa() {
		return datumOdrzavanjaCasa;
	}

	public void setDatumOdrzavanjaCasa(String datumOdrzavanjaCasa) {
		this.datumOdrzavanjaCasa = datumOdrzavanjaCasa;
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

	public void setGrupa(Grupa gr) {
		this.grupa = gr;
	}

	public void setSemestar(Semestar sem) {
		this.semestar = sem;
	}

	public void setSala(Sala sal) {
		this.sala = sal;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return IdCasa;
	}

	public String toString() {
		return this.sala.getNazivSale() + " " + this.grupa.getImePredmeta().split(";")[0] + " "
				+ this.grupa.getNastavnik().toString() + " " + this.getDatumOdrzavanjaCasa() + " "
				+ this.getvrijemePocetkaCasaSat() + ":" + this.getVrijemePocetkaCasaMinuta() + " - "
				+ this.getVrijemeZavrsetkaCasaSat() + ":" + this.getVrijemeZavrsetkaCasaMinuta();
	}

	public Grupa getGrupa() {
		return grupa;
	}

	public String getImePredmeta() {
		return this.getGrupa().getImePredmeta();
	}

	public String getBrojStudenata() {
		int br = this.getGrupa().getStudente().size();
		return Integer.toString(br);
	}

	public String getImeSale() {
		return this.sala.getNazivSale();
	}

	public Semestar getSemestar() {
		return semestar;
	}

	public String getVrijeme() {
		return this.getDatumOdrzavanjaCasa() + " " + this.vrijemePocetkaCasaSat + ":" + this.vrijemePocetkaCasaMinuta
				+ "-" + this.vrijemeZavrsetkaCasaSat + ":" + this.vrijemeZavrsetkaCasaMinuta;
	}

	public String getImeNastavnika() {
		return this.getGrupa().getImeNastavnika();
	}

	public String getTip() {
		return this.getGrupa().getTipgrupe();
	}

}