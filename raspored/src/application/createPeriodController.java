package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entiteti.Grupa;
import entiteti.Semestar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

public class createPeriodController implements Initializable {
	
	@FXML
	private TableView<Grupa> table;
	@FXML
	private TableColumn<Grupa,String> type;
	@FXML
	private TableColumn<Grupa,String> teacher;
	@FXML
	private TableColumn<Grupa,String> subject;
	@FXML
	private TableColumn<Grupa,String> students;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox<String> semester;
	@FXML
	private JFXTextField start;
	@FXML
	private JFXTextField end;
	
	public void createPeriod(ActionEvent event) throws Exception {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp1 = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp1.add((Grupa)e);
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		//subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		table.setItems(temp1);
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Semestar s");
		List<?> temp_list = q.getResultList();

		List<String> temp2 = new ArrayList<String>();
		for (Object e : temp_list)
			temp2.add(((Semestar)e).toString());
		semester.setItems(FXCollections.observableList(temp2));

		em.close();
		emf.close();
	}

}
