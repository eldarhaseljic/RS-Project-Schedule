package data_adder;

import java.util.List;
import entiteti.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class init {

	public static void main(String[] args) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Usmjerenje usmjerenje = new Usmjerenje();
		Semestar semestar = new Semestar();
/*		USMJERENJA !!!
		usmjerenje.setImeUsmjerenja("Racunarstvo i informatika");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		usmjerenje.setImeUsmjerenja("Telekomunikacije");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		usmjerenje.setImeUsmjerenja("Automatika i robotika");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		usmjerenje.setImeUsmjerenja("Elektroenergetski sistemi konverzije energije");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		usmjerenje.setImeUsmjerenja("Elektroenergetske mreze i sistemi");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
*/
		semestar.setDatumPocetkaSemestra(null);
		semestar.setDatumZavrsetkaSemestra(null);
		semestar.setOznakaSemestra(1);
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		
		Query q1 = em.createQuery("SELECT u FROM Usmjerenje u");
		List<Usmjerenje> usmjerenja = q1.getResultList();
		for(Usmjerenje o : usmjerenja) {System.out.println(o.getIDUsmjerenja()+" "+ o.getImeUsmjerenja());}
		System.out.println("gotovo");
	}

}
