package entiteti;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name = "Usmjerenje")
public class Usmjerenje {
	@TableGenerator(name = "idUsm", allocationSize = 1, initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idUsm")
	@Column(name = "USMJERENJE_ID")
	private int IdUsmjerenja;

	private String imeUsmjerenja;

	@ManyToMany(mappedBy = "usmjerenja")
	private Collection<Predmet> predmeti;

	@OneToMany(mappedBy = "usmjerenje", cascade = CascadeType.ALL)
	private Collection<Student> student;

	public String getImeUsmjerenja() {
		return imeUsmjerenja;
	}

	public void setImeUsmjerenja(String imeUsmjerenja) {
		this.imeUsmjerenja = imeUsmjerenja;
	}

	public int getIDUsmjerenja() {
		return IdUsmjerenja;
	}

	public String toString () { return this.imeUsmjerenja;}
}
