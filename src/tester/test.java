package tester;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Korisnik;
import entiteti.Student;
import entiteti.Usmjerenje;

public class test {

	public static void main(String[] args) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		// SELECT PRIMJER I TEST DATA ADDERA
		Query q1 = em.createQuery("SELECT u FROM Usmjerenje u");
		List<Usmjerenje> usmjerenja = q1.getResultList();
		for(Usmjerenje o : usmjerenja) System.out.println(o.getIDUsmjerenja()+" "+ o.getImeUsmjerenja());
		
		
		// UPDATE PRIMJER
		EntityTransaction updateTransaction = em.getTransaction();
		updateTransaction.begin();
		Query q3 =  em.createQuery("UPDATE Student s SET s.usmjerenje= :uid"+ " WHERE s.IdStudenta= :sid", Student.class).setParameter("uid",usmjerenja.get(0));
		q3.setParameter("sid", 1703);
		int updateCount = q3.executeUpdate();
		if (updateCount > 0) {
			System.out.println("Done...("+updateCount+")");
		}
		updateTransaction.commit();
		
		// SELECT PRIMJER I TEST DATA ADDERA
		Query q2 = em.createQuery("SELECT s FROM Student s");
		List<Student> student = q2.getResultList();
		for(Student o : student) System.out.println(o.getIDStud()+" "+o.getImeStud()+" "+o.getPrezStud());
		
		// SELECT PRIMJER I TEST DATA ADDERA
		Query q4 = em.createQuery("SELECT k FROM Korisnik k WHERE k.isNastavnik=:isn");
		q4.setParameter("isn", true);
		List<Korisnik> korisnik = q4.getResultList();
		for(Korisnik o : korisnik) System.out.println(o.getIme() + "-" + o.getPrezime()+ "-" + o.getUsername() + "-" + o.getPassword() + "-" + o.getEmail());
	}

}
