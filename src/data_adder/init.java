package data_adder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

		//USMJERENJA
		Usmjerenje usmjerenje = new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Racunarstvo i informatika");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();
		
		Usmjerenje usmjerenje2 = new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Telekomunikacije");
		em.getTransaction().begin();
		em.persist(usmjerenje2);
		em.getTransaction().commit();
		
		Usmjerenje usmjerenje3 = new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Automatika i robotika");
		em.getTransaction().begin();
		em.persist(usmjerenje3);
		em.getTransaction().commit();
		
		Usmjerenje usmjerenje4 = new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Elektroenergetski sistemi konverzije energije");
		em.getTransaction().begin();
		em.persist(usmjerenje4);
		em.getTransaction().commit();
		
		Usmjerenje usmjerenje5= new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Elektroenergetske mreze i sistemi");
		em.getTransaction().begin();
		em.persist(usmjerenje5);
		em.getTransaction().commit();
		
		// STUDENTI
		try {	
		      FileReader readfile = new FileReader("name_rs.txt");
		      BufferedReader readbuffer = new BufferedReader(readfile);
		      String s;
		      while(readbuffer.read()!=-1)
			  {
		    	  Student stud = new Student();
		    	  s=readbuffer.readLine();
		    	  String[] parts = s.split(";");
		    	  stud.setImeStud(parts[0]);
		    	  stud.setPrezStud(parts[1]);
		    	  em.getTransaction().begin();
		  		  em.persist(stud);
		  		  em.getTransaction().commit();
			  }
		} catch (IOException e) {
		      e.printStackTrace();
		}
		//NASTAVNICI
		try {	
		      FileReader readfile = new FileReader("name_nast.txt");
		      BufferedReader readbuffer = new BufferedReader(readfile);
		      String s;
		      while(readbuffer.read()!=-1)
			  {
		    	  Nastavnik nast= new Nastavnik();
		    	  s=readbuffer.readLine();
		    	  String[] parts = s.split(";");
		    	  nast.setImeNast(parts[0]);
		    	  nast.setPrezNast(parts[1]);
		    	  em.getTransaction().begin();
		  		  em.persist(nast);
		  		  em.getTransaction().commit();
			  }
		} catch (IOException e) {
		      e.printStackTrace();
		}
		Query q1 = em.createQuery("SELECT u FROM Usmjerenje u");
		List<Usmjerenje> usmjerenja = q1.getResultList();
		for(Usmjerenje o : usmjerenja) {System.out.println(o.getIDUsmjerenja()+" "+ o.getImeUsmjerenja());}
		System.out.println("gotovo");
	}

}
