package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Korisnik;
import entiteti.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class deleteUserController implements Initializable {

	@FXML
	TableView<Korisnik> usrTable;
	@FXML
	TableColumn<Korisnik, String> name ,lastname;
	@FXML
	CheckBox checkTeacher, checkStudent, checkAll;
	@FXML
	Label errCheck, errTable;
	
	public void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usrTable.setDisable(true);
		usrTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	public void testTeacher(MouseEvent event) throws IOException {
		if (checkTeacher.isSelected()) {
			checkStudent.setDisable(true);
			checkAll.setDisable(true);
			usrTable.setDisable(false);
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
		
			Query q = em.createQuery("SELECT p FROM Korisnik p WHERE p.isProdekan = :a and p.isNastavnik = :b",Korisnik.class);
			q.setParameter("a", false);
			q.setParameter("b", true);
			List<?> temp_list = q.getResultList();
			
			ObservableList<Korisnik> temp1 = FXCollections.observableArrayList();
			for (Object nast : temp_list)
				temp1.add((Korisnik)nast);
			
			name.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("ime"));
			lastname.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("prezime"));
			usrTable.setItems(temp1);
		} else {
			checkStudent.setDisable(false);
			checkAll.setDisable(false);
			usrTable.setDisable(true);
		}
	}
	
	public void testStudent(MouseEvent event) throws IOException {
		if (checkStudent.isSelected()) {
			checkTeacher.setDisable(true);
			checkAll.setDisable(true);
			usrTable.setDisable(false);
			
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
		
			Query q = em.createQuery("SELECT p FROM Korisnik p WHERE p.isProdekan = :a and p.isNastavnik = :b",Korisnik.class);
			q.setParameter("a", false);
			q.setParameter("b", false);
			List<?> temp_list = q.getResultList();
			
			ObservableList<Korisnik> temp1 = FXCollections.observableArrayList();
			for (Object nast : temp_list)
				temp1.add((Korisnik)nast);
			
			name.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("ime"));
			lastname.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("prezime"));
			usrTable.setItems(temp1);
		} else {
			checkTeacher.setDisable(false);
			checkAll.setDisable(false);
			usrTable.setDisable(true);
		}
	}
	
	public void testAll(MouseEvent event) throws IOException {
		if (checkAll.isSelected()) {
			checkTeacher.setDisable(true);
			checkStudent.setDisable(true);
			usrTable.setDisable(false);
			
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
		
			Query q = em.createQuery("SELECT p FROM Korisnik p WHERE p.isProdekan = :a",Korisnik.class);
			q.setParameter("a", false);
			List<?> temp_list = q.getResultList();
			
			ObservableList<Korisnik> temp1 = FXCollections.observableArrayList();
			for (Object nast : temp_list)
				temp1.add((Korisnik)nast);
			
			name.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("ime"));
			lastname.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("prezime"));
			usrTable.setItems(temp1);
		} else {
			checkTeacher.setDisable(false);
			checkStudent.setDisable(false);
			usrTable.setDisable(true);
		}
	}
	public void deleteUser(ActionEvent event) throws Exception {
		if(!checkAll.isSelected() && !checkStudent.isSelected() && !checkTeacher.isSelected())
		{
			errCheck.setText("You need to choose one option.");
		}
		else if(usrTable.getSelectionModel().isEmpty())
		{
			errTable.setText("You need to choose atleast one user.");
		}
		else
		{
			ObservableList<Korisnik> temp = usrTable.getSelectionModel().getSelectedItems();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			for (Korisnik e : temp) {
				Korisnik temp_gr = em.find(Korisnik.class, e.getId());
				if(checkStudent.isSelected())
				{
					Query q = em.createQuery("SELECT p FROM Student p WHERE "
										   + "p.imeStud = :a and p.prezStud = :b",Student.class);
					q.setParameter("a", e.getIme());
					q.setParameter("b", e.getPrezime());
					List<?> temp_list = q.getResultList();
					for(int i = 0; i<temp_list.size(); ++i) 
					{
					em.getTransaction().begin();
					em.remove(temp_list.get(i));
					em.getTransaction().commit();
					}
				}
				else if (checkTeacher.isSelected())
				{
					Query q = em.createQuery("SELECT p FROM Nastavnik p WHERE "
										   + "p.imeNast = :a and p.prezNast = :b",Student.class);
					q.setParameter("a", e.getIme());
					q.setParameter("b", e.getPrezime());
					List<?> temp_list = q.getResultList();
					for(int i = 0; i<temp_list.size(); ++i) 
					{
					em.getTransaction().begin();
					em.remove(temp_list.get(i));
					em.getTransaction().commit();
					}
				}
				else 
				{
					Query q = em.createQuery("SELECT p FROM Student p WHERE "
							   + "p.imeStud = :a and p.prezStud = :b",Student.class);
					q.setParameter("a", e.getIme());
					q.setParameter("b", e.getPrezime());
					List<?> temp_list = q.getResultList();
					if(temp_list.isEmpty()) 
					{	
						Query q1 = em.createQuery("SELECT p FROM Nastavnik p WHERE "
							   + "p.imeNast = :a and p.prezNast = :b",Student.class);
						q1.setParameter("a", e.getIme());
						q1.setParameter("b", e.getPrezime());
						temp_list = q1.getResultList();
					}
					for(int i = 0; i<temp_list.size(); ++i) 
					{
					em.getTransaction().begin();
					em.remove(temp_list.get(i));
					em.getTransaction().commit();
					}
				}
				
				em.getTransaction().begin();
				em.remove(temp_gr);
				em.getTransaction().commit();
			}
			
			String x;
			if (temp.size() == 1)
				x = " user.";
			else
				x = " users.";

			ProdekanController.Information = "You deleted " + temp.size() + x;
			show(event);
			
			em.close();
			emf.close();
		}
	}
}
