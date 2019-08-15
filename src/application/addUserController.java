package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Korisnik;
import entiteti.Nastavnik;
import entiteti.Student;
import entiteti.Usmjerenje;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class addUserController implements Initializable {

	@FXML
	CheckBox checkTeacher, checkStudent;

	@FXML
	TextField name, lastName;

	@FXML
	Label errName, errLastName, errCheck, errList;

	@FXML
	ComboBox<String> list;

	public void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void testTeacher(MouseEvent event) throws IOException {
		list.setValue("");
		if (checkTeacher.isSelected()) {
			checkStudent.setDisable(true);
			list.setDisable(false);
		} else {
			checkStudent.setDisable(false);
			list.setDisable(true);
		}
	}

	public void testStudent(MouseEvent event) throws IOException {
		list.setValue("");
		if (checkStudent.isSelected()) {
			checkTeacher.setDisable(true);
			list.setDisable(false);
		} else {
			checkTeacher.setDisable(false);
			list.setDisable(true);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		list.setDisable(true);
	}

	public void Press(MouseEvent event) throws IOException {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		List<String> temp = new ArrayList<String>();
		if (checkStudent.isSelected()) {

			Query q = em.createQuery("SELECT s FROM Usmjerenje s");
			List<?> temp_list = q.getResultList();

			for (Object e : temp_list)
				temp.add(((Usmjerenje) e).getImeUsmjerenja());

		} else {
			temp.add("Redovni profesor");
			temp.add("Vanredni profesor");
			temp.add("Docent");
			temp.add("Lektor");
			temp.add("Viši asistent");
			temp.add("Asistent");
		}
		list.setItems(FXCollections.observableList(temp));
		em.close();
		emf.close();
	}

	public void addUser(ActionEvent event) throws Exception {
		if (name.getText().isBlank() 
		|| lastName.getText().isBlank() 
		|| list.isDisable()
		|| list.getSelectionModel().isEmpty()) 
		{
			if (name.getText().isBlank())
				errName.setText("You didn't enter the name.");
			else
				errName.setText("");

			if (lastName.getText().isBlank())
				errLastName.setText("You didn't enter the last name");
			else
				errLastName.setText("");

			if (list.isDisable()) 
				errCheck.setText("You didn't selected the student or teacher option");
			else
				errCheck.setText("");
			
			if (list.getSelectionModel().isEmpty() && checkStudent.isSelected()) {
				errList.setText("You didn't selected the orientation for the student ");
			} else if (list.getSelectionModel().isEmpty() && checkTeacher.isSelected()) {
				errList.setText("You didn't selected the rank for the teacher");
			} else {
				errList.setText("");
			}
		} else {
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
			boolean exist = false;
			if (checkStudent.isSelected()) {
				Query q = em.createQuery("SELECT n FROM Student n");
				@SuppressWarnings("unchecked")
				List<Student> studenti = q.getResultList();

				for (Student e : studenti) {
					if (e.getImeStud().toLowerCase().equals(name.getText().toLowerCase())
							&& e.getPrezStud().toLowerCase().equals(lastName.getText().toLowerCase())
							&& e.getUsmjerenje().equals(list.getValue())) {
						exist = true;
						ProdekanController.Information = "The entity already exists in the database";
						show(event);
						break;
					}
				}

				Query q1 = em.createQuery("SELECT n FROM Nastavnik n");
				@SuppressWarnings("unchecked")
				List<Nastavnik> nastavnici = q1.getResultList();

				for (Nastavnik e : nastavnici) {
					if (e.getImeNast().toLowerCase().equals(name.getText().toLowerCase())
							&& e.getPrezNast().toLowerCase().equals(lastName.getText().toLowerCase())) {
						exist = true;
						ProdekanController.Information = "The entity already exists like a teacher";
						show(event);
						break;
					}
				}

				if (!exist) {
					Student novi = new Student();
					novi.setImeStud(name.getText());
					novi.setPrezStud(lastName.getText());
					Query q2 = em.createQuery("SELECT n FROM Usmjerenje n WHERE n.imeUsmjerenja = :a",
							Usmjerenje.class);
					q2.setParameter("a", list.getValue());
					@SuppressWarnings("unchecked")
					List<Usmjerenje> usmjerenje = q2.getResultList();
					novi.setUsmjerenje(usmjerenje.get(0));

					em.getTransaction().begin();
					em.persist(novi);
					em.getTransaction().commit();
				}
			} else {
				if (list.getValue() == "Prodekan") {
					ProdekanController.Information = "Prodekan is only one";
					show(event);
				} else {

					Query q = em.createQuery("SELECT n FROM Nastavnik n");
					@SuppressWarnings("unchecked")
					List<Nastavnik> nastavnici = q.getResultList();

					for (Nastavnik e : nastavnici) {
						if (e.getImeNast().toLowerCase().equals(name.getText().toLowerCase())
								&& e.getPrezNast().toLowerCase().equals(lastName.getText().toLowerCase())) {
							exist = true;
							ProdekanController.Information = "The entity already exists in the database";
							show(event);
							break;
						}
					}

					Query q1 = em.createQuery("SELECT n FROM Student n");
					@SuppressWarnings("unchecked")
					List<Student> studenti = q1.getResultList();

					for (Student e : studenti) {
						if (e.getImeStud().toLowerCase().equals(name.getText().toLowerCase())
								&& e.getPrezStud().toLowerCase().equals(lastName.getText().toLowerCase())) {
							exist = true;
							ProdekanController.Information = "The entity already exists like a student";
							show(event);
							break;
						}
					}

					if (!exist) {
						Nastavnik novi = new Nastavnik();
						novi.setImeNast(name.getText());
						novi.setPrezNast(lastName.getText());
						novi.setTitula(list.getValue());

						em.getTransaction().begin();
						em.persist(novi);
						em.getTransaction().commit();
					}
				}
			}

			if (!exist) {
				Korisnik studkor = new Korisnik();
				studkor.setIme(name.getText());
				studkor.setPrezime(lastName.getText());

				if (checkStudent.isSelected()) {
					studkor.setNastavnik(false);
					studkor.setProdekan(false);
				} else {
					studkor.setNastavnik(true);
					studkor.setProdekan(false);
				}

				studkor.setEmail((name.getText() + "." + lastName.getText() + "@fet.ba").toLowerCase());
				String username = name.getText() + "." + lastName.getText();
				studkor.setUsername(username.toLowerCase());
				if (checkTeacher.isSelected()) {
					studkor.setPassword(name.getText().toLowerCase() + "123");
				}

				em.getTransaction().begin();
				em.persist(studkor);
				em.getTransaction().commit();

				ProdekanController.Information = "User successfully added";
				show(event);
			}
			em.close();
			emf.close();
		}
	}
}
