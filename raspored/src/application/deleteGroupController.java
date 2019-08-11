package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entiteti.Grupa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class deleteGroupController implements Initializable {
	
	@FXML
	private TableView<Grupa> table;
	@FXML
	private TableColumn<Grupa,String> teacher;
	@FXML
	private TableColumn<Grupa,String> subject;
	@FXML
	private TableColumn<Grupa,String> type;
	@FXML
	private TableColumn<Grupa,String> students;
	
	public void deleteGroup(ActionEvent event) throws Exception {
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Grupa g = table.getSelectionModel().getSelectedItem();
		
		int id = g.getId();
	    Grupa grupa = em.find(Grupa.class, id);
	    if(grupa != null)
	    {
	    	em.getTransaction().begin();
	    	em.remove(grupa);
	    	em.getTransaction().commit();
	    	ProdekanController.Information = "You deleted the group";
	    	Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
			Scene scene = new Scene(root);
			primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.show();
	    }
	    
	    em.close();
	    emf.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Grupa)e);
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		//subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		table.setItems(temp);
	}

}
