package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Nastavnik;
import entiteti.Semestar;
import entiteti.Usmjerenje;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdekanController implements Initializable {

	// Sluzi za prosljedjivanje informacije
	public static String Information;

	@FXML
	private Label usr;

	@FXML
	private Label email;

	@FXML
	private Label titula;
	public static List<?> temp_list;

	public void close(ActionEvent event) throws Exception {
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usr.setText(MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime());
		email.setText(MainController.trenutniKor.getEmail());

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT n FROM Nastavnik n");
		@SuppressWarnings("unchecked")
		List<Nastavnik> nastavnici = q.getResultList();
		for (Nastavnik n : nastavnici) {
			if (MainController.trenutniKor.getIme().equals(n.getImeNast())
					&& MainController.trenutniKor.getPrezime().equals(n.getPrezNast()))
				titula.setText(n.getTitula());
		}

		em.close();
		emf.close();
	}

	public void addBuilding(ActionEvent event) throws Exception {
		show(event, "/fxml_files/addBuildingScreen.fxml", "New Building");
	}

	public void deleteBuilding(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no buildings !";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteBuildingScreen.fxml", "Delete a Building");
		}
		em.close();
		emf.close();
	}

	public void addHall(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no buildings,to add a hall into it!";
			show(event, "/fxml_files/Info.fxml", "Error");
		} else {
			show(event, "/fxml_files/addHallScreen.fxml", "New Hall");
		}
	}

	public void deleteHall(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no buildings,no halls also!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteHallScreen.fxml", "Delete a Hall");
		}
		em.close();
		emf.close();
	}

	public void addOrientation(ActionEvent event) throws Exception {
		show(event, "/fxml_files/addOrientationScreen.fxml", "New Orientation");
	}

	public void deleteOrientation(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT u FROM Usmjerenje u");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no orientations!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteOrientationScreen.fxml", "Delete Orientation");
		}
		em.close();
		emf.close();
	}

	public void addSemester(ActionEvent event) throws Exception {
		show(event, "/fxml_files/addSemesterScreen.fxml", "New Semester");
	}

	public void deleteSemester(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Semestar s");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no semesters!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteSemesterScreen.fxml", "Delete Semester");
		}
		em.close();
		emf.close();
	}

	public void addSubject(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT x FROM Nastavnik x");
		@SuppressWarnings("unchecked")
		List<Nastavnik> temp_list = q.getResultList();

		Query q2 = em.createQuery("SELECT x FROM Usmjerenje x");
		@SuppressWarnings("unchecked")
		List<Usmjerenje> temp2_list = q2.getResultList();
		
		Query q3 = em.createQuery("SELECT x FROM Semestar x");
		@SuppressWarnings("unchecked")
		List<Semestar> temp3_list = q3.getResultList();
		
		em.close();
		emf.close();
		
		if(temp_list.isEmpty())
		{	
			ProdekanController.Information ="You must have at least one teacher";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else if(temp2_list.isEmpty())
		{
			ProdekanController.Information ="You must have at least one orientation";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else if(temp3_list.isEmpty())
		{	
			
			ProdekanController.Information ="You must have at least one semester";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else 
		{
			show(event,"/fxml_files/addSubjectScreen.fxml","New Subject");
		}
	}

	public void deleteSubject(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Predmet s");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no subjects!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteSubjectScreen.fxml", "Delete Subject");
		}
		em.close();
		emf.close();
	}

	public void addGroup(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT p FROM Predmet p");
		List<?> predmeti = q.getResultList();

		Query q1 = em.createQuery("SELECT n FROM Nastavnik n");
		List<?> nastavnici = q1.getResultList();

		Query q2 = em.createQuery("SELECT s FROM Student s");
		List<?> studenti = q2.getResultList();

		em.close();
		emf.close();
		
		if(predmeti.isEmpty())
		{	
			ProdekanController.Information ="You must have at least one subject";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else if(nastavnici.isEmpty())
		{
			ProdekanController.Information ="You must have at least one teacher";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else if(studenti.isEmpty())
		{	
			
			ProdekanController.Information ="You must have at least one student";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else 
		{
		show(event, "/fxml_files/addGroupScreen.fxml", "Add Group");
		};
	}

	public void showBuildings(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();
		
		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no buildings!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/showBuildingsScreen.fxml", "Buildings");
		}
		em.close();
		emf.close();
	}

	public void showOrientations(ActionEvent event) throws Exception {

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT u FROM Usmjerenje u");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no orientations!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/showOrientationsScreen.fxml", "Orientations");
		}
		em.close();
		emf.close();
	}

	public void showGroups(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT g FROM GrupaStudenata g");
		temp_list = q.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no groups!";
			show(event,"/fxml_files/Info.fxml","Info");
		} else {
			show(event,"/fxml_files/showGroupsScreen.fxml","Groups");
		}
		em.close();
		emf.close();
	}

	public void deleteGroup(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT g FROM GrupaStudenata g");
		temp_list = q.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no groups!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/deleteGroupsScreen.fxml", "Delete Group");
		}
		em.close();
		emf.close();
	}

	public void showHalls(ActionEvent event) throws Exception {

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Sala s");
		temp_list = q.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no halls!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/showHallsScreen.fxml", "Halls");
		}
		em.close();
		emf.close();
	}

	public void showSubjects(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Predmet s");
		temp_list = q.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "There are no subjects!";
			show(event, "/fxml_files/Info.fxml", "Info");
		} else {
			show(event, "/fxml_files/showSubjectsScreen.fxml", "Subjects");
		}
		em.close();
		emf.close();
	}

	public void createPeriod(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT g FROM GrupaStudenata g");
		temp_list = q.getResultList();

		Query q1 = em.createQuery("SELECT n FROM Sala n");
		List<?> sale = q1.getResultList();

		Query q2 = em.createQuery("SELECT s FROM Semestar s");
		List<?>  semestri= q2.getResultList();

		if (temp_list.isEmpty()) {
			ProdekanController.Information = "You must have at least one group!";
			show(event, "/fxml_files/Info.fxml", "Info");
		}
		else if(sale.isEmpty())
		{
			ProdekanController.Information ="You must have at least one hall";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else if(semestri.isEmpty())
		{	
			ProdekanController.Information ="You must have at least one semester";
			show(event,"/fxml_files/Info.fxml","Error");
		}
		else {
			show(event, "/fxml_files/createPeriodScreen.fxml", "Groups");
		}
		em.close();
		emf.close();
	}

	public void showUsers(ActionEvent event) throws Exception {
		show(event, "/fxml_files/showUsersScreen.fxml", "Users");
	}

	// Funkcija za pokretanje bilo kojeg gui prozora
	private void show(Event event, String resurs, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(resurs));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle(title);
		primaryStage.show();
	}
}
