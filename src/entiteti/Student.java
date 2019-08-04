package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity(name ="Student")
public class Student {
	@TableGenerator(
		name = "index",
		allocationSize = 1,
		initialValue = 1700)
	@Id 
	@GeneratedValue(
		strategy=GenerationType.TABLE, 
		generator="index")
	@Column(name = "STUDENT_ID")
	private int IdStudenta;
	
	private String imeStud;
	private String prezStud;
	
	@ManyToOne
	@JoinColumn(name = "USMJERENJE_ID")
	private Usmjerenje usmjerenje;
	
	@ManyToOne
	@JoinColumn(name = "SEMESTAR_ID")
	private Semestar semestar;
	
	public String getImeStud() {
		return imeStud;
	}
	public void setImeStud(String imeStud) {
		this.imeStud = imeStud;
	}
	public String getPrezStud() {
		return prezStud;
	}
	public void setPrezStud(String prezStud) {
		this.prezStud = prezStud;
	}
	
	public int getIDStud() {
		return IdStudenta;
	}
	public void setSemestar(Usmjerenje usmjerenje) {
		this.usmjerenje = usmjerenje;
	}
	public void setUsmjerenje(Semestar semestar) {
		this.semestar = semestar;
	}
}
