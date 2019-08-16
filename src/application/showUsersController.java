package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entiteti.Nastavnik;
import entiteti.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showUsersController implements Initializable {

	@FXML
	TableView<Nastavnik> teachersTable;
	@FXML
	TableView<Student> studTable;
	@FXML
	TableColumn<Nastavnik, String> teachers;
	@FXML
	TableColumn<Student, String> students;
	@FXML 
	JFXTextField searchField;
	@FXML
	ChoiceBox<String> choices;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Nastavnik> temp1 = FXCollections.observableArrayList();
		ObservableList<Student> temp2 = FXCollections.observableArrayList();

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q1 = em.createQuery("SELECT n FROM Nastavnik n", Nastavnik.class);
		@SuppressWarnings("unchecked")
		List<Nastavnik> n = q1.getResultList();

		Query q2 = em.createQuery("SELECT s FROM Student s", Student.class);
		@SuppressWarnings("unchecked")
		List<Student> s = q2.getResultList();

		for (Nastavnik nast : n)
			temp1.add(nast);

		for (Student stud : s)
			temp2.add(stud);

		teachers.setCellValueFactory(new PropertyValueFactory<Nastavnik, String>("ime"));
		students.setCellValueFactory(new PropertyValueFactory<Student, String>("ime"));
		
		FilteredList<Nastavnik> nastavnici = new FilteredList<Nastavnik>(temp1,p->true);
		FilteredList<Student> studenti = new FilteredList<Student>(temp2,p->true);

		teachersTable.setItems(nastavnici);
		studTable.setItems(studenti);
		
		choices.getItems().addAll("Students","Teachers");
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			switch(choices.getValue())
			{
			case "Students":
				studenti.setPredicate(p -> p.getIme().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Teachers":
				nastavnici.setPredicate(p -> p.toString().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
		
		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                searchField.setText("");
                studenti.setPredicate(null);
                nastavnici.setPredicate(null);
            }
        });

		em.close();
		emf.close();
	}

}