package application;

import java.time.LocalDate;

public class Period {
	
	private LocalDate datum;
	private String predmet;
	private String brstud;
	private String mjesto;
	private Integer predavanje;
	private Integer vjezbe;
	
	public Period(LocalDate d, String pred, String b, String m, Integer p, Integer v)
	{
		this.datum = d;
		this.predmet = pred;
		this.brstud = b;
		this.mjesto = m;
		this.predavanje = p;
		this.vjezbe = v;
	}
	
	public LocalDate getDatum() {
		return datum;
	}
	
	public String getPredmet() {
		return predmet;
	}
	
	public String getMjesto() {
		return mjesto;
	}
	
	public String getBrstud() {
		return brstud;
	}
	
	public Integer getPredavanje() {
		return predavanje;
	}
	
	public Integer getVjezbe() {
		return vjezbe;
	}

}
