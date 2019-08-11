package tester;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Grupa;
import entiteti.Korisnik;
import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Student;
import entiteti.Zgrada;

public class Test {

	public static void main(String[] args) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		/*
		 * // SELECT PRIMJER I TEST DATA ADDERA Query q1 =
		 * em.createQuery("SELECT u FROM Usmjerenje u");
		 * 
		 * @SuppressWarnings("unchecked") List<Usmjerenje> usmjerenja =
		 * q1.getResultList(); for (Usmjerenje o : usmjerenja)
		 * System.out.println(o.getIDUsmjerenja() + " " + o.getImeUsmjerenja());
		 * System.out.println();
		 * 
		 * 
		 * // UPDATE PRIMJER EntityTransaction updateTransaction = em.getTransaction();
		 * updateTransaction.begin(); Query q3 = em
		 * .createQuery("UPDATE Student s SET s.usmjerenje= :uid" +
		 * " WHERE s.IdStudenta= :sid", Student.class) .setParameter("uid",
		 * usmjerenja.get(0)); q3.setParameter("sid", 1703); int updateCount =
		 * q3.executeUpdate(); if (updateCount > 0) { System.out.println("Done...(" +
		 * updateCount + ")"); } updateTransaction.commit(); System.out.println();
		 */

		// SELECT PRIMJER I TEST DATA ADDERA
		Query q2 = em.createQuery("SELECT s FROM Student s");
		@SuppressWarnings("unchecked")
		List<Student> student = q2.getResultList();

		for (Student o : student)
			System.out.println(o.getIDStud() + " " + o.getImeStud() + " " + o.getPrezStud());
		System.out.println();

		// SELECT PRIMJER I TEST DATA ADDERA
		Query q4 = em.createQuery("SELECT k FROM Korisnik k WHERE k.isNastavnik=:isn");
		q4.setParameter("isn", true);
		@SuppressWarnings("unchecked")
		List<Korisnik> korisnik = q4.getResultList();
		for (Korisnik o : korisnik)
			System.out.println(o.getIme() + "-" + o.getPrezime() + "-" + o.getUsername() + "-" + o.getPassword() + "-"
					+ o.getEmail());
		System.out.println();

		// TEST DODAVANJA ZGRADA
		Query q5 = em.createQuery("SELECT z FROM Zgrada z");
		@SuppressWarnings("unchecked")
		List<Zgrada> zgrade = q5.getResultList();
		for (Zgrada z : zgrade)
			System.out.println(z.getNazivZg() + "-" + z.getAdresaZg());
		
		//TEST DODAVANJA GRUPA
		Query q6 = em.createQuery("SELECT g FROM GrupaStudenata g");
		@SuppressWarnings("unchecked")
		List<Grupa> grupe = q6.getResultList();
		for(Grupa g : grupe)
		{
			System.out.println(g.getTipgrupe() + "-" + g.getNastavnik().getImeNast() + " " + g.getNastavnik().getPrezNast());
			for(Student s : g.getStudente())
				System.out.println(s.getImeStud() + " " + s.getPrezStud());
		}
		
		Query q7 = em.createQuery("SELECT s FROM Predmet s");
		@SuppressWarnings("unchecked")
		List<Predmet> predmet = q7.getResultList();
		for (Predmet o : predmet)
			System.out.println(o.getImePred());
		System.out.println();
		
		Query q8 = em.createQuery("SELECT n FROM Nastavnik n WHERE n.imeNast = :in", Nastavnik.class);
		q8.setParameter("in", "Amer");
		@SuppressWarnings("unchecked")
		List<Nastavnik> nastavnici = q8.getResultList();
		for(Nastavnik n : nastavnici)
			System.out.println(n.getImeNast() + n.getPredmeti().size());

		em.close();
		emf.close();
	}

}