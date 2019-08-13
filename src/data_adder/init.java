package data_adder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Korisnik;
import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Student;
import entiteti.Usmjerenje;

public class init {

	public static void main(String[] args) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		/*
		// USMJERENJA
		Usmjerenje usmjerenje = new Usmjerenje();
		usmjerenje.setImeUsmjerenja("Racunarstvo i informatika");
		em.getTransaction().begin();
		em.persist(usmjerenje);
		em.getTransaction().commit();

		Usmjerenje usmjerenje2 = new Usmjerenje();
		usmjerenje2.setImeUsmjerenja("Telekomunikacije");
		em.getTransaction().begin();
		em.persist(usmjerenje2);
		em.getTransaction().commit();

		Usmjerenje usmjerenje3 = new Usmjerenje();
		usmjerenje3.setImeUsmjerenja("Automatika i robotika");
		em.getTransaction().begin();
		em.persist(usmjerenje3);
		em.getTransaction().commit();

		Usmjerenje usmjerenje4 = new Usmjerenje();
		usmjerenje4.setImeUsmjerenja("Elektroenergetski sistemi konverzije energije");
		em.getTransaction().begin();
		em.persist(usmjerenje4);
		em.getTransaction().commit();

		Usmjerenje usmjerenje5 = new Usmjerenje();
		usmjerenje5.setImeUsmjerenja("Elektroenergetske mreze i sistemi");
		em.getTransaction().begin();
		em.persist(usmjerenje5);
		em.getTransaction().commit();
<<<<<<< HEAD
*/

		Usmjerenje usmjerenje6 = new Usmjerenje();
		usmjerenje6.setImeUsmjerenja("Biomedicinski inzinjering");
		em.getTransaction().begin();
		em.persist(usmjerenje6);
		em.getTransaction().commit();
		
		//Predmeti
		try {
			FileReader readfile = new FileReader("predmeti.txt");
			BufferedReader readbuffer = new BufferedReader(readfile);
			String s;
			while (readbuffer.read() != -1) {
				Predmet pred = new Predmet();
				s = readbuffer.readLine();
				pred.setImePred(s.toUpperCase());
				em.getTransaction().begin();
				em.persist(pred);
				em.getTransaction().commit();
				}
			readbuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		System.out.println("Orientation added successfully");
		// STUDENTI
		try {
			FileReader readfile = new FileReader("name_rs.txt");
			BufferedReader readbuffer = new BufferedReader(readfile);
			String s;
			while (readbuffer.read() != -1) {
				Student stud = new Student();
				s = readbuffer.readLine();
				String[] parts = s.split(";");
				stud.setImeStud(parts[0]);
				stud.setPrezStud(parts[1]);
				em.getTransaction().begin();
				em.persist(stud);
				em.getTransaction().commit();
				Korisnik studkor = new Korisnik();
				studkor.setIme(stud.getImeStud());
				studkor.setPrezime(stud.getPrezStud());
				studkor.setNastavnik(false);
				studkor.setProdekan(false);
				studkor.setEmail((stud.getImeStud() + "." + stud.getPrezStud() + "@fet.ba").toLowerCase());
				String username = stud.getImeStud() + "." + stud.getPrezStud();
				studkor.setUsername(username.toLowerCase());
				studkor.setPassword(stud.getImeStud().toLowerCase() + "123");
				em.getTransaction().begin();
				em.persist(studkor);
				em.getTransaction().commit();
			}
			readbuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Students added successfully");
		// NASTAVNICI
		try {
			FileReader readfile = new FileReader("name_nast.txt");
			BufferedReader readbuffer = new BufferedReader(readfile);
			String s;
			boolean middle = true;
			while (readbuffer.read() != -1) {
				Nastavnik nast = new Nastavnik();
				s = readbuffer.readLine();
				String[] parts = s.split(";");
				nast.setImeNast(parts[0]);
				nast.setPrezNast(parts[1]);
				String ime = "Emir";
				String prezime = "Meskovic";

				if (nast.getImeNast().equals(ime) && nast.getPrezNast().equals(prezime)) {
					nast.setTitula("Prodekan");
					middle = false;
				} else if (middle) {
					nast.setTitula("Profesor");
				} else {
					nast.setTitula("Asistent");
				}

				em.getTransaction().begin();
				em.persist(nast);
				em.getTransaction().commit();
				Korisnik nastkor = new Korisnik();
				nastkor.setIme(nast.getImeNast());
				nastkor.setPrezime(nast.getPrezNast());

				// Posto sad u bazi ima Emir Meskovic onaj dole dio nam
				// nece ni trebati ali ja sam samo zakomentarisao
				//
				// Haselja

				if (!(nast.getImeNast().equals(ime) && nast.getPrezNast().equals(prezime))) {
					nastkor.setNastavnik(true);
					nastkor.setProdekan(false);
				} else {
					nastkor.setNastavnik(false);
					nastkor.setProdekan(true);
				}

				nastkor.setEmail((nast.getImeNast() + "." + nast.getPrezNast() + "@fet.ba").toLowerCase());
				String username = nast.getImeNast() + "." + nast.getPrezNast();
				nastkor.setUsername(username.toLowerCase());
				nastkor.setPassword(nast.getImeNast().toLowerCase() + "123");
				em.getTransaction().begin();
				em.persist(nastkor);
				em.getTransaction().commit();
			}
			readbuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Professors added successfully");
		// Predmeti
		try {
			FileReader readfile = new FileReader("predmeti.txt");
			BufferedReader readbuffer = new BufferedReader(readfile);
			String s;
			while (readbuffer.read() != -1) {
				s = readbuffer.readLine();
				String[] parts = s.split(";");

				 //System.out.println(parts[0]);
				// System.out.println(parts[1]);

				String[] naziv = parts[2].split(" ");
				// System.out.println(naziv[0]+" "+naziv[1]);

				Collection<Nastavnik> nastavnici = new ArrayList<Nastavnik>();

				Query q2 = em.createQuery("SELECT n FROM Nastavnik n WHERE n.imeNast "
										+ "	= :a and n.prezNast = :b and (n.titula = :c or n.titula = :d)",
						Nastavnik.class);
				q2.setParameter("a", naziv[0]);
				q2.setParameter("b", naziv[1]);
				q2.setParameter("c", "Profesor");
				q2.setParameter("d", "Prodekan");
				
				@SuppressWarnings("unchecked")
				List<Nastavnik> n = q2.getResultList();
				nastavnici.add((Nastavnik) n.get(0));

				Collection<Usmjerenje> usmjerenja = new ArrayList<Usmjerenje>();

				Query q3 = em.createQuery("SELECT n FROM Usmjerenje n WHERE n.imeUsmjerenja = :in", Usmjerenje.class);

				q3.setParameter("in", parts[1]);
				@SuppressWarnings("unchecked")
				List<Usmjerenje> a = q3.getResultList();
				usmjerenja.add((Usmjerenje) a.get(0));

				Predmet novi = new Predmet();
				novi.setImePred(parts[0].toUpperCase());
				novi.setNastavnici(nastavnici);
				novi.setUsmjerenja(usmjerenja);
				em.getTransaction().begin();
				em.persist(novi);
				em.getTransaction().commit();
			}
			readbuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Subjects added successfully");
		/*
		 * //PRODEKAN Korisnik nastkor = new Korisnik(); String ime = "Emir"; String
		 * prez = "Meskovic"; nastkor.setIme(ime); nastkor.setPrezime(prez);
		 * nastkor.setNastavnik(false); nastkor.setProdekan(true);
		 * nastkor.setEmail(ime+"."+prez+"@gmail.com"); String username = ime + "." +
		 * prez; nastkor.setUsername(username.toLowerCase());
		 * nastkor.setPassword(ime.toLowerCase()+"123"); em.getTransaction().begin();
		 * em.persist(nastkor); em.getTransaction().commit();
		 */
		em.close();
		emf.close();
	}

}